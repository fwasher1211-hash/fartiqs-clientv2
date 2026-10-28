package com.fartiqs.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class FartiqsScreen extends Screen {
    private enum Tab { ALL, HUD, GAMEPLAY, VISUAL }
    private record Mod(String name, String category, String key, int icon) {}
    private static final Mod[] MODS = {
        new Mod("Profit HUD","HUD","profit_hud",0), new Mod("2x Pay","GAMEPLAY","two_x_pay",1), new Mod("Auction Tools","GAMEPLAY","auction_mod",2),
        new Mod("Watermark","VISUAL","watermark_module",3), new Mod("Particles","VISUAL","particles_module",4), new Mod("Music Label","HUD","music_label",5)
    };
    private Tab tab=Tab.ALL; private String search=""; private int scroll=0; private int focused=0; private boolean icons= true; private boolean settings=false;
    private int panelX,panelY,panelW,panelH,gridTop; private final List<Mod> visible=new ArrayList<>();
    public FartiqsScreen(){super(Text.literal("Fartiqs Client")); icons=FartiqsConfig.data.icons; }
    @Override protected void init(){ panelW=Math.min(900,width-36); panelH=Math.min(590,height-30); panelX=(width-panelW)/2; panelY=(height-panelH)/2; gridTop=panelY+184; }
    @Override public void render(DrawContext ctx,int mouseX,int mouseY,float delta){
        ctx.fill(0,0,width,height,0xEB0D0D12); drawPanel(ctx,panelX,panelY,panelW,panelH,0xFF15151C,12);
        ctx.drawText(textRenderer,"Fartiqs Client",panelX+24,panelY+22,0xFFFFFFFF,false);
        drawPill(ctx,panelX+panelW-154,panelY+15,94,27,icons?0xFF36243F:0xFF24242D); ctx.drawCenteredTextWithShadow(textRenderer,Text.literal(icons?"ICONS: ON":"ICONS: OFF"),panelX+panelW-107,panelY+24,0xFFFFFFFF);
        drawPill(ctx,panelX+panelW-48,panelY+15,30,27,0xFF30202E); ctx.drawCenteredTextWithShadow(textRenderer,Text.literal("X"),panelX+panelW-33,panelY+24,0xFFFFFFFF);
        drawPanel(ctx,panelX+24,panelY+58,panelW-48,40,0xFF24152C,18); ctx.drawText(textRenderer,"⌕",panelX+39,panelY+69,0xFFFF3FA4,false); ctx.drawText(textRenderer,search.isEmpty()?"Search mods...":search,panelX+67,panelY+72,search.isEmpty()?0xFF9A9AA8:0xFFFFFFFF,false);
        String[] labels={"ALL","HUD","GAMEPLAY","VISUAL"}; int tw=(panelW-48-24)/4; for(int i=0;i<4;i++){int x=panelX+24+i*(tw+8); drawPill(ctx,x,panelY+111,tw,32,tab.ordinal()==i?0xFF8C35D4:0xFF28232E); ctx.drawCenteredTextWithShadow(textRenderer,Text.literal(labels[i]),x+tw/2,panelY+122,tab.ordinal()==i?0xFFFFFFFF:0xFF9A9AA8);}
        visible.clear(); String q=search.toLowerCase(Locale.ROOT); for(Mod m:MODS) if((tab==Tab.ALL||m.category.equals(tab.name()))&&(m.name.toLowerCase(Locale.ROOT).contains(q)||m.category.toLowerCase(Locale.ROOT).contains(q))) visible.add(m);
        int cols=4,gap=10,cw=(panelW-48-gap*3)/4,ch=118; int rows=(visible.size()+3)/4; int maxScroll=Math.max(0,rows*(ch+gap)-(panelH-210)); scroll=Math.max(0,Math.min(scroll,maxScroll));
        ctx.enableScissor(panelX+20,gridTop,panelX+panelW-20,panelY+panelH-25);
        for(int i=0;i<visible.size();i++){Mod m=visible.get(i); int x=panelX+24+(i%cols)*(cw+gap), y=gridTop+(i/cols)*(ch+gap)-scroll; boolean on=FartiqsConfig.isOn(m.key); boolean hover=mouseX>=x&&mouseX<=x+cw&&mouseY>=y&&mouseY<=y+ch; int border=on?0xFFB14BFF:(hover?0xFF3A3A47:0xFF26262F); drawPanel(ctx,x,y,cw,ch,0xFF1A1A22,10); outline(ctx,x,y,cw,ch,border);
            if(icons){ctx.drawText(textRenderer,iconText(m.icon),x+14,y+12,0xFFFFFFFF,false);} ctx.drawText(textRenderer,m.name,x+14,y+42,0xFFFFFFFF,false); ctx.drawText(textRenderer,m.category,x+14,y+59,0xFF9A9AA8,false);
            int bar=on?0xFF22C55E:0xFFEF4444; drawPanel(ctx,x+9,y+ch-29,cw-18,20,bar,7); ctx.drawCenteredTextWithShadow(textRenderer,Text.literal(on?"ENABLED":"DISABLED"),x+cw/2,y+ch-23,0xFFFFFFFF);
        }
        ctx.disableScissor(); if(visible.isEmpty())ctx.drawCenteredTextWithShadow(textRenderer,Text.literal("No modules match your search"),width/2,gridTop+20,0xFF9A9AA8);
        ctx.drawText(textRenderer,"Right Shift: open/close   •   Esc: close   •   Enter: toggle focused",panelX+24,panelY+panelH-18,0xFF777783,false); super.render(ctx,mouseX,mouseY,delta);
    }
    private String iconText(int i){return switch(i){case 0->"◉";case 1->"⇄";case 2->"◇";case 3->"⌖";case 4->"✧";default->"♫";};}
    private void drawPanel(DrawContext c,int x,int y,int w,int h,int color,int r){c.fill(x+r,y,x+w-r,y+h,color);c.fill(x,y+r,x+w,y+h-r,color);c.fill(x+r/2,y+r/2,x+w-r/2,y+h-r/2,color);}
    private void drawPill(DrawContext c,int x,int y,int w,int h,int color){drawPanel(c,x,y,w,h,color,h/2);}
    private void outline(DrawContext c,int x,int y,int w,int h,int color){c.fill(x,y,x+w,y+1,color);c.fill(x,y+h-1,x+w,y+h,color);c.fill(x,y,x+1,y+h,color);c.fill(x+w-1,y,x+w,y+h,color);}
    @Override public boolean mouseClicked(double mx,double my,int button){if(button!=0)return super.mouseClicked(mx,my,button); if(my>=panelY+15&&my<=panelY+45&&mx>=panelX+panelW-154&&mx<panelX+panelW-54){icons=!icons;FartiqsConfig.data.icons=icons;FartiqsConfig.save();return true;} if(my>=panelY+15&&mx>=panelX+panelW-50){close();return true;} if(my>=panelY+111&&my<=panelY+143){int tw=(panelW-48-24)/4;int idx=(int)((mx-(panelX+24))/(tw+8));if(idx>=0&&idx<4)tab=Tab.values()[idx];return true;} int gap=10,cw=(panelW-48-gap*3)/4,ch=118; for(int i=0;i<visible.size();i++){int x=panelX+24+(i%4)*(cw+gap),y=gridTop+(i/4)*(ch+gap)-scroll;if(mx>=x&&mx<=x+cw&&my>=y&&my<=y+ch){Mod m=visible.get(i);FartiqsConfig.set(m.key,!FartiqsConfig.isOn(m.key));focused=i;return true;}} if(my>=panelY+58&&my<=panelY+98){search="";return true;}return super.mouseClicked(mx,my,button);}
    @Override public boolean charTyped(char chr,int modifiers){if(Character.isLetterOrDigit(chr)||Character.isWhitespace(chr)||"-_".indexOf(chr)>=0){search+=chr;scroll=0;return true;}return super.charTyped(chr,modifiers);}
    @Override public boolean keyPressed(int key,int scan,int modifiers){if(key==GLFW.GLFW_KEY_ESCAPE){close();return true;} if(key==GLFW.GLFW_KEY_BACKSPACE&&!search.isEmpty()){search=search.substring(0,search.length()-1);return true;} if(key==GLFW.GLFW_KEY_ENTER&&!visible.isEmpty()){Mod m=visible.get(Math.min(focused,visible.size()-1));FartiqsConfig.set(m.key,!FartiqsConfig.isOn(m.key));return true;}if(key==GLFW.GLFW_KEY_DOWN){focused=Math.min(visible.size()-1,focused+4);return true;}if(key==GLFW.GLFW_KEY_UP){focused=Math.max(0,focused-4);return true;}if(key==GLFW.GLFW_KEY_LEFT){focused=Math.max(0,focused-1);return true;}if(key==GLFW.GLFW_KEY_RIGHT){focused=Math.min(visible.size()-1,focused+1);return true;}return super.keyPressed(key,scan,modifiers);}
    @Override public boolean mouseScrolled(double mx,double my,double horizontal,double vertical){scroll=Math.max(0,scroll-(int)(vertical*35));return true;}
    @Override public void close(){MinecraftClient.getInstance().setScreen(null);}
}
