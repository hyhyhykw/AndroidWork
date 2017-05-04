package com.zx.housekeeper.biz;

import java.text.SimpleDateFormat;

import com.zx.housekeeper.constant.Constant;

public class DateUitls {

	

	public long getMills() {
		return System.currentTimeMillis();
	}

	public static String Mills2Date(long Mills){
		SimpleDateFormat sdf=new SimpleDateFormat(Constant.PATTERN);
		return sdf.format(Mills);
	}
}
