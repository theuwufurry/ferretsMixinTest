package com.example.mixin.core.entitys;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Shulker.class)
public abstract class ShulkerMixin extends AbstractGolem {
  @Shadow
  protected abstract boolean isClosed();

  protected ShulkerMixin(EntityType<? extends AbstractGolem> type, Level world) {
    super(type, world);
  }

  @ModifyExpressionValue(
    method = "hurtServer",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/entity/monster/Shulker;isClosed()Z"
    )
  )
  private boolean allowArrowsOnClosed(boolean original) {
    return isClosed();
  }
}
