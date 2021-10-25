package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class EffectMixin
{

    @Inject(at = @At("HEAD"), method = "onStatusEffectApplied")
    public void addEffect(StatusEffectInstance effect, Entity source, CallbackInfo ci)
    {
        if ((LivingEntity) (Object) this instanceof ClientPlayerEntity)
            ExtraSounds.playEffectSound(effect.getEffectType(), true);
    }

    @Inject(at = @At("HEAD"), method = "removeStatusEffectInternal")
    public void removeEffect(StatusEffect type, CallbackInfoReturnable<StatusEffectInstance> cir)
    {
        if ((LivingEntity) (Object) this instanceof ClientPlayerEntity)
            //if (entity.isPlayer() && !entity.hasStatusEffect((StatusEffect) (Object) this))
            ExtraSounds.playEffectSound(type, false);
    }
}
