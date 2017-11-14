package com.cs.networklibrary.subscribers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cs.common.utils.RxBus;
import com.cs.networklibrary.entity.SubscriptionLogout;
import com.cs.networklibrary.progress.ProgressCancelListener;
import com.cs.networklibrary.progress.ProgressDialogHandler;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by chenshuai on 16/3/10.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

	private SubscriberOnNextListener<T> mSubscriberOnNextListener;
	private SubscriberOnErrorListener mSubscriberOnErrorListener;
	private ProgressDialogHandler mProgressDialogHandler;

	private Context context;
	private boolean shouldShowError = true;
	private RxBus mRxBus = RxBus.getInstance();
	public ProgressSubscriber(Context context, SubscriberOnNextListener<T> mSubscriberOnNextListener) {
		this(context, mSubscriberOnNextListener, null);
	}

	public ProgressSubscriber(Context context, SubscriberOnNextListener<T> mSubscriberOnNextListener, SubscriberOnErrorListener subscriberOnErrorListener) {
		this.mSubscriberOnNextListener = mSubscriberOnNextListener;
		mSubscriberOnErrorListener = subscriberOnErrorListener;
		this.context = context;
		mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
	}

	public ProgressSubscriber(Context context, SubscriberOnNextListener<T> mSubscriberOnNextListener, boolean shouldShowError) {
		this.mSubscriberOnNextListener = mSubscriberOnNextListener;
		this.context = context;
		mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
		this.shouldShowError = shouldShowError;
	}

	private void showProgressDialog() {
		if (mProgressDialogHandler != null) {
			mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
		}
	}

	private void dismissProgressDialog() {
		if (mProgressDialogHandler != null) {
			mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
			mProgressDialogHandler = null;
		}
	}

	/**
	 * 订阅开始时调用
	 * 显示ProgressDialog
	 */
	@Override
	public void onStart() {
		showProgressDialog();
	}

	/**
	 * 完成，隐藏ProgressDialog
	 */
	@Override
	public void onCompleted() {
		dismissProgressDialog();
	}

	/**
	 * 对错误进行统一处理
	 * 隐藏ProgressDialog
	 *
	 * @param e
	 */
	@Override
	public void onError(Throwable e) {
		if (e instanceof SocketTimeoutException) {
			Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
		} else if (e instanceof ConnectException) {
			Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
		} else {
			if (shouldShowError) {
				Log.e("-----------",e.getMessage());
				if (e.getMessage().equals("Unable to resolve host \"test.med-vision.cn\": No address associated with hostname")){
					Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
					dismissProgressDialog();
					return;
				}
				if (e.getMessage().equals("已经退出登录")||e.getMessage().equals("无效token")){
					mRxBus.send(new SubscriptionLogout());dismissProgressDialog();
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

			}
		}
		dismissProgressDialog();
		if (mSubscriberOnErrorListener != null) {
			mSubscriberOnErrorListener.onError(e.getMessage());
		}
	}

	/**
	 * 将onNext方法中的返回结果交给Activity或Fragment自己处理
	 *
	 * @param t 创建Subscriber时的泛型类型
	 */
	@Override
	public void onNext(T t) {
		if (mSubscriberOnNextListener != null) {
			mSubscriberOnNextListener.onNext(t);
		}
	}

	/**
	 * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
	 */
	@Override
	public void onCancelProgress() {
		if (!this.isUnsubscribed()) {
			this.unsubscribe();
		}
	}
}