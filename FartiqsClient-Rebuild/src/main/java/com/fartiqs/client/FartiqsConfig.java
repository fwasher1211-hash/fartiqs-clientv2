package com.fartiqs.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FartiqsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("fartiqsclient.json");
    public static Data data = new Data();
    private FartiqsConfig() {}
    public static void load() { try { if (Files.exists(FILE)) try (Reader r=Files.newBufferedReader(FILE)) { Data d=GSON.fromJson(r, Data.class); if (d != null) data=d; } else save(); } catch (Exception ignored) { data=new Data(); } }
    public static void save() { try { Files.createDirectories(FILE.getParent()); try (Writer w=Files.newBufferedWriter(FILE)) { GSON.toJson(data,w); } } catch (Exception ignored) {} }
    public static boolean isOn(String key) { return data.modules.getOrDefault(key, true); }
    public static void set(String key, boolean value) { data.modules.put(key,value); save(); }
    public static final class Data {
        public java.util.Map<String,Boolean> modules = new java.util.LinkedHashMap<>();
        public int accent = 0xFFB14BFF; public float backgroundDim = 0.30f; public float blurStrength = 0.25f;
        public float logoScale = 1.0f; public boolean logoGlow = true; public boolean logoFloat = true;
        public boolean particles = true; public float particleDensity = 0.5f; public float particleSpeed = 0.5f;
        public boolean watermark = true; public String watermarkPosition = "BOTTOM_LEFT"; public float watermarkOpacity = 0.8f;
        public boolean musicLabel = false; public boolean icons = true; public String backgroundMode = "GRADIENT";
        public String buttonShape = "PILL"; public int cornerRadius = 12; public int buttonFill = 0xFF25252D; public int textColor = 0xFFFFFFFF; public String hoverStyle = "GLOW";
    }
}
