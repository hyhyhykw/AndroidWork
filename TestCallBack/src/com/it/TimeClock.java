package com.it;

import java.util.TimerTask;

public class TimeClock extends TimerTask {

	public void run() {
		listener.receiveTime();
	}

	public void setOnReceiveTimeListener(OnReceiveTimeListener listener) {
		this.listener = listener;
	}

	protected OnReceiveTimeListener listener;

	public interface OnReceiveTimeListener {
		void receiveTime();
	}

}
