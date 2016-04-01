package com.shultzy88.wagonsmod.client.gui;

import org.lwjgl.opengl.GL11;

import com.shultzy88.wagonsmod.entity.EntityCoveredWagon;
import com.shultzy88.wagonsmod.inventory.ContainerWagon;
import com.shultzy88.wagonsmod.main.WagonsMod;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiCoveredWagon extends GuiContainer {
	private static final ResourceLocation wagonGuiTexture = new ResourceLocation(
			WagonsMod.MOD_ID + ":textures/gui/container/covered_wagon.png");
	private static int invRows;

	public GuiCoveredWagon(IInventory playerInv, EntityCoveredWagon wagon) {
		super(new ContainerWagon(playerInv, wagon));
		short short1 = 222;
		int i = short1 - 108;
		invRows = wagon.getInventory().getSizeInventory() / 9 ;
		ySize = i + invRows * 18;

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		this.fontRendererObj.drawString("Covered Wagon", 8, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(wagonGuiTexture);
		int guiX = (width - xSize) / 2;
		int guiY = (height - ySize) / 2;
		drawTexturedModalRect(guiX, guiY, 0, 0, xSize, invRows * 18 + 17);
		drawTexturedModalRect(guiX, guiY + invRows * 18 + 17, 0, 126, xSize, 96);
	}

}
