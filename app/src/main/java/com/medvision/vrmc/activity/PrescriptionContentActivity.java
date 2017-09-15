package com.medvision.vrmc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.LoginInfo;
import com.medvision.vrmc.bean.NophonePrescriptionContent;
import com.medvision.vrmc.bean.UserList;
import com.medvision.vrmc.imp.QueryPrescription;
import com.medvision.vrmc.presenter.AddTaskPresenter;
import com.medvision.vrmc.presenter.QueryPrescriptionPresenter;
import com.medvision.vrmc.presenter.UserListPresenter;
import com.medvision.vrmc.utils.MyLog;
import com.medvision.vrmc.utils.SpUtils;



import com.medvision.vrmc.adapter.MyAdapterC;
import com.medvision.vrmc.bean.LocalInfo;
import com.medvision.vrmc.bean.Prescription;
import com.medvision.vrmc.imp.AddTask;
import com.medvision.vrmc.imp.UserListimp;
import com.medvision.vrmc.utils.ToastCommom;
import com.medvision.vrmc.bean.PrescriptionContent;

/**
 * Created by raytine on 2017/4/24.
 */

public class PrescriptionContentActivity extends BaseActivity implements QueryPrescription, MyAdapterC.Callplat, UserListimp, AddTask {
    @BindView(R.id.pc_back)
    ImageView pcBack;
    @BindView(R.id.network_error_layout)
    ViewStub networkErrorLayout;
    //    private PopupWindow window;
    private LoginInfo loginInfo;
    private AddTaskPresenter addp;
    private UserListPresenter ulp;
    private String userid;
    private String username;
    private PrescriptionContent.DataBean id;
    private String token;
    @BindView(R.id.list_pcontent)
    ListView listPcontent;
    private List<PrescriptionContent.DataBean> patient_content = new ArrayList();
    private MyAdapterC mac;
    private QueryPrescriptionPresenter queryPrescriptionPresenter;
    private List<String> titles = new ArrayList<>();
    private View view;
    private String  PrescritionId;
    //    private SpinnerAdapter adapter;
    private LocalInfo users;
    private View networkErrorView;
    private ImageView networkSetting;
    private ImageView error_back;
    private Button refresh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_pc, null);
        setContentView(view);
        ButterKnife.bind(this);
        PrescritionId = getIntent().getStringExtra("id");
        queryPrescriptionPresenter = new QueryPrescriptionPresenter(PrescriptionContentActivity.this);
        ulp = new UserListPresenter(this);
        addp = new AddTaskPresenter(this);
        initData();
        getData();
        getPrescriptionContent();
    }

    private void initData() {
        mac = new MyAdapterC(PrescriptionContentActivity.this, patient_content, R.layout.layout_chufangcontent, this);
        listPcontent.setAdapter(mac);
//        adapter = new SpinnerAdapter();
        pcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //查询内容列表方法
    public void getPrescriptionContent() {
        Map<String, Object> priArgs = new HashMap<>();
        priArgs.put("prescriptionId", PrescritionId);
        priArgs.put("token", token);
//        MyLog.e("0000000", token + "  " + id);
        queryPrescriptionPresenter.setMap(priArgs);
        queryPrescriptionPresenter.getPrescriptionlist();

    }

    @Override
    public void updateView(Prescription user) {

    }

    @Override
    public void update(PrescriptionContent p) {
        if (p.getData() != null) {
            for (int i = 0; i < p.getData().size(); i++) {
                patient_content.add(p.getData().get(i));
            }
            mac.notifyDataSetChanged();
        } else {
            ToastCommom.createInstance().ToastShow(PrescriptionContentActivity.this, "没有数据");
        }
    }

    @Override
    public void update(NophonePrescriptionContent p, int s) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String msg) {
        if (msg == null){
            ToastCommom.createInstance().ToastShow(this,"请检查网络连接...");
            if (networkErrorView == null){
            networkErrorView = networkErrorLayout.inflate();
            networkSetting = (ImageView)networkErrorView.findViewById(R.id.network_setting);
            error_back = (ImageView)networkErrorView.findViewById(R.id.error_back);
            refresh = (Button)findViewById(R.id.network_refresh);}else{
                networkErrorView.setVisibility(View.VISIBLE);
            }
            error_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkErrorView.setVisibility(View.GONE);
                    getPrescriptionContent();
                }
            });
        }
    }

    @Override
    public void tokenchange() {

    }

    //回调listview子view的点击事件，查询内容详情
    @Override
    public void onsuccess(PrescriptionContent.DataBean id) { //弹窗显示设备列表
//        datatimes = id;
//        Map<String, String> priArgs = new HashMap<>();
//        priArgs.put("contentId", datatimes.getContentId());
//        priArgs.put("token", token);
        int i = checkNetWork();
        if (i == 0) {
            ToastCommom.createInstance().ToastShow(PrescriptionContentActivity.this, "请设置网络环境");
        } else {
            if (id.getContent_type() == 1) {
                this.id = id;
                Map<String, String> priArgs = new HashMap<>();
                priArgs.put("vrRoomId", null);
                priArgs.put("token", token);
                ulp.getUserList(priArgs);
            } else {
                ToastCommom.createInstance().ToastShow(PrescriptionContentActivity.this, "暂时不支持视频以外类型文件");
            }
        }
    }

    @Override
    public void addsuccess(String s) {
//        window.dismiss();
        ToastCommom.createInstance().ToastShow(this, s);
    }

    @Override
    public void addfailed(String s) {
        ToastCommom.createInstance().ToastShow(this, s);
    }

    private List<UserList.DataBean> pTitles = new ArrayList<>();

    @Override
    public void success(UserList s) {
        if (pTitles.size() != 0) {
            pTitles.clear();
        }
        if (titles.size() != 0) {
            titles.clear();
        }
        for (int i = 0; i < s.getData().size(); i++) {
            pTitles.add(s.getData().get(i));
            titles.add(s.getData().get(i).getUser_realname());
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                userid = pTitles.get(options1).getUserId();
                username = titles.get(options1);
                new SweetAlertDialog(PrescriptionContentActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("注意事项")
                        .setContentText("请检查" + username + "VR眼镜是否在使用!" + "\n" + "确认播放声识别(反应速度训练)" + "\n" + "到" + username + "VR眼镜!")
                        .setCancelText("否")
                        .setConfirmText("是")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Map<String, Object> adds = new HashMap<>();
                                adds.put("content", id.getContentId());
                                adds.put("token", token);
                                adds.put("type", id.getContent_type());
                                adds.put("userId", userid);
                                adds.put("prescriptionContentId", id.getId());
                                MyLog.e("-----", "type:" + id.getContent_type() + "userId:" + userid + "prescriptionContentId" + id.getId());
                                addp.addTask(adds);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();
            }
        }).setTitleText("选择设备").build();
        pvOptions.setPicker(titles, null, null);
        pvOptions.show();
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void failed(String s) {
        ToastCommom.createInstance().ToastShow(this, s);
    }

    class SpinnerAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int position) {
            return titles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            view = View.inflate(PrescriptionContentActivity.this, R.layout.item, null);
            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText(titles.get(position));
            return view;
        }
    }

    public void getData() {
        SpUtils instance = SpUtils.getInstance();
        instance.init(PrescriptionContentActivity.this);
        users = instance.getUser();
        token = users.getToken();
        loginInfo = instance.getLogin();

    }

    public void saveData(LocalInfo info, LoginInfo infos) {
        SpUtils instance = SpUtils.getInstance();
        instance.init(PrescriptionContentActivity.this);
        instance.saveUser(info);
        instance.saveLogin(infos);
    }
    private void showNetError() {
        // not repeated infalte
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.VISIBLE);
            return;
        }
    }
    private void showNormal() {
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }
    }

}
