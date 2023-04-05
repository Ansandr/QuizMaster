package fun.socialcraft.quiz.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class AdventureUtil {

    public static final Component formatAmpersand(String string) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(string);
    }

    public static final Component formatAdventure(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    public static final String formatString(Component component) {
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }
}
