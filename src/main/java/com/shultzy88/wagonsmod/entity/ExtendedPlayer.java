package com.shultzy88.wagonsmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties {

	/** name of tag that will store our data pervasively */
	public static final String EXT_PROP_NAME = "ExtendedPlayer";
	/** player these properties are using */
	private EntityPlayer player;
	/** used to determine what should happen when the player interacts with a wagon while holding a lasso */
	private byte mode;

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound playerProperties = new NBTTagCompound();
		
		playerProperties.setByte("LassoMode", mode);
		compound.setTag(EXT_PROP_NAME, playerProperties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound playerProperties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		
		playerProperties.getByte("LassoMode");
	}

	@Override
	public void init(Entity player, World world) {
		this.player = (EntityPlayer) player;
		this.mode = LassoMode.ADD.mode;
	}
	
	public enum LassoMode {
		ADD("add", (byte) 0),
		REMOVE("remove", (byte) 1),
		ADD_ALL("add_all", (byte) 2),
		REMOVE_ALL("remove_all", (byte) 3),
		MOUNT("mount", (byte) 4);
		
		private final String modeName;
		private final byte mode;
		
		LassoMode(String modeName, byte modeID) {
			this.modeName = modeName;
			this.mode = modeID;
		}
		
		public String getMode() { return modeName; }
		public int getModeId() { return mode; }
	}
	
	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(EXT_PROP_NAME, new ExtendedPlayer());
	}

	public static final ExtendedPlayer getProperties(EntityPlayer player) {
		return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}

	public int getMode() {
		return mode;
	}

}
