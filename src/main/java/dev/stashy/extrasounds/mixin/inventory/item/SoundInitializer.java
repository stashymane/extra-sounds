package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public class SoundInitializer
{
    @Inject(method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("TAIL"))
    private static void register(Identifier id, Item item, CallbackInfoReturnable<Item> cir)
    {
        ((ItemSoundContainer) item).initSound(id);
    }
}
