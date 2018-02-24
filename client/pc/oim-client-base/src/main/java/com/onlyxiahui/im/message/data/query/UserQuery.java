package com.onlyxiahui.im.message.data.query;

/**
 * 用户查询信息
 * @author XiaHui
 * @date 2017-11-23 17:48:18
 */
public class UserQuery {

	private String account;// 帐号
	private String nickname;// 昵称
	private String name;// 姓名
	private String spell;
	private String simpleSpell;
	private String gender;// 性别
	private String constellation;
	private String blood;
	private String startBirthdate;//
	private String endBirthdate;//
	private String homeAddress;// 家庭地址
	private String locationAddress;// 所在地
	private String queryText;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getSimpleSpell() {
		return simpleSpell;
	}

	public void setSimpleSpell(String simpleSpell) {
		this.simpleSpell = simpleSpell;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public String getStartBirthdate() {
		return startBirthdate;
	}

	public void setStartBirthdate(String startBirthdate) {
		this.startBirthdate = startBirthdate;
	}

	public String getEndBirthdate() {
		return endBirthdate;
	}

	public void setEndBirthdate(String endBirthdate) {
		this.endBirthdate = endBirthdate;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

}
