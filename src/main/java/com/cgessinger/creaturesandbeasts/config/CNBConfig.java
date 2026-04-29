package com.cgessinger.creaturesandbeasts.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import java.nio.file.Path;

/**
 * Mod configuration backed by a TOML file via Night Config.
 * Call {@link #load(Path)} once during mod initialisation.
 */
public class CNBConfig {

    // ---- Live values (read after load()) ----
    public static int hideAmount = 5;
    public static int hideCost = 1;
    public static double hideMultiplier = 0.01D;

    // ------------------------------------------------------------------ //

    public static void load(Path configPath) {
        CommentedFileConfig cfg = CommentedFileConfig.builder(configPath)
                .writingMode(WritingMode.REPLACE)
                .build();
        cfg.load();

        boolean dirty = false;

        if (!cfg.contains("general.hideAmount")) {
            cfg.set("general.hideAmount", 5);
            cfg.setComment("general.hideAmount",
                    " Determines how many yeti hides can be used to reinforce an item. Range: [0, ...]");
            dirty = true;
        }
        if (!cfg.contains("general.hideCost")) {
            cfg.set("general.hideCost", 1);
            cfg.setComment("general.hideCost",
                    " Determines the experience cost of applying yeti hide to an item. Range: [0, ...]");
            dirty = true;
        }
        if (!cfg.contains("general.hideMultiplier")) {
            cfg.set("general.hideMultiplier", 0.01);
            cfg.setComment("general.hideMultiplier",
                    " Determines the armor multiplier per yeti hide applied to an item. Range: [0.0, ...]");
            dirty = true;
        }

        hideAmount    = Math.max(0, cfg.<Integer>get("general.hideAmount"));
        hideCost      = Math.max(0, cfg.<Integer>get("general.hideCost"));
        Number mult   = cfg.get("general.hideMultiplier");
        hideMultiplier = Math.max(0.0, mult.doubleValue());

        if (dirty) cfg.save();
        cfg.close();
    }
}
