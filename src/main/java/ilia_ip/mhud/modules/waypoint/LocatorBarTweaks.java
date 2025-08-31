package ilia_ip.mhud.modules.waypoint;

import ilia_ip.mhud.util.Waypoint;
import ilia_ip.mhud.util.Waypoints;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.bar.Bar;
import net.minecraft.client.gui.hud.bar.LocatorBar;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.resource.waypoint.WaypointStyleAsset;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.waypoint.TrackedWaypoint;
import net.minecraft.world.waypoint.WaypointStyles;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;

@Mixin(LocatorBar.class)
public abstract class LocatorBarTweaks implements Bar {


    @Shadow
    @Final
    private static Identifier ARROW_UP;

    @Shadow
    @Final
    private static Identifier ARROW_DOWN;

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("RETURN"), method = "renderAddons")
    private void renderWaypoints(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (client.player == null || client.cameraEntity == null) return;

        Waypoints.WAYPOINTS.stream()
                .sorted(Comparator.comparingDouble(
                        waypoint -> -waypoint.pos().squaredDistanceTo(client.cameraEntity.getPos())
                ))
                .forEachOrdered(waypoint -> renderWaypoint(client, context, getCenterY(client.getWindow()), waypoint));

        if (!client.options.playerListKey.isPressed()) return;

        Text bestText = null;
        double bestYaw = 61;
        for (Waypoint lodestone : Waypoints.WAYPOINTS) {
            double yaw = getRelativeYaw(lodestone.pos(), client.gameRenderer.getCamera());
            double absYaw = Math.abs(yaw);
            if (absYaw < Math.abs(bestYaw)) {
                bestYaw = yaw;
                bestText = lodestone.name();
            }
        }

        if (bestText != null) {
            TextRenderer textRenderer = client.textRenderer;

            int x = getXFromYaw(context, bestYaw) - textRenderer.getWidth(bestText) / 2;
            int width = textRenderer.getWidth(bestText);

            context.fill(x + 5 - 2, getCenterY(client.getWindow()) - 10 - 2, x + width + 5 + 2, getCenterY(client.getWindow()) - 10 + 9 + 2, ColorHelper.withAlpha(0.5F, Colors.BLACK));

            context.drawTextWithShadow(
                    textRenderer,
                    bestText,
                    x + 5,
                    getCenterY(client.getWindow()) - 10,
                    Colors.WHITE
            );
        }
    }

    @Unique
    private static void renderWaypoint(MinecraftClient client, DrawContext context, int centerY, Waypoint lodestone) {
        if (client.player == null || client.cameraEntity == null) return;

        double relativeYaw = getRelativeYaw(lodestone.pos(), client.gameRenderer.getCamera());
        if (relativeYaw <= -61.0 || relativeYaw > 60.0)  return;

        WaypointStyleAsset waypointStyleAsset = client.getWaypointStyleAssetManager().get(WaypointStyles.DEFAULT);

        Identifier identifier = waypointStyleAsset.getSpriteForDistance(
                (float) Math.sqrt(lodestone.pos().squaredDistanceTo(client.cameraEntity.getPos()))
        );
        int color = ColorHelper.withAlpha(255, lodestone.color());

        int x = getXFromYaw(context, relativeYaw);
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, identifier, x, centerY - 2, 9, 9, color);

        TrackedWaypoint.Pitch pitch = getPitch(lodestone.pos(), client.gameRenderer);
        if (pitch != TrackedWaypoint.Pitch.NONE) {
            int yOffset;
            Identifier texture;
            if (pitch == TrackedWaypoint.Pitch.DOWN) {
                yOffset = 6;
                texture = ARROW_DOWN;
            } else {
                yOffset = -6;
                texture = ARROW_UP;
            }

            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, texture, x + 1, centerY + yOffset, 7, 5);
        }
    }

    @Unique
    private static double getRelativeYaw(Vec3d pos, TrackedWaypoint.YawProvider yawProvider) {
        Vec3d vec3d = yawProvider.getCameraPos().subtract(pos).rotateYClockwise();
        float f = (float) MathHelper.atan2(vec3d.getZ(), vec3d.getX()) * (180.0F / (float)Math.PI);
        return MathHelper.subtractAngles(yawProvider.getCameraYaw(), f);
    }

    @Unique
    private static int getXFromYaw(DrawContext context, double relativeYaw) {
        return MathHelper.ceil((context.getScaledWindowWidth() - 9) / 2.0F) + (int)(relativeYaw * 173.0 / 2.0 / 60.0);
    }

    @Unique
    private static TrackedWaypoint.Pitch getPitch(Vec3d pos, TrackedWaypoint.PitchProvider cameraProvider) {
        Vec3d vec3d = cameraProvider.project(pos);
        boolean bl = vec3d.z > 1.0;
        double d = bl ? -vec3d.y : vec3d.y;
        if (d < -1.0) {
            return TrackedWaypoint.Pitch.DOWN;
        } else if (d > 1.0) {
            return TrackedWaypoint.Pitch.UP;
        } else {
            if (bl) {
                if (vec3d.y > 0.0) {
                    return TrackedWaypoint.Pitch.UP;
                }

                if (vec3d.y < 0.0) {
                    return TrackedWaypoint.Pitch.DOWN;
                }
            }

            return TrackedWaypoint.Pitch.NONE;
        }
    }
}
