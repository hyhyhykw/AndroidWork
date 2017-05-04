package com.it;

import java.util.Timer;

import com.it.TimeClock.OnReceiveTimeListener;

public class Person implements OnReceiveTimeListener {

	 Timer timer;

	public static void main(String[] args) {
		Person person=new Person();
		person.timer = new Timer();
		TimeClock timeIsOut=new TimeClock();
		timeIsOut.setOnReceiveTimeListener(person);
		person.timer.schedule(timeIsOut, 3000);
	}

	public void receiveTime() {
		System.out.println("时间到!");
		if (null != timer) {
			timer.cancel();
		}
	}

}
