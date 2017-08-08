package com.medvision.vrmc.presenter;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import com.medvision.vrmc.imp.DownFile;
import com.medvision.vrmc.model.DownFileModel;

/**
 * Created by raytine on 2017/2/20.
 */

public class DownFilePresenter {
    private DownFile downFile;
    private DownFileModel downFileModel;
    private Handler mHandler = new Handler();
    String path = Environment.getExternalStorageDirectory()+"/vrmc/video/";
    private String name;
    private String url;
    private  Context context;
    public DownFilePresenter(Context context, String name, String url, DownFile downFile) {
        this.downFile =downFile;
        this.context =context;
        downFileModel = new DownFileModel();
        this.name = name;
        this.url = url;
    }
     public void loadfile(){
         downFileModel.loadFile(context,path,name,url, new DownFileModel.OnLoginListener() {


             @Override
             public void loadprogress(boolean iscompele) {

             }

             @Override
             public void loginSuccess() {
                 mHandler.post(new Runnable() {
                     @Override
                     public void run() {
                         downFile.showsuccess();
                     }
                 });
             }

             @Override
             public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        downFile.showfaild();
                    }
                });
             }
         });
     }
}
