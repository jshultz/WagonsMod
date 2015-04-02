package com.shultzy88.wagons.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemCoveredWagon extends Item
{
	public ItemCoveredWagon(int itemID)
	{
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTransport);
	}
}
