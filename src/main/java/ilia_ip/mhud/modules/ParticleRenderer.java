package ilia_ip.mhud.modules;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ParticleManager.class)
public class ParticleRenderer {

    @Unique
    private static final List<Class<? extends Particle>> ALLOWED_PARTICLES = List.of(
            DamageParticle.class,
            SpellParticle.class,
            SweepAttackParticle.class,
            TotemParticle.class,
            FireflyParticle.class,
            FlameParticle.class,
            WaterSuspendParticle.class
    );

    @Unique
    private static final List<Class<? extends Particle>> NOT_ALLOWED_PARTICLES = List.of(
            ExplosionLargeParticle.class,
            ExplosionSmokeParticle.class,
            BlockDustParticle.class
    );

    @Redirect(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;render(Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/render/Camera;F)V"),
            method = "renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V"
    )
    private static void renderParticle(Particle instance, VertexConsumer vertexConsumer, Camera camera, float v) {
        if (Mhud.CONFIG.LESS_PARTICLES && NOT_ALLOWED_PARTICLES.contains(instance.getClass())) return;

        if (Mhud.CONFIG.NO_PARTICLES && !ALLOWED_PARTICLES.contains(instance.getClass())) return;

        instance.render(vertexConsumer, camera, v);
    }


}
