package com.fred.ecoxp;

public class ShopItem {
	
	String name;
	String permission;
	int price;
	int id;
	
	public ShopItem(int id, int price, String name, String permission) {
		this.name = name;
		this.id = id;
		this.price = price;
		this.permission = permission;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getID() {
		return id;
	}
}
