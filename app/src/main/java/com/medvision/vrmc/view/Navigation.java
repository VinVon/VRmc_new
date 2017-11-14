package com.medvision.vrmc.view;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medvision.vrmc.R;

import org.w3c.dom.Text;


/**
 * Created by Administrator on 2016/3/23.
 * 设置标题栏
 */
public class Navigation {
	private FragmentActivity mActivity;

	private Navigation(FragmentActivity activity) {
		this.mActivity = activity;

	}

	public static Navigation getInstance(FragmentActivity activity) {
		return new Navigation(activity);
	}

	public Navigation setBack() {
		View view = mActivity.findViewById(R.id.base_header_back_iv);
		view.setVisibility(View.VISIBLE);
		view.setOnClickListener(v -> mActivity.finish());
		return this;
	}
	public Navigation setBacks(View.OnClickListener clickListener) {
		View view = mActivity.findViewById(R.id.base_header_back_iv);
		view.setVisibility(View.VISIBLE);
		view.setOnClickListener(clickListener);
		return this;
	}
	public Navigation setBackss(View.OnClickListener clickListener) {
		View view1 = mActivity.findViewById(R.id.base_header_back_close);
		view1.setVisibility(View.VISIBLE);
		view1.setOnClickListener(clickListener);
		return this;
	}
	public Navigation setTitle(String title) {
		((TextView) mActivity.findViewById(R.id.base_header_title_tv)).setText(title);
		return this;
	}

	public Navigation setRight(String text, View.OnClickListener clickListener) {
		TextView bt = ((TextView) mActivity.findViewById(R.id.base_header_right_bt));
		bt.setText(text);
		bt.setOnClickListener(clickListener);
		return this;
	}

	public Navigation setRightDrawable(int resourceId, View.OnClickListener clickListener) {
		ImageView iv = ((ImageView) mActivity.findViewById(R.id.base_header_right_iv));
		iv.setImageResource(resourceId);
		iv.setOnClickListener(clickListener);
		return this;
	}

	public Navigation setRightDrawableListener(View.OnClickListener clickListener) {
		ImageView iv = ((ImageView) mActivity.findViewById(R.id.base_header_right_iv));
		iv.setOnClickListener(clickListener);
		return this;
	}

	public Navigation setRightDrawable(int resourceId) {
		ImageView iv = ((ImageView) mActivity.findViewById(R.id.base_header_right_iv));
		iv.setImageResource(resourceId);
		return this;
	}
}
