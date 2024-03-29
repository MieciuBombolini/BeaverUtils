package dev.bebomny.beaver.beaverutils.mixins;

import dev.bebomny.beaver.beaverutils.client.BeaverUtilsClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(at = {@At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;getId()I",
            ordinal = 0)},
            method = {"updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"})
    private void onPlayerDamageBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        //Autotool
    }

    @Inject(method = "getReachDistance", at = @At("HEAD"), cancellable = true)
    public void onGetReachDistance(CallbackInfoReturnable<Float> cir) {
        if(!BeaverUtilsClient.getInstance().features.reach.isEnabled()) {
            return;
        }

        cir.setReturnValue(BeaverUtilsClient.getInstance().features.reach.getDistance());
    }

    @Inject(method = "hasExtendedReach", at = @At("HEAD"), cancellable = true)
    public void hasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
        if(!BeaverUtilsClient.getInstance().features.reach.isEnabled()) {
            return;
        }

        cir.setReturnValue(true);
    }
}
