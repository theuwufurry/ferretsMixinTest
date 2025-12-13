//package com.example.mixin.core;
//
//import com.llamalad7.mixinextras.injector.ModifyReturnValue;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.component.BundleContents;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//
//@Mixin(BundleContents.class)
//public class BundleContentsClientMixin {
//
//  @ModifyReturnValue(
//    method = "getItemUnsafe(I)Lnet/minecraft/world/item/ItemStack;",
//    at = @At("RETURN")
//  )
//  private net.minecraft.world.item.ItemStack clampClientStack(net.minecraft.world.item.ItemStack stack) {
//    if (stack.getCount() > 99) {
//      ItemStack clone = stack.copy();
//      clone.setCount(99);
//      return clone;
//    }
//    return stack;
//  }
//}
