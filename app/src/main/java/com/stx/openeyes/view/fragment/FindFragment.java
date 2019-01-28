package com.stx.openeyes.view.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.stx.openeyes.R;
import com.stx.openeyes.model.FindMoreEntity;
import com.stx.openeyes.utils.CommonAdapter;
import com.stx.openeyes.utils.HttpAdress;
import com.stx.openeyes.utils.JsonParseUtils;
import com.stx.openeyes.utils.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 发现更多
 */
public class FindFragment extends Fragment {


    @BindView(R.id.find_grid)
    GridView findGrid;
    private List<FindMoreEntity> dataEntities=new ArrayList<>();
    private View view;
    public FindFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find, container, false);

        ButterKnife.bind(this, view);
        initData();
        setListener();
        return view;
    }
    //设置事件监听
    private void setListener() {
            findGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
    }

    //初始化数据
    private void initData() {
        OkHttpUtils.get()
                .url(HttpAdress.FIND_MORE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        parseJson(response);
                    }
                });
    }

    //设置适配器
    private void setAdapter(List<FindMoreEntity> dataEntities) {
            findGrid.setAdapter(new CommonAdapter<FindMoreEntity>(getContext(),dataEntities,R.layout.grid_item) {
                @Override
                public void convert(ViewHolder viewHolder, FindMoreEntity dataEntity) {
                    viewHolder.setText(R.id.grid_tv, dataEntity.getName());
                    viewHolder.setImageResourcewithFresco(R.id.grid_iv, Uri.parse(dataEntity.getBgPicture()));
                }
            });
    }
    //解析json数据
    private void parseJson(String jsonData){
        List<FindMoreEntity> entities = JsonParseUtils.parseFromJson(jsonData);
        dataEntities.addAll(entities);
        //给适配器设置数据
        setAdapter(dataEntities);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
