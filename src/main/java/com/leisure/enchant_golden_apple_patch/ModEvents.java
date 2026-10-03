package com.leisure.enchant_golden_apple_patch;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class ModEvents {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (Player player : server.getPlayerList().getPlayers()) {
                // 只有拥有伤害吸收 IV 的玩家才可能是吃了附魔金苹果
                var absorption = player.getEffect(MobEffects.ABSORPTION);
                if (absorption == null || absorption.getAmplifier() != 3) {
                    continue;
                }

                // 升级生命恢复：II → V
                var regen = player.getEffect(MobEffects.REGENERATION);
                if (regen != null && regen.getAmplifier() != 4) {
                    player.removeEffect(MobEffects.REGENERATION);
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 4));
                }

                // 升级抗性提升：I → II
                var resist = player.getEffect(MobEffects.RESISTANCE);
                if (resist != null && resist.getAmplifier() != 1) {
                    player.removeEffect(MobEffects.RESISTANCE);
                    player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 600, 1));
                }
            }
        });
    }
}