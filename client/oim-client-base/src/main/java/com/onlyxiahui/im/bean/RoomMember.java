package com.onlyxiahui.im.bean;



/**
 * 描述：聊天室成员
 * 
 * @author XiaHui
 * @date 2015年3月13日 下午8:30:06
 * @version 0.0.1
 */
public class RoomMember {

	private String id;
	private String roomId;// 帐号
	private String userId;
	private String position;
	private String nickname;

	//
	public static final String position_owner = "1";
	public static final String position_admin = "2";
	public static final String position_normal = "3";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
