package com.example.mixin.core;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Makes bundles 10x bigger, handles stackable and unstackable items safely
 */
@Mixin(BundleContents.Mutable.class)
public abstract class BundleMutableMixin {

  @Unique
  private static final Fraction MAX_BUNDLE_WEIGHT = Fraction.getFraction(10, 1);

  @Inject(
    method = "getMaxAmountToAdd(Lnet/minecraft/world/item/ItemStack;)I",
    at = @At("HEAD"),
    cancellable = true
  )
  private void increaseCapacity(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
    BundleContentsAccessor accessor = (BundleContentsAccessor) this;

    Fraction remaining = MAX_BUNDLE_WEIGHT.subtract(accessor.getWeight());
    if (remaining.compareTo(Fraction.ZERO) <= 0) {
      cir.setReturnValue(0);
      cir.cancel();
      return;
    }

    Fraction itemWeight = BundleContentsInvoker.callGetWeight(stack);
    int maxByWeight = remaining.divideBy(itemWeight).intValue();
    int maxByStack = ignite_mod_template$getSafeStackLimit(stack, accessor.getItems());

    int maxAdd = Math.min(maxByWeight, maxByStack);

    cir.setReturnValue(Math.max(maxAdd, 0));
    cir.cancel();
  }

  @Unique
  private static int ignite_mod_template$getSafeStackLimit(ItemStack incoming, List<ItemStack> existingItems) {
    int countInBundle = 0;
    for (ItemStack item : existingItems) {
      if (ItemStack.isSameItemSameComponents(incoming, item)) {
        countInBundle += item.getCount();
      }
    }
    return Math.max(99 - countInBundle, 0);
  }

  /**
   * Accessor for the private fields of BundleContents.Mutable
   */
  @Mixin(BundleContents.Mutable.class)
  public interface BundleContentsAccessor {
    @Accessor("weight")
    Fraction getWeight();

    @Accessor("items")
    List<ItemStack> getItems();
  }

  /**
   * Invoker for the package-private getWeight(ItemStack) method
   */
  @Mixin(BundleContents.class)
  public interface BundleContentsInvoker {
    @Invoker("getWeight")
    static Fraction callGetWeight(ItemStack stack) {
      throw new AssertionError();
    }
  }
}
