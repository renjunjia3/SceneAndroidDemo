package com.scene.sceneandroiddemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.scene.mylib.view.tab.APSTSViewPager;
import com.scene.mylib.view.tab.AdvancedPagerSlidingTabStrip;
import com.scene.sceneandroiddemo.ui.fragments.FirstFragment;
import com.scene.sceneandroiddemo.ui.fragments.FourthFragment;
import com.scene.sceneandroiddemo.ui.fragments.SecondFragment;
import com.scene.sceneandroiddemo.ui.fragments.ThirdFragment;

import butterknife.Bind;

/**
 * 主tab
 */
public class MainTabActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    AdvancedPagerSlidingTabStrip mAPSTS;
    @Bind(R.id.vp_main)
    public APSTSViewPager mVP;
    //viewpager标识
    public static final int VIEW_FIRST = 0;
    public static final int VIEW_SECOND = 1;
    public static final int VIEW_THIRD = 2;
    public static final int VIEW_FOURTH = 3;
    //viewpager总页数
    private static final int VIEW_SIZE = 4;
    //fragment实例
    private FirstFragment mFirstFragment = null;
    private SecondFragment mSecondFragment = null;
    private ThirdFragment mThirdFragment = null;
    private FourthFragment mFourthFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initToolBar();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main_tab;
    }

    private void initToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void init() {
        mVP.setOffscreenPageLimit(VIEW_SIZE);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());

        mVP.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        //禁用viewpager滑动
        mVP.setNoFocus(true);
        adapter.notifyDataSetChanged();
        mAPSTS.setViewPager(mVP);
        mAPSTS.setOnPageChangeListener(this);
        mVP.setCurrentItem(VIEW_FIRST);
        //设置圆点显示
        //mAPSTS.showDot(VIEW_FIRST, "99+");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class FragmentAdapter extends FragmentStatePagerAdapter implements AdvancedPagerSlidingTabStrip.IconTabProvider {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        if (null == mFirstFragment)
                            mFirstFragment = FirstFragment.instance();
                        return mFirstFragment;

                    case VIEW_SECOND:
                        if (null == mSecondFragment)
                            mSecondFragment = SecondFragment.instance();
                        return mSecondFragment;

                    case VIEW_THIRD:
                        if (null == mThirdFragment)
                            mThirdFragment = ThirdFragment.instance();
                        return mThirdFragment;

                    case VIEW_FOURTH:
                        if (null == mFourthFragment)
                            mFourthFragment = FourthFragment.instance();
                        return mFourthFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return VIEW_SIZE;
        }

        @Override
        public String getPageIconText(int position) {
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        return "first1";
                    case VIEW_SECOND:
                        return "second2";
                    case VIEW_THIRD:
                        return "third3";
                    case VIEW_FOURTH:
                        return "fourth4";
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public Integer getPageIcon(int index) {
            if (index >= 0 && index < VIEW_SIZE) {
                switch (index) {
                    case VIEW_FIRST:
                        return R.mipmap.home_main_icon_n;
                    case VIEW_SECOND:
                        return R.mipmap.home_categry_icon_n;
                    case VIEW_THIRD:
                        return R.mipmap.home_live_icon_n;
                    case VIEW_FOURTH:
                        return R.mipmap.home_mine_icon_n;
                    default:
                        break;
                }
            }
            return 0;
        }

        @Override
        public Integer getPageSelectIcon(int index) {
            if (index >= 0 && index < VIEW_SIZE) {
                switch (index) {
                    case VIEW_FIRST:
                        toolbar.setTitle("1111");
                        return R.mipmap.home_main_icon_f_n;
                    case VIEW_SECOND:
                        toolbar.setTitle("2222");
                        return R.mipmap.home_categry_icon_f_n;
                    case VIEW_THIRD:
                        toolbar.setTitle("3333");
                        return R.mipmap.home_live_icon_f_n;
                    case VIEW_FOURTH:
                        toolbar.setTitle("4444");
                        return R.mipmap.home_mine_icon_f_n;
                    default:
                        break;
                }
            }
            return 0;
        }
    }
}
