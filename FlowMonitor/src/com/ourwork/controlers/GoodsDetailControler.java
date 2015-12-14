package com.ourwork.controlers;

import com.ourwork.models.GoodsDetailModel;

import android.app.Activity;

public class GoodsDetailControler extends BaseControler{

	public GoodsDetailControler(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		modelDelegate = new GoodsDetailModel(handler);
	}

}
