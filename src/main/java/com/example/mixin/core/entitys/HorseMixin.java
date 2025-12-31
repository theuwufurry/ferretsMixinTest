package com.example.mixin.core.entitys;

import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/**
 * @author theuwufurry
 *
 * removes horse jorking
 *
 */
@Mixin(AbstractHorse.class)
public class HorseMixin {

  @Inject(
    method = "canPerformRearing",
    at = @At("HEAD"),
    cancellable = true
  )
  private void disableRearing(CallbackInfoReturnable<Boolean> cir) {
    cir.setReturnValue(false);
  }
}
