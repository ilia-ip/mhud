package ilia_ip.mhud.modules.freelook;

import ilia_ip.mhud.util.CameraOverriddenEntity;
import ilia_ip.mhud.Mhud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class FreelookCamera {
    @Unique
    boolean firstTime = true;

    @Shadow
    protected void setRotation(float yaw, float pitch) {}

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V", ordinal = 1, shift = At.Shift.AFTER))
    public void lockRotation(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
        if (Mhud.FREELOOKING && focusedEntity instanceof ClientPlayerEntity) {
            CameraOverriddenEntity cameraOverriddenEntity = (CameraOverriddenEntity) focusedEntity;

            if (firstTime && MinecraftClient.getInstance().player != null) {
                cameraOverriddenEntity.mhud$setCameraPitch(MinecraftClient.getInstance().player.getPitch());
                cameraOverriddenEntity.mhud$setCameraYaw(MinecraftClient.getInstance().player.getYaw());
                firstTime = false;
            }
            this.setRotation(cameraOverriddenEntity.mhud$getCameraYaw(), cameraOverriddenEntity.mhud$getCameraPitch());

        }
        if (!Mhud.FREELOOKING && focusedEntity instanceof ClientPlayerEntity) {
            firstTime = true;
        }
    }
}
