package com.shultzy88.wagonsmod.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.MathHelper;

public class WagonTeam {
	
	private EntityWagon wagon;
	
	private final float[] radius;
	private final float angularOffset;
	
	private List<EntityAnimal> members;
	
	public WagonTeam(EntityWagon wagon) {
		radius = new float [] { 2.3F, 2.5F };
		angularOffset = 0.5F;
		
		members = new ArrayList<>();
		
		this.wagon = wagon;
		List<EntityHorse> nearbyAnimals = this.wagon.worldObj.getEntitiesWithinAABB(EntityHorse.class, wagon.getBoundingBox().expand(5.D, 3.D, 5.D));
		
		if (!wagon.worldObj.isRemote) {
			
			Iterator iterator = nearbyAnimals.iterator();
			
			// search through the list to find only the animals that have an
			// entity of type EntityEmptyRider and add to members list.
			while (iterator.hasNext()) {
				EntityHorse nextAnimal = (EntityHorse) iterator.next();
				ExtendedAnimal extAnimal = ExtendedAnimal.getProperties(nextAnimal);
				
				if (wagon.getUniqueID().toString().equals(extAnimal.getWagonID())) {
					members.add(nextAnimal);
				}
						
			}
		}
	}
	
	public int size() {
		return members.size();
	}

	public EntityAnimal getMember(int index) {
		return members.get(index);
	}
	
	public void addMember(EntityAnimal member) {
		int slot = members.indexOf(member);
		
		// choose a position from the index of the item
		switch (slot) {
		case 0:
			member.setPositionAndRotation(wagon.posX + MathHelper.cos((float) wagon.rotationYaw + angularOffset) * radius[0],
					wagon.posY, wagon.posZ + MathHelper.sin((float) wagon.rotationYaw + angularOffset), wagon.rotationYaw,
					wagon.rotationPitch);
			break;
		case 1:
			member.setPositionAndRotation(wagon.posX + MathHelper.cos((float) wagon.rotationYaw - angularOffset) * radius[0],
					wagon.posY, wagon.posZ + MathHelper.sin((float) wagon.rotationYaw - angularOffset), wagon.rotationYaw,
					wagon.rotationPitch);
			break;

		case 2:
			member.setPositionAndRotation(wagon.posX + MathHelper.cos((float) wagon.rotationYaw + angularOffset) * radius[1],
					wagon.posY, wagon.posZ + MathHelper.sin((float) wagon.rotationYaw + angularOffset), wagon.rotationYaw,
					wagon.rotationPitch);
			break;

		case 3:
			member.setPositionAndRotation(wagon.posX + MathHelper.cos((float) wagon.rotationYaw + angularOffset) * radius[1],
					wagon.posY, wagon.posZ + MathHelper.sin((float) wagon.rotationYaw + angularOffset), wagon.rotationYaw,
					wagon.rotationPitch);
			break;
			
		default:
			System.err.println("Unable to place horse in desired slot");
			break;
		}
		members.add(member);		
	}
	
	public void removeMember(EntityAnimal member) {
		members.remove(member);
	}
	
	public void update() {
		Iterator iterator = members.iterator();
		
		while (iterator.hasNext()) {
			
			EntityAnimal animal = (EntityAnimal) iterator.next();
			
			animal.setMoveForward(wagon.distanceWalkedModified);
		}
	}
}
