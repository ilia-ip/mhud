package ilia_ip.mhud.modules.item;

import ilia_ip.mhud.Mhud;
import ilia_ip.mhud.util.TickToTime;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Colors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.List;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemTweaks {

    @Unique
    private static final List<Item> IMPORTANT_ITEMS = List.of(
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE,

            Items.NETHERITE_INGOT,
            Items.DIAMOND,
            Items.DIAMOND_BLOCK,
            Items.NETHERITE_BLOCK,

            Items.MACE,
            Items.TRIDENT,

            Items.TRIAL_KEY,
            Items.OMINOUS_TRIAL_KEY
    );

    @Unique
    private ItemEntity itemEntity;

    @Inject(
            at = @At("HEAD"),
            method = "updateRenderState(Lnet/minecraft/entity/ItemEntity;Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;F)V"
    )
    private void updateRenderState(ItemEntity itemEntity, ItemEntityRenderState itemEntityRenderState, float f, CallbackInfo ci) {
        this.itemEntity = itemEntity;
    }

    @Inject(
            at = @At("HEAD"),
            method = "render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
    )
    private void render(ItemEntityRenderState itemEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        int big = 2;
        if (Mhud.CONFIG.BIG_IMPORTANT_ITEMS && itemEntity.getStack().getItem() != null && IMPORTANT_ITEMS.contains(itemEntity.getStack().getItem())) {
            matrixStack.scale(2, 2, 2);
            big = 3;
        }


        if (!Mhud.CONFIG.DESPAWN_TIMER || itemEntity.distanceTo(MinecraftClient.getInstance().player) > 10) return;
        String str = TickToTime.convert(6000 - itemEntity.getItemAge());

        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        matrixStack.push();
        matrixStack.translate(itemEntity.getX() - camera.getPos().x, itemEntity.getY() - camera.getPos().y + big, itemEntity.getZ() - camera.getPos().z);
        matrixStack.multiply(camera.getRotation());
        matrixStack.scale(0.05f, -0.05f, 0.05f);

        float offset = (float) -textRenderer.getWidth(str) / 2;

        textRenderer.draw(str, offset, 0, Colors.WHITE, true, matrixStack.peek().getPositionMatrix(), vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
        matrixStack.pop();
    }
}