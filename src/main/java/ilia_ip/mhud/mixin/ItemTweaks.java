package ilia_ip.mhud.mixin;

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
public class ItemTweaks {

    @Unique
    private static final List<Item> UTIL_ITEMS = List.of(
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE,

            Items.WIND_CHARGE,

            Items.POTION,
            Items.SPLASH_POTION,

            Items.GOLDEN_CARROT,
            Items.CARROT,
            Items.COOKED_BEEF,
            Items.BREAD,

            Items.TOTEM_OF_UNDYING,
            Items.EXPERIENCE_BOTTLE,
            Items.ENDER_PEARL,
            Items.FIREWORK_ROCKET
    );

    @Unique
    private static final List<Item> ITEMS = List.of(
            Items.SHIELD,

            Items.DIAMOND_AXE,
            Items.NETHERITE_AXE,
            Items.IRON_AXE,
            Items.STONE_AXE,
            Items.WOODEN_AXE,

            Items.NETHERITE_SWORD,
            Items.DIAMOND_SWORD,
            Items.IRON_SWORD,
            Items.STONE_SWORD,
            Items.WOODEN_SWORD,

            Items.CROSSBOW,
            Items.BOW,

            Items.MACE,
            Items.TRIDENT
    );



    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void scaleItems(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {

        if (!ITEMS.contains(player.getStackInHand(hand).getItem()) && Mhud.enabled("small_items")) {
            matrices.scale(0.5f, 0.5f, 0.5f);
        } else if (UTIL_ITEMS.contains(player.getStackInHand(hand).getItem()) && Mhud.enabled("small_utils")) {
            matrices.scale(0.5f, 0.5f, 0.5f);
        }

        boolean using_shield = player.isUsingItem() && player.getActiveItem().getItem() == Items.SHIELD;
        if (player.getStackInHand(hand).getItem() == Items.SHIELD && Mhud.enabled("side_shield")) {
            if (!using_shield) matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            matrices.translate(0, -0.3, 0);
        }
    }
}
