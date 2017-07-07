package vr.promulgator.com.newvrmc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import vr.promulgator.com.newvrmc.R;
import vr.promulgator.com.newvrmc.activity.PrescriptionContentActivity;
import vr.promulgator.com.newvrmc.adapter.MyadapterNo;
import vr.promulgator.com.newvrmc.bean.NophonePrescriptionContent;
import vr.promulgator.com.newvrmc.bean.Prescription;
import vr.promulgator.com.newvrmc.bean.PrescriptionContent;
import vr.promulgator.com.newvrmc.bean.UserList;
import vr.promulgator.com.newvrmc.imp.AddTask;
import vr.promulgator.com.newvrmc.imp.QueryPrescription;
import vr.promulgator.com.newvrmc.imp.UserListimp;
import vr.promulgator.com.newvrmc.presenter.AddTaskPresenter;
import vr.promulgator.com.newvrmc.presenter.QueryPrescriptionPresenter;
import vr.promulgator.com.newvrmc.presenter.UserListPresenter;
import vr.promulgator.com.newvrmc.utils.PullToRefreshSwipeLayout;
import vr.promulgator.com.newvrmc.utils.SpUtils;
import vr.promulgator.com.newvrmc.utils.ToastCommom;

/**
 * Created by raytine on 2017/7/4.
 */

public class ContentFragment extends BaseFragment implements QueryPrescription, MyadapterNo.Callplat, UserListimp, AddTask, SwipeRefreshLayout.OnRefreshListener,PullToRefreshSwipeLayout.OnPullUpListener {
    private static ContentFragment fragment;
    @BindView(R.id.listview)
    ListView listview;
    Unbinder unbinder;
    @BindView(R.id.swipefresh)
    PullToRefreshSwipeLayout swipefresh;

    private MyadapterNo mac;
    private String userId;
    private List<String> titles = new ArrayList<>();
    private String token;
    private AddTaskPresenter addp;
    private String userid;
    private String username;
    private List<NophonePrescriptionContent.DataBean> patient_content = new ArrayList();
    private QueryPrescriptionPresenter queryPrescriptionPresenter;
    private UserListPresenter ulp;
    private NophonePrescriptionContent.DataBean id;
    private int paging = 1;

    public static ContentFragment newInstance() {
        if (fragment == null) {
            return new ContentFragment();
        }
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        unbinder = ButterKnife.bind(this, view);
        queryPrescriptionPresenter = new QueryPrescriptionPresenter(this);
        ulp = new UserListPresenter(this);
        addp = new AddTaskPresenter(this);
        Bundle arguments = getArguments();
        userId = arguments.getString("id");
        token = SpUtils.getInstance().getToken();
        paging = 1;
        initView();
        if (userId != null) {
            Map<String, Object> priArgs = new HashMap<>();
            priArgs.put("userId", userId);
            priArgs.put("paging", paging);
            Log.e("--bingan", userId);
            priArgs.put("token", token);
            Log.e("0000000", token + "  " + id);
            queryPrescriptionPresenter.setMap(priArgs);
            queryPrescriptionPresenter.getPrescriptionContentNophonelist(1);
        }
        return view;
    }

    private void initView() {
        mac = new MyadapterNo(getActivity(), patient_content, R.layout.layout_chufangcontent, this);
        listview.setAdapter(mac);
        swipefresh.setOnRefreshListener(this);
        swipefresh.setOnPullUpListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void tokenchange() {

    }

    @Override
    public void updateView(Prescription user) {

    }

    @Override
    public void update(PrescriptionContent p) {

    }

    @Override
    public void update(NophonePrescriptionContent p,int status) {
        if (status ==1){ //刷新或者请求数据
            if (patient_content.size() != 0) {
                patient_content.clear();
            }
            if (p.getData() != null) {
                for (int i = 0; i < p.getData().size(); i++) {
                    patient_content.add(p.getData().get(i));
                }
                mac.notifyDataSetChanged();
            } else {
                ToastCommom.createInstance().ToastShow(getActivity(), "没有数据");
            }
        }else if (status ==2){//加载更多数据
            if (p.getData() != null && p.getData().size() !=0) {
                for (int i = 0; i < p.getData().size(); i++) {
                    patient_content.add(p.getData().get(i));
                }
                mac.notifyDataSetChanged();
                swipefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipefresh!= null)
                        swipefresh.setViewStatus(PullToRefreshSwipeLayout.NONE);
                    }
                },2000);

            } else {
                paging -=1;
                swipefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipefresh!= null)
                        swipefresh.setViewStatus(PullToRefreshSwipeLayout.NONE);
                    }
                },2000);


            }

        }else{
            if (patient_content.size() != 0) {
                patient_content.clear();
            }
            if (p.getData() != null) {
                for (int i = 0; i < p.getData().size(); i++) {
                    patient_content.add(p.getData().get(i));
                }
                swipefresh.setRefreshing(false);
                mac.notifyDataSetChanged();
                ToastCommom.createInstance().ToastShow(getActivity(), "数据更新成功....");
            } else {
                ToastCommom.createInstance().ToastShow(getActivity(), "没有数据");
            }
        }
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onsuccess(NophonePrescriptionContent.DataBean id) {
        //        datatimes = id;
//        Map<String, String> priArgs = new HashMap<>();
//        priArgs.put("contentId", datatimes.getContentId());
//        priArgs.put("token", token);
        int i = checkNetWork();
    ;
        if (i == 0) {
            ToastCommom.createInstance().ToastShow(getActivity(), "请设置网络环境");
        }else {
            if (id.getContent_type() == 1) {
                this.id = id;
                Map<String, String> priArgs = new HashMap<>();
                priArgs.put("vrRoomId", SpUtils.getInstance().getLogin().getData().getVrRoomId());
                priArgs.put("token", token);
                ulp.getUserList(priArgs);
            } else {
                ToastCommom.createInstance().ToastShow(getActivity(), "暂时不支持视频以外类型文件");
            }
        }
    }

    @Override
    public void addsuccess(String s) {
        ToastCommom.createInstance().ToastShow(getActivity(), s);
    }

    @Override
    public void addfailed(String s) {
        ToastCommom.createInstance().ToastShow(getActivity(), s);
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
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                userid = pTitles.get(options1).getUserId();
                username = titles.get(options1);
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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
                                Log.e("-----", "type:" + id.getContent_type() + "userId:" + userid + "prescriptionContentId" + id.getId());
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
    }

    @Override
    public void failed(String s) {

    }

    @Override
    public void onRefresh() {

        paging = 1;
        fresh();
    }

    private void fresh() {
        Map<String, Object> priArgs = new HashMap<>();
        priArgs.put("userId", userId);
        priArgs.put("paging", paging);
        Log.e("--bingan", userId);
        priArgs.put("token", token);
        Log.e("0000000", token + "  " + id);
        queryPrescriptionPresenter.setMap(priArgs);
        queryPrescriptionPresenter.getPrescriptionContentNophonelist(3);
    }

    @Override
    public void onPullUp() {
          if (patient_content.size() >=10){
              paging+=1;
              swipefresh.setViewStatus(PullToRefreshSwipeLayout.LOADING);
              loadMore();
          }
    }

    private void loadMore() {
        Map<String, Object> priArgs = new HashMap<>();
        priArgs.put("userId", userId);
        priArgs.put("paging", paging);
        priArgs.put("token", token);
        Log.e("0000000", token + "  " + paging);
        queryPrescriptionPresenter.setMap(priArgs);
        queryPrescriptionPresenter.getPrescriptionContentNophonelist(2);
    }


}
