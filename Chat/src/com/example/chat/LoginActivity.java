package com.example.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.chat.util.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 注册及登录界面
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @author Administrator
 * @create at 2013-7-11
 * @version 1.1.0
 */
public class LoginActivity extends Activity implements OnClickListener {
	private EditText etname, etpassword;
	private Button login;
	private Button register;
	private String name, password;
	private static Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String json = "";
	private String flag = "";
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(LoginActivity.this, "登录失败请检查用户名和密码", 200).show();
				break;
			case 1:
				Toast.makeText(LoginActivity.this, "注册失败,用户名已存在", 200).show();
				break;
			case 2:
				new AlertDialog.Builder(LoginActivity.this)
						.setTitle("是否登录")
						.setMessage("注册成功是否登录")
						.setPositiveButton("登录",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										flag = "2";
										new LoginThread().start();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										etname.setText("");
										etpassword.setText("");

									}
								}).show();
				break;
			case 3:
				Toast.makeText(LoginActivity.this, "服务器已断开，请链接服务器！", 200)
						.show();
				break;
			default:
				break;
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		etname = (EditText) findViewById(R.id.username);
		etpassword = (EditText) findViewById(R.id.passwrod);
		login = (Button) findViewById(R.id.denglu);
		login.setOnClickListener(this);
		register = (Button) findViewById(R.id.zhuce);
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		name = etname.getText().toString();
		password = etpassword.getText().toString();
		if (name.equals("")) {
			Toast.makeText(this, "请输入昵称", 200).show();
			return;
		}
		if (password.equals("")) {
			Toast.makeText(this, "请输入密码", 200).show();
			return;
		}
		switch (v.getId()) {
		case R.id.denglu:
			flag = "2";// 2登录
			break;
		case R.id.zhuce:
			flag = "-2";// -2注册
			break;
		default:
			break;
		}

		new LoginThread().start();
	}

	public static Socket getScoket() {
		return socket;

	}

	/**
	 * 获取服务器消息
	 * 
	 * @Copyright Copyright (c) 2012 - 2100
	 * @author Administrator
	 * @create at 2013-7-11
	 * @version 1.1.0
	 */
	class LoginThread extends Thread {
		public void run() {
			try {
				socket = new Socket("192.168.253.1", 5678);
				out = new PrintWriter(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream(), "utf-8"));
				out.println(Util.getJSonStr(flag, password, name)); // 2表示连接
				out.flush();
				String str = in.readLine();
				String flag = "";
				try {
					JSONArray array = new JSONArray(str);
					flag = array.getString(0);
					json = array.getString(1);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (flag.equals("4")) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				}
				if (flag.equals("5")) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
				if (flag.equals("1")) {// 1表示登陆成功
					Intent intent = new Intent();
					intent.putExtra("name", etname.getText().toString());
					intent.putExtra("json", json);
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else if (flag.equals("-1")) {
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);
				}

			} catch (IOException e1) {
				Message msg = new Message();
				msg.what = 3;
				handler.sendMessage(msg);
				e1.printStackTrace();
			}
		}

	}
}
