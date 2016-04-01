package com.shultzy88.wagonsmod.network;

import com.shultzy88.wagonsmod.main.WagonsMod;

import akka.io.Tcp.Message;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage>
{
    /**
     * Handle a message received on the client side
     * @return a message to send back to server or null if reply not required.
     */
    public abstract IMessage handleClientMessage(EntityPlayer player, T message, MessageContext ctx);
    
    /**
     * Handle a message received on the server side
     * @return a message to send back to the client or null if reply not required.
     */
    public abstract IMessage handleServerMessage(EntityPlayer player, T message, MessageContext ctx);
    
    /*
     * As soon as the message is received parse the side the message is being processed on as to know
     * automatically without checking the packets and we have easy access to the player object.
     */
    @Override
    public IMessage onMessage(T message, MessageContext ctx)
    {
	if (ctx.side.isClient())
	{
	    return handleClientMessage(WagonsMod.proxy.getPlayerFromContext(ctx), message, ctx);
	} else {
	    return handleServerMessage(WagonsMod.proxy.getPlayerFromContext(ctx), message, ctx);
	}
    }
}
