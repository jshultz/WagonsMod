package jshultz.wagoncraft.client.renderer.entity;

import jshultz.wagoncraft.entity.EntityHorseCart;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderHorseCart extends RenderEntity {

    private static final ResourceLocation TEST_TEXTURE = new ResourceLocation("textures/entity/boat/boat_oak.png");

    public ModelBoat modelTestEntity = new ModelBoat();

    public RenderHorseCart(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 0.5f;
    }

    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        bindEntityTexture(entity);
        modelTestEntity.render(entity, 0.0f, -0.1f, 0.0f, 0.0f, 0.0f, 0.125f);
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityHorseCart entity) {
        return TEST_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityHorseCart> {

        @Override
        public Render<? super EntityHorseCart> createRenderFor(RenderManager renderManager) {
            return new RenderHorseCart(renderManager);
        }
    }
}
