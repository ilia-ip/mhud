package ilia_ip.mhud.modules;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class Zoom {

    @Unique
    private static double ZOOM = 0d;

    @Shadow
    private float getFov(Camera camera, float tickProgress, boolean changingFov) {return 0f;}

    @Redirect(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;getFov(Lnet/minecraft/client/render/Camera;FZ)F"),
            method = "renderWorld"
    )
    private float zoom(GameRenderer instance, Camera camera, float tickProgress, boolean changingFov) {
        if (ZOOM < 3 && Mhud.ZOOMING) {
            ZOOM += 0.1 * ZOOM + 0.1;
        } else if (ZOOM > 0 && !Mhud.ZOOMING) {
            ZOOM -= 0.1 * ZOOM + 0.1;
        }
        return getFov(camera, tickProgress, changingFov) /  (float) (1+ZOOM);
    }
}
