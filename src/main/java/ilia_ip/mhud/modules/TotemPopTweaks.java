package ilia_ip.mhud.modules;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class TotemPopTweaks {

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;I)V"),
            method = "renderFloatingItem"
    )
    private void renderFloatingItemWithOverlays(MatrixStack matrices, float tickProgress, CallbackInfo ci) {
        if (Mhud.CONFIG.SMALL_TOTEM_POP) matrices.scale(0.2f, 0.2f, 0.2f);
    }
}
