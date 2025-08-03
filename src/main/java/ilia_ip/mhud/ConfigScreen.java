package ilia_ip.mhud;

import com.mojang.logging.LogUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {

    public static final Text ENABLED = Text.translatable("conf.mhud.enabled");
    public static final Text DISABLED = Text.translatable("conf.mhud.disabled");

    protected ConfigScreen() {
        super(Text.of("Mhud Config"));
    }

    @Override
    protected void init() {
        int x = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 - 100;

        TextWidget armor_text = new TextWidget(x, 80, 200, 20, Text.translatable("conf.mhud.armor_hud"), textRenderer);
        ButtonWidget armor_hud = ButtonWidget.builder(
                Mhud.CONFIG.ArmorHudEnabled ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.ArmorHudEnabled = !Mhud.CONFIG.ArmorHudEnabled;
                    widget.setMessage(Mhud.CONFIG.ArmorHudEnabled ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
        ).dimensions(x, 100, 200, 20).build();

        TextWidget potion_text = new TextWidget(x, 130, 200, 20, Text.translatable("conf.mhud.potion_hud"), textRenderer);
        ButtonWidget potion_hud = ButtonWidget.builder(
                Mhud.CONFIG.PotionHudEnabled ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.PotionHudEnabled = !Mhud.CONFIG.PotionHudEnabled;
                    widget.setMessage(Mhud.CONFIG.PotionHudEnabled ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
        ).dimensions(x, 150, 200, 20).build();

        ButtonWidget close = ButtonWidget.builder(
                Text.translatable("conf.mhud.close"),
                (widget) -> {
                    MinecraftClient.getInstance().setScreen(null);
                }
        ).dimensions(x, 180, 200, 20).build();

        addDrawableChild(armor_text);
        addDrawableChild(potion_text);
        addDrawableChild(armor_hud);
        addDrawableChild(potion_hud);
        addDrawableChild(close);
    }
}
