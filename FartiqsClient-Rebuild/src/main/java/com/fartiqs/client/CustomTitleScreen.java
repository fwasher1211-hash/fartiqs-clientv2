package com.fartiqs.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class CustomTitleScreen extends Screen {
    public CustomTitleScreen() { super(Text.literal("Fartiqs Client")); }

    @Override
    protected void init() {
        int x = width / 2 - 90;
        int y = height / 2 - 8;
        int w = 180;
        int h = 30;
        addDrawableChild(ButtonWidget.builder(Text.literal("Singleplayer"), b -> client.setScreen(new SelectWorldScreen(this))).dimensions(x, y, w, h).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Multiplayer"), b -> client.setScreen(new MultiplayerScreen(this))).dimensions(x, y + 36, w, h).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Realms"), b -> client.setScreen(new net.minecraft.client.realms.gui.screen.RealmsMainScreen(this))).dimensions(x, y + 72, w, h).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Mods"), b -> client.setScreen(new FartiqsScreen())).dimensions(x, y + 108, w, h).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Options"), b -> client.setScreen(new OptionsScreen(this, client.options))).dimensions(x, y + 144, w, h).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Fartiqs Settings"), b -> client.setScreen(new FartiqsSettingsScreen(this))).dimensions(x, y + 180, w, h).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fillGradient(0, 0, width, height, 0xFF100D19, 0xFF39202F);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("FARTIQS"), width / 2, height / 2 - 145, 0xFFFF65DC);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("C L I E N T"), width / 2, height / 2 - 118, 0xFFFFFFFF);
        if (FartiqsConfig.data.watermark) context.drawText(textRenderer, "Fartiqs Client", 12, height - 18, 0xCCFFFFFF, false);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override public boolean shouldCloseOnEsc() { return false; }
}
