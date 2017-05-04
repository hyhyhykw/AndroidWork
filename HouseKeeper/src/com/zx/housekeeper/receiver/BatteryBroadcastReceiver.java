package com.zx.housekeeper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 * battery charge change broadcast receiver
 * 
 * @author HY
 * 
 */
public class BatteryBroadcastReceiver extends BroadcastReceiver {

	/** current battery charge */
	protected int currentBattery;
	/** battery temperature */
	protected int batteryTemp;
	/** battery change listener */
	protected OnReceiveBatteryListener listener;

	@Override
	public void onReceive(Context context, Intent intent) {
		currentBattery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		batteryTemp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
		listener.receiveCurrentBattery(currentBattery, batteryTemp);
	}

	public void setOnReceiveBatteryListener(OnReceiveBatteryListener listener) {
		this.listener = listener;
	}

	/**
	 * call back interface :help receive battery charge and temperature
	 * 
	 * @author HY
	 * 
	 */
	public interface OnReceiveBatteryListener {
		void receiveCurrentBattery(int currentBattery, int batteryTemp);
	}

}
