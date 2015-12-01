package com.ourwork.flowmonitor;

import android.app.Activity;
import android.os.Bundle;

import com.ourwork.views.SettingView;

public class SettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new SettingView(this);
	}

}
