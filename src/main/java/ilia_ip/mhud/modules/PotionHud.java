package ilia_ip.mhud.modules;

import com.google.common.collect.Ordering;
import ilia_ip.mhud.Mhud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.*;

import java.util.Collection;

@Mixin(InGameHud.class)
public class PotionHud {

    @Final
    @Shadow
    private static Identifier EFFECT_BACKGROUND_AMBIENT_TEXTURE;

    @Final
    @Shadow
    private MinecraftClient client;

    @Final
    @Shadow
    private static Identifier EFFECT_BACKGROUND_TEXTURE;

    /**
     * @author ...
     * @reason welp
     */
    @Overwrite
    private void renderStatusEffectOverlay(DrawContext context, RenderTickCounter tickCounter) {
        if (client.player == null) return;

        Collection<StatusEffectInstance> collection = client.player.getStatusEffects();
        if (!collection.isEmpty() && (client.currentScreen == null || !client.currentScreen.showsStatusEffects())) {
            int good_i = 0;
            int bad_i = 0;

            for(StatusEffectInstance statusEffectInstance : Ordering.natural().reverse().sortedCopy(collection)) {
                RegistryEntry<StatusEffect> registryEntry = statusEffectInstance.getEffectType();

                int x, y;

                if (statusEffectInstance.shouldShowIcon()) {
                    if (Mhud.CONFIG.POTION_HUD) {
                        x = 1;
                        y = context.getScaledWindowHeight() / 3;

                        if ((registryEntry.value()).isBeneficial()) {
                            ++good_i;
                            y += 26 * good_i;
                        } else {
                            ++bad_i;
                            y += 26 * bad_i;
                            x += 25;
                        }
                    } else {

                        x = context.getScaledWindowWidth();
                        y = 1;

                        if (client.isDemo()) {
                            y += 15;
                        }

                        if ((registryEntry.value()).isBeneficial()) {
                            ++good_i;
                            x -= 25 * good_i;
                        } else {
                            ++bad_i;
                            x -= 25 * bad_i;
                            y += 26;
                        }
                    }

                    float f = 1.0F;

                    int color = Colors.WHITE;
                    if (statusEffectInstance.isAmbient()) {
                        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, EFFECT_BACKGROUND_AMBIENT_TEXTURE, x, y, 24, 24);
                    } else {
                        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, EFFECT_BACKGROUND_TEXTURE, x, y, 24, 24);


                        if (statusEffectInstance.isDurationBelow(200)) {
                            color = Colors.LIGHT_RED;
                            int m = statusEffectInstance.getDuration();
                            int n = 10 - m / 20;
                            f = MathHelper.clamp((float)m / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F) + MathHelper.cos((float)m * (float)Math.PI / 5.0F) * MathHelper.clamp((float)n / 10.0F * 0.25F, 0.0F, 0.25F);
                            f = MathHelper.clamp(f, 0.0F, 1.0F);
                        }
                    }

                    context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, InGameHud.getEffectTexture(registryEntry), x + 3, y + 3, 18, 18, ColorHelper.getWhite(f));

                    String duration = getDurationString(statusEffectInstance);

                    if (!statusEffectInstance.isAmbient() && Mhud.CONFIG.POTION_HUD) context.drawText(client.textRenderer, duration, x+4, y+13, color, true);
                }
            }

        }
    }

    @Unique
    private static String getDurationString(StatusEffectInstance statusEffectInstance) {
        String duration;
        if (statusEffectInstance.getDuration()/20 >= 60) {
            if (statusEffectInstance.getDuration()/20/60 >= 60) {
                duration = statusEffectInstance.getDuration()/20/60/60 + "h";
            } else {
                duration = statusEffectInstance.getDuration()/20/60 + "m";
            }
        } else {
            duration = String.valueOf(statusEffectInstance.getDuration()/20);
        }
        return duration;
    }
}
