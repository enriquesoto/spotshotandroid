package com.aqpup.waitforit.navigation;

public class Item_friend {

	private String name;
	private String address;
	private int icono;
	private Long targetUserId;

	public Item_friend(String name, String address, int icono, Long targetUserId) {
		this.name = name;
		this.address = address;
		this.icono = icono;
		this.targetUserId = targetUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getIcono() {
		return icono;
	}

	public void setIcono(int icono) {
		this.icono = icono;
	}

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}

}
