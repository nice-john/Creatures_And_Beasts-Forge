package com.cgessinger.creaturesandbeasts.init;

import com.cgessinger.creaturesandbeasts.CreaturesAndBeasts;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CNBSoundEvents {
    public static DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CreaturesAndBeasts.MOD_ID);

    public static final RegistryObject<SoundEvent> LITTLE_GREBE_AMBIENT = SOUND_EVENTS.register(
            "entity.little_grebe.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.little_grebe.ambient"))
    );

    public static final RegistryObject<SoundEvent> LITTLE_GREBE_HURT = SOUND_EVENTS.register(
            "entity.little_grebe.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.little_grebe.hurt"))
    );

    public static final RegistryObject<SoundEvent> LITTLE_GREBE_CHICK_AMBIENT = SOUND_EVENTS.register(
            "entity.little_grebe_chick.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.little_grebe_chick.ambient"))
    );

    public static final RegistryObject<SoundEvent> CINDERSHELL_AMBIENT = SOUND_EVENTS.register(
            "entity.cindershell.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.cindershell.ambient"))
    );

    public static final RegistryObject<SoundEvent> CINDERSHELL_HURT = SOUND_EVENTS.register(
            "entity.cindershell.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.cindershell.hurt"))
    );

    public static final RegistryObject<SoundEvent> CINDERSHELL_ADULT_EAT = SOUND_EVENTS.register(
            "entity.cindershell_adult.eat",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.cindershell_adult.eat"))
    );

    public static final RegistryObject<SoundEvent> CINDERSHELL_BABY_EAT = SOUND_EVENTS.register(
            "entity.cindershell_baby.eat",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.cindershell_baby.eat"))
    );


    public static final RegistryObject<SoundEvent> SPORELING_OVERWORLD_AMBIENT = SOUND_EVENTS.register(
            "entity.sporeling_overworld.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.sporeling_overworld.ambient"))
    );

    public static final RegistryObject<SoundEvent> SPORELING_OVERWORLD_HURT = SOUND_EVENTS.register(
            "entity.sporeling_overworld.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.sporeling_overworld.hurt"))
    );

    public static final RegistryObject<SoundEvent> SPORELING_NETHER_AMBIENT = SOUND_EVENTS.register(
            "entity.sporeling_nether.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.sporeling_nether.ambient"))
    );

    public static final RegistryObject<SoundEvent> SPORELING_NETHER_HURT = SOUND_EVENTS.register(
            "entity.sporeling_nether.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.sporeling_nether.hurt"))
    );

    public static final RegistryObject<SoundEvent> SPORELING_WARPED_AMBIENT = SOUND_EVENTS.register(
            "entity.sporeling_warped.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.sporeling_warped.ambient"))
    );

    public static final RegistryObject<SoundEvent> SPORELING_WARPED_HURT = SOUND_EVENTS.register(
            "entity.sporeling_warped.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.sporeling_warped.hurt"))
    );

    public static final RegistryObject<SoundEvent> SPORELING_BITE = SOUND_EVENTS.register(
            "entity.sporeling.bite",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.sporeling.bite"))
    );

    public static final RegistryObject<SoundEvent> LILYTAD_AMBIENT = SOUND_EVENTS.register(
            "entity.lilytad.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.lilytad.ambient"))
    );

    public static final RegistryObject<SoundEvent> LILYTAD_HURT = SOUND_EVENTS.register(
            "entity.lilytad.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.lilytad.hurt"))
    );

    public static final RegistryObject<SoundEvent> LILYTAD_DEATH = SOUND_EVENTS.register(
            "entity.lilytad.death",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.lilytad.death"))
    );

    public static final RegistryObject<SoundEvent> YETI_AMBIENT = SOUND_EVENTS.register(
            "entity.yeti.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.yeti.ambient"))
    );
    public static final RegistryObject<SoundEvent> YETI_HURT = SOUND_EVENTS.register(
            "entity.yeti.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.yeti.hurt"))
    );
    public static final RegistryObject<SoundEvent> YETI_STEP = SOUND_EVENTS.register(
            "entity.yeti.step",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.yeti.step"))
    );
    public static final RegistryObject<SoundEvent> YETI_HIT = SOUND_EVENTS.register(
            "entity.yeti.hit",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.yeti.hit"))
    );
    public static final RegistryObject<SoundEvent> YETI_ADULT_EAT = SOUND_EVENTS.register(
            "entity.yeti_adult.eat",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.yeti_adult.eat"))
    );
    public static final RegistryObject<SoundEvent> YETI_BABY_EAT = SOUND_EVENTS.register(
            "entity.yeti_baby.eat",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.yeti_baby.eat"))
    );

    public static final RegistryObject<SoundEvent> MINIPAD_HURT = SOUND_EVENTS.register(
            "entity.minipad.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.minipad.hurt"))
    );
    public static final RegistryObject<SoundEvent> MINIPAD_STEP = SOUND_EVENTS.register(
            "entity.minipad.step",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.minipad.step"))
    );
    public static final RegistryObject<SoundEvent> MINIPAD_SWIM = SOUND_EVENTS.register(
            "entity.minipad.swim",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.minipad.swim"))
    );

    public static final RegistryObject<SoundEvent> END_WHALE_AMBIENT = SOUND_EVENTS.register(
            "entity.end_whale.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.end_whale.ambient"))
    );

    public static final RegistryObject<SoundEvent> CACTEM_AMBIENT = SOUND_EVENTS.register(
            "entity.cactem.ambient",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.cactem.ambient"))
    );
    public static final RegistryObject<SoundEvent> CACTEM_HURT = SOUND_EVENTS.register(
            "entity.cactem.hurt",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.cactem.hurt"))
    );
    public static final RegistryObject<SoundEvent> CACTEM_HEAL = SOUND_EVENTS.register(
            "entity.cactem.heal",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.cactem.heal"))
    );

    public static final RegistryObject<SoundEvent> PLAYER_HEAL = SOUND_EVENTS.register(
            "item.heal_spell_book.player_heal",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "item.heal_spell_book.player_heal"))
    );

    public static final RegistryObject<SoundEvent> SPEAR_THROW = SOUND_EVENTS.register(
            "item.cactem_spear.throw",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "item.cactem_spear.throw"))
    );

    public static final RegistryObject<SoundEvent> LIZARD_EGG_HATCH = SOUND_EVENTS.register(
            "entity.lizard.egg_hatch",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreaturesAndBeasts.MOD_ID, "entity.lizard.egg_hatch"))
    );

}
