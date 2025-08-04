package ilia_ip.mhud.mixin;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class Crosshair {


    @Unique
    private static final Identifier CROSSHAIR_TEXTURE = Identifier.ofVanilla("hud/crosshair");

    @Unique
    private static final MinecraftClient client = MinecraftClient.getInstance();


    @Inject(at = @At("HEAD"), method = "renderCrosshair")
    private void renderCrosshair3d(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (!client.options.getPerspective().isFirstPerson() && Mhud.CONFIG.Crosshair3dPerson) {
            context.drawGuiTexture(RenderPipelines.CROSSHAIR, CROSSHAIR_TEXTURE, (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() - 15) / 2, 15, 15);
        }
    }

    @Inject(at = @At("TAIL"), method = "renderCrosshair")
    private void renderCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (client.targetedEntity instanceof LivingEntity && client.targetedEntity.isAlive() && Mhud.CONFIG.CrosshairIndicator) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, CROSSHAIR_TEXTURE, (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() - 15) / 2, 15, 15, Colors.GREEN);
        }
    }
}
