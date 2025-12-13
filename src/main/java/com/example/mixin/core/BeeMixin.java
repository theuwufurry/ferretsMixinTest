package com.example.mixin.core;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.animal.bee.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Flowey
 * @mm-patch 0024-Monumenta-Remove-bee-death-neutral-after-sting.patch
 * <p>
 * Bees should not lose aggro on death
 */
@Mixin(Bee.class)
public class BeeMixin {

  // Remove the first @WrapOperation - it has wrong parameters
  // stopBeingAngry() takes no parameters, not a boolean

  @WrapOperation(
    method = "doHurtTarget",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/entity/animal/bee/Bee;stopBeingAngry()V"
    )
  )
  private void cancelStopBeingAngry(Bee instance, Operation<Void> original) {
    // bees should be perma mad
    //      original.call(instance);
  }


  @WrapOperation(
    method = "doHurtTarget",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/entity/animal/bee/Bee;setHasStung(Z)V"
    )
  )
  private void stung(Bee instance, boolean hasStung, Operation<Void> original) {
    // bees should be perma mad
    //      original.call(instance);
  }
}
