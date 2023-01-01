package net.spell_engine.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.spell_engine.SpellEngineMod;
import net.spell_engine.api.spell.Sound;

import java.util.List;
import java.util.Map;

public class SoundHelper {
    public static List<String> soundKeys = List.of(
        "bind_spell",
        "casting_arcane",
        "casting_fire",
        "casting_frost",
        "casting_healing",
        "casting_lightning",
        "casting_soul",
        "release_arcane",
        "release_fire",
        "release_frost",
        "release_lightning",
        "release_healing",
        "release_soul",

        // Spell specific impact sounds

        "arcane_missile_release",
        "arcane_blast_release",
        "arcane_missile_impact",
        "arcane_blast_impact",
        "arcane_beam_start",
        "arcane_beam_casting",
        "arcane_beam_impact",
        "arcane_beam_release",

        "fireball_impact",
        "fire_breath_start",
        "fire_breath_casting",
        "fire_breath_release",
        "fire_breath_impact",
        "fire_meteor_release",
        "fire_meteor_impact",
        "impact_frostbolt"
    );

    public static Map<String, Float> soundDistances = Map.of(
            "fire_meteor_impact", Float.valueOf(48F)
    );

    public static void registerSounds() {
        for (var soundKey: soundKeys) {
            var soundId = new Identifier(SpellEngineMod.ID, soundKey);
            var customTravelDistance = soundDistances.get(soundKey);
            var soundEvent = (customTravelDistance == null)
                    ? new SoundEvent(soundId)
                    : new SoundEvent(soundId, customTravelDistance);
            if (customTravelDistance != null) {
                System.out.println("Registering " + soundId + " with distance: " + customTravelDistance);
            }
            Registry.register(Registry.SOUND_EVENT, soundId, soundEvent);
        }
    }

    public static void playSound(World world, Entity entity, Sound sound) {
        if (sound == null) {
            return;
        }
        try {
            var soundEvent = Registry.SOUND_EVENT.get(new Identifier(sound.id()));
            world.playSound(
                    (PlayerEntity)null,
                    entity.getX(),
                    entity.getY(),
                    entity.getZ(),
                    soundEvent,
                    SoundCategory.PLAYERS,
                    sound.volume(),
                    sound.randomizedPitch());
        } catch (Exception e) {
            System.err.println("Failed to play sound: " + sound.id());
            e.printStackTrace();
        }
    }
}
