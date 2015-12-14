package com.ourwork.controlers;

import com.ourwork.models.GoodsModel;

import android.app.Activity;

public class GoodsControler extends BaseControler{

	public GoodsControler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		modelDelegate = new GoodsModel(handler);
	}

}
