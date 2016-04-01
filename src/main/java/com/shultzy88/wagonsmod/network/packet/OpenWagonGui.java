package com.shultzy88.wagonsmod.network.packet;

import com.shultzy88.wagonsmod.main.WagonsMod;
import com.shultzy88.wagonsmod.network.AbstractServerMessageHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class OpenWagonGui implements IMessage {

	/** identifier of the GUI */
	private int id;
	
	public OpenWagonGui() {}
	
	public OpenWagonGui(int id) {
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
	}
	
	public static class Handler extends AbstractServerMessageHandler<OpenWagonGui> {
		
		@Override
		public IMessage handleServerMessage(EntityPlayer player, OpenWagonGui message, MessageContext ctx) {
			player.openGui(WagonsMod.INSTANCE, message.id, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			return null;

		}
	}
}
