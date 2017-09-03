package jshultz.wagoncraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHorseCart extends Entity {

    public EntityHorseCart(World world) {
        super(world);
        preventEntitySpawning = true;
        setSize(1.0f, 0.5f);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }
}
