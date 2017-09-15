package com.medvision.vrmc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.medvision.vrmc.R;
import com.medvision.vrmc.view.Navigation;


/**
 * Created by raytine on 2017/7/19.
 */

public class WebViewActivity extends AppCompatActivity {
    @BindView(R.id.web)
    WebView web;
    private  String url = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        ButterKnife.bind(this);
        String st = getIntent().getStringExtra("title");
        if (st == null){
            st = "内容详情";
        }
        Navigation.getInstance(this).setBacks(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(web.canGoBack()){
                    if(web.getUrl().equals(url)){
                        finish();
                    }else{
                        web.goBack();
                    }
                }else{
                   finish();
                }
            }
        }).setTitle(st).setRight("关闭", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        url  =  getIntent().getStringExtra("url");
        web.loadUrl(url);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
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

    @Override
    public void onBackPressed() {
        if (web.canGoBack()){
            if(web.getUrl().equals(url)){
                super.onBackPressed();
            }else{
                web.goBack();
            }
        }else{
            super.onBackPressed();
        }
    }
}
