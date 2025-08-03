package ilia_ip.mhud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;


public class ArmorHud implements HudElement {
    public static final Identifier HOTBAR_TEXTURE = Identifier.ofVanilla("hud/hotbar");
    public static final int HOTBAR_SIZE = 22;

    @Override
    public void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (!Mhud.CONFIG.ArmorHudEnabled) return;

        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) return;

        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);

        ArrayList<ItemStack> armor = new ArrayList<>(List.of(helmet, chestplate, leggings, boots));

        if (helmet.isEmpty()) armor.remove(helmet);
        if (chestplate.isEmpty()) armor.remove(chestplate);
        if (leggings.isEmpty()) armor.remove(leggings);
        if (boots.isEmpty()) armor.remove(boots);

        if (armor.isEmpty()) return;

        int offhand_offset = (player.getOffHandStack().isEmpty()) ? 0 : 29;

        int x_start = drawContext.getScaledWindowWidth() / 2 - 91 - 7 - offhand_offset;

        int y = drawContext.getScaledWindowHeight() - 22;
        int x = x_start - 20 * armor.size() - 2;

        // addasdsadasdasd it took soooo long
        drawContext.enableScissor(x, y, x_start, y + 22);
        drawContext.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HOTBAR_TEXTURE, x, y, 182, 22);
        drawContext.disableScissor();

        drawContext.enableScissor(x_start - 2, y, x_start, y + 22);
        drawContext.drawGuiTexture(RenderPipelines.GUI_TEXTURED, HOTBAR_TEXTURE, x_start - 182, y, 182, 22);
        drawContext.disableScissor();


        for (ItemStack itemStack : armor) {
            drawContext.drawItem(itemStack, x + 3, y + 3);
            drawContext.drawStackOverlay(MinecraftClient.getInstance().textRenderer, itemStack, x + 3, y + 3);
            x += 20;
        }
    }
}
