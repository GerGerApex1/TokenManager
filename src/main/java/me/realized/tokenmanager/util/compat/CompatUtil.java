package me.realized.tokenmanager.util.compat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.realized.tokenmanager.util.NumberUtil;
import org.bukkit.Bukkit;

public final class CompatUtil {

    private static final long SUB_VERSION;
    private static final Pattern VERSION_PATTERN = Pattern.compile("^(?:1\\.)?(\\d+)");

    static {
        long version = 0;
        final String bukkitVersion = Bukkit.getBukkitVersion();
        final Matcher matcher = VERSION_PATTERN.matcher(bukkitVersion);

        if (matcher.find()) {
            version = NumberUtil.parseLong(matcher.group(1)).orElse(0);
        }

        if (version == 0) {
            final String packageName = Bukkit.getServer().getClass().getPackage().getName();
            final String[] parts = packageName.substring(packageName.lastIndexOf('.') + 1).split("_");

            if (parts.length > 1) {
                version = NumberUtil.parseLong(parts[1]).orElse(0);
            }
        }

        SUB_VERSION = version;
    }

    private CompatUtil() {}

    public static long getSubVersion() {
        return SUB_VERSION;
    }

    public static boolean isPre1_17() {
        return SUB_VERSION < 17;
    }

    public static boolean isPre1_14() {
        return SUB_VERSION < 14;
    }

    public static boolean isPre1_13() {
        return SUB_VERSION < 13;
    }

    public static boolean isPre1_12() {
        return SUB_VERSION < 12;
    }

    public static boolean isPre1_9() {
        return SUB_VERSION < 9;
    }
}
