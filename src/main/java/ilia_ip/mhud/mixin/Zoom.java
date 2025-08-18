package ilia_ip.mhud.mixin;

import com.mojang.logging.LogUtils;
import ilia_ip.mhud.Mhud;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class Zoom {

    @Unique
    private static double ZOOM = 0d;

    @Shadow
    private float getFov(Camera camera, float tickProgress, boolean changingFov) {return 0f;}

    @Inject(at = @At("HEAD"), method = "render")
    private void render(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {

    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;getFov(Lnet/minecraft/client/render/Camera;FZ)F"), method = "renderWorld")
    private float zoom(GameRenderer instance, Camera camera, float tickProgress, boolean changingFov) {
        if (ZOOM < 3 && Mhud.ZOOM_KEYBINDING.isPressed()) {
            ZOOM += 0.1 * ZOOM + 0.1;
        } else if (ZOOM > 0 && !Mhud.ZOOM_KEYBINDING.isPressed()) {
            ZOOM -= 0.1 * ZOOM + 0.1;
        }
        return getFov(camera, tickProgress, changingFov) /  (float) (1+ZOOM);
    }
}
