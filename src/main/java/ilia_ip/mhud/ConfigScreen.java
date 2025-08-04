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

        TextWidget crosshair3d_text = new TextWidget(x, 180, 200, 20, Text.translatable("conf.mhud.crosshair3dp_hud"), textRenderer);
        ButtonWidget crosshair3d_hud = ButtonWidget.builder(
                Mhud.CONFIG.Crosshair3dPerson ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.Crosshair3dPerson = !Mhud.CONFIG.Crosshair3dPerson;
                    widget.setMessage(Mhud.CONFIG.Crosshair3dPerson ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
        ).dimensions(x, 200, 200, 20).build();

        TextWidget crosshairInd_text = new TextWidget(x, 230, 200, 20, Text.translatable("conf.mhud.crosshairInd_hud"), textRenderer);
        ButtonWidget crosshairInd_hud = ButtonWidget.builder(
                Mhud.CONFIG.CrosshairIndicator ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.CrosshairIndicator = !Mhud.CONFIG.CrosshairIndicator;
                    widget.setMessage(Mhud.CONFIG.CrosshairIndicator ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
        ).dimensions(x, 250, 200, 20).build();

        TextWidget small_utils_text = new TextWidget(x, 280, 200, 20, Text.translatable("conf.mhud.small_utils_hud"), textRenderer);
        ButtonWidget small_utils_hud = ButtonWidget.builder(
                Mhud.CONFIG.SmallUtils ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.SmallUtils = !Mhud.CONFIG.SmallUtils;
                    widget.setMessage(Mhud.CONFIG.SmallUtils ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
        ).dimensions(x, 300, 200, 20).build();

        TextWidget side_shield_text = new TextWidget(x, 330, 200, 20, Text.translatable("conf.mhud.side_shield_hud"), textRenderer);
        ButtonWidget side_shield_hud = ButtonWidget.builder(
                Mhud.CONFIG.SideShield ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.SideShield = !Mhud.CONFIG.SideShield;
                    widget.setMessage(Mhud.CONFIG.SideShield ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
        ).dimensions(x, 350, 200, 20).build();

        TextWidget nameplate3d_text = new TextWidget(x, 380, 200, 20, Text.translatable("conf.mhud.nameplate_3dp_hud"), textRenderer);
        ButtonWidget nameplate3d_hud = ButtonWidget.builder(
                Mhud.CONFIG.Nameplate3dPerson ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.Nameplate3dPerson = !Mhud.CONFIG.Nameplate3dPerson;
                    widget.setMessage(Mhud.CONFIG.Nameplate3dPerson ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
        ).dimensions(x, 400, 200, 20).build();

        ButtonWidget close = ButtonWidget.builder(
                Text.translatable("conf.mhud.close"),
                (widget) -> {
                    MinecraftClient.getInstance().setScreen(null);
                }
        ).dimensions(x, 430, 200, 20).build();

        addDrawableChild(armor_text);
        addDrawableChild(potion_text);
        addDrawableChild(crosshair3d_text);
        addDrawableChild(crosshairInd_text);
        addDrawableChild(crosshair3d_hud);
        addDrawableChild(crosshairInd_hud);
        addDrawableChild(side_shield_hud);
        addDrawableChild(side_shield_text);
        addDrawableChild(small_utils_hud);
        addDrawableChild(nameplate3d_hud);
        addDrawableChild(nameplate3d_text);
        addDrawableChild(small_utils_text);
        addDrawableChild(armor_hud);
        addDrawableChild(potion_hud);
        addDrawableChild(close);
    }
}
