package com.stx.openeyes.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.openeyes.R;
import com.stx.openeyes.model.FindDetailEntity;
import com.stx.openeyes.utils.CommonAdapter;
import com.stx.openeyes.utils.HttpAdress;
import com.stx.openeyes.utils.ViewHolder;
import com.stx.openeyes.view.activity.VideoDetailActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 发现更多 通用fragment
 */
public class CommonFindFragment extends Fragment {

    @BindView(R.id.find_listview)
    ListView findListview;
    private List<FindDetailEntity.ItemListEntity> itemListEntities = new ArrayList<>();
    private static final String[] RANK = new String[]{"date", "shareCount"};
    private boolean isLoad = false;//判断是否在加载数据
    private String nextPageUrl;//下一页数据请求地址
    private CommonAdapter<FindDetailEntity.ItemListEntity> adapter;
    private View footview;

    public CommonFindFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_common_find, container, false);
        ButterKnife.bind(this, inflate);
        initData();
        initview();
        setListener();
        return inflate;
    }

    //初始化控件
    private void initview() {
        //添加底部布局
        footview = LayoutInflater.from(getContext()).inflate(R.layout.list_footer, null);
    }

    //初始化数据
    private void initData() {
        String name = getArguments().getString("name");//分类名称
        int position = getArguments().getInt("position");
        Log.i("====rank", position + "-----" + RANK[position]);
        String rank = RANK[position];
        String encode = URLEncoder.encode(name);//必须将中文进行URL编码才能加到接口中
        String url = String.format(HttpAdress.FIND_DETAIL, encode, rank);//请求地址
        downloadData(url);
    }

    /**
     * 下载网络数据
     * @param url 网络请求地址
     */
    private void downloadData(String url) {
        OkHttpUtils.get().url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("===>response", response);
                        parseJson(response);
                    }
                });
    }

    //设置适配器
    private void setAdapter(List<FindDetailEntity.ItemListEntity> data) {
        //实例化适配器
        adapter = new CommonAdapter<FindDetailEntity.ItemListEntity>(getContext(), data, R.layout.list_home_vedio_item) {
            @Override
            public void convert(ViewHolder viewHolder, FindDetailEntity.ItemListEntity itemListEntity) {
                viewHolder.setText(R.id.tv_title, itemListEntity.getData().getTitle());
                //获取时间
                int duration = itemListEntity.getData().getDuration();
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
                viewHolder.setText(R.id.tv_time, "#" + itemListEntity.getData().getCategory() + " / " + minute + "'" + second + '"');
                ImageView imageView = viewHolder.getView(R.id.iv);
                Glide.with(mContext).load(itemListEntity.getData().getCover().getFeed()).into(imageView);
            }
        };
        findListview.setAdapter(adapter);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    //解析数据
    private void parseJson(String response) {
        FindDetailEntity entity = new Gson().fromJson(response, FindDetailEntity.class);
        itemListEntities.addAll(entity.getItemList());
        isLoad = false;//数据下载完之后设置为false
        nextPageUrl = entity.getNextPageUrl();
        //如果下一页数据的请求地址为null，则加载底部布局
        if (nextPageUrl == null) {
            findListview.addFooterView(footview, null, false);
        }
        //设置适配器
        setAdapter(itemListEntities);
    }

    //设置事件监听
    private void setListener() {
        //listview的滑动监听
        findListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (!isLoad) {
                        downloadData(nextPageUrl);
                        isLoad = true;
                    }
                }
            }
        });
        //listview的点击事件监听
        findListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VideoDetailActivity.class);
                Bundle bundle = new Bundle();
                Log.i("--->", position + "");
                FindDetailEntity.ItemListEntity.DataEntity data = itemListEntities.get(position).getData();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
