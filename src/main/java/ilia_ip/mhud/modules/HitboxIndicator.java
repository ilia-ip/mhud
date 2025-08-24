package ilia_ip.mhud.modules;

import com.google.common.collect.ImmutableList;
import ilia_ip.mhud.Mhud;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityHitbox;
import net.minecraft.client.render.entity.state.EntityHitboxAndView;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class HitboxIndicator {

    @Shadow
    public Entity targetedEntity;

    @Unique
    public Vec3d prev_pos = Vec3d.ZERO;

    @Inject(at = @At(value = "HEAD"), method = "render(Lnet/minecraft/client/render/entity/state/EntityRenderState;DDDLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V")
    private void render(EntityRenderState state, double x, double y, double z, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderer<?, EntityRenderState> renderer, CallbackInfo ci) {
        if (targetedEntity == null || state.hitbox == null || !Mhud.CONFIG.HITBOX_INDICATOR) return;

        if (new Box(prev_pos.subtract(0.5), targetedEntity.getPos().add(0.5)).contains(state.x, state.y, state.z)) {

            ImmutableList<EntityHitbox> new_hitboxes = ImmutableList.copyOf(state.hitbox.hitboxes().stream().map(
                    (box) -> new EntityHitbox(box.x0(), box.y0(), box.z0(), box.x1(), box.y1(), box.z1(), Integer.MAX_VALUE, 0, 0)
            ).toList());

            state.hitbox = new EntityHitboxAndView(state.hitbox.viewX(), state.hitbox.viewY(), state.hitbox.viewZ(), new_hitboxes);
        }
        prev_pos = targetedEntity.getPos();
    }
}
