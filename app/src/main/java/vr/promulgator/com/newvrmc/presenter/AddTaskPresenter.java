package vr.promulgator.com.newvrmc.presenter;

import android.os.Handler;

import java.util.Map;

import vr.promulgator.com.newvrmc.imp.AddTask;
import vr.promulgator.com.newvrmc.model.AddTaskModel;

/**
 * Created by raytine on 2017/4/14.
 */

public class AddTaskPresenter {
    private AddTask addTask ;
    private AddTaskModel addTaskModel;
    private Handler handler = new Handler();

    public AddTaskPresenter(AddTask addTask) {
        this.addTask = addTask;
        addTaskModel = new AddTaskModel();

    }
    public void addTask(Map<String ,Object> map){
        addTaskModel.AddTask(map, new AddTaskModel.AddTaskListLisencer() {
            @Override
            public void AddTaskSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        addTask.addsuccess("发送播放任务成功");
                    }
                });
            }

            @Override
            public void tokenChange() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        addTask.tokenchange();
                    }
                });
            }

            @Override
            public void AddTaskFailed(String user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                addTask.addfailed(user);
                    }
                });
            }
        });
    }
}
