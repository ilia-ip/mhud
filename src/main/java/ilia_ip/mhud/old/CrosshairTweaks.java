package ilia_ip.mhud.old;

import ilia_ip.mhud.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class CrosshairTweaks {


    @Unique
    private static final Identifier CROSSHAIR_TEXTURE = Identifier.ofVanilla("hud/crosshair");

    @Unique
    private static final MinecraftClient client = MinecraftClient.getInstance();


    @Inject(at = @At("HEAD"), method = "renderCrosshair")
    private void renderCrosshair3d(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (!client.options.getPerspective().isFirstPerson() && Config.enabled("third_person_crosshair")) {
            context.drawGuiTexture(RenderPipelines.CROSSHAIR, CROSSHAIR_TEXTURE, (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() - 15) / 2, 15, 15);
        }
    }

    @Inject(at = @At("TAIL"), method = "renderCrosshair")
    private void renderCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (client.targetedEntity instanceof LivingEntity && client.targetedEntity.isAlive() && Config.enabled("crosshair_indicator")) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, CROSSHAIR_TEXTURE, (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() - 15) / 2, 15, 15, Colors.GREEN);
        }
    }
}
