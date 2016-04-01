package com.shultzy88.wagonsmod.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemLead;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * This is the abstract wagon class for all concrete wagon types and must extend
 * from this class
 * 
 * Uses a factory method to instantate wagon entities
 */
public abstract class EntityWagon extends Entity {
	private String wagonName;
	/** true if no player in wagon */
	protected boolean isWagonEmpty;
	/** true if EntityAnimal array has the required horsepower */
	protected boolean hasSuffientHP;
	protected double speedMultiplier;
	protected double wagonX;
	protected double wagonY;
	protected double wagonZ;
	/** lateral rotation */
	protected double wagonYaw;
	/** vertical rotation */
	protected double wagonPitch;
	/** turn progress */
	protected int dHeading;
	protected double velocityX;
	protected double velocityY;
	protected double velocityZ;
	/** minimum horsepower requirement to move load */
	protected byte minAnimals;
	/** limit for horses can be harnessed to wagon */
	protected byte maxAnimals;
	private WagonTeam team;

	protected static final String __OBFID = "CL_00001667";

	public EntityWagon(World world) {
		super(world);
		this.constructTeam();
		this.hasSuffientHP = false;
		this.stepHeight = 1.0F;
	}

	public EntityWagon(World world, double spawnX, double spawnY, double spawnZ, float wagonYaw) {
		this(world);
		this.setPosition(spawnX, spawnY, spawnZ);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = spawnX;
		this.prevPosY = spawnY;
		this.prevPosZ = spawnZ;
		this.rotationYaw = wagonYaw;
	}

	/**
	 * Sets the list of entities [horses or oxen - not yet implemented] that are
	 * coupled to this entity on entity loading
	 */
	protected void constructTeam() {

		team = new WagonTeam(this);

	}

	public abstract int getWagonType();

	public WagonTeam getTeam() {
		return team;
	}

	/**
	 * Returns the required number of animals to move the wagon
	 * 
	 * @return minAnimals
	 */
	public abstract int getMinimum();

	/**
	 * Returns the upper limit for animals that can be joined to a wagon
	 * 
	 * @return maxAnimals
	 */
	public abstract int getMaximum();

	/**
	 * Called when a wagon item is used from hotbar - has no real use until more
	 * types are added
	 */
	public static EntityWagon createWagon(World world, double spawnX, double spawnY, double spawnZ, int wagonType) {
		return new EntityCoveredWagon(world, spawnX, spawnY, spawnZ, 0);
	}

	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setDamageTaken(float p_70266_1_) {
		dataWatcher.updateObject(19, Float.valueOf(p_70266_1_));
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public float getDamageTaken() {
		return dataWatcher.getWatchableObjectFloat(19);
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit(int p_70265_1_) {
		dataWatcher.updateObject(17, Integer.valueOf(p_70265_1_));
	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit() {
		return dataWatcher.getWatchableObjectInt(17);
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection(int p_70269_1_) {
		dataWatcher.updateObject(18, Integer.valueOf(p_70269_1_));
	}

	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection() {
		return dataWatcher.getWatchableObjectInt(18);
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {
		// should only be executed on server to
		if (!worldObj.isRemote) {
			ExtendedPlayer extPlayer = ExtendedPlayer.getProperties(player);

			List<EntityHorse> animals = player.worldObj.getEntitiesWithinAABB(EntityHorse.class,
					getBoundingBox().expand(5.0d, 3.0d, 5.0d));
			List<EntityHorse> leashedAnimals = new ArrayList();

			// get a list of all horses leashed to player
			for (EntityHorse animal : animals) {
				if (animal instanceof EntityHorse && animal.getLeashed() && animal.getLeashedToEntity() == player) {
					leashedAnimals.add(animal);
				}
			}

			// angle of the player with respect to wagon - from radians to
			// degrees.
			double playerAng = Math.atan2(posZ-player.posZ, posX-player.posX) * 180 / Math.PI;

			/*
			 * To check whether the player is in the front or the back we must
			 * check the angle the player makes with the wagon and compare it to
			 * the yaw.
			 * 
			 * If the player is within 45 degrees to the left or right when
			 * facing the front of the wagon, check if the player has at least
			 * one horse leashed or this entity has a "team" of at least 1 when
			 * the player is holding a lead for the latter. However, if the
			 * player is at least 46 degrees on either side to the local -z, the
			 * player will mount this entity regardless of whether or not the
			 * threshold for the horsepower has been met. NOTE: both conditions
			 * check against the player overriding interaction by sneaking
			 */
			if (MathHelper.abs(rotationYaw - (float) playerAng) < 45 && !player.isSneaking()) {
				// validate that the player can add or remove animals to the
				// "team".
				ItemStack currentItem = player.getHeldItem();
				if (leashedAnimals.size() > 0 || team.size() > 0) {
					/* for single add and remove modes a random animal on the 
					 * interval of [0, x) will be selected (while below capacity)
					 * from the appropriate collection will be selected.
					 * Where x is the number of elements.
					 */
					Random random = new Random();
					switch (extPlayer.getMode()) {
					case 0: // add random
						if (team.size() < maxAnimals && leashedAnimals.size() > 0) {
							EntityHorse randAnimal = leashedAnimals.get(random.nextInt(leashedAnimals.size()));
							randAnimal.clearLeashed(!player.capabilities.isCreativeMode, true);
							setMemberState(randAnimal, true);
							break;
						} else {
							player.mountEntity(this);
							break;
						}
					case 1: // remove random
						if (currentItem != null && currentItem.getItem() instanceof ItemLead && leashedAnimals.size() == currentItem.stackSize) {
							EntityAnimal randAnimal = team.getMember(random.nextInt(team.size()));
							randAnimal.setLeashedToEntity(player, true);
							team.removeMember(randAnimal);
							setMemberState(randAnimal, false);
							break;
						} else {
							player.mountEntity(this);
							break;
						}
						
					case 2: // add all
						if (team.size() < maxAnimals) {
							for (int i = 0; i < leashedAnimals.size(); i++) {
								setMemberState(leashedAnimals.get(i), true);
								leashedAnimals.get(i).clearLeashed(!player.capabilities.isCreativeMode, true);
							}
							break;
						} else {
							player.mountEntity(this);
							break;
						}
					case 3: // remove all
						if (team.size() > 0 && currentItem != null && currentItem.stackSize == team.size()) {
							for (int i = 0; i < team.size(); i++) {
								setMemberState(team.getMember(i), false);
								team.getMember(i).setLeashedToEntity(player, true);
								team.removeMember(team.getMember(i));
							}
							break;
						} else {
							player.mountEntity(this);
							break;
						}

					default:
						player.addChatMessage(new ChatComponentText("Invalid player state"));
						break;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Allows the wagon to move like it is being pulled by a team of horses.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (getTimeSinceHit() > 0) {
			setTimeSinceHit(getTimeSinceHit() - 1);
		}

		if (getDamageTaken() > 0.0F) {
			setDamageTaken(getDamageTaken() - 1.0F);
		}

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		byte b0 = 5;
		double d0 = 0.0D;

		for (int i = 0; i < b0; ++i) {
			double d1 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (double) (i + 0) / (double) b0
					- 0.125D;
			double d3 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (double) (i + 1) / (double) b0
					- 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(boundingBox.minX, d1, boundingBox.minZ,
					boundingBox.maxX, d3, boundingBox.maxZ);

			if (worldObj.isAABBInMaterial(axisalignedbb, Material.grass)) {
				d0 += 1.0D / (double) b0;
			}
		}

		double prevMotionXZ = Math.sqrt(motionX * motionX + motionZ * motionZ);
		double d2;
		double d4;
		int j;

		if (prevMotionXZ > 0.26249999999999996D) {
			d2 = Math.cos((double) rotationYaw * Math.PI / 180.0D);
			d4 = Math.sin((double) rotationYaw * Math.PI / 180.0D);

			for (j = 0; (double) j < 1.0D + prevMotionXZ * 60.0D; ++j) {
				double d5 = (double) (rand.nextFloat() * 2.0F - 1.0F);
				double d6 = (double) (rand.nextInt(2) * 2 - 1) * 0.7D;
				double d8;
				double d9;

				if (rand.nextBoolean()) {
					d8 = posX - d2 * d5 * 0.8D + d4 * d6;
					d9 = posZ - d4 * d5 * 0.8D - d2 * d6;
					worldObj.spawnParticle("splash", d8, posY - 0.125D, d9, motionX, motionY, motionZ);
				} else {
					d8 = posX + d2 + d4 * d5 * 0.7D;
					d9 = posZ + d4 - d2 * d5 * 0.7D;
					worldObj.spawnParticle("splash", d8, posY - 0.125D, d9, motionX, motionY, motionZ);
				}
			}
		}

		double d11;
		double d12;

		// reduce speed to zero if wagon is empty
		if (worldObj.isRemote && isWagonEmpty) {
			if (dHeading > 0) {
				d2 = posX + (wagonX - posX) / (double) dHeading;
				d4 = posY + (wagonY - posY) / (double) dHeading;
				d11 = posZ + (wagonZ - posZ) / (double) dHeading;
				d12 = MathHelper.wrapAngleTo180_double(wagonYaw - (double) rotationYaw);
				rotationYaw = (float) ((double) rotationYaw + d12 / (double) dHeading);
				rotationPitch = (float) ((double) rotationPitch
						+ (wagonPitch - (double) rotationPitch) / (double) dHeading);
				--dHeading;
				setPosition(d2, d4, d11);
				setRotation(rotationYaw, rotationPitch);
			} else {
				d2 = posX + motionX;
				d4 = posY + motionY;
				d11 = posZ + motionZ;
				setPosition(d2, d4, d11);

				if (inWater)
					motionX = motionZ *= 0.5D;

				motionX *= 0.9900000095367432D;
				motionY *= 0.949999988079071D;
				motionZ *= 0.9900000095367432D;
			}
		} else {
			if (d0 < 1.0D) {
				d2 = d0 * 2.0D - 1.0D;
				motionY += 0.03999999910593033D * d2;
			} else {
				if (motionY < 0.0D) {
					motionY /= 2.0D;
				}

				motionY += 0.007000000216066837D;
			}

			hasSuffientHP = team.size() >= minAnimals ? true : false;
			
			team.update();

			// While mounted and has enough horsepower, update motion by the
			// player's rotation. Only change yaw when accelerating.
			if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && hasSuffientHP) {
				EntityPlayer theDriver = (EntityPlayer) riddenByEntity;
				float strafing = riddenByEntity.rotationYaw + -theDriver.moveStrafing * 90.0F;
				motionX += -Math.sin((double) (strafing * (float) Math.PI / 180.0F)) * speedMultiplier
						* (double) theDriver.moveForward * 0.05000000074505806D;
				motionZ += Math.cos((double) (strafing * (float) Math.PI / 180.0F)) * speedMultiplier
						* (double) theDriver.moveForward * 0.05000000074505806D;
			}

			double motionXZ = Math.sqrt(motionX * motionX + motionZ * motionZ);

			if (prevMotionXZ > 0.35D) {
				d4 = 0.35D / prevMotionXZ;
				motionX *= d4;
				motionZ *= d4;
				d2 = 0.35D;
			}

			if (motionXZ > prevMotionXZ && speedMultiplier < 0.35D) {
				speedMultiplier += (0.35D - speedMultiplier) / 35.0D;

				if (speedMultiplier > 0.35D) {
					speedMultiplier = 0.35D;
				}
			} else {
				speedMultiplier -= (speedMultiplier - 0.07D) / 35.0D;

				if (speedMultiplier < 0.07D) {
					speedMultiplier = 0.07D;
				}
			}

			int l;

			moveEntity(motionX, motionY, motionZ);

			motionX *= 0.8900000095367432D;
			motionY *= 0.949999988079071D;
			motionZ *= 0.8900000095367432D;

			rotationPitch = 0.0F;
			d4 = (double) rotationYaw;
			d11 = prevPosX - posX;
			d12 = prevPosZ - posZ;

			if (d11 * d11 + d12 * d12 > 0.001D) {
				d4 = (double) ((float) (Math.atan2(d12, d11) * 180.0D / Math.PI));
			}

			double d7 = MathHelper.wrapAngleTo180_double(d4 - (double) rotationYaw);

			if (d7 > 20.0D) {
				d7 = 20.0D;
			}

			if (d7 < -20.0D) {
				d7 = -20.0D;
			}

			rotationYaw = (float) ((double) rotationYaw + d7);
			setRotation(rotationYaw, rotationPitch);

			if (!worldObj.isRemote) {
				List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
						boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

				if (list != null && !list.isEmpty()) {
					for (int k1 = 0; k1 < list.size(); ++k1) {
						Entity entity = (Entity) list.get(k1);

						if (entity != riddenByEntity && entity.canBePushed() && entity instanceof EntityWagon) {
							entity.applyEntityCollision(this);
						}
					}
				}

				if (riddenByEntity != null && riddenByEntity.isDead) {
					riddenByEntity = null;
				}
			}
		}
	}

	public void setMemberState(EntityAnimal animal, boolean isNew) {
		if (isNew)
			team.addMember(animal);
		else
			team.removeMember(animal);
	}

	public void setWagonName(String entityName) {
		wagonName = entityName;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {

	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(17, new Integer(0));
		dataWatcher.addObject(18, new Integer(1));
		dataWatcher.addObject(19, new Float(0.0));
		dataWatcher.addObject(20, new Integer(0));
		dataWatcher.addObject(21, new Integer(6));
		dataWatcher.addObject(22, Byte.valueOf((byte) 0));
	}
}