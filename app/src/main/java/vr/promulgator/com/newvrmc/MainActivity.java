package vr.promulgator.com.newvrmc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import vr.promulgator.com.newvrmc.activity.BaseActivity;
import vr.promulgator.com.newvrmc.activity.ModifyPassActivity;
import vr.promulgator.com.newvrmc.activity.PaintActivity;
import vr.promulgator.com.newvrmc.bean.LocalInfo;
import vr.promulgator.com.newvrmc.update.UpdateStatus;
import vr.promulgator.com.newvrmc.utils.ToastCommom;
import vr.promulgator.com.newvrmc.utils.UpdateVersionUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;


import vr.promulgator.com.newvrmc.bean.LoginInfo;
import vr.promulgator.com.newvrmc.imp.LoginOutimp;
import vr.promulgator.com.newvrmc.presenter.LoginOutPresenter;
import vr.promulgator.com.newvrmc.update.ApkUtils;
import vr.promulgator.com.newvrmc.update.SDCardUtils;
import vr.promulgator.com.newvrmc.update.VersionInfo;
import vr.promulgator.com.newvrmc.utils.SpUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.img_more)
    ImageView imgMore;
    @BindView(R.id.main_drawlay)
    DrawerLayout mainDrawlay;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.tab_bar_keyword_et)
    Button tabBarKeywordEt;
    @BindView(R.id.tv_vrromm)
    TextView tvVrromm;
    private String token;
    private LoginInfo loginInfo;
    private boolean isExit = false;
    TextView name1;
    TextView name2;
    private RelativeLayout relativeLayout;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_main_frame, null);
        setContentView(view);
        ButterKnife.bind(this);
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(mReceiver, mFilter);
        tabBarKeywordEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PaintActivity.class));
            }
        });
        navView.setNavigationItemSelectedListener(this);
        navView.setItemIconTintList(null);
        imgMore.setOnClickListener(this);
        initView();
    }

    private void initView() {
        loginInfo = SpUtils.getInstance().getLogin();
        tvVrromm.setText(loginInfo.getData().getVrRoomName());
        relativeLayout = (RelativeLayout) navView.getHeaderView(0);
         name1 = (TextView) relativeLayout.findViewById(R.id.head_name);
         name2 = (TextView) relativeLayout.findViewById(R.id.head_hosname);
        name1.setText(loginInfo.getData().getUsername());
        name2.setText(loginInfo.getData().getVrRoomName());
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
            updateDir = new File(SDCardUtils.getRootDirectory() + "/updateVersion/gdmsaec-app.apk");
        } else {
            updateDir = context.getFilesDir();
        }
        updateFile = new File(updateDir.getPath(), context.getResources()
                .getString(R.string.app_name) + ".apk");
        if (updateFile.exists()) {
            Log.e("---------------update", "升级包存在，删除升级包");
            updateFile.delete();
        } else {
            Log.e("-----------update", "升级包不存在，不用删除升级包");
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
                mainDrawlay.openDrawer(Gravity.LEFT);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mReceiver);

    }

    //根据文件的url来获取数据库保存的名字，路径
    public String getPathName(String url) {
        String names = null;
        try {
            String name = url.substring(0, url.indexOf(".mp") + 4);
            names = name.substring(name.lastIndexOf("/") + 1);
        } catch (Exception e) {
            ToastCommom.createInstance().ToastShow(this, "资源不足");
        }
        return names;
    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private boolean isNet = false;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                Log.d("mark", "网络状态已经改变");
                connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable() && isNet) {
                    //查询数据库，是否有为下载完的
                    Log.e("--------------mark", "可用网络");

                } else if (info == null || !info.isAvailable()) {
                    isNet = true;
                    Log.e("--------------mark", "没有可用网络");
                }
            }
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera://修改密码
                startActivity(new Intent(MainActivity.this, ModifyPassActivity.class));
                break;
            case R.id.nav_upversion: //版本升级
                updateVersion();
                break;
            case R.id.nav_exit:
                LoginOutPresenter loginOutPresenter = new LoginOutPresenter(new LoginOutimp() {
                    @Override
                    public void outSuccess(String st) {
                        ToastCommom.createInstance().ToastShow(MainActivity.this,st);
                        SpUtils instance = SpUtils.getInstance();
                        LocalInfo user = instance.getUser();
                        user.setFirstLogin(true);
                        instance.saveUser(user);
                        startActivity(new Intent(MainActivity.this,AuthorizationActivity.class));
                    }

                    @Override
                    public void outFailed(String st) {
                        ToastCommom.createInstance().ToastShow(MainActivity.this,st);
                    }

                    @Override
                    public void tokenchange() {
                    }
                });
                new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE).
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
                                Map<String ,String> outs = new HashMap<>();
                                outs.put("token",token);
                                loginOutPresenter.logout(outs);
                            }
                        }).show();
                break;
            case R.id.nav_kefu:
                new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("联系客服").setContentText("账号问题请联系客服处理!"+"\n"+"客服电话:4001667866")
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
                break;

        }
        return false;
    }

    private void updateVersion() {
        Map<String, String> priArgsss = new HashMap<>();
        priArgsss.put("systemVersion", ApkUtils.getVersionCode(this) + "");
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
                        //弹出更新提示
                        UpdateVersionUtil.showDialog(MainActivity.this, versionInfo);
                        break;
                    case UpdateStatus.NO:
                        //没有新版本
                        ToastCommom.createInstance().ToastShow(getApplicationContext(), "已经是最新版本了!");
                        clearUpateFile(MainActivity.this);
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


}
