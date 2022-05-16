package net.lastcraft.packetlib.libraries.command;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.CommandTabComplete;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.Cooldown;
import net.lastcraft.base.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CraftSpigotCommand extends Command implements SpigotCommand {
    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    private static final int FINAL_COOLDOWN = 5;

    private final CommandManager commandManager;

    @Setter
    private CommandInterface commandInterface;
    @Setter
    private CommandTabComplete commandTabComplete;
    @Getter @Setter
    private Group group;
    @Setter
    private boolean onlyPlayers;
    @Setter
    private boolean onlyGame;

    private int cooldown;
    private String cooldownType;

    CraftSpigotCommand(CommandManager commandManager, String command,
                       CommandInterface commandInterface, String... aliases) {
        super(command, "", "", Arrays.asList(aliases));
        this.commandManager = commandManager;

        this.commandInterface = commandInterface;

        this.group = Group.DEFAULT;

        this.cooldown = FINAL_COOLDOWN;
        this.cooldownType = "command_cooldown";

        registerCommand();

        commandManager.register(this);
    }

    @SuppressWarnings("unchecked")
    private void registerCommand() {
        List<String> commands = new ArrayList<>(getAliases());
        commands.add(this.getName());
        try {
            Method register = SimpleCommandMap.class.getDeclaredMethod("register",
                    String.class, Command.class, Boolean.TYPE, String.class);
            register.setAccessible(true);
            for (String cmd : commands) {
                register.invoke(commandManager.getCommandMap(), cmd, this, !(this.getName().equals(cmd)), "lastcraft");
            }

            register.setAccessible(false);

            Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);
            Map<String, Command> map = (Map) knownCommands.get(commandManager.getCommandMap());
            for (String cmd : commands) {
                map.put(cmd.toLowerCase(), this);
            }

            knownCommands.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCooldown(int second, String type) {
        this.cooldown = second * 20;
        this.cooldownType = type;
    }

    @Override
    public int getSecondCooldown() {
        return cooldown / 20;
    }

    @Override
    public int getLevel() {
        return group.getLevel();
    }

    @Override
    public void disable() {
        LastCraft.getCommandsAPI().disableCommand((SpigotCommand) this);
    }

    @Override
    public Command getCommand() {
        return this;
    }

    @Override
    public void setMinimalGroup(int level) {
        setMinimalGroup(Group.getGroupByLevel(level));
    }

    @Override
    public void setMinimalGroup(Group group) {
        this.group = group;
    }

    @Override
    public final boolean execute(CommandSender commandSender, String command, String[] args) {
        GamerEntity gamerEntity = GAMER_MANAGER.getEntity(commandSender);
        if (gamerEntity == null) {
            return false;
        }

        boolean checkPlayer = commandSender instanceof Player;

        if (!checkPlayer && onlyPlayers) {
            gamerEntity.sendMessage("§cДанную команду нельзя использовать из консоли!");
            return false;
        }

        if (checkPlayer) {
            Player player = (Player) commandSender;
            BukkitGamer gamer = (BukkitGamer) gamerEntity;
            if (!player.isOnline()) {
                return false;
            }

            if (gamer.getGroup() != Group.ADMIN) {
                if (Cooldown.hasCooldown(player.getName(), cooldownType)) {
                    if (cooldown != FINAL_COOLDOWN) {
                        Language lang = gamerEntity.getLanguage();
                        int time = Cooldown.getSecondCooldown(player.getName(), cooldownType);
                        gamerEntity.sendMessage(String.format(lang.getMessage("COOLDOWN"),
                                String.valueOf(time), StringUtil.getCorrectWord(time, "TIME_SECOND_1", lang)));
                    }
                    return false;
                }
                Cooldown.addCooldown(player.getName(), cooldownType, cooldown);
            }

            if (onlyGame && (GameState.getCurrent() == GameState.GAME || LastCraft.isGame())) {
                gamerEntity.sendMessageLocale("ERROR_COMMAND_IN_GAME", GameSettings.prefix);
                return false;
            }

            if (gamer.getGroup().getLevel() < group.getLevel()) {
                if (group == Group.ADMIN || group == Group.MODERATOR || group == Group.HELPER) {
                    gamer.sendMessageLocale("NO_PERMS");
                } else {
                    gamer.sendMessageLocale("NO_PERMS_GROUP", group.getNameEn());
                }
                return false;
            }
        }

        this.fixArgs(args);
        this.commandInterface.execute(gamerEntity, command, args);
        return true;
    }

    private void fixArgs(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg.length() > 2) {
                    char c0 = arg.charAt(0);
                    char cl = arg.charAt(arg.length() - 1);
                    if ((c0 == '[' && cl == ']') || (c0 == '<' && cl == '>')) {
                        args[i] = arg.substring(1, arg.length() - 1);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean testPermissionSilent(CommandSender commandSender) {
        if (onlyPlayers && !(commandSender instanceof Player)) {
            return false;
        }

        GamerEntity gamerEntity = GAMER_MANAGER.getEntity(commandSender);
        if (gamerEntity == null) {
            return false;
        }

        if (gamerEntity instanceof BukkitGamer) {
            BukkitGamer gamer = (BukkitGamer) gamerEntity;
            return gamer.getGroup().getLevel() >= group.getLevel();
        }

        return true;
    }

    @Override
    public final List<String> tabComplete(CommandSender commandSender, String alias, String[] args) throws IllegalArgumentException {
        if (onlyPlayers && !(commandSender instanceof Player)) {
            return ImmutableList.of();
        }

        GamerEntity gamerEntity = GAMER_MANAGER.getEntity(commandSender);
        if (gamerEntity == null) {
            return ImmutableList.of();
        }

        if (gamerEntity instanceof BukkitGamer) {
            BukkitGamer gamer = (BukkitGamer) gamerEntity;
            if (gamer.getGroup().getLevel() < group.getLevel()) {
                return ImmutableList.of();
            }
        }

        if (commandTabComplete != null) {
            List<String> complete = commandTabComplete.getComplete(gamerEntity, alias, args);
            if (complete == null) {
                return super.tabComplete(commandSender, alias, args);
            }
            return complete.stream()
                    .limit(15)
                    .collect(Collectors.toList());
        }

        return super.tabComplete(commandSender, alias, args);
    }
}
