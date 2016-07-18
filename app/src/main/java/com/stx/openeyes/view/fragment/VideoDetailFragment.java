package com.stx.openeyes.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stx.openeyes.R;

import butterknife.ButterKnife;

/**
 * 视频详情
 */
public class VideoDetailFragment extends Fragment {

  /*  @Bind(R.id.video_detail_iv)
    SimpleDraweeView videoDetailIv;
    @Bind(R.id.video_paly)
    ImageView videoPaly;
    @Bind(R.id.video_detail_ivmo)
    SimpleDraweeView videoDetailIvmo;
    @Bind(R.id.video_detail_title)
    TextView videoDetailTitle;
    @Bind(R.id.video_detail_time)
    TextView videoDetailTime;
    @Bind(R.id.video_detail_desc)
    TextView videoDetailDesc;
    @Bind(R.id.video_detail_iv_fav)
    ImageView videoDetailIvFav;
    @Bind(R.id.video_detail_tv_fav)
    TextView videoDetailTvFav;
    @Bind(R.id.video_detail_iv_share)
    ImageView videoDetailIvShare;
    @Bind(R.id.video_detail_tv_share)
    TextView videoDetailTvShare;
    @Bind(R.id.video_detail_iv_reply)
    ImageView videoDetailIvReply;
    @Bind(R.id.video_detail_tv_reply)
    TextView videoDetailTvReply;
    @Bind(R.id.video_detail_iv_down)
    ImageView videoDetailIvDown;
    @Bind(R.id.video_detail_tv_down)
    TextView videoDetailTvDown;*/

    public VideoDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail, container, false);
        //获取数据
        ButterKnife.bind(this, view);
        initData();
        return view;
    }
    //初始化数据
    private void initData() {

    }
   /* //事件监听
    @OnClick({R.id.video_paly, R.id.video_detail_iv_fav, R.id.video_detail_iv_share, R.id.video_detail_iv_reply, R.id.video_detail_iv_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_paly://视频播放
                break;
            case R.id.video_detail_iv_fav://收藏
                break;
            case R.id.video_detail_iv_share://分享
                break;
            case R.id.video_detail_iv_reply://评论
                break;
            case R.id.video_detail_iv_down://缓存
                break;
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
