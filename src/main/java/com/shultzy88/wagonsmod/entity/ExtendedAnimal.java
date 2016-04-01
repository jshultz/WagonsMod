package com.shultzy88.wagonsmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedAnimal implements IExtendedEntityProperties {

	public static final String EXTENDED_TAG_ID = "ExtendedAnimal";
	private EntityAnimal entity;
	
	private String wagonUUID;
	private boolean isBound;

	enum Property {
		WAGON_UUID("WagonUUID"),
		IS_BOUND("IsBound");
		
		private String propertyId;
		
		Property(String propertyId) {
			this.propertyId = propertyId;
		}
	}
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
		NBTTagCompound properties = new NBTTagCompound();
		
		properties.setString(Property.WAGON_UUID.toString(), wagonUUID);
		properties.setBoolean(Property.IS_BOUND.toString(), isBound);
		
		compound.setTag(EXTENDED_TAG_ID, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		
		wagonUUID = compound.getString(Property.WAGON_UUID.toString());
		isBound = compound.getBoolean(Property.IS_BOUND.toString());
	}
	
	@Override
	public void init(Entity entity, World world) {
		this.entity = (EntityAnimal) entity;
		isBound = false;
	}

	public void setProperties(String wagonUUID, boolean isBound) {
		this.wagonUUID = wagonUUID;
		this.isBound = isBound;
	}
	
	public String getWagonID() { return wagonUUID; }
	
	public boolean isBound() { return isBound; }
	
	public static void register(EntityAnimal animal) { animal.registerExtendedProperties(EXTENDED_TAG_ID, new ExtendedAnimal()); }
	
	public static ExtendedAnimal getProperties(EntityAnimal animal) { return (ExtendedAnimal) animal.getExtendedProperties(EXTENDED_TAG_ID); }

}
