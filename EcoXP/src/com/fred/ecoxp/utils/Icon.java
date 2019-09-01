package com.fred.ecoxp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.fred.ecoxp.EcoXP;

public class Icon {
	
	public static ItemStack getIcon(int id, EcoXP plugin) {
		
		if(!plugin.getConfig().contains("shop." + id))
			return null;
		
		ConfigurationSection from = plugin.getConfig().getConfigurationSection("shop." + id);
		
		String materialName = from.getString("icon.material");
		int amount = from.getInt("icon.amount");
		String displayname = ChatColor.translateAlternateColorCodes('&', from.getString("icon.displayname"));
		List<String> lore = new ArrayList<String>();
		int price = from.getInt("price");
		
		ItemStack icon = new ItemStack(Material.getMaterial(materialName), amount);
		ItemMeta meta = icon.getItemMeta();
		meta.setDisplayName(displayname);
		lore.add(ChatColor.GRAY + "Costs: " + price + " Levels.");
		lore.add(ChatColor.YELLOW + "#" + ChatColor.ITALIC + id);
		meta.setLore(lore);
		icon.setItemMeta(meta);
		return icon;
		
	}
	
	public static void saveIcon(int id, ItemStack icon, EcoXP plugin) {
		
		String materialName = icon.getType().toString();
		int amount = icon.getAmount();
		String displayname = icon.getItemMeta().getDisplayName();
		List<String> lore = icon.getItemMeta().getLore();
		
		plugin.getConfig().set("shop." + id + ".material", materialName);
		plugin.getConfig().set("shop." + id + ".amount", amount);
		plugin.getConfig().set("shop." + id + ".displayname", displayname);
		plugin.getConfig().set("shop." + id + ".lore", lore);
		plugin.saveConfig();
	}
	
	public static ItemStack next() {
		ItemStack is = new ItemStack(Material.GREEN_CONCRETE, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Next ->");
		is.setItemMeta(meta);
		return is;
	}
	
	public static ItemStack previous() {
		ItemStack is = new ItemStack(Material.RED_CONCRETE, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "<- Previous");
		is.setItemMeta(meta);
		return is;
	}
	
	public static ItemStack nothing() {
		ItemStack is = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName("");
		is.setItemMeta(meta);
		return is;
	}
	
	public static ItemStack confirm() {
		ItemStack is = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Confirm");
		meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to confirm."));
		is.setItemMeta(meta);
		return is;
	}
	
	public static ItemStack deny() {
		ItemStack is = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Return");
		meta.setLore(Arrays.asList(ChatColor.GRAY + "Click to deny."));
		is.setItemMeta(meta);
		return is;
	}
	
	public static ItemStack purchased() {
		ItemStack is = new ItemStack(Material.IRON_BARS, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.ITALIC + "* Purchased *");
		meta.setLore(Arrays.asList(ChatColor.WHITE + "You already own this item."));
		is.setItemMeta(meta);
		return is;
	}
}
