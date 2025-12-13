package com.example.mixin.core;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Bees;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;



/*
make bundles 10* bigger
 */
@Mixin(BundleContents.Mutable.class)
public class BundleMutableMixin {
  @Inject(
    method = "getMaxAmountToAdd(Lnet/minecraft/world/item/ItemStack;)I",
    at = @At("HEAD"),
    cancellable = true
  )
  private void increaseCapacity(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
    int maxCapacity = 10;
    BundleContentsAccessor accessor = (BundleContentsAccessor) this;
    List<ItemStack> items = accessor.getItems();

    // checks stops over flowing items from crashing the client

    for (ItemStack item : items) {
      if (ItemStack.isSameItemSameComponents(stack, item)){
        int numberOfItems = stack.getCount() + item.getCount();
        if ( numberOfItems > 99){
          cir.cancel();
          cir.setReturnValue(0);
          return;
        }
      }
    }

   //
    Fraction maxWeight = Fraction.getFraction(maxCapacity, 1);
    Fraction itemWeight = ignite_mod_template$getWeight(stack);
    cir.setReturnValue(Math.max(maxWeight.divideBy(itemWeight).intValue(), 0));
  }
  @Unique
  private static Fraction ignite_mod_template$getWeight(ItemStack stack) {
    BundleContents bundleContents = stack.get(DataComponents.BUNDLE_CONTENTS);
    if (bundleContents != null) {
      return Fraction.getFraction(1, 16).add(bundleContents.weight());
    } else {
      List<BeehiveBlockEntity.Occupant> beesList = ((Bees) stack.getOrDefault(DataComponents.BEES, Bees.EMPTY)).bees();
      return !beesList.isEmpty() ? Fraction.ONE : Fraction.getFraction(1, stack.getMaxStackSize());
    }
  }

  /**
   * Accessor interface to get private 'items' field from Mutable.
   */
  @Mixin(BundleContents.Mutable.class)
  public interface BundleContentsAccessor {
    @Accessor("items")
    List<ItemStack> getItems();
  }
}
