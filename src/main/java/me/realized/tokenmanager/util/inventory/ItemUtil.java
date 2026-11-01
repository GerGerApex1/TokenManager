package me.realized.tokenmanager.util.inventory;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.function.Consumer;
import me.realized.tokenmanager.util.EnumUtil;
import me.realized.tokenmanager.util.NumberUtil;
import me.realized.tokenmanager.util.StringUtil;
import me.realized.tokenmanager.util.compat.CompatUtil;
import me.realized.tokenmanager.util.compat.Items;
import me.realized.tokenmanager.util.compat.Skulls;
import me.realized.tokenmanager.util.compat.SpawnEggs;
import me.realized.tokenmanager.util.compat.Terracottas;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public final class ItemUtil {

    private static final Map<String, Enchantment> ENCHANTMENTS;
    private static final Map<String, PotionEffectType> EFFECTS;

    static {
        final Map<String, Enchantment> enchantments = new HashMap<>();
        Arrays.stream(Enchantment.values()).forEach(enchantment -> {
            enchantments.put(enchantment.getName(), enchantment);

            if (!CompatUtil.isPre1_13()) {
                enchantments.put(enchantment.getKey().getKey(), enchantment);
            }
        });
        enchantments.put("power", Enchantment.POWER);
        enchantments.put("flame", Enchantment.FLAME);
        enchantments.put("infinity", Enchantment.INFINITY);
        enchantments.put("punch", Enchantment.PUNCH);
        enchantments.put("sharpness", Enchantment.SHARPNESS);
        enchantments.put("baneofarthopods", Enchantment.BANE_OF_ARTHROPODS);
        enchantments.put("baneofarthropods", Enchantment.BANE_OF_ARTHROPODS);
        enchantments.put("smite", Enchantment.SMITE);
        enchantments.put("efficiency", Enchantment.EFFICIENCY);
        enchantments.put("unbreaking", Enchantment.UNBREAKING);
        enchantments.put("thorns", Enchantment.THORNS);
        enchantments.put("fireaspect", Enchantment.FIRE_ASPECT);
        enchantments.put("knockback", Enchantment.KNOCKBACK);
        enchantments.put("fortune", Enchantment.FORTUNE);
        enchantments.put("looting", Enchantment.LOOTING);
        enchantments.put("respiration", Enchantment.RESPIRATION);
        enchantments.put("blastprotection", Enchantment.BLAST_PROTECTION);
        enchantments.put("featherfalling", Enchantment.FEATHER_FALLING);
        enchantments.put("fireprotection", Enchantment.FIRE_PROTECTION);
        enchantments.put("projectileprotection", Enchantment.PROJECTILE_PROTECTION);
        enchantments.put("protection", Enchantment.PROTECTION);
        enchantments.put("silktouch", Enchantment.SILK_TOUCH);
        enchantments.put("aquaaffinity", Enchantment.AQUA_AFFINITY);
        enchantments.put("luck", Enchantment.LUCK_OF_THE_SEA);
        enchantments.put("luckofthesea", Enchantment.LUCK_OF_THE_SEA);
        enchantments.put("lure", Enchantment.LURE);
        enchantments.put("depthstrider", Enchantment.DEPTH_STRIDER);
        enchantments.put("frostwalker", Enchantment.FROST_WALKER);
        enchantments.put("mending", Enchantment.MENDING);
        enchantments.put("bindingcurse", Enchantment.BINDING_CURSE);
        enchantments.put("vanishingcurse", Enchantment.VANISHING_CURSE);
        enchantments.put("sweepingedge", Enchantment.SWEEPING_EDGE);
        enchantments.put("loyalty", Enchantment.LOYALTY);
        enchantments.put("impaling", Enchantment.IMPALING);
        enchantments.put("riptide", Enchantment.RIPTIDE);
        enchantments.put("channeling", Enchantment.CHANNELING);
        enchantments.put("multishot", Enchantment.MULTISHOT);
        enchantments.put("quickcharge", Enchantment.QUICK_CHARGE);
        enchantments.put("piercing", Enchantment.PIERCING);
        enchantments.put("soulspeed", Enchantment.SOUL_SPEED);
        enchantments.put("swiftsneak", Enchantment.SWIFT_SNEAK);
        enchantments.put("density", Enchantment.DENSITY);
        enchantments.put("breach", Enchantment.BREACH);
        enchantments.put("windburst", Enchantment.WIND_BURST);
        enchantments.put("lunge", Enchantment.LUNGE);
        ENCHANTMENTS = Collections.unmodifiableMap(enchantments);

        final Map<String, PotionEffectType> effects = new HashMap<>();
        Arrays.stream(PotionEffectType.values()).forEach(type -> {
            if (type == null) {
                return;
            }

            effects.put(type.getName(), type);

            if (!CompatUtil.isPre1_13()) {
                effects.put(type.getKey().getKey(), type);
            }
        });
        effects.put("speed", PotionEffectType.SPEED);
        effects.put("slowness", PotionEffectType.SLOWNESS);
        effects.put("haste", PotionEffectType.HASTE);
        effects.put("fatigue", PotionEffectType.MINING_FATIGUE);
        effects.put("miningfatigue", PotionEffectType.MINING_FATIGUE);
        effects.put("strength", PotionEffectType.STRENGTH);
        effects.put("heal", PotionEffectType.INSTANT_HEALTH);
        effects.put("instanthealth", PotionEffectType.INSTANT_HEALTH);
        effects.put("harm", PotionEffectType.INSTANT_DAMAGE);
        effects.put("instantdamage", PotionEffectType.INSTANT_DAMAGE);
        effects.put("jump", PotionEffectType.JUMP_BOOST);
        effects.put("jumpboost", PotionEffectType.JUMP_BOOST);
        effects.put("nausea", PotionEffectType.NAUSEA);
        effects.put("regeneration", PotionEffectType.REGENERATION);
        effects.put("resistance", PotionEffectType.RESISTANCE);
        effects.put("fireresistance", PotionEffectType.FIRE_RESISTANCE);
        effects.put("waterbreathing", PotionEffectType.WATER_BREATHING);
        effects.put("invisibility", PotionEffectType.INVISIBILITY);
        effects.put("blindness", PotionEffectType.BLINDNESS);
        effects.put("nightvision", PotionEffectType.NIGHT_VISION);
        effects.put("hunger", PotionEffectType.HUNGER);
        effects.put("weakness", PotionEffectType.WEAKNESS);
        effects.put("poison", PotionEffectType.POISON);
        effects.put("wither", PotionEffectType.WITHER);
        effects.put("healthboost", PotionEffectType.HEALTH_BOOST);
        effects.put("absorption", PotionEffectType.ABSORPTION);
        effects.put("saturation", PotionEffectType.SATURATION);
        effects.put("glowing", PotionEffectType.GLOWING);
        effects.put("levitation", PotionEffectType.LEVITATION);
        effects.put("luck", PotionEffectType.LUCK);
        effects.put("unluck", PotionEffectType.UNLUCK);
        effects.put("badluck", PotionEffectType.UNLUCK);
        effects.put("slowfalling", PotionEffectType.SLOW_FALLING);
        effects.put("conduitpower", PotionEffectType.CONDUIT_POWER);
        effects.put("dolphinsgrace", PotionEffectType.DOLPHINS_GRACE);
        effects.put("badomen", PotionEffectType.BAD_OMEN);
        effects.put("heroofthevillage", PotionEffectType.HERO_OF_THE_VILLAGE);
        effects.put("darkness", PotionEffectType.DARKNESS);
        effects.put("trialomen", PotionEffectType.TRIAL_OMEN);
        effects.put("raidomen", PotionEffectType.RAID_OMEN);
        effects.put("windcharged", PotionEffectType.WIND_CHARGED);
        effects.put("weaving", PotionEffectType.WEAVING);
        effects.put("oozing", PotionEffectType.OOZING);
        effects.put("infested", PotionEffectType.INFESTED);
        effects.put("breathofthenautilus", PotionEffectType.BREATH_OF_THE_NAUTILUS);
        EFFECTS = Collections.unmodifiableMap(effects);
    }

    public static ItemStack loadFromString(final String line) {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Line is empty or null");
        }

        final String[] args = line.split(" +");
        String[] materialData = args[0].split(":");
        Material material = Material.matchMaterial(materialData[0]);

        if (material == null) {
            try {
                material = Material.matchMaterial(materialData[0], true);
            } catch (Throwable ignored) {}
        }

        // TEMP: Allow confirm button item loading in 1.13
        if (!CompatUtil.isPre1_13()) {
            if (materialData[0].equalsIgnoreCase("STAINED_CLAY")) {
                material = Material.TERRACOTTA;

                if (materialData.length > 1) {
                    material = Terracottas.from((short) NumberUtil.parseLong(materialData[1]).orElse(0));
                }
            }
        }

        if (material == null) {
            throw new IllegalArgumentException("'" + args[0] + "' is not a valid material");
        }

        ItemStack result = new ItemStack(material, 1);

        if (materialData.length > 1) {
            // Handle potions and spawn eggs switching to NBT in 1.9+
            if (!CompatUtil.isPre1_9()) {
                if (material.name().contains("POTION")) {
                    final List<String> values = Arrays.asList(materialData[1].split("-"));
                    final String typeName = values.get(0);
                    final boolean extended = values.contains("extended");
                    final boolean strong = values.contains("strong");

                    final PotionType type = resolvePotionType(typeName, extended, strong);

                    if (type == null) {
                        throw new IllegalArgumentException("'" + values.get(0) + "' is not a valid PotionType. Available: " + EnumUtil.getNames(PotionType.class));
                    }

                    final PotionMeta meta = (PotionMeta) result.getItemMeta();
                    applyPotionType(meta, type, extended, strong);
                    result.setItemMeta(meta);
                } else if (CompatUtil.isPre1_13() && material.name().equals("MONSTER_EGG")) {
                    final EntityType type;

                    if ((type = EnumUtil.getByName(materialData[1], EntityType.class)) == null) {
                        throw new IllegalArgumentException("'" + materialData[0] + "' is not a valid EntityType. Available: " + EnumUtil.getNames(EntityType.class));
                    }

                    result = new SpawnEggs(type).toItemStack();
                }
            }

            final OptionalLong value;

            if ((value = NumberUtil.parseLong(materialData[1])).isPresent()) {
                final ItemMeta meta = result.getItemMeta();

                if (meta instanceof Damageable) {
                    ((Damageable) meta).setDamage((int) value.getAsLong());
                    result.setItemMeta(meta);
                } else {
                    result.setDurability((short) value.getAsLong());
                }
            }
        }

        if (args.length < 2) {
            return result;
        }

        result.setAmount(Integer.parseInt(args[1]));

        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                final String argument = args[i];
                final String[] pair = argument.split(":", 2);

                if (pair.length < 2) {
                    continue;
                }

                applyMeta(result, pair[0], pair[1]);
            }
        }

        return result;
    }

    private static PotionType resolvePotionType(final String rawName, final boolean extended, final boolean strong) {
        String name = rawName.toUpperCase();

        switch (name) {
            case "SPEED":
                name = "SWIFTNESS";
                break;
            case "HEAL":
            case "INSTANT_HEAL":
                name = "HEALING";
                break;
            case "HARM":
            case "INSTANT_DAMAGE":
                name = "HARMING";
                break;
            case "JUMP":
                name = "LEAPING";
                break;
            case "REGEN":
                name = "REGENERATION";
                break;
            default:
                break;
        }

        if (extended) {
            final PotionType longType = EnumUtil.getByName("LONG_" + name, PotionType.class);

            if (longType != null) {
                return longType;
            }
        }

        if (strong) {
            final PotionType strongType = EnumUtil.getByName("STRONG_" + name, PotionType.class);

            if (strongType != null) {
                return strongType;
            }
        }

        final PotionType mappedType = EnumUtil.getByName(name, PotionType.class);

        if (mappedType != null) {
            return mappedType;
        }

        return EnumUtil.getByName(rawName.toUpperCase(), PotionType.class);
    }

    @SuppressWarnings("deprecation")
    private static void applyPotionType(final PotionMeta meta, final PotionType type, final boolean extended, final boolean strong) {
        try {
            meta.setBasePotionType(type);
            return;
        } catch (NoSuchMethodError | Exception ignored) {}

        try {
            meta.setBasePotionData(new PotionData(type, extended, strong));
        } catch (Throwable ignored) {}
    }

    public static ItemStack loadFromString(final String line, final Consumer<String> errorHandler) {
        ItemStack result;

        try {
            result = loadFromString(line);
        } catch (Exception ex) {
            result = ItemBuilder
                .of(Material.REDSTONE_BLOCK)
                .name("&4&m------------------")
                .lore(
                    "&cThere was an error",
                    "&cwhile loading this",
                    "&citem, please contact",
                    "&can administrator.",
                    "&4&m------------------"
                )
                .build();
            errorHandler.accept(ex.getMessage());
        }

        return result;
    }

    private static void applyMeta(final ItemStack item, final String key, final String value) {
        final ItemMeta meta = item.getItemMeta();

        if (key.equalsIgnoreCase("name")) {
            meta.setDisplayName(StringUtil.color(value.replace("_", " ")));
            item.setItemMeta(meta);
            return;
        }

        if (key.equalsIgnoreCase("lore")) {
            meta.setLore(StringUtil.color(Lists.newArrayList(value.split("\\|")), line -> line.replace("_", " ")));
            item.setItemMeta(meta);
            return;
        }

        if (key.equalsIgnoreCase("unbreakable") && value.equalsIgnoreCase("true")) {
            if (CompatUtil.isPre1_12()) {
                //meta.spigot().setUnbreakable(true);
            } else {
                meta.setUnbreakable(true);
            }

            item.setItemMeta(meta);
            return;
        }

        if (key.equalsIgnoreCase("flags")) {
            final String[] flags = value.split(",");

            for (final String flag : flags) {
                final ItemFlag itemFlag = EnumUtil.getByName(flag, ItemFlag.class);

                if (itemFlag == null) {
                    continue;
                }

                meta.addItemFlags(itemFlag);
            }

            item.setItemMeta(meta);
            return;
        }

        final Enchantment enchantment = ENCHANTMENTS.get(key);

        if (enchantment != null) {
            item.addUnsafeEnchantment(enchantment, Integer.parseInt(value));
            return;
        }

        if (item.getType().name().contains("POTION")) {
            final PotionEffectType effectType = EFFECTS.get(key);

            if (effectType != null) {
                final String[] values = value.split(":");
                final PotionMeta potionMeta = (PotionMeta) meta;
                potionMeta.addCustomEffect(new PotionEffect(effectType, Integer.parseInt(values[1]), Integer.parseInt(values[0])), true);
                item.setItemMeta(potionMeta);
                return;
            }
        }

        if ((meta instanceof SkullMeta || Items.equals(Items.HEAD, item)) && (key.equalsIgnoreCase("player") || key.equalsIgnoreCase("owner") || key.equalsIgnoreCase("texture"))) {
            final SkullMeta skullMeta = (SkullMeta) meta;

            // Since Base64 texture strings are much longer than usernames...
            if (value.length() > 16) {
                Skulls.setSkull(skullMeta, value);
            } else {
                skullMeta.setOwner(value);
            }

            item.setItemMeta(skullMeta);
            return;
        }

        if (item.getType().name().contains("LEATHER_") && key.equalsIgnoreCase("color")) {
            final LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
            final String[] values = value.split(",");
            leatherArmorMeta.setColor(Color.fromRGB(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2])));
            item.setItemMeta(leatherArmorMeta);
            return;
        }

        if (key.equalsIgnoreCase("custommodeldata") && !CompatUtil.isPre1_14()) {
            meta.setCustomModelData(Integer.parseInt(value));
            item.setItemMeta(meta);
        }
    }

    public static void copyNameLore(final ItemStack from, final ItemStack to) {
        final ItemMeta fromMeta = from.getItemMeta(), toMeta = to.getItemMeta();

        if (fromMeta.hasDisplayName()) {
            toMeta.setDisplayName(fromMeta.getDisplayName());
        }

        if (fromMeta.hasLore()) {
            toMeta.setLore(fromMeta.getLore());
        }

        to.setItemMeta(toMeta);
    }

    private ItemUtil() {}
}
