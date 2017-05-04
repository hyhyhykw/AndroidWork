package com.example.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.chat.adapter.ImgGridViewAdapter;
import com.example.chat.adapter.MessageAdapter;
import com.example.chat.adapter.OnLineAdapter;
import com.example.chat.adapter.ViewAdapter;
import com.example.chat.interfaci.ReceiveServerMessage;
import com.example.chat.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主要聊天界面
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @author Administrator
 * @create at 2013-5-9
 * @version 1.1.0
 */
public class MainActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private static ReceiveServerMessage interfaceMessage;
	private ListView listview;// 用来显示聊天信息的listview
	private ImageView btsend, addImg;// 发送按钮
	private EditText edittext;// 输入框
	private MessageModel modle;
	private MessageAdapter adapter;// 数据适配器
	private List<MessageModel> listmodle = new ArrayList<MessageModel>();// 存放信息的list
	private BufferedReader in;
	private PrintWriter out;
	private static Socket socket;
	private String serverStr;
	private String name, strjson;
	private LayoutInflater flater;
	private View chatRoom, onLine;
	private ViewPager viewpager;
	private List<View> views = new ArrayList<View>();
	private ImageView cursor;
	private int bmpW;// 动画图片宽度
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private Button btChat, btOnline;
	private ListView listOnline;
	private OnLineAdapter onLineAdapter;
	private List<String> listdataOnline = new ArrayList<String>();
	private String type;// 在线状态 1 在线 2 上线 0 下线；
	private String typename;// 上下线者名称
	private String personalName;// 私聊者名称
	private String strChat = "";// 私聊信息
	private boolean isPersonalChat = false;
	private LinearLayout imgLayout;
	private GridView imgGrid;
	private ImgGridViewAdapter gridAdapter;
	boolean isGridShow = false;
	private int imgId[] = { R.drawable.emoji_000, R.drawable.emoji_001,
			R.drawable.emoji_002, R.drawable.emoji_003, R.drawable.emoji_004,
			R.drawable.emoji_005, R.drawable.emoji_006, R.drawable.emoji_007,
			R.drawable.emoji_008, R.drawable.emoji_009, R.drawable.emoji_010,
			R.drawable.emoji_011, R.drawable.emoji_012 };
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String str = edittext.getText().toString();
				if (socket.isConnected()) {
					if (!socket.isOutputShutdown()) {
						System.out.println("---");
						out.println(Util.getJSonStr("1", str, name)); // 1标识在线正常发送0标识下线
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
				edittext.setText("");
				break;
			case 1:
				modle = new MessageModel();
				modle.setA(true);
				modle.setMessage(serverStr);
				modle.setDate(Util.getDate());
				listmodle.add(modle);
				adapter.notifyDataSetChanged();// 更新聊天内容
				listview.setSelection(listmodle.size());// 每次发送之后将listview滑动到最低端
														// 从而显示最新消息
				edittext.setText("");
				break;
			case 2:
				Toast.makeText(MainActivity.this, typename + "上线了", 200).show();
				addUser(typename);
				break;
			case 3:
				Toast.makeText(MainActivity.this, typename + "下线了", 200).show();
				removeUser(typename);
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
		setContentView(R.layout.activity_chat);
		name = getIntent().getStringExtra("name");
		strjson = getIntent().getStringExtra("json");
		getOnlineData();
		btChat = (Button) findViewById(R.id.chatroom);
		btOnline = (Button) findViewById(R.id.online);
		btChat.setOnClickListener(new ButtonClick(0));
		btOnline.setOnClickListener(new ButtonClick(1));
		init();
		flater = LayoutInflater.from(this);
		chatRoom = flater.inflate(R.layout.chat, null);
		onLine = flater.inflate(R.layout.online, null);
		imgLayout = (LinearLayout) chatRoom.findViewById(R.id.layoutImg);
		addImg = (ImageView) chatRoom.findViewById(R.id.addimg);
		addImg.setOnClickListener(this);
		listview = (ListView) chatRoom.findViewById(R.id.listView1);
		btsend = (ImageView) chatRoom.findViewById(R.id.imageB);
		edittext = (EditText) chatRoom.findViewById(R.id.editText1);
		listOnline = (ListView) onLine.findViewById(R.id.listonline);
		listOnline.setOnItemClickListener(this);
		btsend.setOnClickListener(this);
		views.add(chatRoom);
		views.add(onLine);
		InitImageView();
		initGird();
		viewpager = (ViewPager) findViewById(R.id.viewPages);
		viewpager.setOnPageChangeListener(new PageListener());
		viewpager.setAdapter(new ViewAdapter(views));
		adapter = new MessageAdapter(this, listmodle);
		listview.setAdapter(adapter);
		onLineAdapter = new OnLineAdapter(listdataOnline, this);
		listOnline.setAdapter(onLineAdapter);
	}

	@Override
	protected void onRestart() {
		isPersonalChat = false;
		strChat = "";
		super.onRestart();
	}

	/**
	 * 初始化滑动的view
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_selected).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	private void initGird() {
		imgGrid = (GridView) chatRoom.findViewById(R.id.imggridView);
		gridAdapter = new ImgGridViewAdapter(this, imgId);
		imgGrid.setAdapter(gridAdapter);
		imgGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				imgLayout.setVisibility(View.GONE);
				isGridShow = false;
				// modle = new MessageModel();
				// modle.setA(false);
				// modle.setMessage("");
				// modle.setImgId(imgId[position]);
				// modle.setDate(Util.getDate());
				// listmodle.add(modle);
				// adapter.notifyDataSetChanged();// 更新聊天内容
				// listview.setSelection(listmodle.size());//
				// 每次发送之后将listview滑动到最低端
				// // 从而显示最新消息
				// edittext.setText("");
				// if (socket.isConnected()) {
				// if (!socket.isOutputShutdown()) {
				// System.out.println("---");
				// // out.println("1," + str + "," + name);
				// out.println(Util.getJSonStr("6",
				// String.valueOf(position), name)); // 6标记发送表情
				// out.flush();
				// }
				// }
			}
		});
	}

	private void init() {
		try {
			socket = LoginActivity.getScoket();
			// socket = new Socket("10.100.138.219", 5678);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "utf-8"));
			if (in == null) {
				System.out.println("null");
			}
			out = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		new ChatThread().start();
	}

	public static Socket getScoket() {
		return socket;

	}

	class ChatThread extends Thread {
		public void run() {
			while (true) {
				String s;
				try {
					if (!socket.isClosed()) {
						s = in.readLine();
						if (s != null) {
							getServerJson(s);
							Message message = new Message();
							if (type.equals("1")) {
								message.what = 1;
								handler.sendMessage(message);
							} else if (type.equals("2")) {
								message.what = 2;
								handler.sendMessage(message);

							} else if (type.equals("0")) {
								message.what = 3;
								handler.sendMessage(message);
							} else if (type.equals("3")) {// 私聊信息
								if (interfaceMessage != null) {
									interfaceMessage
											.getServerMessage(serverStr);
									System.out.println("infer:" + strChat);
								} else {
									System.out
											.println("nullnullnllnyllnullnullnull");
								}
								if (!isPersonalChat) {
									strChat = serverStr;
									Intent i = new Intent();
									i.putExtra("personalName", personalName);// 私聊者名称
									i.putExtra("clientName", name);// 当前客户端名称
									i.putExtra("strChat", strChat);// 聊天信息
									i.setClass(MainActivity.this,
											ReceivePersonalChatActivity.class);
									startActivity(i);
									isPersonalChat = true;
								}
							} else if (type.equals("6")) {
								System.out.println("6666:" + strChat);
							}
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}

	}

	public static void setCallBack(ReceiveServerMessage message) {
		interfaceMessage = message;
	}

	/**
	 * 得到在线的数据
	 */
	private void getOnlineData() {
		try {
			JSONArray array = new JSONArray(strjson);
			for (int i = 0; i < array.length(); i++) {
				listdataOnline.add(array.getString(i).toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
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
		case R.id.addimg:

			if (!isGridShow) {
				imgLayout.setVisibility(View.VISIBLE);
				isGridShow = true;
				return;
			}
			imgLayout.setVisibility(View.GONE);
			isGridShow = false;
			break;
		default:
			break;
		}

	}

	class PageListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

	}

	class ButtonClick implements OnClickListener {
		int index;

		public ButtonClick(int index) {
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			viewpager.setCurrentItem(index);

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BREAK) {

		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 得到聊天室信息
	 * 
	 * @param str
	 */
	private void getServerJson(String str) {
		System.out.println("str:" + str);
		try {
			JSONObject json = new JSONObject(str);
			type = json.getString("type");
			typename = json.getString("name");// 消息发送者
			serverStr = json.getString("chatContent");// 消息内容
			if (type.equals("3")) {
				personalName = json.getString("personalName");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 新的用户上线
	 */
	private void addUser(String name) {
		listdataOnline.add(name);
		onLineAdapter.notifyDataSetChanged();
	}

	/**
	 * 用户下线从在线列表中删除
	 * 
	 * @param name
	 */
	private void removeUser(String name) {
		for (int i = 0; i < listdataOnline.size(); i++) {
			if (name.equals(listdataOnline.get(i))) {
				listdataOnline.remove(i);
				break;
			}
		}

	}

	@Override
	protected void onDestroy() {
		// 1标识在线正常发送 0标识下线
		out.println(Util.getJSonStr("0", "", name));
		out.flush();
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TextView txt = (TextView) arg1.findViewById(R.id.name);
		String strName = txt.getText().toString();
		if (strName.equals(name)) {
			Toast.makeText(this, "不能与自己聊天", 200).show();
			return;
		}
		Intent i = new Intent();
		i.putExtra("personalName", strName);
		i.putExtra("clientName", name);
		i.putExtra("strChat", strChat);
		i.setClass(this, PersonalChatActivity.class);
		startActivity(i);
		isPersonalChat = true;

	}
}