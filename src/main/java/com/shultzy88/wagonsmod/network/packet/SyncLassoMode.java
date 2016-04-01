package com.shultzy88.wagonsmod.network.packet;

import com.shultzy88.wagonsmod.entity.ExtendedPlayer;
import com.shultzy88.wagonsmod.network.AbstractClientMessageHandler;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

public class SyncLassoMode implements IMessage {

	private NBTTagCompound playerTag;
	
	public SyncLassoMode() { }
	
	public SyncLassoMode(EntityPlayer player, byte mode) {
		playerTag = new NBTTagCompound();
		ExtendedPlayer.getProperties(player).saveNBTData(playerTag);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		playerTag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, playerTag);
	}

	public static class Handler extends AbstractClientMessageHandler<SyncLassoMode> {

		@Override
		public IMessage handleClientMessage(EntityPlayer player, SyncLassoMode message, MessageContext ctx) {
			ExtendedPlayer playerProps = ExtendedPlayer.getProperties(player);
			
			playerProps.loadNBTData(message.playerTag);
			
			switch (playerProps.getMode()) {
			case 0:
				player.addChatMessage(new ChatComponentText("Interact with wagon to add"));
				break;

			case 1:
				player.addChatMessage(new ChatComponentText("Interact with wagon to remove"));
				break;
				
			case 2:
				player.addChatMessage(new ChatComponentText("Interact with wagon to add all"));
				break;
				
			case 3:
				player.addChatMessage(new ChatComponentText("Interact with wagon to remove all"));
				break;

			default:
				player.addChatMessage(new ChatComponentText("[ERROR] Problem processing input"));
				break;
			}
			return null;
		}
		
	}
}
