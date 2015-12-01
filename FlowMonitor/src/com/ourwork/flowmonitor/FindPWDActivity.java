package com.ourwork.flowmonitor;

import com.ourwork.views.FindPWDView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * “找回密码界面”的Activity
 * @author 邓耀宁
 *
 */
public class FindPWDActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new FindPWDView(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		FindPWDView.setActivityfinish(true);
	}
	
	
}
