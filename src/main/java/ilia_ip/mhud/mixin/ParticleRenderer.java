package ilia_ip.mhud.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.logging.LogUtils;
import ilia_ip.mhud.Mhud;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Queue;

@Mixin(ParticleManager.class)
public class ParticleRenderer {

    @Unique
    private static final List<Class<? extends Particle>> ALLOWED_PARTICLES = List.of(
            DamageParticle.class,
            SpellParticle.class,
            SweepAttackParticle.class,
            TotemParticle.class,
            FlameParticle.class,
            FireworksSparkParticle.FireworkParticle.class,
            SculkChargeParticle.class,
            FireflyParticle.class
    );

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;render(Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/render/Camera;F)V"),
            method = "renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V",
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void renderParticles(Camera camera, float tickProgress, VertexConsumerProvider.Immediate vertexConsumers, ParticleTextureSheet sheet, Queue<Particle> particles, CallbackInfo ci, @Local(ordinal = 0) Particle particle) {
        if (Mhud.enabled("less_particles") && !ALLOWED_PARTICLES.contains(particle.getClass())) ci.cancel();
    }
}
