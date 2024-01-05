package fr.orion.core.spigot.utils;

import fr.orion.api.utils.Utils;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class SpigotUtils {

    public String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public List<String> colorize(String... messages) {
        return SpigotUtils.colorize(Arrays.asList(messages));
    }

    public List<String> colorize(List<String> messages) {
        return messages.stream().map(SpigotUtils::colorize).collect(Collectors.toList());
    }

    public Map<Integer, String> coloredStringListToMap(String... list) {
        return SpigotUtils.coloredStringListToMap(Arrays.asList(list));
    }

    public Map<Integer, String> coloredStringListToMap(List<String> list) {
        return Utils.stringListToMap(list);
    }

}
