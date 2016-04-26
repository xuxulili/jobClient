package com.cqupt.jobclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cqupt.jobclient.acticity.BaseActivity;
import com.cqupt.jobclient.acticity.FragmentCQUPT;
import com.cqupt.jobclient.acticity.FragmentNY;
import com.cqupt.jobclient.acticity.FragmentSC;
import com.cqupt.jobclient.acticity.FragmentXD;
import com.cqupt.jobclient.acticity.FragmentHK;
import com.cqupt.jobclient.acticity.OnLoadingListener;
import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.adapter.TabFragmentAdapter;
import com.cqupt.jobclient.model.NewsPicture;
import com.cqupt.jobclient.utils.GetData;
import com.cqupt.jobclient.utils.NetWorkUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements OnLoadingListener {
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
    private CircleImageView circleImageView;
    private ImageView imageView;
    private ArrayList<NewsPicture> newsPictureArrayList;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private String url_picture;
    private MaterialDialog noNetWorkDialog1;
    private MaterialDialog noNetWorkDialog2;
    private MaterialDialog noNetWorkDialog3;
    private boolean isLoading;
    private boolean hasLoadPicture;
    private Timer mTimer = new Timer();
    private int pagePicture;

    @Override
    public void onLoad(ArrayList<NewsPicture> newsPictureArrayList) {
        if (newsPictureArrayList != null && newsPictureArrayList.size() > 0) {
            pagePicture = 0;
            this.newsPictureArrayList = newsPictureArrayList;
        } else {
            Log.e("获取得到图片数据", "无");
        }


    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (newsPictureArrayList != null && newsPictureArrayList.size() > 0) {
//                Log.e("获取得到图片数据", newsPictureArrayList.get(pagePicture).getImageUrl());
                handler_refresh_delay.sendEmptyMessage(0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initDialog();
        initToolbar();
        intDrawerLayout();
        intTabLayout();
        intViewpager();

        mTimer.schedule(timerTask, 8000, 8000);
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
    private void intDrawerLayout() {
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mainNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        if (mainNavigationView != null) {
            intNavigationView();
        }
//        设置NavigationView开闭
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mainDrawerLayout, mainToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mainDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        setupNavigationView(mainNavigationView);
    }

    //    初始化NavigationView
    private void intNavigationView() {
        imageView = (ImageView) findViewById(R.id.na_img);
        imageView.setImageResource(R.drawable.cy);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .build();
//        initDataHandler.sendEmptyMessageDelayed(0, 4000);

    }

    private void initPictureData() {
        if (!isLoading) {
            Log.e("无使用网络", "");
            CYPictureHandler.sendEmptyMessage(0);
        } else {
            while (!hasLoadPicture) {
                Log.e("网络使用等待", "");
                initDataHandler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    }

    //    初始化tabLayout
    private void intTabLayout() {
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

    //响应导航页事件
    private void setupNavigationView(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mainDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.job_experience:
                                if (noNetWorkDialog2 == null) {
                                    noNetWorkDialog2 = new MaterialDialog.Builder(MainActivity.this)
                                            .negativeText("返回")
                                            .positiveText("查看原网页")
                                            .content("开发中，敬请期待！")
                                            .callback(new MaterialDialog.ButtonCallback() {
                                                @Override
                                                public void onPositive(MaterialDialog dialog) {
                                                    if (NetWorkUtil.isNetWorkConnected(MainActivity.this)) {
                                                        noNetWorkDialog2.dismiss();
////                                                        http://job.cqupt.edu.cn/#job:2
                                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.itmian4.com/forum-45-1.html"));
//                                                        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("url", "http://www.itmian4.com/forum-45-1.html");
                                                        bundle.putString("getTitle", "面经");
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                        //必须在startactity之后调用，第一个为打开activity的动画，第二个为退出所打开activity的动画
                                                        overridePendingTransition(R.anim.base_slide_right_in, 0);
                                                    } else {
                                                        Toast.makeText(MainActivity.this, "请连接网络", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onNegative(MaterialDialog dialog) {
                                                    noNetWorkDialog2.dismiss();
                                                }
                                            })
                                            .cancelable(true)
                                            .build();
                                }
                                if (!noNetWorkDialog2.isShowing()) {
                                    noNetWorkDialog2.show();
                                }
                                menuItem.setChecked(false);
                                break;
                            case R.id.about:
                                if (noNetWorkDialog1 == null) {
                                    noNetWorkDialog1 = new MaterialDialog.Builder(MainActivity.this)
                                            .title("重邮求职助手")
                                            .content("掌握实时动态，圆梦美好未来!\n反馈地址:jobxwj@foxmail.com,谢谢!\n开发者:xuxu")
                                            .negativeText("返回")
                                            .callback(new MaterialDialog.ButtonCallback() {
                                                @Override
                                                public void onNegative(MaterialDialog dialog) {
                                                    dialog.dismiss();
                                                    super.onNegative(dialog);
                                                }
                                            })
                                            .cancelable(true).build();
                                }
                                if (!noNetWorkDialog1.isShowing()) {
                                    noNetWorkDialog1.show();
                                }
                                menuItem.setChecked(false);
                                break;
                            case R.id.useBook:
                                if (noNetWorkDialog3 == null) {
                                    noNetWorkDialog3 = new MaterialDialog.Builder(MainActivity.this)
                                            .title("应用手册")
                                            .positiveText("返回")
                                            .content("1、新闻内容丰富多样，如果您喜欢哪篇文章，可以点击" +
                                                            "右上角分享按钮，把文章保存至本地记事本QQ微信等等，方便电脑端阅读；也可以点击查看原文查看原网页；" + "\n"
                                                            + "2、点击右下方的按钮，您可以查看本地天气，也可在上方编辑框输入城市名，" +
                                                            "查询其他城市天气，这时您可以点击分享按钮，会自动为您截取当前屏幕，随意分享图片；" + "\n"
                                                            + "3、重邮主页不稳定，有时无法获取数据，不过应用能自动重新获取，" +
                                                            "请耐心等待，肯定能够获取相关新闻；" + "\n" + "4、点击顶部的中间位置，IT前沿可以自动返回顶部。"
                                            )
                                            .callback(new MaterialDialog.ButtonCallback() {
                                                @Override
                                                public void onPositive(MaterialDialog dialog) {
                                                    noNetWorkDialog3.dismiss();
                                                }

                                                @Override
                                                public void onNegative(MaterialDialog dialog) {
                                                }
                                            })
                                            .cancelable(true)
                                            .build();
                                }
                                if (!noNetWorkDialog3.isShowing()) {
                                    noNetWorkDialog3.show();
                                }
                                menuItem.setChecked(false);
                                break;
                            case R.id.clear:
                                GetData.clearData(app.getContext());
                                Snackbar.make(navigationView, "清理完成！", Snackbar.LENGTH_SHORT).show();
//                                Toast.makeText(app.getContext(), "清理完成！", Toast.LENGTH_SHORT).show();

                                break;

                            case R.id.exit:
                                finish();
                                break;
                        }
                        return true;
                    }
                });
    }

    //    初始化viewpager
    private void intViewpager() {
        mainViewpager = (ViewPager) findViewById(R.id.viewpager);
        if (mainViewpager != null) {
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
                baseFragmentList, mainTabList);
        mainViewpager.setAdapter(tabFragmentAdapter);
        mainTabLayout.setupWithViewPager(mainViewpager);//将TabLayout和ViewPager关联起来。
        mainTabLayout.setTabsFromPagerAdapter(tabFragmentAdapter);
    }

    private android.os.Handler initDataHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            initPictureData();
        }
    };
    private android.os.Handler CYPictureHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            Thread thread_cyPicture = new Thread(new Runnable() {
                @Override
                public void run() {
//                    Log.e("开始获取图片数据", "1");
                    newsPictureArrayList = GetData.getNewsPicture();
                    if (newsPictureArrayList != null) {
                        hasLoadPicture = true;
                        handler_refresh.sendEmptyMessage(0);
                    }

                }
            });
            thread_cyPicture.start();
        }
    };

    private android.os.Handler handler_refresh = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (newsPictureArrayList != null) {
                for (int i = 0; i < newsPictureArrayList.size(); i++) {
                    url_picture = newsPictureArrayList.get(i).getImageUrl();
                    handler_refresh_delay.sendEmptyMessageDelayed(0, 1000);
                    if (i == newsPictureArrayList.size() - 1) {
                        i = 0;
                    }
                }
            }
        }
    };
    private android.os.Handler handler_refresh_delay = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            imageLoader.displayImage(newsPictureArrayList.get(pagePicture).getImageUrl(), imageView, options);
            pagePicture++;
            if (pagePicture == newsPictureArrayList.size()) {
                pagePicture = 0;
            }

        }
    };

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

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("退出"); //设置标题
        builder.setMessage("是否确认退出?"); //设置内容
//        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if ((System.currentTimeMillis() - exitTime) > 2000) {
//                        exitTime = System.currentTimeMillis();//返回当前时间至毫秒
//                    } else {
                    dialog.dismiss();
                    moveTaskToBack(false);
//                finish();
//                    }

                }
                return true;
            }
        });
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                finish();
//                Toast.makeText(app.getContext(), "确认" + which, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                Toast.makeText(app.getContext(), "取消" + which, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("后台运行", new DialogInterface.OnClickListener() {//设置忽略按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                moveTaskToBack(false);//后台运行实现
//                Toast.makeText(app.getContext(), "后台运行" + which, Toast.LENGTH_SHORT).show();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showExitDialog();
                Toast.makeText(app.getContext(), "再按一次后台运行！", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}
