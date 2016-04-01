package com.shultzy88.wagonsmod.network.packet;

import com.shultzy88.wagonsmod.main.WagonsMod;
import com.shultzy88.wagonsmod.network.AbstractServerMessageHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

/**
 * Client-side packet class as part of a state machine system. User presses key to send this packet to the server,
 * server responds by saving a tag to the player. Then sends a reply message that the client that will overlay on
 * the screen. 
 * @author Shultzy
 *
 */
public class ModeSelectGui implements IMessage {
	
	private int id;
	
	public ModeSelectGui() {}
	
	public ModeSelectGui(int id) {
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.getByte(id);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(id);
	}
	
	public static class Handler extends AbstractServerMessageHandler<ModeSelectGui> {

		@Override
		public IMessage handleServerMessage(EntityPlayer player, ModeSelectGui message, MessageContext ctx) {
			player.openGui(WagonsMod.INSTANCE, WagonsMod.WAGON_GUI_ID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			return null;
		}	
	}
}
