package dev.stashy.extrasounds;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.Function;

public class ExtraSounds implements ModInitializer
{
    public static SoundConfig config;
    private static final Function<SoundEvent, Text> registryTextProvider = T -> new TranslatableText(
            T.getId().getPath());

    private static final Random r = new Random();

    @Override
    public void onInitialize()
    {
        AutoConfig.register(SoundConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(SoundConfig.class).getConfig();
        GuiRegistry registry = AutoConfig.getGuiRegistry(SoundConfig.class);
        registry.registerTypeProvider(
                (i13n, field, config, defaults, guiProvider) -> Collections.singletonList(
                        ConfigEntryBuilder.create().startDropdownMenu(
                                new TranslatableText(i13n),
                                DropdownMenuBuilder.TopCellElementBuilder.of(
                                        Utils.getUnsafely(field, config, Utils.getUnsafely(field, defaults)),
                                        str -> Registry.SOUND_EVENT.get(Identifier.tryParse(str)),
                                        registryTextProvider
                                ),
                                DropdownMenuBuilder.CellCreatorBuilder.ofWidth(200, registryTextProvider)
                        )
                                          .setTooltipSupplier(soundEvent -> Optional
                                                  .of(new TranslatableText[]{new TranslatableText(
                                                          "subtitles." + soundEvent.getId().getPath())}))
                                          .setSelections(Registry.SOUND_EVENT)
                                          .setDefaultValue(() -> Utils.getUnsafely(field, defaults))
                                          .setSaveConsumer(newValue -> Utils.setUnsafely(field, config, newValue))
                                          .build()
                ), SoundEvent.class);
    }

    public static void inventoryClick(ItemStack clicked, ItemStack cursor, SlotActionType actionType)
    {
        boolean hasCursor = !cursor.isEmpty();
        boolean hasSlot = !clicked.isEmpty();

        switch (actionType)
        {
            case PICKUP_ALL:
                if (hasCursor)
                    ExtraSounds.playSound(ExtraSounds.config.itemPickupAll);
                return;
            case CLONE:
                ExtraSounds.playSound(ExtraSounds.config.itemClone);
                return;
            default:
                if (hasCursor)
                    ExtraSounds.playItemSound(cursor, false);
                else if (hasSlot)
                    ExtraSounds.playItemSound(clicked, true);
        }
    }

    private static final List<Item> dustItems = Arrays
            .asList(Items.REDSTONE, Items.STRING, Items.SUGAR, Items.BONE_MEAL, Items.BLAZE_POWDER,
                    Items.GLOWSTONE_DUST, Items.GUNPOWDER, Items.FEATHER,
                    Items.WHEAT); //last two added just because it fits
    private static final List<Item> paperItems = Arrays
            .asList(Items.PAPER, Items.MAP, Items.FILLED_MAP, Items.ENCHANTED_BOOK, Items.NAME_TAG);

    private static long lastPlayed = System.currentTimeMillis();

    public static void playItemSound(ItemStack stack, boolean pickup)
    {
        long now = System.currentTimeMillis();
        if (now - lastPlayed > 5)
        {
            InventorySound is = ((ItemSoundContainer) stack.getItem()).getInventorySound();
            playSound(is.sound, is.baseVol * config.itemPickup.volume,
                      getItemPitch(config.itemPickup.pitch, config.itemPickup.pitchRange, pickup));
            lastPlayed = now;
        }
    }

    /*
    public static void playItemSound(ItemStack stack, boolean pickup)
    {
        SoundEvent snd = config.itemPickup.sound;
        Item item = stack.getItem();
        String name = stack.getTranslationKey();
        if (config.blockBasedSound)
            if (dustItems.contains(item) || name.endsWith("dye"))
            {
                snd = SoundEvents.BLOCK_SAND_BREAK;
            }
            else if (item instanceof BlockItem)
            {
                snd = ((BlockItem) stack.getItem()).getBlock().getDefaultState().getSoundGroup().getPlaceSound();
            }
            else if (item.isFood())
            {
                if (stack.getItem().getFoodComponent() != null)
                    if (stack.getItem().getFoodComponent().isMeat())
                        snd = SoundEvents.ENTITY_COD_FLOP;
                    else if (stack.getItem().getFoodComponent().isSnack())
                        snd = SoundEvents.ENTITY_CHICKEN_STEP;
                    else
                        snd = SoundEvents.ENTITY_WOLF_STEP;
            }
            else if (item instanceof ToolItem)
            {
                ToolMaterial mat = ((ToolItem) stack.getItem()).getMaterial();
                if (mat.equals(ToolMaterials.WOOD))
                    snd = SoundEvents.BLOCK_WOOD_PLACE;
                else if (mat.equals(ToolMaterials.IRON))
                    snd = SoundEvents.ITEM_ARMOR_EQUIP_IRON;
                else if (mat.equals(ToolMaterials.GOLD))
                    snd = SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
                else if (mat.equals(ToolMaterials.DIAMOND))
                    snd = SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
                else if (mat.equals(ToolMaterials.NETHERITE))
                    snd = SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
                else
                    snd = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
            }
            else if (name.endsWith("ingot") || item.equals(Items.NETHERITE_SCRAP))
            {
                snd = SoundEvents.BLOCK_ANCIENT_DEBRIS_PLACE;
            }
            else if (item instanceof SnowballItem)
            {
                snd = SoundEvents.BLOCK_SNOW_PLACE;
            }
            else if (item.equals(Items.SLIME_BALL) || item.equals(Items.CLAY_BALL))
            {
                snd = SoundEvents.BLOCK_SLIME_BLOCK_PLACE;
            }
            else if (item.equals(Items.SADDLE))
            {
                snd = SoundEvents.ENTITY_HORSE_SADDLE;
            }
            else if (paperItems.contains(item) || item instanceof BookItem || item instanceof BannerPatternItem)
            {
                snd = SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN;
            }
            else if (item instanceof ElytraItem || item.equals(Items.RABBIT_HIDE))
            {
                snd = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
            }
            else if (item instanceof PotionItem)
            {
                snd = SoundEvents.ITEM_BOTTLE_FILL;
            }
            else if (item instanceof ArmorItem)
            {
                snd = ((ArmorItem) stack.getItem()).getMaterial().getEquipSound();
            }
            else if (item instanceof HorseArmorItem)
            {
                snd = SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
            }
            else if (item instanceof BucketItem)
            {
                Fluid fl = ((BucketItemAccessor) stack.getItem()).getFluid();
                if (fl instanceof FlowableFluid)
                    snd = fl.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
            }
            else if (item instanceof MilkBucketItem)
                snd = SoundEvents.ITEM_BUCKET_EMPTY;
            else if (item instanceof BoatItem)
            {
                snd = SoundEvents.BLOCK_WOOD_PLACE;
            }

        float pitch = config.itemPickup.pitch;
        if (pickup)
            pitch += config.itemPickup.pitchRange / 2;
        else
            pitch -= config.itemPickup.pitchRange / 2;
        playSound(snd, config.itemPickup.volume, pitch);
    }*/

    public static void playSound(SoundConfig.SoundSource src)
    {
        playSound(src, getPitch(src.pitch, src.pitchRange));
    }

    public static void playSound(SoundConfig.SoundSource src, float pitch)
    {
        playSound(src.sound, src.volume, pitch);
    }

    private static void playSound(SoundEvent snd, float vol, float pitch)
    {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.playSound(snd, vol * config.masterVolume, pitch);
    }

    public static float getPitch(float pitch, float pitchRange)
    {
        return pitch - pitchRange / 2 + r.nextFloat() * pitchRange;
    }

    public static float getItemPitch(float pitch, float pitchRange, boolean pickup)
    {
        if (pickup)
            return pitch + pitchRange / 2;
        else
            return pitch - pitchRange / 2;
    }
}