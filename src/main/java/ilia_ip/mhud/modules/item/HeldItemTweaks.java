package ilia_ip.mhud.modules.item;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(HeldItemRenderer.class)
public class HeldItemTweaks {

    @Unique
    private static final List<Item> UTIL_ITEMS = List.of(
            Items.WIND_CHARGE,

            Items.POTION,
            Items.SPLASH_POTION,
            Items.LINGERING_POTION,

            Items.APPLE,
            Items.MUSHROOM_STEW,
            Items.BREAD,
            Items.PORKCHOP,
            Items.COOKED_PORKCHOP,
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE,
            Items.COD,
            Items.SALMON,
            Items.COOKED_COD,
            Items.COOKED_SALMON,
            Items.COOKIE,
            Items.MELON_SLICE,
            Items.BEEF,
            Items.COOKED_BEEF,
            Items.CHICKEN,
            Items.COOKED_CHICKEN,
            Items.CARROT,
            Items.POTATO,
            Items.BAKED_POTATO,
            Items.GOLDEN_CARROT,
            Items.PUMPKIN_PIE,
            Items.RABBIT,
            Items.COOKED_RABBIT,
            Items.MUTTON,
            Items.COOKED_MUTTON,
            Items.RABBIT_STEW,
            Items.CHORUS_FRUIT,
            Items.BEETROOT,
            Items.BEETROOT_SOUP,
            Items.SUSPICIOUS_STEW,
            Items.SWEET_BERRIES,
            Items.GLOW_BERRIES,
            Items.HONEY_BOTTLE,

            Items.TOTEM_OF_UNDYING,
            Items.EXPERIENCE_BOTTLE,
            Items.ENDER_PEARL,
            Items.FIREWORK_ROCKET
    );

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"),
            method = "renderFirstPersonItem"
    )
    private void renderItems(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {

        if (UTIL_ITEMS.contains(player.getStackInHand(hand).getItem()) && Mhud.CONFIG.SMALL_UTILS) {
            matrices.scale(0.5f, 0.5f, 0.5f);
        }

        boolean using_shield = player.isUsingItem() && player.getActiveItem().getItem() == Items.SHIELD;

        if (player.getStackInHand(hand).getItem() == Items.SHIELD && Mhud.CONFIG.SIDE_SHIELD) {
            if (!using_shield) matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            matrices.translate(0, -0.3, 0);
        }
    }
}
