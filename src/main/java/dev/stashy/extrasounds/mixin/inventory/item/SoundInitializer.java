package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Registry.class)
public class SoundInitializer
{
    @Inject(method = "register(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;", at = @At("TAIL"))
    private static void register(Registry<Object> registry, Identifier id, Object entry, CallbackInfoReturnable<Object> cir)
    {
        if (entry instanceof Item)
            ((ItemSoundContainer) entry).initSound(id);
    }
}
