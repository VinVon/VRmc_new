package com.medvision.vrmc;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.UrlPath.UrlHttp;
import com.medvision.vrmc.activity.AdminInfo;
import com.medvision.vrmc.activity.BaseActivity;
import com.medvision.vrmc.activity.CetificationActivity0;
import com.medvision.vrmc.activity.ModifyPassActivity;
import com.medvision.vrmc.activity.WebViewActivity;
import com.medvision.vrmc.adapter.abs.HeaderBottomAdapter;
import com.medvision.vrmc.bean.CertifiStatus;
import com.medvision.vrmc.bean.CertificationInfo;
import com.medvision.vrmc.bean.CertificationStatus;
import com.medvision.vrmc.bean.LocalInfo;
import com.medvision.vrmc.bean.LoginInfo;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.imp.LoginOutimp;
import com.medvision.vrmc.network.UserService;
import com.medvision.vrmc.presenter.LoginOutPresenter;
import com.medvision.vrmc.update.ApkUtils;
import com.medvision.vrmc.update.SDCardUtils;
import com.medvision.vrmc.update.UpdateStatus;
import com.medvision.vrmc.update.VersionInfo;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.SpUtils;
import com.medvision.vrmc.utils.ToastCommom;
import com.medvision.vrmc.utils.UpdateVersionUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.view.CircleTransform;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.img_more)
    ImageView imgMore;
    @BindView(R.id.main_drawlay)
    DrawerLayout mainDrawlay;
    @BindView(R.id.nav_view)
    NavigationView navView;

    @BindView(R.id.recycle_main)
    RecyclerView recycleMain;
    @BindView(R.id.main_swipe)
    SwipeRefreshLayout mainSwipe;
    private LinearLayoutManager layoutManager;
    private HeaderBottomAdapter adapter;
    private String token;
    private LoginInfo loginInfo;
    private boolean isExit = false;
    TextView name1;
    TextView name2;
    TextView namePhone;
    ImageView imgHead;
    private RelativeLayout relativeLayout;
    private UserService mUsetService;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };
    private View view;

    @Override
    protected void onResume() {
        super.onResume();
        //获取认证状态
        requestStatus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_main_frame, null);
        setContentView(view);
        ButterKnife.bind(this);
        mUsetService = HttpMethods.getInstance().getClassInstance(UserService.class);
//        IntentFilter mFilter = new IntentFilter();
//        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        this.registerReceiver(mReceiver, mFilter);
        navView.setNavigationItemSelectedListener(this);
        navView.setItemIconTintList(null);
        imgMore.setOnClickListener(this);
        initView();
        initData();
        updateVersion();
    }

    private void initData() {
        mUsetService.getNewsInfo(new BaseReq())
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<>(this, o -> {
                    adapter.setNewsInfo(o);
                }));
        if (mainSwipe.isRefreshing()){
            mainSwipe.setRefreshing(false);
        }
    }

    private void requestStatus() {
        MyLog.e("TAG", "获取认证状态");
        mUsetService.requestCertificateStatus(new BaseReq())
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CertificationStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CertificationStatus o) {
                        CertifiStatus userStatus = SpUtils.getInstance().getUserStatus();
                        if (userStatus != null) {
                            userStatus.setStatus(o.getStatus());
                            SpUtils.getInstance().saveUserStatus(userStatus);
                            showStatusDialog(o.getStatus());
                        }
                    }
                });
//                .subscribe(new ProgressSubscriber<CertificationStatus>(this, o -> {
//                    CertifiStatus userStatus = SpUtils.getInstance().getUserStatus();
//                    if (userStatus != null) {
//                        userStatus.setStatus(o.getStatus());
//                        SpUtils.getInstance().saveUserStatus(userStatus);
//                        showStatusDialog(o.getStatus());
//                    }
//
//                }));
    }

    /**
     * 根据认证状态码，显示不同的dialog
     * 1:未认证2:待认证3:认证失败4:认证通过9:停诊
     *
     * @param status
     */
    private SweetAlertDialog sweetAlertDialog;
    private SweetAlertDialog sweetAlertDialog2;
    private SweetAlertDialog sweetAlertDialog3;
    private SweetAlertDialog sweetAlertDialog4;

    private void showStatusDialog(int status) {
        MyLog.e("TAG", "认证状态" + status);
        switch (status) {
            case 1://未认证
                CertifiStatus userStatus = SpUtils.getInstance().getUserStatus();
                userStatus.setIstrue(false);
                SpUtils.getInstance().saveUserStatus(userStatus);
                name2.setText("未认证");
                sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("认证状态").setContentText("未认证")
                        .setCancelText("取消")
                        .setConfirmText("认证")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, CetificationActivity0.class);
                                startActivity(intent);
                            }
                        });
                sweetAlertDialog.show();

                break;
            case 2://待审核
                name2.setText("待审核");
                sweetAlertDialog2 = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("认证状态").setContentText("待审核")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        });
                sweetAlertDialog2.show();
                break;
            case 3://认证失败
                name2.setText("认证失败");
                sweetAlertDialog3 = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("认证状态").setContentText("认证失败")
                        .setCancelText("取消")
                        .setConfirmText("重新认证")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, CetificationActivity0.class);
                                startActivity(intent);
                            }
                        });
                sweetAlertDialog3.show();
                break;
            case 4://认证通过
                CertifiStatus userStatuss = SpUtils.getInstance().getUserStatus();
                requestHeadImage();
                if (userStatuss.istrue()) {
                } else {
                    sweetAlertDialog4 = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("认证状态").setContentText("通过")
                            .setConfirmText("确定")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    if (userStatuss != null) {
                                        userStatuss.setIstrue(true);
                                        SpUtils.getInstance().saveUserStatus(userStatuss);
                                    }
                                }
                            });
                    sweetAlertDialog4.show();
                }
                break;
            case 9://停诊
                break;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismiss();
        }
        if (sweetAlertDialog2 != null && sweetAlertDialog2.isShowing()) {
            sweetAlertDialog2.dismiss();
        }
        if (sweetAlertDialog3 != null && sweetAlertDialog3.isShowing()) {
            sweetAlertDialog3.dismiss();
        }
        if (sweetAlertDialog4 != null && sweetAlertDialog4.isShowing()) {
            sweetAlertDialog4.dismiss();
        }
    }

    private void requestHeadImage() {
        mUsetService.requestCertificateInfo(new BaseReq())
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CertificationInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CertificationInfo o) {
//                    File file = new File(SDCardUtils.getRootDirectory()+"/updateVersion/img");
//                    if (!file.exists()) {
//                        file.mkdirs();
//                    }
//                    long maxSize = Runtime.getRuntime().maxMemory() / 8;//设置图片缓存大小为运行时缓存的八分之一
//                    OkHttpClient client = new OkHttpClient.Builder()
//                            .cache(new Cache(file, maxSize))
//                            .build();
//                    Picasso picasso = new Picasso.Builder(this).downloader(
//                            new OkHttp3Downloader(client)).build();
                        Picasso.with(MainActivity.this).load(o.getHeadPictureUrl()).transform(new CircleTransform()).fit().into(imgHead);
                        name1.setText(o.getRealname());
                        name2.setText(o.getHospital());
                        LoginInfo login = SpUtils.getInstance().getLogin();
                        LoginInfo.DataBean data = login.getData();
                        data.setHospital(o.getHospital());
                        login.setData(data);
                        SpUtils.getInstance().saveLogin(login);
                    }
                });
//                .subscribe(new ProgressSubscriber<CertificationInfo>(this, o -> {
//
//                }));
    }

    private void initView() {
        SpUtils instance = SpUtils.getInstance();
        instance.init(this);
        loginInfo = instance.getLogin();
        relativeLayout = (RelativeLayout) navView.getHeaderView(0);
        name1 = (TextView) relativeLayout.findViewById(R.id.head_name);
        name2 = (TextView) relativeLayout.findViewById(R.id.head_hosname);
        imgHead = (ImageView) relativeLayout.findViewById(R.id.left_icons);
//        namePhone = (TextView) relativeLayout.findViewById(R.id.head_phone);
        name1.setText(loginInfo.getData().getUsername());
        name2.setText(loginInfo.getData().getHospital());
//        namePhone.setText(loginInfo.getData().getRealname());
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleMain.setLayoutManager(layoutManager);
        recycleMain.setAdapter(adapter = new HeaderBottomAdapter(this));
        mainSwipe.setOnRefreshListener(this);
    }

    /**
     * 清理升级文件
     *
     * @param context
     */
    private void clearUpateFile(final Context context) {
        File updateDir;
        File updateFile;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            updateDir = new File(SDCardUtils.getRootDirectory() + "/updateVersion");
        } else {
            updateDir = context.getFilesDir();
        }
        updateFile = new File(updateDir.getPath(), "vrmc_" + ApkUtils.getVersionName(this) + ".apk");

        if (updateFile.exists()) {
            MyLog.e("---------------update", "升级包存在，删除升级包" + updateFile.toString());
            updateFile.delete();
        } else {
            MyLog.e("-----------update", "升级包不存在，不用删除升级包" + updateFile.toString());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastCommom.createInstance().ToastShow(getApplicationContext(), "再按一次退出程序");
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                System.exit(0);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_more:
                MyLog.e("----token", SpUtils.getInstance().getToken());
                mainDrawlay.openDrawer(Gravity.LEFT);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        this.unregisterReceiver(mReceiver);

    }


    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private boolean isNet = false;

//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//                MyLog.d("mark", "网络状态已经改变");
//                connectivityManager = (ConnectivityManager)
//                        getSystemService(Context.CONNECTIVITY_SERVICE);
//                info = connectivityManager.getActiveNetworkInfo();
//                if (info != null && info.isAvailable() && isNet) {
//                    //查询数据库，是否有为下载完的
//                    MyLog.e("--------------mark", "可用网络");
//
//                } else if (info == null || !info.isAvailable()) {
//                    isNet = true;
//                    MyLog.e("--------------mark", "没有可用网络");
//                }
//            }
//        }
//    };

    /**
     * 侧滑菜单
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_info:
                if (SpUtils.getInstance().getUserStatus().getStatus() == 4) {
                    startActivity(new Intent(MainActivity.this, AdminInfo.class));
                } else {
                    ToastUtil.showMessage(this, "未认证成功,无法查看个人信息...");
                }
                break;
            case R.id.nav_camera://修改密码
                startActivity(new Intent(MainActivity.this, ModifyPassActivity.class));
                break;
            case R.id.nav_upversion: //版本升级
                updateVersion();
                break;
            case R.id.nav_about:
                Intent intents = new Intent(MainActivity.this, WebViewActivity.class);
                intents.putExtra("url", "http://mp.weixin.qq.com/s/QLJzUbgHek3ZB0flLqzbAg");
                startActivity(intents);
                break;
            case R.id.nav_exit:
                LoginOutPresenter loginOutPresenter = new LoginOutPresenter(new LoginOutimp() {
                    @Override
                    public void outSuccess(String st) {
                        ToastCommom.createInstance().ToastShow(MainActivity.this, st);
                        SpUtils instance = SpUtils.getInstance();
                        LocalInfo user = instance.getUser();
                        user.setFirstLogin(true);
                        instance.saveUser(user);
                        startActivity(new Intent(MainActivity.this, AuthorizationActivity.class));
                    }

                    @Override
                    public void outFailed(String st) {
                        ToastCommom.createInstance().ToastShow(MainActivity.this, st);
                    }

                    @Override
                    public void tokenchange() {
                    }
                });
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).
                        setTitleText("退出登录")
                        .setCancelText("取消")
                        .setConfirmText("确认")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Map<String, String> outs = new HashMap<>();
                                outs.put("token", token);
                                loginOutPresenter.logout(outs);
                            }
                        }).show();
                break;
            case R.id.nav_kefu:
                //第二个参数是需要申请的权限
//                if (ContextCompat.checkSelfPermission(this,
//                        Manifest.permission.READ_PHONE_STATE)
//                        != PackageManager.PERMISSION_GRANTED)
//                {
//                    ActivityCompat.requestPermissions(this,
//                            new String[]{Manifest.permission.READ_PHONE_STATE},
//                            1);
//                }else {
                //权限已经被授予，在这里直接写要执行的相应方法即可
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("联系客服").setContentText("账号问题请联系客服处理!" + "\n" + "客服电话:4001667866")
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4001667866"));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).show();
//                }
                break;
            case R.id.nav_kangfu:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", UrlHttp.BASE_URL_EVERYTHING + "/h5/help");
                startActivity(intent);
                break;

        }
        return false;
    }

    /**
     * 升级版本
     */
    private void updateVersion() {
        Map<String, String> priArgsss = new HashMap<>();
        priArgsss.put("systemVersion", "2");
        MyLog.e("----version", ApkUtils.getVersionCode(this) + "");
        SpUtils instance = SpUtils.getInstance();
        instance.init(MainActivity.this);
        token = instance.getUser().getToken();
        priArgsss.put("token", token);
        UpdateVersionUtil.checkVersion(priArgsss, MainActivity.this, new UpdateVersionUtil.UpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
                //判断回调过来的版本检测状态

                switch (updateStatus) {
                    case UpdateStatus.YES:
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    1);
                            updateVersion();
                        } else {
                            //弹出更新提示
                            UpdateVersionUtil.showDialog(MainActivity.this, versionInfo);
                            clearUpateFile(MainActivity.this);
                        }

                        break;
                    case UpdateStatus.NO:
                        //没有新版本
                        ToastCommom.createInstance().ToastShow(getApplicationContext(), "已经是最新版本了!");
//                        clearUpateFile(MainActivity.this);
                        break;
                    case UpdateStatus.NOWIFI:
                        //当前是非wifi网络
                        ToastCommom.createInstance().ToastShow(getApplicationContext(), "只有在wifi下更新！");
//							DialogUtils.showDialog(MainActivity.this, "温馨提示","当前非wifi网络,下载会消耗手机流量!", "确定", "取消",new DialogOnClickListenner() {
//								@Override
//								public void btnConfirmClick(Dialog dialog) {
//									dialog.dismiss();
//									//点击确定之后弹出更新对话框
//									UpdateVersionUtil.showDialog(SystemActivity.this,versionInfo);
//								}
//
//								@Override
//								public void btnCancelClick(Dialog dialog) {
//									dialog.dismiss();
//								}
//							});
                        break;
                    case UpdateStatus.ERROR:
                        //检测失败
                        ToastCommom.createInstance().ToastShow(getApplicationContext(), "检测失败，请稍后重试！");
                        break;
                    case UpdateStatus.TIMEOUT:
                        //链接超时
                        ToastCommom.createInstance().ToastShow(getApplicationContext(), "链接超时，请检查网络设置!en");
                        break;
                }
            }
        });
    }


    @Override
    public void onRefresh() {
        initData();
    }
}
