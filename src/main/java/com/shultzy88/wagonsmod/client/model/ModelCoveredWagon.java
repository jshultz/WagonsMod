package com.shultzy88.wagonsmod.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import com.shultzy88.wagonsmod.entity.EntityCoveredWagon;

public class ModelCoveredWagon extends ModelBase {
	/** The chassis of the wagon, includes: floor, panels and seat */
	private ModelRenderer chassis;
	/** The front axle section, tongue and yoke */
	private ModelRenderer frontAxle;
	/** The back axle */
	private ModelRenderer backAxle;
	/** The wheel on front left side */
	private ModelRenderer wheel1;
	/** The wheel on front right side */
	private ModelRenderer wheel2;
	/** The wheel on back left side */
	private ModelRenderer wheel3;
	/** The wheel on back right side */
	private ModelRenderer wheel4;
	/** The top cover section of wagon */
	private ModelRenderer coverTop;
	/** The diagonal cover section of wagon */
	private ModelRenderer coverDiag;
	/** The bottom cover section of wagon */
	private ModelRenderer coverBot;

	protected int cycleIndex = 0;

	protected float[] undulationCycle = { 0f, 45f, 90f, 135f, 180f, 225f, 270f, 315f };

	public ModelCoveredWagon() {
		// UV texture map
		textureWidth = 256;
		textureHeight = 128;

		setTextureOffset("wheelfl.hub", 0, 6);
		setTextureOffset("wheelfl.spoke1", 10, 4);
		setTextureOffset("wheelfl.spoke2", 0, 4);
		setTextureOffset("wheelfl.spoke3", 10, 4);
		setTextureOffset("wheelfl.spoke4", 0, 4);
		setTextureOffset("wheelfl.rim1", 0, 0);
		setTextureOffset("wheelfl.rim2", 14, 4);
		setTextureOffset("wheelfl.rim3", 0, 2);
		setTextureOffset("wheelfl.rim4", 18, 4);
		setTextureOffset("wheelfr.hub", 0, 10);
		setTextureOffset("wheelfr.spoke1", 10, 4);
		setTextureOffset("wheelfr.spoke2", 0, 4);
		setTextureOffset("wheelfr.spoke3", 10, 4);
		setTextureOffset("wheelfr.spoke4", 0, 4);
		setTextureOffset("wheelfr.rim1", 0, 0);
		setTextureOffset("wheelfr.rim2", 14, 4);
		setTextureOffset("wheelfr.rim3", 0, 2);
		setTextureOffset("wheelfr.rim4", 18, 4);
		setTextureOffset("wheelbl.hub", 0, 6);
		setTextureOffset("wheelbl.spoke1", 12, 19);
		setTextureOffset("wheelbl.spoke2", 0, 19);
		setTextureOffset("wheelbl.spoke3", 12, 19);
		setTextureOffset("wheelbl.spoke4", 0, 19);
		setTextureOffset("wheelbl.rim1", 0, 15);
		setTextureOffset("wheelbl.rim2", 16, 19);
		setTextureOffset("wheelbl.rim3", 0, 17);
		setTextureOffset("wheelbl.rim4", 20, 19);
		setTextureOffset("wheelbr.hub", 0, 10);
		setTextureOffset("wheelbr.spoke1", 12, 19);
		setTextureOffset("wheelbr.spoke2", 0, 19);
		setTextureOffset("wheelbr.spoke3", 12, 19);
		setTextureOffset("wheelbr.spoke4", 0, 19);
		setTextureOffset("wheelbr.rim1", 0, 15);
		setTextureOffset("wheelbr.rim2", 16, 19);
		setTextureOffset("wheelbr.rim3", 0, 17);
		setTextureOffset("wheelbr.rim4", 20, 19);
		setTextureOffset("frontaxle.main", 0, 0);
		setTextureOffset("frontaxle.tongue", 0, 41);
		setTextureOffset("backaxle.main", 0, 43);
		setTextureOffset("chassis.cover", 54, 0);
		setTextureOffset("chassis.frame", 184, 25);
		setTextureOffset("chassis.footrest", 196, 0);
		setTextureOffset("chassis.backrest", 194, 56);
		setTextureOffset("chassis.leftpanel", 0, 0);
		setTextureOffset("chassis.rightpanel", 0, 0);

		// Model - Base
		chassis = new ModelRenderer(this, "chassis");
		chassis.setRotationPoint(-16f, -6f, 0f);
		chassis.addBox("cover", -2f, -34f, -16f, 42, 32, 32);
		chassis.addBox("frame", -14f, -12f, -16f, 54, 10, 32);
		chassis.addBox("backrest", -8f, -18f, -15f, 1, 6, 30);
		chassis.addBox("footrest", -18f, -8f, -14f, 4, 1, 28);
		chassis.addBox("leftpanel", -14f, -14f, -16f, 12, 2, 1);
		chassis.addBox("rightpanel", -14f, -14f, 15f, 12, 2, 1);
		
		// Mechanics
		frontAxle = new ModelRenderer(this, "frontaxle");
		frontAxle.addBox("main", -1f, -2f, -19f, 2, 3, 38);
		frontAxle.addBox("tongue", -33f, -2f, -0.5f, 32, 1, 1);
		chassis.addChild(frontAxle);

		backAxle = new ModelRenderer(this, "backaxle");
		backAxle.setRotationPoint(15f, -7f, 0f);
		backAxle.addBox("main", -1f, -1f, -19f, 2, 2, 38);

		wheel1 = new ModelRenderer(this, "wheelfl");
		wheel1.addBox("hub", -1f, -1f, -21f, 2, 2, 2);
		wheel1.addBox("spoke1", -0.5f, -5f, -20f, 1, 4, 1);
		wheel1.addBox("spoke2", -5f, -0.5f, -20f, 4, 1, 1);
		wheel1.addBox("spoke3", -0.5f, 1f, -20f, 1, 4, 1);
		wheel1.addBox("spoke4", 1f, -0.5f, -20f, 4, 1, 1);
		wheel1.addBox("rim1", -5f, -6f, -20f, 10, 1, 1);
		wheel1.addBox("rim2", -6f, -5f, -20f, 1, 10, 1);
		wheel1.addBox("rim3", -5f, 5f, -20f, 10, 1, 1);
		wheel1.addBox("rim4", 5f, -5f, -20f, 1, 10, 1);
		frontAxle.addChild(wheel1);

		wheel2 = new ModelRenderer(this, "wheelfr");
		wheel2.addBox("hub", -1f, -1f, 19f, 2, 2, 2);
		wheel2.addBox("spoke1", -0.5f, -5f, 19f, 1, 4, 1);
		wheel2.addBox("spoke2", -5f, -0.5f, 19f, 4, 1, 1);
		wheel2.addBox("spoke3", -0.5f, 1f, 19f, 1, 4, 1);
		wheel2.addBox("spoke4", 1f, -0.5f, 19f, 4, 1, 1);
		wheel2.addBox("rim1", -5f, -6f, 19f, 10, 1, 1);
		wheel2.addBox("rim2", -6f, -5f, 19f, 1, 10, 1);
		wheel2.addBox("rim3", -5f, 5f, 19f, 10, 1, 1);
		wheel2.addBox("rim4", 5f, -5f, 19f, 1, 10, 1);
		frontAxle.addChild(wheel2);

		wheel3 = new ModelRenderer(this, "wheelbl");
		wheel3.addBox("hub", -1f, -1f, -21f, 2, 2, 2);
		wheel3.addBox("spoke1", -0.5f, -6f, -20f, 1, 5, 1);
		wheel3.addBox("spoke2", -6f, -0.5f, -20f, 5, 1, 1);
		wheel3.addBox("spoke3", -0.5f, 1f, -20f, 1, 5, 1);
		wheel3.addBox("spoke4", 1f, -0.5f, -20f, 5, 1, 1);
		wheel3.addBox("rim1", -6f, -7f, -20f, 12, 1, 1);
		wheel3.addBox("rim2", -7f, -6f, -20f, 1, 12, 1);
		wheel3.addBox("rim3", -6f, 6f, -20f, 12, 1, 1);
		wheel3.addBox("rim4", 6f, -6f, -20f, 1, 12, 1);
		backAxle.addChild(wheel3);

		wheel4 = new ModelRenderer(this, "wheelbr");
		wheel4.addBox("hub", -1f, -1f, 19f, 2, 2, 2);
		wheel4.addBox("spoke1", -0.5f, -6f, 19f, 1, 5, 1);
		wheel4.addBox("spoke2", -6f, -0.5f, 19f, 5, 1, 1);
		wheel4.addBox("spoke3", -0.5f, 1f, 19f, 1, 5, 1);
		wheel4.addBox("spoke4", 1f, -0.5f, 19f, 5, 1, 1);
		wheel4.addBox("rim1", -6f, -7f, 19f, 12, 1, 1);
		wheel4.addBox("rim2", -7f, -6f, 19f, 1, 12, 1);
		wheel4.addBox("rim3", -6f, 6f, 19f, 12, 1, 1);
		wheel4.addBox("rim4", 6f, -6f, 19f, 1, 12, 1);
		backAxle.addChild(wheel4);
	}

	@Override
	public void render(Entity entity, float time, float swingSuppress, float par4, float parAngleY, float parAngleX,
			float par7) {
		renderCoveredWagon((EntityCoveredWagon) entity, time, swingSuppress, par4, parAngleY, parAngleX, par7);

	}

	private void renderCoveredWagon(EntityCoveredWagon entity, float time, float swingSuppress, float par4,
			float parAngleY, float parAngleX, float par7) {
		GL11.glPushMatrix();
		chassis.render(par7);
		backAxle.render(par7);
		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float time, float swingSuppress, float par3, float angleY, float angleX, float par6,
			Entity entity) {

	}

	@Override
	protected void setTextureOffset(String textureName, int offsetX, int offsetY) {
		super.setTextureOffset(textureName, offsetX, offsetY);
	}

	/**
	 * Convert degrees to radians
	 * 
	 * @param degrees
	 * @return
	 */
	private float degToRad(float degrees) {
		return degrees * (float) Math.PI / 180;
	}

	protected void setRotation(ModelRenderer model, float rotX, float rotY, float rotZ) {
		model.rotateAngleX = degToRad(rotX);
		model.rotateAngleY = degToRad(rotY);
		model.rotateAngleZ = degToRad(rotZ);
	}

	// spin methods are good for testing and debug rotation points and offsets
	// in the model
	protected void spinX(ModelRenderer model) {
		model.rotateAngleX += degToRad(0.5f);
	}

	protected void spinY(ModelRenderer model) {
		model.rotateAngleY += degToRad(0.5f);
	}

	protected void spinZ(ModelRenderer model) {
		model.rotateAngleZ += degToRad(0.5f);
	}
}
