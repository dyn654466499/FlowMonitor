package com.ourwork.utils;

import com.ourwork.flowmonitor.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ViewUtils {

	public static View getProgressView(Context context,String info){
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.dialog_progress, null, false);
		((TextView)layout.findViewById(R.id.dialog_info)).setText(info);
		return layout;
	}
}
