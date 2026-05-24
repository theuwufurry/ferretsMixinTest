package com.example.mixin.core;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class NoKnockaboutDamage {

  @WrapOperation(
    method = "hurtServer",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/entity/LivingEntity;markHurt()V"
    ),
    remap = false
  )
  private void skipMarkHurtForDoT(
    LivingEntity self,
    Operation<Void> original,
    @Local(argsOnly = true) DamageSource damageSource) {
    if (damageSource.getDirectEntity() == null && damageSource.getSourcePosition() == null) {
      return; // skip the markHurt call
    }
    original.call(self);
  }
}
