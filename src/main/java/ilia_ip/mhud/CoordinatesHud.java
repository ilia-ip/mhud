package ilia_ip.mhud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

public class CoordinatesHud implements HudElement {
    @Override
    public void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        drawContext.fill(10, 10, 150, 150, client.options.getTextBackgroundColor(0.4F));

        int x = 20;
        int y = 20;

        drawContext.drawText(client.textRenderer, Text.of("X: " + Math.floor(client.player.getX()*100)/100), x, y, Colors.WHITE, false);
        drawContext.drawText(client.textRenderer, Text.of("Y: " + Math.floor(client.player.getY()*100)/100), x, y+20, Colors.WHITE, false);
        drawContext.drawText(client.textRenderer, Text.of("Z: " + Math.floor(client.player.getZ()*100)/100), x, y+40, Colors.WHITE, false);

    }
}
