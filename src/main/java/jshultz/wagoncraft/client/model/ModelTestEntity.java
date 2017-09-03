package jshultz.wagoncraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelTestEntity extends ModelBase {

    public ModelRenderer main;

    public ModelTestEntity() {
        main = new ModelRenderer(this, 0, 0);
        main.addBox(0f, -4.0f, 0f, 2, 2, 2, 0.0f);
    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GL11.glPushMatrix();
        main.render(2.0f);
        GL11.glPopMatrix();
    }
}
