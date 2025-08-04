package ilia_ip.mhud.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ilia_ip.mhud.Mhud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.entity.Entity;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FogRenderer.class)
public class NoFog {
    @ModifyVariable(method = "applyFog(Lnet/minecraft/client/render/Camera;IZLnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;", at =
    @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/MappableRingBuffer;getBlocking()Lcom/mojang/blaze3d/buffers/GpuBuffer;"))
    private FogData getFogBuffer(FogData value) {
        if (Mhud.CONFIG.NoFog) value.renderDistanceStart = value.renderDistanceEnd = value.environmentalStart = value.environmentalEnd = value.skyEnd = value.cloudEnd =
                MinecraftClient.getInstance().options.getClampedViewDistance() * 32;
        return value;
    }
}
