package com.ourwork.models;

import com.ourwork.utils.DataCacheUtils;

import android.os.Handler;
import android.os.Message;

import static com.ourwork.controlers.ControlerState.*;
public class SettingModel extends Models{

	public SettingModel(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeModelState(int changeState) {
		// TODO Auto-generated method stub
		switch (changeState) {
		case MODEL_SETTING_CLEARCACHE:
			break;

		default:
			break;
		}
	}

	@Override
	public void changeModelState(Message changeStateMessage) {
		// TODO Auto-generated method stub
		
	}

}
