package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNBSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, CreaturesAndBeasts.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> LITTLE_GREBE_AMBIENT = register("entity.little_grebe.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> LITTLE_GREBE_HURT = register("entity.little_grebe.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> LITTLE_GREBE_CHICK_AMBIENT = register("entity.little_grebe_chick.ambient");

    public static final DeferredHolder<SoundEvent, SoundEvent> CINDERSHELL_AMBIENT = register("entity.cindershell.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> CINDERSHELL_HURT = register("entity.cindershell.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CINDERSHELL_ADULT_EAT = register("entity.cindershell_adult.eat");
    public static final DeferredHolder<SoundEvent, SoundEvent> CINDERSHELL_BABY_EAT = register("entity.cindershell_baby.eat");

    public static final DeferredHolder<SoundEvent, SoundEvent> SPORELING_OVERWORLD_AMBIENT = register("entity.sporeling_overworld.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPORELING_OVERWORLD_HURT = register("entity.sporeling_overworld.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPORELING_NETHER_AMBIENT = register("entity.sporeling_nether.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPORELING_NETHER_HURT = register("entity.sporeling_nether.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPORELING_WARPED_AMBIENT = register("entity.sporeling_warped.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPORELING_WARPED_HURT = register("entity.sporeling_warped.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPORELING_BITE = register("entity.sporeling.bite");

    public static final DeferredHolder<SoundEvent, SoundEvent> LILYTAD_AMBIENT = register("entity.lilytad.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> LILYTAD_HURT = register("entity.lilytad.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> LILYTAD_DEATH = register("entity.lilytad.death");

    public static final DeferredHolder<SoundEvent, SoundEvent> YETI_AMBIENT = register("entity.yeti.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> YETI_HURT = register("entity.yeti.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> YETI_STEP = register("entity.yeti.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> YETI_HIT = register("entity.yeti.hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> YETI_ADULT_EAT = register("entity.yeti_adult.eat");
    public static final DeferredHolder<SoundEvent, SoundEvent> YETI_BABY_EAT = register("entity.yeti_baby.eat");

    public static final DeferredHolder<SoundEvent, SoundEvent> MINIPAD_HURT = register("entity.minipad.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> MINIPAD_STEP = register("entity.minipad.step");
    public static final DeferredHolder<SoundEvent, SoundEvent> MINIPAD_SWIM = register("entity.minipad.swim");

    public static final DeferredHolder<SoundEvent, SoundEvent> END_WHALE_AMBIENT = register("entity.end_whale.ambient");

    public static final DeferredHolder<SoundEvent, SoundEvent> CACTEM_AMBIENT = register("entity.cactem.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> CACTEM_HURT = register("entity.cactem.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CACTEM_HEAL = register("entity.cactem.heal");

    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_HEAL = register("item.heal_spell_book.player_heal");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPEAR_THROW = register("item.cactem_spear.throw");

    public static final DeferredHolder<SoundEvent, SoundEvent> LIZARD_EGG_HATCH = register("entity.lizard.egg_hatch");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(CreaturesAndBeasts.MOD_ID, name)));
    }
}
