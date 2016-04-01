package com.shultzy88.wagonsmod.network;

import com.shultzy88.wagonsmod.main.WagonsMod;
import com.shultzy88.wagonsmod.network.packet.ModeSelectGui;
import com.shultzy88.wagonsmod.network.packet.OpenWagonGui;
import com.shultzy88.wagonsmod.network.packet.SyncLassoMode;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketDispatcher {
	private static byte packetId = 0;

	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(WagonsMod.MOD_ID);

	public static final void register() {
		// This simplifies the process of packet registration by incrementing
		// the ID for each new packet registered.
		// Which is called from the pre-initialization phase in the CommonProxy
		// class.
		registerMessage(OpenWagonGui.Handler.class, OpenWagonGui.class, Side.SERVER);
		registerMessage(SyncLassoMode.Handler.class, SyncLassoMode.class, Side.CLIENT);
		registerMessage(ModeSelectGui.Handler.class, ModeSelectGui.class, Side.SERVER);
	}

	private static final void registerMessage(Class handlerClass, Class messageClass, Side side) {
		dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
	}

	// --=Wrapper Methods=-- //

	/**
	 * Send this message to a specific player. <br>
	 * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	 */
	public static final void sendTo(IMessage message, EntityPlayerMP player) {
		dispatcher.sendTo(message, player);
	}

	/**
	 * Send this message to all players. <br>
	 * See {@link SimpleNetworkWrapper#sendToAll(IMessage)}
	 */
	public static final void sendToAll(IMessage message) {
		dispatcher.sendToAll(message);
	}

	/**
	 * Send this message to all players within range of a given point in the
	 * same dimension. <br>
	 * See {@link SimpleNetworkWrapper#sendToAllAround(IMessage, TargetPoint)
	 */
	public static final void sendToAllAround(IMessage message, TargetPoint point) {
		dispatcher.sendToAllAround(message, point);
	}

	/**
	 * Send this message to all players in a specific dimension. <br>
	 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
	 */
	public static final void sendToDimension(IMessage message, int dimensionId) {
		dispatcher.sendToDimension(message, dimensionId);
	}

	/**
	 * Send this message to the server. <br>
	 * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
	 */
	public static final void sendToServer(IMessage message) {
		dispatcher.sendToServer(message);
	}
}
