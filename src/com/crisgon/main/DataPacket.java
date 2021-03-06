package com.crisgon.main;

import java.io.Serializable;

public class DataPacket implements Serializable {

	private static final String TAG = "DataPacket";
	private static final long serialVersionUID = 1L;

	private String ip;
	private String nickname;
	private String message;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SendPacket [ip=" + ip + ", nickname=" + nickname + ", message=" + message + "]";
	}
	
}
