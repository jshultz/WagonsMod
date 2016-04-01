package com.shultzy88.wagonsmod.entity;

import java.util.*;

import com.shultzy88.wagonsmod.main.WagonsMod;
import com.shultzy88.wagonsmod.network.*;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class EntityCoveredWagon extends EntityWagon {
	/** Represents the inventory of the wagon and has 54 slots. */
	private InventoryCoveredWagon wagonInv;

	public EntityCoveredWagon(World world) {
		super(world);
		this.isWagonEmpty = true;
		this.speedMultiplier = 0.07D;
		this.preventEntitySpawning = true;
		this.setSize(2.96F, 2.8F);
		this.minAnimals = 2;
		this.maxAnimals = 4;
		this.wagonInv = new InventoryCoveredWagon(this);
	}

	public EntityCoveredWagon(World world, double spawnX, double spawnY, double spawnZ, float rotationYaw) {
		super(world, spawnX, spawnY, spawnZ, rotationYaw);
		this.isWagonEmpty = true;
		this.speedMultiplier = 0.07D;
		this.preventEntitySpawning = true;
		this.minAnimals = 2;
		this.maxAnimals = 4;
		this.wagonInv = new InventoryCoveredWagon(this);
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return false;
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like boats or
	 * minecarts.
	 */
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.boundingBox;
	}

	/**
	 * returns the bounding box for this entity
	 */
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	public boolean canBePushed() {
		return false;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding
	 * this one.
	 */
	public double getMountedYOffset() {
		return (double) height * 0.0D - 0.30000001192092896D;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource damageSource, float damageMultiplier) {
		if (isEntityInvulnerable()) {
			return false;
		} else if (!worldObj.isRemote && !isDead) {
			setForwardDirection(-getForwardDirection());
			setTimeSinceHit(10);
			setDamageTaken(getDamageTaken() + damageMultiplier * 10.0F);
			setBeenAttacked();
			boolean flag = damageSource.getEntity() instanceof EntityPlayer
					&& ((EntityPlayer) damageSource.getEntity()).capabilities.isCreativeMode;

			if (flag || getDamageTaken() > 40.0F) {
				if (riddenByEntity != null) {
					riddenByEntity.mountEntity(this);
				}

				setDead();
			}

			return true;
		} else {
			return true;
		}
	}

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in
	 * multiplayer.
	 */
	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		setForwardDirection(-getForwardDirection());
		setTimeSinceHit(10);
		setDamageTaken(getDamageTaken() * 11.0F);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	/**
	 * Sets the position and rotation. Only difference from the other one is no
	 * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
	 */
	public void setPositionAndRotation2(double posX, double posY, double posZ, float yaw, float pitch, int p_70056_9_) {
		if (isWagonEmpty) {
			dHeading = p_70056_9_ + 5;
		} else {
			double d3 = posX - posX;
			double d4 = posY - posY;
			double d5 = posZ - posZ;
			double d6 = d3 * d3 + d4 * d4 + d5 * d5;

			if (d6 <= 1.0D) {
				return;
			}

			dHeading = 3;
		}

		wagonX = posX;
		wagonY = posY;
		wagonZ = posZ;
		wagonYaw = (double) yaw;
		wagonPitch = (double) pitch;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}

	@Override
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
		super.setVelocity(p_70016_1_, p_70016_3_, p_70016_5_);
	}

	/**
	 * Overrides default position of rider, that being the player that controls
	 * the entity to be be in the front of the wagon.
	 */
	public void updateRiderPosition() {
		if (riddenByEntity != null) {
			double offsetX = Math.cos(((double) rotationYaw * Math.PI / 180.0D) - 0.25D) * -1.8D;
			double offsetZ = Math.sin(((double) rotationYaw * Math.PI / 180.0D) - 0.25D) * -1.8D;
			riddenByEntity.setPosition(posX + offsetX, posY + getMountedYOffset() + riddenByEntity.getYOffset() + 1.5D,
					posZ + offsetZ);
		}
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		
		if (!player.isSneaking()) {
			return super.interactFirst(player);
		} else {
			player.openGui(WagonsMod.INSTANCE, WagonsMod.WAGON_GUI_ID, worldObj, (int) posX, (int) posY, (int) posZ);
			return true;
		}
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	public int getMinimum() {
		return minAnimals;
	}

	public int getMaximum() {
		return maxAnimals;
	}

	/**
	 * Takes in the distance the entity has fallen this tick and whether its on
	 * the ground to update the fall distance and deal fall damage if landing on
	 * the ground. Args: distanceFallenThisTick, onGround
	 */
	protected void updateFallState(double distanceFallenThisTick, boolean onGround) {
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);

		if (onGround) {
			if (fallDistance > 5.0F) {
				fall(fallDistance);

				if (!worldObj.isRemote && !isDead) {
					setDead();
					int l;

					for (l = 0; l < 3; ++l) {
						func_145778_a(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
					}

					for (l = 0; l < 2; ++l) {
						func_145778_a(Items.stick, 1, 0.0F);
					}

					for (l = 0; l < 2; ++l) {
						func_145778_a(Item.getItemFromBlock(Blocks.wool), 1, 0.0F);
					}
				}

				fallDistance = 0.0F;
			}
		} else if (worldObj.getBlock(i, j - 1, k).getMaterial() != Material.water && distanceFallenThisTick < 0.0D) {
			fallDistance = (float) ((double) fallDistance - distanceFallenThisTick);
		}
	}

	/**
	 * true if no player in wagon
	 */
	public void setIsWagonEmpty(boolean isEmpty) {
		isWagonEmpty = isEmpty;
	}

	@Override
	public int getWagonType() {
		return 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		// for every ItemStack not null in inventory write to NBT
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < wagonInv.getSizeInventory(); i++) {
			if (wagonInv.getStackInSlot(i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				wagonInv.getStackInSlot(i).writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		compound.setTag("Items", list);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		NBTTagList list = compound.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			wagonInv.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}
	}

	public IInventory getInventory() {
		return wagonInv;
	}
}