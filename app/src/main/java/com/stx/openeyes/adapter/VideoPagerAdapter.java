package com.stx.openeyes.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stx.openeyes.view.fragment.VideoDetailFragment;

import java.util.List;

/**
 * Created by xhb on 2016/3/4.
 * 视频详情适配器
 */
public class VideoPagerAdapter extends FragmentStatePagerAdapter {
    private List<VideoDetailFragment> fragments;

    public VideoPagerAdapter(FragmentManager fm, List<VideoDetailFragment> fragments) {
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
