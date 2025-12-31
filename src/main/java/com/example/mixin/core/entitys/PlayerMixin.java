package com.example.mixin.core.entitys;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(HitResult.class)
public class PlayerMixin {
}
