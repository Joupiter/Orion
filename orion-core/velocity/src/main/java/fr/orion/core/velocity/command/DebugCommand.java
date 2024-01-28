package fr.orion.core.velocity.command;

import com.mojang.brigadier.Command;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import fr.orion.core.velocity.VelocityPlugin;
import fr.orion.core.velocity.utils.VelocityCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@Getter
@AllArgsConstructor
public class DebugCommand implements VelocityCommand<VelocityPlugin> {

    private final VelocityPlugin plugin;

    @Override
    public CommandMeta getCommandMeta() {
        return getServer().getCommandManager()
                .metaBuilder("debug")
                .plugin(getPlugin())
                .build();
    }

    @Override
    public BrigadierCommand getCommand() {
        return new BrigadierCommand(BrigadierCommand
                .literalArgumentBuilder("debug")
                .requires(commandSource -> commandSource.hasPermission("orion.debug"))
                .executes(context -> {
                    CommandSource source = context.getSource();
                    source.sendMessage(Component.text("Hello world!", NamedTextColor.GREEN));
                    return Command.SINGLE_SUCCESS;
                }).build());
    }

}