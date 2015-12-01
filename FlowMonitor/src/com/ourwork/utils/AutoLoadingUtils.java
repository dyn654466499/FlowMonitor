package com.ourwork.utils;

import com.ourwork.flowmonitor.R;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

public class AutoLoadingUtils {
    private Activity mActivity;
    private int resID;
	public AutoLoadingUtils(Activity mActivity, int resID) {
		super();
		this.mActivity = mActivity;
		this.resID = resID;
	}
    
	public void showAutoLoadingLayout(){
		ImageView image = (ImageView) mActivity.findViewById(resID);  
		image.setBackgroundResource(R.anim.autoloading);  
        AnimationDrawable anim = (AnimationDrawable) image.getBackground();  
        anim.start();  
	}
}
