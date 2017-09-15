package com.medvision.vrmc.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.MainActivity;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.VerifyImg;
import com.medvision.vrmc.bean.requestbody.VerifyInfoReq;
import com.medvision.vrmc.network.UserService;
import com.medvision.vrmc.utils.Constant;
import com.medvision.vrmc.utils.SpUtils;
import com.medvision.vrmc.view.Navigation;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.view.CircleTransform;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 认证界面照片
 * Created by raytine on 2017/8/16.
 */

public class CetificationActivity extends AppCompatActivity {
    @BindView(R.id.certification_head)
    ImageView certificationHead;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.rl_3)
    RelativeLayout rl3;
    @BindView(R.id.cetifi_tv_img1)
    TextView cetifiTvImg1;
    @BindView(R.id.cetifi_tv_img2)
    TextView cetifiTvImg2;
    @BindView(R.id.certification_doc)
    ImageView certificationDoc;
    @BindView(R.id.certification_heart)
    ImageView certificationHeart;
    @BindView(R.id.btn_certifi)
    Button btnCertifi;

    private ArrayList<String> paths = new ArrayList<>();
    private static final int REQUEST_IMAGE = 1;//头像
    private static final int REQUEST_IMAGE2 = 2;//第一张证书
    private static final int REQUEST_IMAGE3 = 3;//第二张证书
    private int docId;
    private VerifyInfoReq req;
    private String img, img2, img3,dsc2,dsc3;
    private UserService userService;
    private String imgType2,imgType3;
    //上传图片返回的id
    private String verifyImgId;
    private String verifyImgId2;
    private String verify1ImgId3;
    File headFile ;
    File file2  ;
    File file3 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cetification_activity);
        ButterKnife.bind(this);
        Navigation.getInstance(this).setBack().setTitle("认证信息");
        userService = HttpMethods.getInstance().getClassInstance(UserService.class);
        docId = getIntent().getIntExtra("id", 0);
        Bundle extras = getIntent().getExtras();
        req = (VerifyInfoReq) extras.getSerializable("CertifiBean");
        if (req == null) {
            ToastUtil.showMessage(this, "信息丢失,请确认信息");
            this.finish();
        }
        if (docId == 5 || docId == 6) {
            imgType2 ="2";
            imgType3= "3";
            dsc2= getString(R.string.certification_heart_certificate);
            dsc3= getString(R.string.certification_job_certifica);
            cetifiTvImg1.setText(dsc2);
            cetifiTvImg2.setText(dsc3);
        } else {
            imgType2 ="4";
            imgType3= "5";
            dsc2= getString(R.string.certification_doc_certifica);
            dsc3= getString(R.string.certification_zhichen_certificate);
            cetifiTvImg1.setText(dsc2);
            cetifiTvImg2.setText(dsc3);
        }
    }

    @OnClick({R.id.certification_head, R.id.certification_doc, R.id.certification_heart,R.id.btn_certifi})
    public void OnClick(View view) {

        switch (view.getId()) {
            case R.id.certification_head:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);

                }else {
                    imgSelect(REQUEST_IMAGE);
                }

                break;
            case R.id.certification_doc:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);

                }else {
                    imgSelect(REQUEST_IMAGE2);
                }

                break;
            case R.id.certification_heart:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);

                }else {
                    imgSelect(REQUEST_IMAGE3);
                }

                break;
            case R.id.btn_certifi:
                if (img == null){
                    ToastUtil.showMessage(this, "请选择头像照片");
                    break;
                }
                if (img2 == null){
                    ToastUtil.showMessage(this, "请选择"+dsc2);
                    break;
                }
                if (img3 == null){
                    ToastUtil.showMessage(this, "请选择"+dsc3);
                    break;
                }
                commitInfo();
                break;
        }
    }


    /**
     * 上传认证信息
     */
    private void commitInfo() {
        String token = SpUtils.getInstance().getToken();
        if (headFile.exists()){
            RequestBody requestBody = RequestBody.create(MultipartBody.FORM, headFile);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.addFormDataPart("fileData", headFile.getName(), requestBody)
                    .addFormDataPart("token",token)
                    .addFormDataPart("fileType", "1")
                    .addFormDataPart("filename", headFile.getName());
            Observable<VerifyImg> map = userService.uploadVerifyImg(builder.build())
                    .map(new HttpResultFunc<>());
            if (file2.exists()){
                RequestBody requestBody1 = RequestBody.create(MultipartBody.FORM, file2);
                MultipartBody.Builder builder1 = new MultipartBody.Builder();
                builder1.addFormDataPart("fileData", file2.getName(), requestBody1)
                        .addFormDataPart("token", token)
                        .addFormDataPart("fileType", imgType2)
                        .addFormDataPart("filename", file2.getName());
                map = map.flatMap(verifyImg -> {
                    //头像ID
                    this.verifyImgId = verifyImg.getImageId();
                    return userService.uploadVerifyImg(builder1.build());
                }).map(new HttpResultFunc<>());

                if (file3.exists()){
                    RequestBody requestBody2 = RequestBody.create(MultipartBody.FORM, file3);
                    MultipartBody.Builder builder2 = new MultipartBody.Builder();
                    builder2.addFormDataPart("fileData", file3.getName(), requestBody2)
                            .addFormDataPart("token", token)
                            .addFormDataPart("fileType", imgType3)
                            .addFormDataPart("filename", file3.getName());
                    map = map.flatMap(verifyImg -> {
                        verifyImgId2 = verifyImg.getImageId();
                        return userService.uploadVerifyImg(builder2.build()).map(new HttpResultFunc<>());
                    });
                    map.flatMap(verifyImg -> {
                        verify1ImgId3 = verifyImg.getImageId();
                        req.setHeadPictureId(verifyImgId);
                        if (docId == 5 || docId == 6) {
                                req.setPsychologicalConsultantImageId(verifyImgId2);
                                req.setEmployeeImageId(verify1ImgId3);

                        } else {
                                req.setDoctorProfessionImageId(verifyImgId2);
                                req.setProfessionalQualificationImageId(verify1ImgId3);

                        }
                        return userService.uploadVerifyInfo(req);
                    })
                            .map(new HttpResultFunc<>())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ProgressSubscriber<>(CetificationActivity.this, noData -> {
                                new SweetAlertDialog(CetificationActivity.this)
                                        .setTitleText("提示")
                                        .setContentText("资料提交成功")
                                        .setConfirmText("确认")
                                        .setConfirmClickListener(sweetAlertDialog -> {
                                            sweetAlertDialog.dismiss();
                                            Intent intent = new Intent(CetificationActivity.this, MainActivity.class);
                                            startActivity(intent);
//                                            finish();
                                        }).show();
                            }));
                }
            }

        }

    }

    private void imgSelect(int requestType) {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // max select image amount
        // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        // default select images (support array list)
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, paths);
        startActivityForResult(intent, requestType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE) {
                // Get the result list of select image paths
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                paths.clear();
                paths.addAll(path);
                img = path.get(0);
                //压缩图片
                onpressPicture(new File(img),REQUEST_IMAGE);
//                Picasso.with(this).load(new File(img)).transform(new CircleTransform()).placeholder(R.mipmap.default_head).fit().into(certificationHead);
            } else if (requestCode == REQUEST_IMAGE2) {
                // Get the result list of select image paths
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                paths.clear();
                paths.addAll(path);
                img2 = path.get(0);
                onpressPicture(new File(img2),REQUEST_IMAGE2);
//                Picasso.with(this).load(new File(img2)).placeholder(R.mipmap.pic).error(R.mipmap.pic).fit().into(certificationDoc);
            } else if (requestCode == REQUEST_IMAGE3) {
                // Get the result list of select image paths
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                paths.clear();
                paths.addAll(path);
                img3 = path.get(0);
                onpressPicture(new File(img3),REQUEST_IMAGE3);
//                Picasso.with(this).load(new File(img3)).placeholder(R.mipmap.pic).error(R.mipmap.pic).fit().into(certificationHeart);

            }
        }
    }

    /**
     *         File file = new File(img);
     File file2 = new File(img2);
     File file3 = new File(img3);
     * 压缩图片
     * @param file
     */
    private void onpressPicture(File file,int code) {
        Luban.with(this)
                .load(file)                     //传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(File file) {
                        if (code == REQUEST_IMAGE) {
                            headFile = file;
                            Picasso.with(CetificationActivity.this).load(file).transform(new CircleTransform()).placeholder(R.mipmap.default_head).fit().into(certificationHead);
                        } else if (code == REQUEST_IMAGE2) {
                            file2 = file;
                            Picasso.with(CetificationActivity.this).load(file).placeholder(R.mipmap.pic).error(R.mipmap.pic).fit().into(certificationDoc);
                        } else if (code == REQUEST_IMAGE3) {
                            file3 = file;
                            Picasso.with(CetificationActivity.this).load(file).placeholder(R.mipmap.pic).error(R.mipmap.pic).fit().into(certificationHeart);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();    //启动压缩
    }

    private int dipXpx(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
