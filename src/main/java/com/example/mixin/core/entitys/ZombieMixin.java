package com.example.mixin.core.entitys;

import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Flowey
 * @mm-patch 0033-Monumenta-Mob-behavior-changes.patch
 * <p>
 * Ban zombie drowning.
 */
@Mixin(Zombie.class)
public class ZombieMixin {

  @Inject(method = "convertsInWater", at = @At("HEAD"), cancellable = true)
  private void onConvertsInWater(CallbackInfoReturnable<Boolean> cir) {
    cir.setReturnValue(false);
  }

}
