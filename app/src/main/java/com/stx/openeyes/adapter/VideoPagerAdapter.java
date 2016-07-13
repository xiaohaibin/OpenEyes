package com.stx.openeyes.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stx.openeyes.view.fragment.Video_detail_Fragment;

import java.util.List;

/**
 * Created by xhb on 2016/3/4.
 * 视频详情适配器
 */
public class VideoPagerAdapter extends FragmentStatePagerAdapter {
    private List<Video_detail_Fragment> fragments;

    public VideoPagerAdapter(FragmentManager fm, List<Video_detail_Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
