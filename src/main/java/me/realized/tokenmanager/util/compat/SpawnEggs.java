package me.realized.tokenmanager.util.compat;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class SpawnEggs extends CompatBase {

    @Getter
    private final EntityType type;

    public SpawnEggs(EntityType type) {
        this.type = type;
    }

    @SuppressWarnings("deprecation")
    public ItemStack toItemStack() {
        try {
            final Material material = Material.getMaterial("MONSTER_EGG");

            if (material == null) {
                return null;
            }

            final ItemStack item = new ItemStack(material);

            if (AS_NMS_COPY == null || GET_TAG == null || SET_TAG == null || TAG_COMPOUND == null || SET_STRING == null || SET == null || AS_BUKKIT_COPY == null) {
                return item;
            }

            final Object nmsItem = AS_NMS_COPY.invoke(null, item);
            Object tag = GET_TAG.invoke(nmsItem);

            if (tag == null) {
                tag = TAG_COMPOUND.getDeclaredConstructor().newInstance();
            }

            final Object id = TAG_COMPOUND.getDeclaredConstructor().newInstance();
            SET_STRING.invoke(id, "id", type.getName());
            SET.invoke(tag, "EntityTag", id);
            SET_TAG.invoke(nmsItem, tag);
            return (ItemStack) AS_BUKKIT_COPY.invoke(null, nmsItem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
