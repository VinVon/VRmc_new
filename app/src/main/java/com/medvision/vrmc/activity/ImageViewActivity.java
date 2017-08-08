package com.medvision.vrmc.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.entity.NoData;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.medvision.vrmc.R;
import com.medvision.vrmc.bean.Buser;
import com.medvision.vrmc.bean.SingerContentInfo;
import com.medvision.vrmc.bean.requestbody.CollectContentReq;
import com.medvision.vrmc.network.ContentService;
import com.medvision.vrmc.utils.Constant;
import com.medvision.vrmc.view.Navigation;
import com.squareup.picasso.Picasso;
import com.wzgiceman.rxbuslibrary.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 内容type 为图片的activity
 * Created by raytine on 2017/5/18.
 */

public class ImageViewActivity extends AppCompatActivity {


    @BindView(R.id.vr_video_title_tv)
    TextView vrVideoTitleTv;
    @BindView(R.id.vr_video_play_times_tv)
    TextView vrVideoPlayTimesTv;
    @BindView(R.id.vr_video_date_tv)
    TextView vrVideoDateTv;
    @BindView(R.id.vr_video_name_tv)
    TextView vrVideoNameTv;
    @BindView(R.id.vr_video_collect_iv)
    ImageButton vrVideoCollectIv;
    @BindView(R.id.vr_video_content_tv)
    TextView vrVideoContentTv;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.banner)
    ImageView banner;
    @BindView(R.id.web_view)
    WebView webView;
    private ContentService contentService;
    private String contentId;
    List images = new ArrayList();
    private int type; //1为开处方选择，0为收藏,2为常用场景过来的
    private boolean selecter;
    private String collextState;//收藏状态
    private String description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview_activity);
        ButterKnife.bind(this);
        Navigation.getInstance(this).setBack().setTitle("内容详情");
         /*註冊*/
        RxBus.getDefault().register(this);
        contentService = HttpMethods.getInstance().getClassInstance(ContentService.class);
        contentId = getIntent().getStringExtra("contentId");
        type = getIntent().getIntExtra("types", 0);
        if (type == 1) {
            selecter = getIntent().getBooleanExtra("selecter", false);
        }
        initView();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
//                Intent intent = new Intent(WebViewActivity.this,WebViewActivity.class);
//                intent.putExtra("url",url);
//                startActivity(intent);
                return true;
            }
        });
    }

    private void initView() {
        contentService.getSingerContent(new CollectContentReq(contentId))
                .map(new HttpResultFunc<>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressSubscriber<SingerContentInfo>(this, o -> {
                    collextState = o.getIsCollected();
                    description = o.getDescription();
                    if (images.size() != 0) {
                        images.clear();
                    }
                    String[] split = o.getExt().getContent().split(",");
                    for (int i = 0; i < split.length; i++) {
                        images.add(split[i]);
                        Log.e("---imageviewactivity", split[i]);
                    }
                    Picasso.with(this).load(o.getCoverPic()).fit().into(banner);
                    vrVideoTitleTv.setText(o.getName());
                    vrVideoPlayTimesTv.setText("使用指数 :" + o.getClicks());
                    vrVideoDateTv.setText(o.getCreatedAt());
                    vrVideoNameTv.setText(o.getDisease() + "-" + o.getTherapy() + "-" + o.getTypeName());
                    vrVideoContentTv.setText("点击查看详情");
                    vrVideoContentTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                    vrVideoContentTv.getPaint().setAntiAlias(true);//抗锯齿
//                    if (o.getIsCollected().equals("1")) {
//                        vrVideoCollectIv.setSelected(true);
//                    } else {
//                        vrVideoCollectIv.setSelected(false);
//                    }
                    if (type == 1) {//开处方界面过来的，和收藏没关系
                        if (selecter) {
                            tvAdd.setBackground(getResources().getDrawable(R.color.gray));
                            tvAdd.setText("已加入");

                        } else {
                            tvAdd.setBackground(getResources().getDrawable(R.color.colorPrimary));
                            tvAdd.setText("加入");
                        }
                    } else if (type == 2) {
                        {
                            if (o.getIsCollected().equals("1")) {
                                tvAdd.setBackground(getResources().getDrawable(R.color.gray));
                                tvAdd.setText("已加入");
                            } else {
                                tvAdd.setBackground(getResources().getDrawable(R.color.colorPrimary));
                                tvAdd.setText("加入");
                            }
                        }
                    } else {
                        if (o.getIsCollected().equals("1")) {
                            tvAdd.setBackground(getResources().getDrawable(R.color.gray));
                            tvAdd.setText("已加入");
                        } else {
                            tvAdd.setBackground(getResources().getDrawable(R.color.colorPrimary));
                            tvAdd.setText("加入");
                        }
                    }
                    webView.loadUrl(Constant.H5_HEADER + o.getDescription());
                }));


//        vrVideoContentTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ImageViewActivity.this, WebViewActivity.class);
//                intent.putExtra("url", Constant.H5_HEADER + description);
//                startActivity(intent);
//            }
//        });
    }

    @OnClick({R.id.tv_add})
    public void OnCliCk(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                if (type == 1) {//开处方界面过来的，和收藏没关系
                    Intent intent = new Intent();
                    if (selecter) {
                        tvAdd.setBackground(getResources().getDrawable(R.color.colorPrimary));
                        tvAdd.setText("加入");
                        selecter = false;
                        intent.putExtra("is", selecter);
                        setResult(102, intent);
                        finish();
                    } else {
                        tvAdd.setBackground(getResources().getDrawable(R.color.gray));
                        tvAdd.setText("已加入");
                        selecter = true;
                        intent.putExtra("is", selecter);
                        setResult(102, intent);
                        finish();
                    }
                } else if (type == 2) {//常用场景
                    if (collextState.equals("1")) {
                        contentService.collectContentCancel(new CollectContentReq(contentId))
                                .map(new HttpResultFunc<>())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ProgressSubscriber<NoData>(ImageViewActivity.this, o -> {
                                    ToastUtil.showMessage(ImageViewActivity.this, "取消收藏成功");
                                    setResult(102);
                                    finish();
                                }));
                    } else {
                        contentService.collectContent(new CollectContentReq(contentId))
                                .map(new HttpResultFunc<>())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ProgressSubscriber<NoData>(ImageViewActivity.this, o -> {
                                    ToastUtil.showMessage(ImageViewActivity.this, "收藏成功");
                                    setResult(102);
                                    finish();
                                }));
                    }
                } else {
                    if (collextState.equals("1")) {
                        contentService.collectContentCancel(new CollectContentReq(contentId))
                                .map(new HttpResultFunc<>())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ProgressSubscriber<NoData>(ImageViewActivity.this, o -> {
                                    collextState = "0";
                                    tvAdd.setBackground(getResources().getDrawable(R.color.colorPrimary));
                                    tvAdd.setText("加入");
                                    ToastUtil.showMessage(ImageViewActivity.this, "取消收藏成功");
                                    selecter = false;
                                    RxBus.getDefault().post(new Buser(selecter));
                                }));
                    } else {
                        contentService.collectContent(new CollectContentReq(contentId))
                                .map(new HttpResultFunc<>())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ProgressSubscriber<NoData>(ImageViewActivity.this, o -> {
                                    collextState = "1";
                                    tvAdd.setBackground(getResources().getDrawable(R.color.gray));
                                    tvAdd.setText("已加入");
                                    ToastUtil.showMessage(ImageViewActivity.this, "收藏成功");
                                    selecter = true;
                                    RxBus.getDefault().post(new Buser(selecter));
                                }));
                    }

                }
                break;
        }
    }

    @Override
    protected void onPause() {

        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }
}
