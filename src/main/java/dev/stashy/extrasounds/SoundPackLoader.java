package dev.stashy.extrasounds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.stashy.extrasounds.debug.DebugUtils;
import dev.stashy.extrasounds.json.SoundSerializer;
import dev.stashy.extrasounds.mixin.BucketFluidAccessor;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundPackLoader
{
    private static RuntimeResourcePack genericPack = RuntimeResourcePack.create("extrasounds");
    private static final Identifier soundsJsonId = new Identifier("extrasounds:sounds.json");

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Sound.class, new SoundSerializer())
            .create();

    public static void init()
    {
        genericPack.addLazyResource(ResourceType.CLIENT_RESOURCES, soundsJsonId,
                                    (runtimeResourcePack, identifier) -> generateJson());
        RRPCallback.BEFORE_VANILLA.register((packs) -> packs.add(genericPack));
    }

    private static byte[] generateJson()
    {
        Map<String, SoundEntry> entries = new HashMap<>();
        Registry.ITEM.getIds().forEach((i) -> loadItem(i, entries));
        Registry.BLOCK.getIds().forEach((i) -> loadBlock(i, entries));
        byte[] json = gson.toJson(entries).getBytes();
        DebugUtils.exportSoundsJson(json);
        return json;
    }

    public static void loadItem(Identifier id, Map<String, SoundEntry> entries)
    {
        Identifier snd = new Identifier("extrasounds:item.click." + id.getPath());
        String is = getItemSound(id);
        float vol = is.startsWith("extrasounds:") ? 1f : 0.2f;
        float pitch = is.startsWith("extrasounds:") ? 1f : 1.7f;

        entries.put(snd.getPath(), new SoundEntry(List.of(
                new Sound(is, vol, pitch, 1,
                          Sound.RegistrationType.SOUND_EVENT, false, false, 16)
        ), false, null));

        Registry.register(Registry.SOUND_EVENT, snd, new SoundEvent(snd));
    }

    public static void loadBlock(Identifier id, Map<String, SoundEntry> entries)
    {
        Block b = Registry.BLOCK.get(id);
        String soundId = getBlockSound(b);
        float vol = 1f;
        if (soundId.equals("minecraft:block.grass.place") || soundId.equals("mineraft:block.slime_block.place"))
            vol = 0.1f;
        else if (!soundId.startsWith("extrasounds:"))
            vol = 0.2f;
        entries.put("item.click." + id.getPath(),
                    new SoundEntry(List.of(
                            new Sound(soundId, vol, 1.7f, 1, Sound.RegistrationType.SOUND_EVENT, false, false, 0)
                    ), true, null));
    }

    private static String getItemSound(Identifier id)
    {
        Item i = Registry.ITEM.get(id);
        if (i instanceof MusicDiscItem)
            return "extrasounds:item.category.music_disc";
        else if (i instanceof BoatItem)
            return "extrasounds:item.category.boat";
        else if (i instanceof ToolItem t)
        {
            switch ((ToolMaterials) t.getMaterial())
            {
                case WOOD -> {return "extrasounds:item.category.gear.wood";}
                case STONE -> {return "extrasounds:item.category.gear.stone";}
                case IRON -> {return "extrasounds:item.category.gear.iron";}
                case GOLD -> {return "extrasounds:item.category.gear.golden";}
                case DIAMOND -> {return "extrasounds:item.category.gear.diamond";}
                case NETHERITE -> {return "extrasounds:item.category.gear.netherite";}
                default -> {return "extrasounds:item.category.gear.generic";}
            }
        }
        else if (i instanceof ArmorItem a)
        {
            switch ((ArmorMaterials) a.getMaterial())
            {
                case IRON -> {return "extrasounds:item.category.gear.iron";}
                case GOLD -> {return "extrasounds:item.category.gear.golden";}
                case DIAMOND -> {return "extrasounds:item.category.gear.diamond";}
                case NETHERITE -> {return "extrasounds:item.category.gear.netherite";}
                case CHAIN -> {return "extrasounds:item.category.gear.chain";}
                case TURTLE -> {return "extrasounds:item.category.gear.turtle";}
                case LEATHER -> {return "extrasounds:item.category.gear.leather";}
                default -> {return "extrasounds:item.category.gear.generic";}
            }
        }
        else if (i instanceof HorseArmorItem a || i instanceof CompassItem || i instanceof SpyglassItem || i instanceof ShearsItem)
            return "extrasounds:item.category.gear.golden";
        else if (i instanceof LeadItem || i instanceof ElytraItem || i instanceof SaddleItem)
            return "extrasounds:item.category.gear.leather";
        else if (i instanceof BowItem || i instanceof CrossbowItem || i instanceof FishingRodItem || i instanceof OnAStickItem)
            return "extrasounds:item.category.gear.generic";
        else if (i instanceof BucketItem b)
        {
            Fluid f = ((BucketFluidAccessor) b).getFluid();
            return f.getBucketFillSound().isPresent() ? f.getBucketFillSound().get().getId()
                                                         .toString() : "extrasounds:item.category.metal";
        }
        else if (i instanceof MinecartItem)
            return "extrasounds:item.category.minecart";
        else if (i instanceof ItemFrameItem)
            return "extrasounds:item.category.frame";
        else if (i instanceof PotionItem || i instanceof ExperienceBottleItem)
            return "extrasounds:item.category.potion";
        else if (i instanceof BannerPatternItem || i instanceof BookItem || i instanceof WritableBookItem || i instanceof WrittenBookItem
                || i instanceof EnchantedBookItem || i instanceof EmptyMapItem || i instanceof FilledMapItem || i instanceof NameTagItem)
            return "extrasounds:item.category.paper";
        else if (i instanceof ArrowItem)
            return "extrasounds:item.category.arrow";
        else if (i instanceof DyeItem)
            return "extrasounds:item.category.dust";
        else if (i instanceof SpawnEggItem)
            return "extrasounds:item.category.wet_slippery";

        return "extrasounds:generic.click";
    }

    private static String getBlockSound(Block b)
    {
        if (b instanceof AbstractRailBlock)
            return "extrasounds:item.category.rail";
        else if (b instanceof BannerBlock)
            return "extrasounds:item.category.banner";
        else if (b instanceof LeavesBlock)
            return "extrasounds:item.category.leaves";
        return b.getSoundGroup(b.getDefaultState()).getPlaceSound().getId().toString();
    }
}
