package com.example.mixin.core.entitys;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


// replcate 1.8 this is a test
@Mixin(ServerboundInteractPacket.class)
public class playerPacketmixin {
  @ModifyReturnValue(
    method = "isWithinRange",
    at = @At("RETURN")
  )
  private boolean modifyRange(boolean original, ServerPlayer player, AABB aabb, double range) {
    AABB inflated = aabb.inflate(0.3);
    return player.isWithinAttackRange(inflated, range + 0.5);
  }
}

