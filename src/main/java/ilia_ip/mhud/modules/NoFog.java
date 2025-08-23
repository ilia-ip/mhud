package ilia_ip.mhud.modules;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.client.render.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FogRenderer.class)
public class NoFog {

    @ModifyVariable(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/MappableRingBuffer;getBlocking()Lcom/mojang/blaze3d/buffers/GpuBuffer;"),
            method = "applyFog(Lnet/minecraft/client/render/Camera;IZLnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;"
    )
    private FogData getFogBuffer(FogData value) {
        if (Mhud.CONFIG.NO_FOG) {
            value.renderDistanceStart
                    = value.renderDistanceEnd
                    = value.environmentalStart
                    = value.environmentalEnd
                    = value.skyEnd
                    = value.cloudEnd
                    = MinecraftClient.getInstance().options.getClampedViewDistance() * 32;
        }
        return value;
    }
}
