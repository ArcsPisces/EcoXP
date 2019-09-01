package com.fred.ecoxp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.fred.ecoxp.EcoXP;
import com.fred.ecoxp.ShopItem;

public class Shop {
	
	/*
	 * 
	 * 0:
	 * 	name: "String"
	 * 	permission: "ecoxp.sample"
	 * 	price: 100
	 * 	icon:
	 * 	  material: STRING
	 * 	  amount: 1
	 * 	  displayname: "&bString"
	 *    lore: {"&8With multiple", "&clines."}
	 * 
	 */
	
	public static int getAvailableItems(EcoXP plugin) {
		if(!plugin.getConfig().contains("shop"))
			return 0;
		
		Set<String> keys = plugin.getConfig().getConfigurationSection("shop").getKeys(false);
		return keys.size();
	}
	
	public static ShopItem getItem(int from, EcoXP plugin) {
		if(!plugin.getConfig().contains("shop." + from))
			return null;
		
		int price = plugin.getConfig().getInt("shop." + from + ".price");
		String name = plugin.getConfig().getString("shop." + from + ".name");
		String permission = plugin.getConfig().getString("shop." + from + ".permission");
		return new ShopItem(from, price, name, permission);
	}
	
	public static List<ItemStack> getItemsOnPage(int pageNo, EcoXP plugin) {
		
		int end = 27*pageNo;
		int start = end-27;
		
		ArrayList<ItemStack> icons = new ArrayList<ItemStack>();
		
		for(int i = start; i < end; i++) {
			icons.add(Icon.getIcon(i, plugin));
		}
		
		return icons;
	}
	
	public static boolean checkPlayerHasPerm(int id, Player p, EcoXP pl) {
		ShopItem item = getItem(id, pl);
		if(item == null)
			return true;
		
		String permission = item.getPermission();
		boolean has = EcoXP.perms.playerHas(p, permission);
		if(has) {
			return true;
		} else {
			return false;
		}
		
	}
}
