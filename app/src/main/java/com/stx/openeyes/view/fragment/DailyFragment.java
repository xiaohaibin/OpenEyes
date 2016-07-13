package com.stx.openeyes.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.stx.openeyes.R;
import com.stx.openeyes.adapter.MyAdapter;
import com.stx.openeyes.model.HomePicEntity;
import com.stx.openeyes.utils.HttpAdress;
import com.stx.openeyes.view.activity.VideoDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * 每日精选
 */
public class DailyFragment extends Fragment {
    private static final String TAG = "TestActivity";
    @Bind(R.id.lv_home)
    ListView lvHome;
    @Bind(R.id.ptr)
    PtrClassicFrameLayout ptr;


    private String mResult;
    private Gson mGson;
    private Context mContext;
    private View llMoban;
    private View rlText;
    Handler mHandler = new Handler();
    private View mView;
    private String nextUrl;
    private MyAdapter mAdapter;
    private RequestQueue mQueue;
    private boolean isRefresh;
    private boolean isRun;
    private boolean cache;
    private boolean isFirst = true;


    public DailyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_daily, container, false);

        ButterKnife.bind(this, mView);

        findView();

        init();

        setLvAdapter();

        downLoad(HttpAdress.DAILY);

        setListener();

        return mView;
    }


    private void findView() {
        rlText = mView.findViewById(R.id.rl_text);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (lvHome instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) lvHome;
                return absListView.getChildCount() > 0 &&
                        (absListView.getFirstVisiblePosition() > 0 ||
                                absListView.getChildAt(0).getTop() < absListView.getPaddingTop());

            } else {
                return ViewCompat.canScrollVertically(lvHome, -1) || lvHome.getScrollY() > 0;
            }

        } else {

            return ViewCompat.canScrollVertically(lvHome, -1);

        }

    }


    private void setListener() {

        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                return !canChildScrollUp();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRefresh = true;
                downLoad(HttpAdress.DAILY);


            }
        });
        //单个的点击事件
        lvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VideoDetailActivity.class);
                Bundle bundle = new Bundle();
                Log.i("--->", position + "");
                HomePicEntity.IssueListEntity.ItemListEntity.DataEntity data = listAll.get(position).getData();
                if (!"video".equals(listAll.get(position).getType())){
                    return;
                }
                bundle.putString("title", data.getTitle());
                //获取到时间
                int duration = data.getDuration();
                int mm = duration / 60;//分
                int ss = duration % 60;//秒
                String second = "";//秒
                String minute = "";//分
                if (ss < 10) {
                    second = "0" + String.valueOf(ss);
                } else {
                    second = String.valueOf(ss);
                }
                if (mm < 10) {
                    minute = "0" + String.valueOf(mm);
                } else {
                    minute = String.valueOf(mm);//分钟
                }
                bundle.putString("time", "#" + data.getCategory() + " / " + minute + "'" + second + '"');
                bundle.putString("desc", data.getDescription());//视频描述
                bundle.putString("blurred", data.getCover().getBlurred());//模糊图片地址
                bundle.putString("feed", data.getCover().getFeed());//图片地址
                bundle.putString("video", data.getPlayUrl());//视频播放地址
                bundle.putInt("collect", data.getConsumption().getCollectionCount());//收藏量
                bundle.putInt("share", data.getConsumption().getShareCount());//分享量
                bundle.putInt("reply", data.getConsumption().getReplyCount());//回复数量
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lvHome.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (!isRun) {
                        isRun = true;
                        downLoad(nextUrl);
                    }
                }

                View c = lvHome.getChildAt(0);
                if (c == null) {
                    return;
                }
                int firstVisiblePosition = lvHome.getFirstVisiblePosition();
                int top = c.getTop();
                int height = -top + firstVisiblePosition * c.getHeight();

                Log.i("===>" + TAG, "===height===>" + height);


            }
        });


    }

    List<HomePicEntity.IssueListEntity.ItemListEntity> listAll = new ArrayList<>();

    private void downLoad(final String url) {

        final StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mGson = new Gson();
                        HomePicEntity homePicEntity = mGson.fromJson(response, HomePicEntity.class);
                        List<HomePicEntity.IssueListEntity> issueList = homePicEntity.getIssueList();
                        HomePicEntity.IssueListEntity issueListEntity = issueList.get(0);
                        List<HomePicEntity.IssueListEntity.ItemListEntity> itemList = issueListEntity.getItemList();
                        HomePicEntity.IssueListEntity issueListEntity2 = issueList.get(1);
                        List<HomePicEntity.IssueListEntity.ItemListEntity> itemList1 = issueListEntity2.getItemList();
                        Log.i("===>" + TAG, "===完成===>");

                        isRun = false;

                        //刷新需要清除数据
                        if (isRefresh) {
                            listAll.removeAll(listAll);
                            ptr.refreshComplete();
                            isRefresh = false;
                        }

                        listAll.addAll(itemList);
                        listAll.addAll(itemList1);

                        nextUrl = homePicEntity.getNextPageUrl();

                        myNotify();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("===>" + TAG, "===w失败===>");
                        isRun = false;
                        if (isRefresh) {
                            ptr.refreshComplete();

                        }


                    }
                }
        );


        mQueue.add(request);
        mQueue.start();


    }


    public void myNotify() {

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();

    }


    private void setLvAdapter() {

        mAdapter = new MyAdapter(mContext, listAll);
        lvHome.setAdapter(mAdapter);

    }

    private void init() {
        mContext = getContext();
        mQueue = Volley.newRequestQueue(mContext);
    }


}
