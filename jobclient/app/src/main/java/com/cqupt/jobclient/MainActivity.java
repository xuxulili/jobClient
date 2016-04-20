package com.cqupt.jobclient;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cqupt.jobclient.acticity.BaseActivity;
import com.cqupt.jobclient.acticity.FragmentCQUPT;
import com.cqupt.jobclient.acticity.FragmentNY;
import com.cqupt.jobclient.acticity.FragmentSC;
import com.cqupt.jobclient.acticity.FragmentXD;
import com.cqupt.jobclient.acticity.FragmentHK;
import com.cqupt.jobclient.adapter.TabFragmentAdapter;
import com.cqupt.jobclient.utils.NetWorkUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends BaseActivity {
    private Toolbar mainToolbar;
    private FrameLayout mainFrameLayout;
    private DrawerLayout mainDrawerLayout;
    private MaterialDialog noNetWorkDialog;
    private NavigationView mainNavigationView;
    private android.support.v7.app.ActionBarDrawerToggle mActionBarDrawerToggle;
    private TabLayout mainTabLayout;
    private ArrayList<String> mainTabList;
    private ViewPager mainViewpager;
    private ArrayList<Fragment> baseFragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDialog();
        initToolbar();
        intDrawerLayout();
        intTabLayout();
        intViewpager();
    }
//检查网络
    private void initDialog() {
        if (!NetWorkUtil.isNetWorkConnected(this)) {
//            welComeHandler.sendEmptyMessageDelayed(0,1500);
            if (noNetWorkDialog == null) {
                noNetWorkDialog = new MaterialDialog.Builder(MainActivity.this)
                        .title("无网络连接")
                        .content("去开启网络？")
                        .positiveText("是")
                        .negativeText("否")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                Intent intent = new Intent(
                                        Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(intent);
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                finish();
                            }
                        })
                        .cancelable(false)//??仰?????????????????dialog
                        .build();
            }
            if (!noNetWorkDialog.isShowing()) {
                noNetWorkDialog.show();
            }
        }
    }
//    ?????Toolbar
    private void initToolbar() {
        mainFrameLayout = (FrameLayout) findViewById(R.id.main_content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
         /**
         * 设置状态栏颜色之前需要再Style文件中添加以下语句
          * <item name="android:windowTranslucentStatus">true</item>
         <item name="android:windowTranslucentNavigation">true</item>
          */
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintResource(R.color.green);
            tintManager.setStatusBarTintEnabled(true);
            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
            mainFrameLayout.setPadding(0, config.getStatusBarHeight(), 0, config.getPixelInsetBottom());
        }
        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            mainToolbar.setTitle(getString(R.string.title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setLogo(R.drawable.ic_discuss);
            mainToolbar.setNavigationIcon(R.drawable.ic_menu);
//            LinkedList<Integer> linkedList = new LinkedList<>();

        }
    }

//    初始化DrawerLayout
    private void intDrawerLayout(){
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mainNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        if(mainNavigationView!=null){
            intNavigationView();
        }
//        设置NavigationView开闭
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mainDrawerLayout, mainToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mainDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }
//    初始化NavigationView
    private void intNavigationView(){

    }

//    初始化tabLayout
    private void intTabLayout(){
        mainTabLayout = (TabLayout) findViewById(R.id.tabs);
        mainTabList = new ArrayList<>();
        mainTabList.add(getString(R.string.CQUPT));
        mainTabList.add(getString(R.string.SichuanUniversity));
        mainTabList.add(getString(R.string.NanjingUniversityofPostsandTelecommunications));
        mainTabList.add(getString(R.string.XidianUniversity));
        mainTabList.add(getString(R.string.hzkj));

        mainTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mainTabLayout.addTab(mainTabLayout.newTab().setText(mainTabList.get(0)));//添加tab选项卡
        mainTabLayout.addTab(mainTabLayout.newTab().setText(mainTabList.get(1)));
        mainTabLayout.addTab(mainTabLayout.newTab().setText(mainTabList.get(2)));
        mainTabLayout.addTab(mainTabLayout.newTab().setText(mainTabList.get(3)));
        mainTabLayout.addTab(mainTabLayout.newTab().setText(mainTabList.get(4)));
    }
//    初始化viewpager
    private void intViewpager() {
        mainViewpager = (ViewPager) findViewById(R.id.viewpager);
        if(mainViewpager!=null){
            setupViewpager();
        }
    }

    private void setupViewpager() {
        baseFragmentList = new ArrayList<>();
        FragmentCQUPT fragmentCQUPT = new FragmentCQUPT();
        baseFragmentList.add(fragmentCQUPT);
        FragmentSC fragmentSC = new FragmentSC();
        baseFragmentList.add(fragmentSC);
        FragmentNY fragmentNY = new FragmentNY();
        baseFragmentList.add(fragmentNY);
        FragmentXD fragmentXD = new FragmentXD();
        baseFragmentList.add(fragmentXD);
        FragmentHK fragmentHK = new FragmentHK();
        baseFragmentList.add(fragmentHK);
        mainViewpager.setOffscreenPageLimit(5);
        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(),
                baseFragmentList,mainTabList);
        mainViewpager.setAdapter(tabFragmentAdapter);
        mainTabLayout.setupWithViewPager(mainViewpager);//将TabLayout和ViewPager关联起来。
        mainTabLayout.setTabsFromPagerAdapter(tabFragmentAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.drawable.ic_input_delete) {
            mainDrawerLayout.openDrawer(GravityCompat.START);
        }
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
