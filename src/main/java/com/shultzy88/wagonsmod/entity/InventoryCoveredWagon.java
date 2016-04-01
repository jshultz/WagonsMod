package com.shultzy88.wagonsmod.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryCoveredWagon implements IInventory {
	/** holds the items that are on the wagon */
	private ItemStack[] inventory;
	/** entity this inventory belongs to */
	private EntityCoveredWagon wagon;

	public InventoryCoveredWagon(EntityCoveredWagon wagon) {
		this.wagon = wagon;
		this.inventory = new ItemStack[54];
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// validate index to prevent out of bounds exception
		if (index < 0 || index >= this.getSizeInventory()) {
			return null;
		}
		return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (getStackInSlot(index) != null) {
			ItemStack itemStack;

			if (this.getStackInSlot(index).stackSize <= count) {
				itemStack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				return itemStack;
			} else {
				itemStack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0)
					this.setInventorySlotContents(index, null);
				else
					// Just to show that changes happened
					this.setInventorySlotContents(index, this.getStackInSlot(index));

				this.markDirty();
				return itemStack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		if (stack != null && stack.stackSize == 0)
			stack = null;

		this.inventory[index] = stack;
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "Covered Wagon";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return wagon.isDead ? false : wagon.getDistanceSqToEntity(player) <= 16.0D;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}
}
