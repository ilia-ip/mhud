package ilia_ip.mhud.mixin;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntityRenderer.class)
public class NamePlateRenderer {
    @Inject(at = @At("HEAD"), method = "hasLabel(Lnet/minecraft/entity/Entity;D)Z", cancellable = true)
    private void renderNameplate(Entity entity, double squaredDistanceToCamera, CallbackInfoReturnable<Boolean> cir) {
        if (!Mhud.enabled("third_person_nameplate")) return;
        if (entity == MinecraftClient.getInstance().player) cir.setReturnValue(MinecraftClient.isHudEnabled());
    }
}
