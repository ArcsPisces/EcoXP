package com.fred.ecoxp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.fred.ecoxp.EcoXP;
import com.fred.ecoxp.ShopItem;
import com.fred.ecoxp.events.PlayerAttemptBuyEvent;
import com.fred.ecoxp.utils.Eco;
import com.fred.ecoxp.utils.Shop;

public class BuyListener implements Listener {
	
	EcoXP pl;
	public BuyListener(EcoXP pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerAttempt(PlayerAttemptBuyEvent e) {
		int levels = Eco.get(e.getPlayer().getName(), pl);
		ShopItem itemAttempted = Shop.getItem(e.getID(), pl);
		if(itemAttempted.getPrice() > levels) {
			e.getPlayer().sendMessage(Eco.prefix + "You do not have enough levels to purchase this.");
		} else {
			Eco.remove(e.getPlayer().getName(), itemAttempted.getPrice(), pl);
			EcoXP.perms.playerAdd(e.getPlayer(), itemAttempted.getPermission());
			e.getPlayer().sendMessage(Eco.prefix + "Purchase successful.");
		}
	}
}
