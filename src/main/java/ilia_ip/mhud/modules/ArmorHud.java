package ilia_ip.mhud.modules;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(InGameHud.class)
public class ArmorHud {

    @Final
    @Shadow
    private static Identifier HOTBAR_TEXTURE;

    @Inject(at = @At("HEAD"), method = "renderHotbar")
    private void renderArmorHud(DrawContext drawContext, RenderTickCounter tickCounter, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null || !Mhud.CONFIG.ARMOR_HUD) return;

        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);

        ArrayList<ItemStack> armor = new ArrayList<>();

        if (!helmet.isEmpty()) armor.add(helmet);
        if (!chestplate.isEmpty()) armor.add(chestplate);
        if (!leggings.isEmpty()) armor.add(leggings);
        if (!boots.isEmpty()) armor.add(boots);

        if (armor.isEmpty()) return;

        int OFFSET = player.getOffHandStack().isEmpty() ? 7 : 36;
        
        int HOTBAR_WIDTH = 182;
        int HOTBAR_HEIGHT = 22;

        int SLOT_SIZE = 20;

        int END_X = (drawContext.getScaledWindowWidth() / 2) - (HOTBAR_WIDTH / 2) - OFFSET;
        int END_Y = drawContext.getScaledWindowHeight();

        int START_Y = END_Y - HOTBAR_HEIGHT;
        int START_X = END_X - armor.size() * SLOT_SIZE - 2;

        int HALF_X = END_X - (armor.size() * SLOT_SIZE) / 2;

        drawContext.enableScissor(START_X, START_Y, HALF_X, END_Y);
        drawContext.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HOTBAR_TEXTURE, START_X, START_Y, HOTBAR_WIDTH, HOTBAR_HEIGHT);
        drawContext.disableScissor();

        drawContext.enableScissor(HALF_X, START_Y, END_X, END_Y);
        drawContext.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HOTBAR_TEXTURE, END_X - HOTBAR_WIDTH, START_Y, HOTBAR_WIDTH, HOTBAR_HEIGHT);
        drawContext.disableScissor();

        int ITEM_Y = START_Y + 3;
        int x = START_X + 3;

        for (ItemStack piece : armor) {
            drawContext.drawItem(piece, x, ITEM_Y);
            drawContext.drawStackOverlay(MinecraftClient.getInstance().textRenderer, piece, x, ITEM_Y);
            x += SLOT_SIZE;
        }
    }

}
