package ilia_ip.mhud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.render.fog.FogRenderer;
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
        int y = 50;

        int text_offset = -20;
        int offset = 50;

        addDrawableChild(
                new TextWidget(x, y+text_offset, 200, 20, Text.translatable("conf.mhud.armor_hud"), textRenderer)
        );
        addDrawableChild(
                ButtonWidget.builder(
                Mhud.CONFIG.ArmorHudEnabled ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.ArmorHudEnabled = !Mhud.CONFIG.ArmorHudEnabled;
                    widget.setMessage(Mhud.CONFIG.ArmorHudEnabled ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
            ).dimensions(x, y, 200, 20).build()
        );

        y+=offset;

        addDrawableChild(
                new TextWidget(x, y+text_offset, 200, 20, Text.translatable("conf.mhud.potion_hud"), textRenderer)
        );
        addDrawableChild(
                ButtonWidget.builder(
                Mhud.CONFIG.PotionHudEnabled ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.PotionHudEnabled = !Mhud.CONFIG.PotionHudEnabled;
                    widget.setMessage(Mhud.CONFIG.PotionHudEnabled ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
            ).dimensions(x, y, 200, 20).build()
        );

        y+=offset;

        addDrawableChild(
                new TextWidget(x, y+text_offset, 200, 20, Text.translatable("conf.mhud.crosshair3dp_hud"), textRenderer)
        );
        addDrawableChild(
                ButtonWidget.builder(
                Mhud.CONFIG.Crosshair3dPerson ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.Crosshair3dPerson = !Mhud.CONFIG.Crosshair3dPerson;
                    widget.setMessage(Mhud.CONFIG.Crosshair3dPerson ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
            ).dimensions(x, y, 200, 20).build()
        );

        y+=offset;

        addDrawableChild(
                new TextWidget(x, y+text_offset, 200, 20, Text.translatable("conf.mhud.crosshairInd_hud"), textRenderer)
        );
        addDrawableChild(
                ButtonWidget.builder(
                Mhud.CONFIG.CrosshairIndicator ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.CrosshairIndicator = !Mhud.CONFIG.CrosshairIndicator;
                    widget.setMessage(Mhud.CONFIG.CrosshairIndicator ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
            ).dimensions(x, y, 200, 20).build()
        );

        y+=offset;

        addDrawableChild(
                new TextWidget(x, y+text_offset, 200, 20, Text.translatable("conf.mhud.small_utils_hud"), textRenderer)
        );
        addDrawableChild(
                ButtonWidget.builder(
                Mhud.CONFIG.SmallUtils ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.SmallUtils = !Mhud.CONFIG.SmallUtils;
                    widget.setMessage(Mhud.CONFIG.SmallUtils ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
            ).dimensions(x, y, 200, 20).build()
        );

        y+=offset;

        addDrawableChild(
                new TextWidget(x, y+text_offset, 200, 20, Text.translatable("conf.mhud.side_shield_hud"), textRenderer)
        );
        addDrawableChild(
                ButtonWidget.builder(
                Mhud.CONFIG.SideShield ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.SideShield = !Mhud.CONFIG.SideShield;
                    widget.setMessage(Mhud.CONFIG.SideShield ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
            ).dimensions(x, y, 200, 20).build()
        );

        y+=offset;

        addDrawableChild(
                new TextWidget(x, y+text_offset, 200, 20, Text.translatable("conf.mhud.nameplate_3dp_hud"), textRenderer)
        );
        addDrawableChild(
            ButtonWidget.builder(
                Mhud.CONFIG.Nameplate3dPerson ? ENABLED : DISABLED,
                (widget) -> {
                    Mhud.CONFIG.Nameplate3dPerson = !Mhud.CONFIG.Nameplate3dPerson;
                    widget.setMessage(Mhud.CONFIG.Nameplate3dPerson ? ENABLED : DISABLED);
                    Mhud.CONFIG.save();
                }
            ).dimensions(x, y, 200, 20).build()
        );

        y+=offset;

        addDrawableChild(
                new TextWidget(x, y+text_offset, 200, 20, Text.translatable("conf.mhud.no_fog"), textRenderer)
        );
        addDrawableChild(
                ButtonWidget.builder(
                        Mhud.CONFIG.NoFog ? ENABLED : DISABLED,
                        (widget) -> {
                            Mhud.CONFIG.NoFog = !Mhud.CONFIG.NoFog;
                            widget.setMessage(Mhud.CONFIG.NoFog ? ENABLED : DISABLED);
                            Mhud.CONFIG.save();
                        }
                ).dimensions(x, y, 200, 20).build()
        );

        y+=offset;

        addDrawableChild(
                new TextWidget(x, y+text_offset, 200, 20, Text.translatable("conf.mhud.fullbright"), textRenderer)
        );
        addDrawableChild(
                ButtonWidget.builder(
                        Mhud.CONFIG.Fullbright ? ENABLED : DISABLED,
                        (widget) -> {
                            Mhud.CONFIG.Fullbright = !Mhud.CONFIG.Fullbright;
                            widget.setMessage(Mhud.CONFIG.Fullbright ? ENABLED : DISABLED);
                            Mhud.CONFIG.save();
                        }
                ).dimensions(x, y, 200, 20).build()
        );
    }
}
