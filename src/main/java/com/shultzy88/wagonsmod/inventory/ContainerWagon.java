package com.shultzy88.wagonsmod.inventory;

import com.shultzy88.wagonsmod.entity.EntityCoveredWagon;
import com.shultzy88.wagonsmod.entity.EntityWagon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWagon extends Container {
	private IInventory wagonInv;
	private EntityWagon wagon;

	public ContainerWagon(IInventory playerInv, final EntityCoveredWagon wagon) {
		this.wagonInv = wagon.getInventory();
		this.wagon = wagon;

		// Wagon Inventory Slots 0-53
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 9; col++) {
				this.addSlotToContainer(new Slot(wagonInv, col + row * 9, 8 + col * 18, 16 + row * 18));
			}
		}

		// Player upper inventory Slots 54-80
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				this.addSlotToContainer(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 134 + row * 18));
			}
		}

		// Player hotbar Slots 0-8
		for (int col = 0; col < 9; col++) {
			this.addSlotToContainer(new Slot(playerInv, col, 8 + col * 18, 198));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return wagonInv.isUseableByPlayer(player) && wagon.isEntityAlive()
				&& wagon.getDistanceSqToEntity(player) < 8.0F;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
		// the item to be transferred
		ItemStack previous = null;
		// slot of the item being transferred
		Slot slot = (Slot) inventorySlots.get(fromSlot);

		// check that the slot is valid and the item can be transferred.
		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();
			
			// move stack to player inventory and merge with existing stack (if possible)
			// otherwise move stack to wagon inventory
			if (fromSlot < 54) {
				if (!this.mergeItemStack(current, 54, this.inventorySlots.size(), true))
					return null;
			} else {
				if (!this.mergeItemStack(current, 0, player.inventory.getSizeInventory(), false))
					return null;
			}
			
			if (current.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
		}
		return previous;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		wagonInv.closeInventory();
	}
}
