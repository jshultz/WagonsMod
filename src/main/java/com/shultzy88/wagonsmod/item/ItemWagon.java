package com.shultzy88.wagonsmod.item;

import java.util.List;

import com.shultzy88.wagonsmod.entity.EntityWagon;
import com.shultzy88.wagonsmod.main.WagonsMod;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.*;
import net.minecraft.world.World;

/** All wagon types use this class for crafting and spawning the entity. */
public class ItemWagon extends Item {
	/** holds metadata about this item */
	private int wagonType;
	/** texture data for each type */
	private static final IIcon[] icons = new IIcon[1];

	public ItemWagon() {
		super();
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabTransport);
	}

	public ItemWagon(int wagonType) {
		this();
		this.wagonType = wagonType;
	}

	@Override
	public void registerIcons(IIconRegister iconReg) {
		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconReg.registerIcon(WagonsMod.MOD_ID + ":wagon_subtype_" + i);
		}
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List subItems) {
		for (int i = 0; i < icons.length; i++) {
			subItems.add(new ItemStack(item, 1));
		}
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return stack.getIconIndex();
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta > 1) {
			meta = 0;
		}
		return icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public boolean onItemUse(ItemStack activeItem, EntityPlayer player, World world, int blockX, int blockY, int blockZ,
			int par6, float par7, float par8, float par9) {
		if (!world.isRemote) {
			// find the discrete heading of the player (North, South, East, West)
			int playerOrientation = MathHelper.floor_float((float) ((player.rotationYaw * 4F / 360) + 0.5)) & 3;
			// Type of wagon is passed in this method,
			// providing the data needed for which entity
			// to spawn.
			EntityWagon entityWagon;
			entityWagon = EntityWagon.createWagon(world, blockX, blockY + 1, blockZ, wagonType);
			entityWagon.rotationYaw = (float) (((MathHelper
					.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) - 1) * 90);

			if (activeItem.hasDisplayName()) {
				entityWagon.setWagonName(activeItem.getDisplayName());
			}

			world.spawnEntityInWorld(entityWagon);
		}

		if (!player.capabilities.isCreativeMode) {
			--activeItem.stackSize;
		}

		return true;
	}

}
