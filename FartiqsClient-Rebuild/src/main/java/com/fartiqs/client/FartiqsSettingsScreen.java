package com.fartiqs.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class FartiqsSettingsScreen extends Screen {
 private final Screen parent; public FartiqsSettingsScreen(Screen parent){super(Text.literal("Fartiqs Client Settings"));this.parent=parent;}
 @Override protected void init(){int x=width/2-110,y=height/2-90;addDrawableChild(ButtonWidget.builder(Text.literal("Background: "+FartiqsConfig.data.backgroundMode),b->{String[] m={"GRADIENT","IMAGE","VIDEO","WORLD_BLUR"};int i=0;for(int n=0;n<m.length;n++)if(m[n].equals(FartiqsConfig.data.backgroundMode))i=n;FartiqsConfig.data.backgroundMode=m[(i+1)%m.length];FartiqsConfig.save();clearAndInit();}).dimensions(x,y,220,24).build());addDrawableChild(ButtonWidget.builder(Text.literal("Logo glow: "+FartiqsConfig.data.logoGlow),b->{FartiqsConfig.data.logoGlow=!FartiqsConfig.data.logoGlow;FartiqsConfig.save();clearAndInit();}).dimensions(x,y+30,220,24).build());addDrawableChild(ButtonWidget.builder(Text.literal("Float logo: "+FartiqsConfig.data.logoFloat),b->{FartiqsConfig.data.logoFloat=!FartiqsConfig.data.logoFloat;FartiqsConfig.save();clearAndInit();}).dimensions(x,y+60,220,24).build());addDrawableChild(ButtonWidget.builder(Text.literal("Particles: "+FartiqsConfig.data.particles),b->{FartiqsConfig.data.particles=!FartiqsConfig.data.particles;FartiqsConfig.save();clearAndInit();}).dimensions(x,y+90,220,24).build());addDrawableChild(ButtonWidget.builder(Text.literal("Watermark: "+FartiqsConfig.data.watermark),b->{FartiqsConfig.data.watermark=!FartiqsConfig.data.watermark;FartiqsConfig.save();clearAndInit();}).dimensions(x,y+120,220,24).build());addDrawableChild(ButtonWidget.builder(Text.literal("Reset to defaults"),b->{FartiqsConfig.data=new FartiqsConfig.Data();FartiqsConfig.save();clearAndInit();}).dimensions(x,y+150,220,24).build());addDrawableChild(ButtonWidget.builder(Text.literal("Back"),b->client.setScreen(parent)).dimensions(x,y+180,220,24).build());}
 @Override public void render(DrawContext c,int mx,int my,float d){renderBackground(c,mx,my,d);c.drawCenteredTextWithShadow(textRenderer,title,width/2,height/2-125,0xFFFF65DC);super.render(c,mx,my,d);}
}
