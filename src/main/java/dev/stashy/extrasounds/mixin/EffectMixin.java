package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatusEffect.class)
public abstract class EffectMixin
{

    @Inject(at = @At("TAIL"), method = "onApplied")
    public void addEffect(LivingEntity entity, AttributeContainer attributes, int amplifier, CallbackInfo ci)
    {
        if (entity.isPlayer() && entity.hasStatusEffect((StatusEffect) (Object) this))
            ExtraSounds.playEffectSound((StatusEffect) (Object) this, true);
    }

    @Inject(at = @At("TAIL"), method = "onRemoved")
    public void removeEffect(LivingEntity entity, AttributeContainer attributes, int amplifier, CallbackInfo ci)
    {
        if (entity.isPlayer() && !entity.hasStatusEffect((StatusEffect) (Object) this))
            ExtraSounds.playEffectSound((StatusEffect) (Object) this, false);
    }
}
