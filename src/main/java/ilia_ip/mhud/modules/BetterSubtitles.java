package ilia_ip.mhud.modules;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.SubtitlesHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SubtitlesHud.class)
public class BetterSubtitles {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V"), method = "render")
    private void render(DrawContext instance, int x1, int y1, int x2, int y2, int color) {
        if (!Mhud.CONFIG.BETTER_SUBTITLES) instance.fill(x1, y1, x2, y2, color);
        // else ignores the call
    }
}
