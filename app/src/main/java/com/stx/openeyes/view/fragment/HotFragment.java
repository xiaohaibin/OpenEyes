package com.stx.openeyes.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.stx.openeyes.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 热门排行
 */
public class HotFragment extends Fragment {
    //标题
    private static final String[] TITLE = new String[]{"周排行", "月排行", "总排行"};

    @Bind(R.id.hot_viewpager)
    ViewPager hotViewpager;
    @Bind(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    private List<Fragment> fragments = new ArrayList<>();

    public HotFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        setAdapter();
        return view;
    }


    //初始化控件
    private void initView() {

    }

    //初始化数据
    private void initData() {
        //循环创建三个布局
        for (int i = 0; i < TITLE.length; i++) {
            CommonHotFragment commonHotFragment = new CommonHotFragment();
            fragments.add(commonHotFragment);
        }
        hotViewpager.setOffscreenPageLimit(3);
    }

    //设置适配器
    private void setAdapter() {
        //实例化适配器
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getFragmentManager(), FragmentPagerItems.with(getContext())
                .add(TITLE[0], fragments.get(0).getClass())
                .add(TITLE[1], fragments.get(1).getClass())
                .add(TITLE[2], fragments.get(2).getClass())
                .create());
        //设置适配器
        hotViewpager.setAdapter(adapter);
        viewpagertab.setViewPager(hotViewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
