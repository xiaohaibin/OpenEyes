package com.stx.openeyes.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.library.StatusBarUtil;
import com.stx.openeyes.R;
import com.stx.openeyes.utils.NetConnectedUtils;
import com.stx.openeyes.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频详情activity
 */
public class VideoDetailActivity extends AppCompatActivity {

    @BindView(R.id.video_toolbar_iv_right)
    ImageButton videoToolbarIvRight;
    @BindView(R.id.video_toolbar)
    Toolbar videoToolbar;
    @BindView(R.id.video_detail_iv)
    SimpleDraweeView videoDetailIv;
    @BindView(R.id.video_paly)
    ImageView videoPaly;
    @BindView(R.id.video_detail_ivmo)
    SimpleDraweeView videoDetailIvmo;
    @BindView(R.id.video_detail_title)
    TextView videoDetailTitle;
    @BindView(R.id.video_detail_time)
    TextView videoDetailTime;
    @BindView(R.id.video_detail_desc)
    TextView videoDetailDesc;
    @BindView(R.id.video_detail_iv_fav)
    ImageView videoDetailIvFav;
    @BindView(R.id.video_detail_tv_fav)
    TextView videoDetailTvFav;
    @BindView(R.id.video_detail_iv_share)
    ImageView videoDetailIvShare;
    @BindView(R.id.video_detail_tv_share)
    TextView videoDetailTvShare;
    @BindView(R.id.video_detail_iv_reply)
    ImageView videoDetailIvReply;
    @BindView(R.id.video_detail_tv_reply)
    TextView videoDetailTvReply;
    @BindView(R.id.video_detail_iv_down)
    ImageView videoDetailIvDown;
    @BindView(R.id.video_detail_tv_down)
    TextView videoDetailTvDown;
    private String video;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        StatusBarUtil.setTranslucent(this);
        ButterKnife.bind(this);
        initData();
    }

    //初始化数据
    private void initData() {
        String feed = getIntent().getStringExtra("feed");//背景图片
        title = getIntent().getStringExtra("title");
        String time = getIntent().getStringExtra("time");//时间
        String desc = getIntent().getStringExtra("desc");//视频详情
        String blurred = getIntent().getStringExtra("blurred");//模糊图片
        video = getIntent().getStringExtra("video");//视频播放地址
        int collect = getIntent().getIntExtra("collect", 0);//收藏量
        int share = getIntent().getIntExtra("share", 0);//分享量
        int reply = getIntent().getIntExtra("reply", 0);//回复量
        //给控件设置数据
        videoDetailIv.setImageURI(Uri.parse(feed));
        videoDetailTitle.setText(title);
        videoDetailTime.setText(time);
        videoDetailDesc.setText(desc);
        videoDetailIvmo.setImageURI(Uri.parse(blurred));
        videoDetailTvFav.setText(String.valueOf(collect));
        videoDetailTvShare.setText(String.valueOf(share));
        videoDetailTvReply.setText(String.valueOf(reply));
    }

    //点击事件
    @OnClick(R.id.video_toolbar_iv_right)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.video_paly, R.id.video_detail_iv_fav, R.id.video_detail_iv_share, R.id.video_detail_iv_reply, R.id.video_detail_iv_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_paly://播放
                if (NetConnectedUtils.isNetConnected(this)) {
                    VideoPlayActivity.start(VideoDetailActivity.this,title,video);
                } else {
                    ToastUtil.showToast(this, "网络异常，请稍后再试");
                }
                break;
            case R.id.video_detail_iv_fav://收藏
                break;
            case R.id.video_detail_iv_share://分享
                break;
            case R.id.video_detail_iv_reply://评论
                break;
            case R.id.video_detail_iv_down://下载
                break;
            default:
                break;
        }
    }
}
