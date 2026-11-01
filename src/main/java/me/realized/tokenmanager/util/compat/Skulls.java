package me.realized.tokenmanager.util.compat;

import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public final class Skulls extends CompatBase {

    private static final Pattern URL_PATTERN = Pattern.compile("\"url\"\\s*:\\s*\"([^\"]+)\"");

    public static void setSkull(final SkullMeta meta, final String value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        // Try modern Bukkit PlayerProfile API (1.18.1+)
        try {
            final String skinUrl = extractUrl(value);

            if (skinUrl != null) {
                final PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID(), "");
                final PlayerTextures textures = profile.getTextures();
                final URL url = URI.create(skinUrl).toURL();
                textures.setSkin(url);
                profile.setTextures(textures);
                meta.setOwnerProfile(profile);
                return;
            }
        } catch (Throwable ignored) {}

        // Fallback to GameProfile reflection for older Bukkit versions (pre-1.18.1)
        try {
            if (GAME_PROFILE_CONST != null && PROPERTY_CONST != null && PUT != null && PROFILE != null && GET_PROPERTIES != null) {
                final Object profile = GAME_PROFILE_CONST.newInstance(UUID.randomUUID(), null);
                final Object propertyMap = GET_PROPERTIES.invoke(profile);

                if (propertyMap != null) {
                    PUT.invoke(propertyMap, "textures", PROPERTY_CONST.newInstance("textures", value));
                    PROFILE.set(meta, profile);
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private static String extractUrl(final String value) {
        if (value.startsWith("http://") || value.startsWith("https://")) {
            return value;
        }

        try {
            final String decoded = new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
            final Matcher matcher = URL_PATTERN.matcher(decoded);

            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (IllegalArgumentException ignored) {}

        if (value.length() >= 64 && !value.contains(" ") && !value.contains(":")) {
            return "http://textures.minecraft.net/texture/" + value;
        }

        return null;
    }

    private Skulls() {}
}
