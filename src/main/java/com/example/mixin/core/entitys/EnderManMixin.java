package com.example.mixin.core.entitys;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Flowey
 * @mm-patch 0033-Monumenta-Mob-behavior-changes.patch
 * @mm-patch 0037-Monumenta-Remove-vanilla-Enderman-teleportation.patch
 */
@Mixin(EnderMan.class)
public abstract class EnderManMixin extends Monster {
  protected EnderManMixin(EntityType<? extends Monster> type, Level world) {
    super(type, world);
  }

  @ModifyExpressionValue(
    method = "hurtServer",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/entity/monster/EnderMan;tryEscape" +
        "(Lcom/destroystokyo/paper/event/entity/EndermanEscapeEvent$Reason;)Z"
    )
  )
  private boolean storeTryEscapeRes(
    boolean original,
    @Share("hasEscaped") LocalBooleanRef ref
  ) {
    ref.set(original);
    return original;
  }

  @ModifyReturnValue(
    method = "hurtServer",
    at = @At("TAIL")
  )
  private boolean modifyHurtReturnValue(boolean original, @Share("hasEscaped") LocalBooleanRef ref) {
    if (ref.get()) return original;
    return true; // force hurt = true if it didn’t escape
  }

  /**
   * @author Flowey
   * @reason Ignore carrying item.
   */
  @Inject(
    method = "requiresCustomPersistence",
    at = @At("HEAD"),
    cancellable = true
  )
  public void requiresCustomPersistence(CallbackInfoReturnable<Boolean> cir) {
      cir.setReturnValue(super.requiresCustomPersistence());
  }

  /**
   * @author Flowey
   * @reason Disable TP.
   */
  @Inject(
    method = "teleport()Z",
    at = @At("HEAD"),
    cancellable = true
  )
  public void teleport(CallbackInfoReturnable<Boolean> cir) {
      cir.setReturnValue(false);
  }
}
