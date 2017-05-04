package com.it;

public class ChatModle {
	private String type;// 在线状态 1在线0下线
	private String chatContent;// 聊天内容
	private String name;// 聊天人名字

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
