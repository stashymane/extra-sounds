package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.SoundManager;
import dev.stashy.extrasounds.sounds.SoundType;
import dev.stashy.extrasounds.sounds.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class BowPullSound
{
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/TypedActionResult;consume(Ljava/lang/Object;)Lnet/minecraft/util/TypedActionResult;"), method = "use")
    void pull(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir)
    {
        SoundManager.playSound(Sounds.Actions.BOW_PULL, SoundType.ACTION);
    }

    @Inject(at = @At(value = "RETURN"), method = "onStoppedUsing")
    void shoot(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci)
    {
        SoundManager.stopSound(Sounds.Actions.BOW_PULL, SoundType.ACTION);
    }
}
