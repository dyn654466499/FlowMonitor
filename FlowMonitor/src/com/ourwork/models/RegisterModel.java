package com.ourwork.models;

import java.util.Timer;
import java.util.TimerTask;

import com.ourwork.views.RegisterView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import static com.ourwork.controlers.ControlerState.*;

/**
 * “注册”view对应的model
 * @author 邓耀宁
 *
 */
public class RegisterModel extends BaseModel{

	public RegisterModel(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeModelState(int changeState) {
		// TODO Auto-generated method stub
		switch (changeState) {
		case MODEL_GET_VCODE:
			new Timer().schedule(new TimerTask() {
				//控制60秒
				int count = 60;
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message.obtain(handler, VIEW_VCODE_CHANGE, count, 0,
							null).sendToTarget();
					//Activity结束时取消线程运作
					if (count == 0 || RegisterView.isActivityfinish()) {
						cancel();
					} else {
						count--;
					}
				}
			}, 0, 1000);
			break;
		case MODEL_SURE_REGISTER:
			
			break;
			
			
		default:
			break;
		}
	}

	@Override
	public void changeModelState(Message changeStateMessage) {
		// TODO Auto-generated method stub
		switch (changeStateMessage.what) {
		
		
		default:
			break;
		}
	}
       
	 
}
