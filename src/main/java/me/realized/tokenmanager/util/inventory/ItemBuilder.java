package me.realized.tokenmanager.util.inventory;

import java.util.Arrays;
import java.util.List;
import me.realized.tokenmanager.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemBuilder {

    private final ItemStack result;

    private ItemBuilder(final Material type, final int amount, final short durability) {
        this.result = new ItemStack(type, amount);

        if (durability != 0) {
            final ItemMeta meta = this.result.getItemMeta();

            if (meta instanceof Damageable) {
                ((Damageable) meta).setDamage(durability);
                this.result.setItemMeta(meta);
            } else {
                this.result.setDurability(durability);
            }
        }
    }

    public static ItemBuilder of(final Material type) {
        return of(type, 1);
    }

    public static ItemBuilder of(final Material type, final int amount) {
        return of(type, amount, (short) 0);
    }

    public static ItemBuilder of(final Material type, final int amount, final short durability) {
        return new ItemBuilder(type, amount, durability);
    }

    public static ItemBuilder of(final String type, final int amount, final short durability) {
        Material material = Material.matchMaterial(type);

        if (material == null) {
            try {
                material = Material.matchMaterial(type, true);
            } catch (Throwable ignored) {}
        }

        if (material == null) {
            material = Material.getMaterial(type);
        }

        return new ItemBuilder(material != null ? material : Material.STONE, amount, durability);
    }

    public ItemBuilder name(final String name) {
        final ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(StringUtil.color(name));
        result.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(final String... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemBuilder lore(final List<String> lore) {
        final ItemMeta meta = result.getItemMeta();
        meta.setLore(StringUtil.color(lore));
        result.setItemMeta(meta);
        return this;
    }

    public ItemStack build() {
        return result;
    }
}
