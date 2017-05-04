package com.example.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.example.chat.adapter.MessageAdapter;
import com.example.chat.interfaci.ReceiveServerMessage;
import com.example.chat.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 私聊界面
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @author Administrator
 * @create at 2013-7-11
 * @version 1.1.0
 */
public class PersonalChatActivity extends Activity implements OnClickListener,
		ReceiveServerMessage {
	private TextView chatInfo;// 显示与谁聊天
	private ListView listview;// 显示聊天记录
	private EditText et;// 聊天输入框
	private ImageView img;// 发送按钮
	private Socket socket;// 当前客户端
	private String personalName;// 私聊者名称
	private MessageModel modle;
	private MessageAdapter adapter;// 数据适配器
	private List<MessageModel> listmodle = new ArrayList<MessageModel>();// 存放信息的list
	@SuppressWarnings("unused")
	private BufferedReader in;
	private PrintWriter out;
	private String clientName;
	private String strChat;// 私聊信息
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String str = et.getText().toString();
				if (socket.isConnected()) {
					if (!socket.isOutputShutdown()) {
						out.println(Util.personalChatJson("3", str,
								personalName, clientName)); // 3私聊
						out.flush();
					}
				}
				modle = new MessageModel();
				modle.setA(false);
				modle.setMessage(str);
				modle.setDate(Util.getDate());
				listmodle.add(modle);
				adapter.notifyDataSetChanged();// 更新聊天内容
				listview.setSelection(listmodle.size());// 每次发送之后将listview滑动到最低端
														// 从而显示最新消息
				et.setText("");
				break;
			case 1:
				modle = new MessageModel();
				modle.setA(true);
				modle.setMessage(strChat);
				modle.setDate(Util.getDate());
				listmodle.add(modle);
				adapter.notifyDataSetChanged();// 更新聊天内容
				listview.setSelection(listmodle.size());// 每次发送之后将listview滑动到最低端
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personal_chat);
		chatInfo = (TextView) findViewById(R.id.chatInfo);
		listview = (ListView) findViewById(R.id.listView1);
		et = (EditText) findViewById(R.id.editText1);
		img = (ImageView) findViewById(R.id.imageB);
		img.setOnClickListener(this);
		personalName = getIntent().getStringExtra("personalName");
		clientName = getIntent().getStringExtra("clientName");
		strChat = getIntent().getStringExtra("strChat");
		socket = MainActivity.getScoket();
		adapter = new MessageAdapter(this, listmodle);
		listview.setAdapter(adapter);
		chatInfo.setText("与" + personalName + "聊天中");
		if (!strChat.equals("")) {
			modle = new MessageModel();
			modle.setA(true);
			modle.setMessage(strChat);
			modle.setDate(Util.getDate());
			listmodle.add(modle);
			adapter.notifyDataSetChanged();// 更新聊天内容
		}
		init();
		MainActivity.setCallBack(this);
	}

	private void init() {
		try {
			socket = LoginActivity.getScoket();
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "utf-8"));
			out = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageB:
			Message message = new Message();
			message.what = 0;
			handler.sendMessage(message);
			break;
		default:
			break;
		}
	}

	@Override
	public void getServerMessage(String str) {
		System.out.println("****:" + str);
		strChat = str;
		Message message = new Message();
		message.what = 1;
		handler.sendMessage(message);
	}

}