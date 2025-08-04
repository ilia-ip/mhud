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

@Mixin(InGameHud.class)
public class Crosshair {


    @Unique
    private static final Identifier CROSSHAIR_TEXTURE = Identifier.ofVanilla("hud/crosshair");
    @Unique
    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE = Identifier.ofVanilla("hud/crosshair_attack_indicator_full");
    @Unique
    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.ofVanilla("hud/crosshair_attack_indicator_background");
    @Unique
    private static final Identifier CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.ofVanilla("hud/crosshair_attack_indicator_progress");

    @Unique
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Shadow
    private boolean shouldRenderSpectatorCrosshair(@Nullable HitResult hitResult) {return false;}

    @Shadow
    public boolean shouldRenderCrosshair() {return false;}

    /**
     * @author author
     * @reason reason
     */
    @Overwrite
    private void renderCrosshair(DrawContext context, RenderTickCounter tickCounter) {
        if (client.player == null || client.interactionManager == null) return;

        GameOptions gameOptions = client.options;
        if (gameOptions.getPerspective().isFirstPerson() || Mhud.CONFIG.Crosshair3dPerson) {

            if (client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR || shouldRenderSpectatorCrosshair(client.crosshairTarget)) {

                if (!shouldRenderCrosshair()) {

                    context.createNewRootLayer();

                    if (client.targetedEntity instanceof LivingEntity && client.targetedEntity.isAlive() && Mhud.CONFIG.CrosshairIndicator) {
                        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, CROSSHAIR_TEXTURE, (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() - 15) / 2, 15, 15, Colors.GREEN);
                    } else {
                        context.drawGuiTexture(RenderPipelines.CROSSHAIR, CROSSHAIR_TEXTURE, (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() - 15) / 2, 15, 15);
                    }

                    if (client.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR) {
                        float cooldown = client.player.getAttackCooldownProgress(0.0F);
                        boolean ready_to_attack = false;

                        if (client.targetedEntity instanceof LivingEntity && cooldown >= 1.0F) {
                            ready_to_attack = client.player.getAttackCooldownProgressPerTick() > 5.0F;
                            ready_to_attack &= client.targetedEntity.isAlive();
                        }

                        int y = context.getScaledWindowHeight() / 2 - 7 + 16;
                        int x = context.getScaledWindowWidth() / 2 - 8;


                        if (ready_to_attack) {
                            context.drawGuiTexture(RenderPipelines.CROSSHAIR, CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE, x, y, 16, 16);
                        } else if (cooldown < 1.0F) {
                            int l = (int)(cooldown * 17.0F);
                            context.drawGuiTexture(RenderPipelines.CROSSHAIR, CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, x, y, 16, 4);
                            context.drawGuiTexture(RenderPipelines.CROSSHAIR, CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE, 16, 4, 0, 0, x, y, l, 4);
                        }
                    }
                }

            }
        }
    }
}
