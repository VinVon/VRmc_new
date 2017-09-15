package com.medvision.vrmc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.CityInfo;
import com.medvision.vrmc.bean.ProfessionalTitle;
import com.medvision.vrmc.bean.requestbody.BaseReq;
import com.medvision.vrmc.bean.requestbody.VerifyInfoReq;
import com.medvision.vrmc.network.UserService;
import com.medvision.vrmc.view.Navigation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by raytine on 2017/8/17.
 */

public class CetificationActivity0 extends AppCompatActivity {
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.radio_sex_1)
    RadioButton radioSex1;
    @BindView(R.id.radio_sex_2)
    RadioButton radioSex2;
    @BindView(R.id.edit_sex)
    RadioGroup editSex;
    @BindView(R.id.edit_city)
    TextView editCity;
    @BindView(R.id.tv_11)
    TextView tv11;
    @BindView(R.id.edit_zhichen)
    Spinner editZhichen;
    @BindView(R.id.tv_zhichen)
    TextView tvZhichen;
    @BindView(R.id.radio_hops_1)
    RadioButton radioHops1;
    @BindView(R.id.radio_hops_2)
    RadioButton radioHops2;
    @BindView(R.id.radio_group_hops)
    RadioGroup radioGroupHops;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.edit_hospital)
    EditText editHospital;
    @BindView(R.id.et_ll_hops)
    LinearLayout etLlHops;
    @BindView(R.id.edit_keshi)
    EditText editKeshi;
    @BindView(R.id.et_ll_keshi)
    LinearLayout etLlKeshi;
    @BindView(R.id.edit_zensuo)
    EditText editZensuo;
    @BindView(R.id.et_ll_zensuo)
    LinearLayout etLlZensuo;
    @BindView(R.id.edit_zhiwei)
    EditText editZhiwei;
    @BindView(R.id.et_ll_zhiwei)
    LinearLayout etLlZhiwei;
    @BindView(R.id.btn_cormif)
    Button btnCormif;
    @BindView(R.id.hospital_ll1)
    LinearLayout hospitalLl1;
    @BindView(R.id.hospital_ll2)
    LinearLayout hospitalLl2;
    private UserService userService;
    private List<CityInfo> citys;
    private List<String> cityNames = new ArrayList<>();
    private List<List<String>> cityFullNames = new ArrayList<>();
    private SpinnerAdapter adapter;
    private int sex = 1;
    private String zhichen;
    private int Htype = 1;
    private String js;
    private String sc;
    private int doctorId; //职称类型id
    private List<ProfessionalTitle> pTitles = new ArrayList<>();
    private boolean oneMore = true;//根据职称id,确定上传照片的id
    //职称集合
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_complete);
        ButterKnife.bind(this);
        Navigation.getInstance(this).setBack().setTitle("基本信息");
        userService = HttpMethods.getInstance().getClassInstance(UserService.class);
        requestProfession();
        initView();
    }

    private void requestProfession() {
        userService.requestTitleInfo(new BaseReq())
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                    pTitles = o;
                    for (int i = 0; i < o.size(); i++) {
                        titles.add(o.get(i).getName());
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    private void initView() {
        radioGroupHops.setOnCheckedChangeListener((group, checkedId) -> {
                    switch (checkedId) {
                        case R.id.radio_hops_1:
                            Htype = 1;
                            hospitalLl1.setVisibility(View.VISIBLE);
                            hospitalLl2.setVisibility(View.GONE);
                            break;
                        case R.id.radio_hops_2:
                            Htype = 2;
                            hospitalLl1.setVisibility(View.GONE);
                            hospitalLl2.setVisibility(View.VISIBLE);
                            break;
                    }
                }
        );
        editSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_sex_1:
                        sex = 1;
                        break;
                    case R.id.radio_sex_2:
                        sex = 2;
                        break;
                }
            }
        });
        adapter = new SpinnerAdapter();
        editZhichen.setAdapter(adapter);
        editZhichen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zhichen = (String) adapter.getItem(position);
                doctorId = pTitles.get(position).getId();
                if (doctorId == 1 || doctorId == 2 || doctorId == 3 || doctorId == 4) {
                    oneMore = true;
                } else if (doctorId == 5 || doctorId == 6) {
                    oneMore = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @OnClick({R.id.btn_cormif, R.id.edit_city})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cormif:
                //检查对象是否有信息丢失或者缺少
                if (checkInfo()) {
                    VerifyInfoReq verifyInfoReq = new VerifyInfoReq();
                    verifyInfoReq.setRealname(realname);
                    verifyInfoReq.setSex(sex);
                    verifyInfoReq.setRegion(city);
                    verifyInfoReq.setProfessionalTitleId(doctorId);
                    verifyInfoReq.setWorkplaceType(Htype);
                    if (Htype == 1) {
                        verifyInfoReq.setHospital(s1);
                        verifyInfoReq.setDepartment(s2);
                    } else {
                        verifyInfoReq.setHospital(s1);
                        verifyInfoReq.setPosition(s2);
                    }
                    Intent intent = new Intent(CetificationActivity0.this, CetificationActivity.class);
                    intent.putExtra("id", doctorId);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CertifiBean", verifyInfoReq);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.edit_city:
                if (cityNames.size() != 0) {
                    pickerEducation();
                } else {
                    requestCity();
                }
                break;
        }
    }

    private String realname;
    private String city = "";
    private String s1;
    private String s2;

    private boolean checkInfo() {
        boolean result = false;
        realname = editName.getText().toString();
        s1 = null;
        s2 = null;
        if (Htype == 1) {
            s1 = editHospital.getText().toString();
            s2 = editKeshi.getText().toString();
        } else {
            s1 = editZensuo.getText().toString();
            s2 = editZhiwei.getText().toString();
        }
        if (realname.isEmpty()) {
            ToastUtil.showMessage(CetificationActivity0.this, "姓名不能为空");
            return result;
        }
        if (city.isEmpty()) {
            ToastUtil.showMessage(CetificationActivity0.this, "城市不能为空");
            return result;
        }
        if (s1.isEmpty()) {
            if (Htype == 1) {
                ToastUtil.showMessage(CetificationActivity0.this, "医院不能为空");
                return result;
            } else {
                ToastUtil.showMessage(CetificationActivity0.this, "诊所不能为空");
                return result;
            }
        }
        if (s2.isEmpty()) {
            if (Htype == 1) {
                ToastUtil.showMessage(CetificationActivity0.this, "科室不能为空");
                return result;
            } else {
                ToastUtil.showMessage(CetificationActivity0.this, "职位不能为空");
                return result;
            }
        }
        result = true;
        return result;
    }

    /**
     * 获取地区列表
     */
    private void requestCity() {
        userService.requestCityInfo(new BaseReq())
                .map(new HttpResultFunc<List<CityInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CityInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<CityInfo> cityInfos) {
                        citys = cityInfos;
                        for (CityInfo cityInfo : cityInfos) {
                            cityNames.add(cityInfo.getName());
                            List<CityInfo.ArrayBean> array = cityInfo.getArray();
                            List<String> fulls = new ArrayList<String>();
                            if (array.size() == 0) {
                                fulls.add(cityInfo.getName());
                            } else {
                                for (CityInfo.ArrayBean arrayBean : array) {
                                    fulls.add(arrayBean.getName());
                                }
                            }
                            cityFullNames.add(fulls);
                        }
                        pickerEducation();
                    }
                });
    }

    /**
     * 城市选择多条目
     */
    private void pickerEducation() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                CityInfo cityInfo = citys.get(options1);
                List<String> strings = cityFullNames.get(options1);
                String s = strings.get(option2);
                if (cityInfo.getName().equals(s)) {
                    s = "";
                }
                city = cityInfo.getArray().get(option2).getCode();
                editCity.setText(cityInfo.getName() + " " + s);
            }
        }).setTitleText("选择城市").build();
        pvOptions.setPicker(cityNames, cityFullNames, null);
        pvOptions.show();
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
            convertView = View.inflate(CetificationActivity0.this, R.layout.items, null);
            TextView text = (TextView) convertView.findViewById(R.id.text);
            text.setText(titles.get(position));
            return convertView;
        }
    }
}
