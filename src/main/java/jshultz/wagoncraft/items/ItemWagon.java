package jshultz.wagoncraft.items;

import jshultz.wagoncraft.entity.EntityHorseCart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWagon extends Item {

    public ItemWagon(String name) {
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        final EntityHorseCart entityHorseCart = new EntityHorseCart(worldIn);
        createEntity(worldIn, entityHorseCart, stack);
        worldIn.spawnEntityInWorld(entityHorseCart);
        return EnumActionResult.PASS;
    }
}
