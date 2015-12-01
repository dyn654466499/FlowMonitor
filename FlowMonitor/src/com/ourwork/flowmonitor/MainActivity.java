package com.ourwork.flowmonitor;

import com.ourwork.fragments.FlowDoctorFragment;
import com.ourwork.fragments.UserCenterFragment;
import com.ourwork.utils.CommonUtils;
import com.ourwork.views.ButtomMenuView;
import com.ourwork.views.ButtomMenuView.MenuOnclickInterface;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

/**
 * 应用启动的主Activity
 * @author 黄创茗
 *
 */
public class MainActivity extends Activity {

	/**
	 * 菜单栏的位置
	 */
	private final static int DOCTOR_POSITION = 1;
	private final static int FLOW_POSITION = 2;
	private final static int ME_POSITION = 3;
	
	//底部菜单view
	private ButtomMenuView mButtomMenuView;
	//底部菜单栏监听事件
	private ImplButtomMenuOnclik menuOnclik;

	private FragmentManager mFragmentManager;
	// 流量医生
	private FlowDoctorFragment mFlowDoctorFragment;
	// 用户中心
	private UserCenterFragment mUserCenterFragment;
	
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		findView();
		 
		//初始化参数
		mFragmentManager = getFragmentManager();
		menuOnclik=new ImplButtomMenuOnclik();
		setCurFragment(DOCTOR_POSITION);
		
		
		//设置监听
		mButtomMenuView.setMenuOnclickInterface(menuOnclik);
		

	}

	/**
	 * 绑定view
	 */
	private void findView()
	{
		mButtomMenuView=(ButtomMenuView) findViewById(R.id.menu_buttom_view);
	}
	/**
	 * 切换fragment
	 * 
	 * @param position
	 */
	private void setCurFragment(int position) {

		FragmentTransaction transaction = mFragmentManager.beginTransaction();

		switch (position) {
		case DOCTOR_POSITION:
			if (mFlowDoctorFragment == null) {
				mFlowDoctorFragment = new FlowDoctorFragment();	
			}
			transaction.replace(R.id.main_fragment, mFlowDoctorFragment);
			transaction.commit();
			break;
		case ME_POSITION:
			if (mUserCenterFragment == null) {
				mUserCenterFragment = new UserCenterFragment();
			} 
			transaction.replace(R.id.main_fragment, mUserCenterFragment);
			transaction.commit();
			break;

		default:
			break;
		}
	}

	/**
	 * 底部菜单栏监听事件
	 * @author ming
	 *
	 */
	class ImplButtomMenuOnclik implements MenuOnclickInterface {

		@Override
		public void onclickDoctorLay() {
			// TODO Auto-generated method stub
			setCurFragment(DOCTOR_POSITION);

		}

		@Override
		public void onclickFlowLay() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onclickMeLay() {
			// TODO Auto-generated method stub
			setCurFragment(ME_POSITION);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
			{
				Toast.makeText(getApplicationContext(), getString(R.string.tips_clickTwiceToExit),
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	
}
