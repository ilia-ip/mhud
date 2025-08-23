package ilia_ip.mhud.modules.freelook;

import ilia_ip.mhud.util.CameraOverriddenEntity;
import ilia_ip.mhud.Mhud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class FreelookEntity implements CameraOverriddenEntity {
    @Unique
    private float cameraPitch;

    @Unique
    private float cameraYaw;

    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    public void changeCameraLookDirection(double xDelta, double yDelta, CallbackInfo ci) {
        //noinspection ConstantValue
        if (Mhud.FREELOOKING && (Object) this instanceof ClientPlayerEntity) {
            double pitchDelta = (yDelta * 0.15);
            double yawDelta = (xDelta * 0.15);

            this.cameraPitch = MathHelper.clamp(this.cameraPitch + (float) pitchDelta, -90.0f, 90.0f);
            this.cameraYaw += (float) yawDelta;

            ci.cancel();

        }
    }

    @Override
    @Unique
    public float mhud$getCameraPitch() {
        return this.cameraPitch;
    }

    @Override
    @Unique
    public float mhud$getCameraYaw() {
        return this.cameraYaw;
    }

    @Override
    @Unique
    public void mhud$setCameraPitch(float pitch) {
        this.cameraPitch = pitch;
    }

    @Override
    @Unique
    public void mhud$setCameraYaw(float yaw) {
        this.cameraYaw = yaw;
    }
}
