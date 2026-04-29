package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class CNBSoundEvents {

    public static final SoundEvent LITTLE_GREBE_AMBIENT      = register("entity.little_grebe.ambient");
    public static final SoundEvent LITTLE_GREBE_HURT         = register("entity.little_grebe.hurt");
    public static final SoundEvent LITTLE_GREBE_CHICK_AMBIENT= register("entity.little_grebe_chick.ambient");

    public static final SoundEvent CINDERSHELL_AMBIENT       = register("entity.cindershell.ambient");
    public static final SoundEvent CINDERSHELL_HURT          = register("entity.cindershell.hurt");
    public static final SoundEvent CINDERSHELL_ADULT_EAT     = register("entity.cindershell_adult.eat");
    public static final SoundEvent CINDERSHELL_BABY_EAT      = register("entity.cindershell_baby.eat");

    public static final SoundEvent SPORELING_OVERWORLD_AMBIENT = register("entity.sporeling_overworld.ambient");
    public static final SoundEvent SPORELING_OVERWORLD_HURT    = register("entity.sporeling_overworld.hurt");
    public static final SoundEvent SPORELING_NETHER_AMBIENT    = register("entity.sporeling_nether.ambient");
    public static final SoundEvent SPORELING_NETHER_HURT       = register("entity.sporeling_nether.hurt");
    public static final SoundEvent SPORELING_WARPED_AMBIENT    = register("entity.sporeling_warped.ambient");
    public static final SoundEvent SPORELING_WARPED_HURT       = register("entity.sporeling_warped.hurt");
    public static final SoundEvent SPORELING_BITE              = register("entity.sporeling.bite");

    public static final SoundEvent LILYTAD_AMBIENT = register("entity.lilytad.ambient");
    public static final SoundEvent LILYTAD_HURT    = register("entity.lilytad.hurt");
    public static final SoundEvent LILYTAD_DEATH   = register("entity.lilytad.death");

    public static final SoundEvent YETI_AMBIENT    = register("entity.yeti.ambient");
    public static final SoundEvent YETI_HURT       = register("entity.yeti.hurt");
    public static final SoundEvent YETI_STEP       = register("entity.yeti.step");
    public static final SoundEvent YETI_HIT        = register("entity.yeti.hit");
    public static final SoundEvent YETI_ADULT_EAT  = register("entity.yeti_adult.eat");
    public static final SoundEvent YETI_BABY_EAT   = register("entity.yeti_baby.eat");

    public static final SoundEvent MINIPAD_HURT    = register("entity.minipad.hurt");
    public static final SoundEvent MINIPAD_STEP    = register("entity.minipad.step");
    public static final SoundEvent MINIPAD_SWIM    = register("entity.minipad.swim");

    public static final SoundEvent END_WHALE_AMBIENT = register("entity.end_whale.ambient");

    public static final SoundEvent CACTEM_AMBIENT  = register("entity.cactem.ambient");
    public static final SoundEvent CACTEM_HURT     = register("entity.cactem.hurt");
    public static final SoundEvent CACTEM_HEAL     = register("entity.cactem.heal");

    public static final SoundEvent PLAYER_HEAL     = register("item.heal_spell_book.player_heal");
    public static final SoundEvent SPEAR_THROW     = register("item.cactem_spear.throw");
    public static final SoundEvent LIZARD_EGG_HATCH= register("entity.lizard.egg_hatch");

    // ─────────────────────────────────────────────────────────────────────────

    private static SoundEvent register(String name) {
        ResourceLocation id = new ResourceLocation(CreaturesAndBeasts.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id,
                SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {
        CreaturesAndBeasts.LOGGER.debug("Registered CNB sound events");
    }
}
