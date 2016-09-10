package com.stx.openeyes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stx.openeyes.utils.ToastUtil;
import com.stx.openeyes.view.CustomTextView;
import com.stx.openeyes.view.activity.FunctionActivity;
import com.stx.openeyes.view.fragment.DailyFragment;
import com.stx.openeyes.view.fragment.FindFragment;
import com.stx.openeyes.view.fragment.HotFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.main_toolbar)
    Toolbar mainToolbar;
    @Bind(R.id.main_toolbar_tv_time)
    CustomTextView mainToolbarTvTime;
    @Bind(R.id.main_toolbar_iv_right)
    ImageButton mainToolbarIvRight;
    @Bind(R.id.tv_daily)
    TextView tvDaily;
    @Bind(R.id.tv_find)
    TextView tvFind;
    @Bind(R.id.tv_hot)
    TextView tvHot;
    @Bind(R.id.main_menu)
    LinearLayout mainMenu;
    private FragmentTransaction transaction;
    private FindFragment findFragment;
    private HotFragment hotFragment;
    private DailyFragment dailyFragment;
    private FragmentManager fragmentManager;
    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取控件
        ButterKnife.bind(this);
        //获取Fragment的管理器
        fragmentManager = getSupportFragmentManager();
        initView();
        setListener();
    }

    //初始化界面
    private void initView() {
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//设置不显示标题
        mainToolbar.setNavigationIcon(R.drawable.ic_action_menu);
        //设置默认第一个菜单按钮为选中状态
        setChocie(1);
    }


    //设置事件监听
    private void setListener() {

        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * 底部菜单栏的点击切换
     *
     * @param currenItem
     */
    private void setChocie(int currenItem) {

        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        clearChioce();
        switch (currenItem) {
            case 1://每日精选
                mainToolbarTvTime.setVisibility(View.VISIBLE);
                mainToolbarIvRight.setImageResource(R.drawable.main_toolbar_eye_selector);
                tvDaily.setTextColor(getResources().getColor(R.color.colorBlack));
                if (dailyFragment == null) {
                    dailyFragment = new DailyFragment();
                    transaction.add(R.id.main_ll_fragment, dailyFragment);
                } else {
                    transaction.show(dailyFragment);
                }
                break;
            case 2://发现更多
                mainToolbarIvRight.setImageResource(R.drawable.ic_action_search);
                mainToolbarTvTime.setVisibility(View.GONE);
                tvFind.setTextColor(getResources().getColor(R.color.colorBlack));
                if (findFragment == null) {
                    findFragment = new FindFragment();
                    transaction.add(R.id.main_ll_fragment, findFragment);
                } else {
                    transaction.show(findFragment);
                }
                break;
            case 3://热门排行
                mainToolbarIvRight.setImageResource(R.drawable.main_toolbar_eye_selector);
                mainToolbarTvTime.setVisibility(View.GONE);
                tvHot.setTextColor(getResources().getColor(R.color.colorBlack));
                if (hotFragment == null) {
                    hotFragment = new HotFragment();
                    transaction.add(R.id.main_ll_fragment, hotFragment);
                } else {
                    transaction.show(hotFragment);
                }
                break;
        }
        //提交事务
        transaction.commit();
    }

    /**
     * 隐藏所有的Fragment，避免fragment混乱
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (dailyFragment != null) {
            transaction.hide(dailyFragment);
        }
        if (findFragment != null) {
            transaction.hide(findFragment);
        }
        if (hotFragment != null) {
            transaction.hide(hotFragment);
        }

    }

    /**
     * 重置所有选项
     */
    private void clearChioce() {
        //还原默认选项
        tvDaily.setTextColor(getResources().getColor(R.color.colorGray));
        tvFind.setTextColor(getResources().getColor(R.color.colorGray));
        tvHot.setTextColor(getResources().getColor(R.color.colorGray));

    }

    // 按两次退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showToast(MainActivity.this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.tv_daily, R.id.tv_find, R.id.tv_hot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_daily:
                setChocie(1);
                break;
            case R.id.tv_find:
                setChocie(2);
                break;
            case R.id.tv_hot:
                setChocie(3);
                break;
        }
    }
}
