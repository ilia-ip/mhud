package ilia_ip.mhud.config;

import ilia_ip.mhud.Mhud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends Screen {

    public ConfigScreen() {
        super(Text.translatable("conf.mhud.title"));
    }

    @Override
    protected void init() {
        int x = 50;
        int y = 50;

        String category = "";
        for (Field field : fields()) {
            if (!category.equals(getCategory(field.getName()))) {
                addDrawableChild(new TextWidget(
                        x,
                        y + 10,
                        200,
                        20,
                        Text.translatable("conf.category.mhud." + getCategory(field.getName())),
                        textRenderer
                ));
                y += 30;
                Mhud.LOGGER.error(category);
                category = getCategory(field.getName());
            }
            addDrawableChild(
                    ButtonWidget.builder(
                            keyText(field.getName()),
                            (widget) -> {

                                    setBool(field.getName(), !getBool(field.getName()));

                                    widget.setMessage(keyText(field.getName()));
                                    Mhud.CONFIG.save();
                            }
                    ).dimensions(x, y, 200, 20).build()
            );

            if (y >= height - 70) {
                y = 50;
                x += 250;
            } else {
                y += 25;
            }
        }
    }

    public List<Field> fields() {
        ArrayList<Field> fields = new ArrayList<>();

        for (Field field : Config.class.getFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Config.ConfigOption.class)) {
                fields.add(field);
            }
        }

        return fields;
    }

    private boolean getBool(String key) {
        try {
            return (boolean)Config.class.getField(key).get(Mhud.CONFIG);
        } catch (Exception e) {
            return false;
        }
    }

    private void setBool(String key, boolean value) {
        try {
            Config.class.getField(key).set(Mhud.CONFIG, value);
        } catch (Exception e) {
            // e
        }
    }

    private String getName(String key) {
        try {
            return Config.class.getField(key).getAnnotation(Config.ConfigOption.class).name();
        } catch (Exception e) {
            return "";
        }
    }

    private String getCategory(String key) {
        try {
            return Config.class.getField(key).getAnnotation(Config.ConfigOption.class).category();
        } catch (Exception e) {
            return "";
        }
    }

    private Text keyText(String key) {
        return ScreenTexts.composeToggleText(Text.translatable("conf.mhud." + getName(key)), getBool(key));
    }
}
