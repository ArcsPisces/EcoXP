package com.fred.ecoxp.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.fred.ecoxp.EcoXP;
import com.fred.ecoxp.events.PlayerAttemptBuyEvent;
import com.fred.ecoxp.utils.Eco;
import com.fred.ecoxp.utils.Icon;
import com.fred.ecoxp.utils.Shop;

public class InventoryClickListener implements Listener {
	
	EcoXP plugin;
	public InventoryClickListener(EcoXP plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if(!plugin.getConfig().contains("gui-title"))
			return;
		
		if(!(e.getWhoClicked() instanceof Player))
			return;
		
		Player player = (Player)e.getWhoClicked();
		
		// Check if the title of the inventory matches.
		if(e.getView().getTitle().contains(plugin.getConfig().getString("gui-title"))) {
			// Check if the item is LEFT clicked.
			if(e.getClick() != ClickType.LEFT) {
				e.setCancelled(true);
				return;
			}
			
			if(e.getCurrentItem() == null) {
				e.setCancelled(true);
				return;
			}
			if(!e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);
				return;
			}
			
			// Check the items meta.
			ItemMeta meta = e.getCurrentItem().getItemMeta();
			// Check if the item is a button.
			if(meta.hasLore()) {
				// Item is a shop item
				// Check if the item is purchasable.
				if(meta.getDisplayName().contains("* Purchased *")) {
					e.getWhoClicked().sendMessage(Eco.prefix + "You already own that item.");
					e.setCancelled(true);
					return;
				}
				
				Inventory confirmGUI = Bukkit.createInventory(null, 9, "Confirm purchase:");
				confirmGUI.setItem(2, Icon.deny());
				confirmGUI.setItem(6, Icon.confirm());
				confirmGUI.setItem(4, e.getCurrentItem());
				e.getWhoClicked().openInventory(confirmGUI);
				
				e.setCancelled(true);
			} else {
				// Check if item is a placeholder
				if(e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE)
					return;
				
				// Get the page number.
				int pageNo = Integer
						.parseInt(e.getView().getTitle()
								.replace(plugin.getConfig().getString("gui-title") + " Page ", ""));
				
				// Item is a button
				if(meta.getDisplayName().contains("Next")) {
					// Check if its the last page.
					int allItems = Shop.getAvailableItems(plugin);
					int currentItems = pageNo * 27;
					if(currentItems >= allItems) {
						e.setCancelled(true);
						return;
					}
					e.getWhoClicked().closeInventory();
					
					int nextPageNo = pageNo + 1;
					Inventory gui = Bukkit.createInventory(null, 45, plugin.getConfig().getString("gui-title") + " Page " + nextPageNo);
					
					// Setup buttons & placeholders.
					for(int i = 36; i < 45; i++) {
						gui.setItem(i, Icon.nothing());
					}
					gui.setItem(38, Icon.previous());
					gui.setItem(42, Icon.next());
					
					// Determine how many items go on this page.
					if(allItems > 26*nextPageNo+1*nextPageNo) {
						// Create an inventory for PAGE 1
						List<ItemStack> allIcons = Shop.getItemsOnPage(nextPageNo, plugin);
						for(int i = 0; i < 27; i++) {
							// For each id create an icon
							if(!Shop.checkPlayerHasPerm(i, player, plugin)) {
								gui.setItem(i, allIcons.get(i));
							} else {
								gui.setItem(i, Icon.purchased());
							}
						}
					} else {
						// Create an inventory for ALL items.
						for(int i = 27; i < allItems; i++) {
							if(!Shop.checkPlayerHasPerm(i, player, plugin)) {
								gui.setItem(i-27, Icon.getIcon(i, plugin));
							} else {
								gui.setItem(i-27, Icon.purchased());
							}
						}
					}
					
					// Present the page.
					e.getWhoClicked().openInventory(gui);
				} else if(meta.getDisplayName().contains("Previous")) {
					// Check if its the first page.
					if(pageNo == 1) {
						e.setCancelled(true);
						return;
					}
					e.getWhoClicked().closeInventory();
					
					int nextPageNo = pageNo - 1;
					Inventory gui = Bukkit.createInventory(null, 45, plugin.getConfig().getString("gui-title") + " Page " + nextPageNo);
					
					// Setup buttons & placeholders.
					for(int i = 36; i < 45; i++) {
						gui.setItem(i, Icon.nothing());
					}
					gui.setItem(38, Icon.previous());
					gui.setItem(42, Icon.next());
					
					List<ItemStack> allIcons = Shop.getItemsOnPage(nextPageNo, plugin);
					// Create an inventory for PAGE 1
					for(int i = 0; i < 27; i++) {
						// For each id create an icon
						if(!Shop.checkPlayerHasPerm(i, player, plugin)) {
							gui.setItem(i, allIcons.get(i));
						} else {
							gui.setItem(i, Icon.purchased());
						}
					}
					
					// Present the page.
					e.getWhoClicked().openInventory(gui);
				}
			}
			
		} else if(e.getView().getTitle() == "Confirm purchase:") {
			
			if(e.getClick() != ClickType.LEFT) {
				e.setCancelled(true);
				return;
			}
			
			if(!e.getCurrentItem().hasItemMeta()) {
				e.setCancelled(true);
				return;
			}
			
			ItemMeta meta = e.getCurrentItem().getItemMeta();
			ItemStack centeritem = e.getInventory().getItem(4);
			
			if(meta.getDisplayName().contains("Confirm")) {
				// Attempt to confirm the purchase.
				int id = Integer
						.parseInt(ChatColor
								.stripColor(centeritem.getItemMeta()
										.getLore().get(1).replace("#", "")));
				PlayerAttemptBuyEvent attemptBuy = new PlayerAttemptBuyEvent((Player)e.getWhoClicked(), id);
				plugin.getServer().getPluginManager().callEvent(attemptBuy);
				e.getWhoClicked().closeInventory();
				e.setCancelled(true);
				return;
				
			} else if(meta.getDisplayName().contains("Return")) {
				e.setCancelled(true);
				e.getWhoClicked().closeInventory();
				return;
			} else {
				e.setCancelled(true);
				return;
			}
		}
	}
}
