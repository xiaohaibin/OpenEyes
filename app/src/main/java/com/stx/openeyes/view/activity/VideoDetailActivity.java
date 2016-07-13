package com.stx.openeyes.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.openeyes.R;
import com.stx.openeyes.utils.NetConnectedUtils;
import com.stx.openeyes.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频详情activity
 */
public class VideoDetailActivity extends AppCompatActivity {

    @Bind(R.id.video_toolbar_iv_right)
    ImageButton videoToolbarIvRight;
    @Bind(R.id.video_toolbar)
    Toolbar videoToolbar;
    @Bind(R.id.video_detail_iv)
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
    TextView videoDetailTvDown;
    private String video;
    private String title;
    //    @Bind(R.id.video_detail_viewpager)
//    ViewPager videoDetailViewpager;
    //保存Fragemnt集合
//    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
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
                    Intent intent=new Intent(this,showVideoActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("video",video);
                    bundle.putString("title",title);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    ToastUtil.showToast(this,"网络异常，请稍后再试");
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
        }
    }
}
