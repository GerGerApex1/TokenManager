package me.realized.tokenmanager.util.profile;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Utility for fetching player UUIDs from Mojang's API.
 */
final class UUIDFetcher {

    private static final String PROFILE_URL = "https://api.mojang.com/users/profiles/minecraft/";
    private static final JsonParser JSON_PARSER = new JsonParser();
    private static final Cache<String, UUID> NAME_TO_UUID = CacheBuilder.newBuilder()
        .concurrencyLevel(4)
        .maximumSize(1000)
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .build();

    private UUIDFetcher() {}

    static String getUUID(final String name) throws Exception {
        final UUID cached = NAME_TO_UUID.getIfPresent(name.toLowerCase());

        if (cached != null) {
            return cached.toString();
        }

        final URL url = URI.create(PROFILE_URL + name).toURL();
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "TokenManager");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        try (Reader reader = new InputStreamReader(connection.getInputStream())) {
            final JsonObject profile = (JsonObject) JSON_PARSER.parse(reader);
            final UUID uuid = get(profile.get("id").getAsString());
            NAME_TO_UUID.put(name.toLowerCase(), uuid);

            if (profile.has("name")) {
                NAME_TO_UUID.put(profile.get("name").getAsString().toLowerCase(), uuid);
            }

            return uuid.toString();
        }
    }

    private static UUID get(final String id) {
        return UUID.fromString(
            id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
    }
}
