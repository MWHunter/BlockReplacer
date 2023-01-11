package io.defineoutside.blockreplacer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class BlockReplacer extends JavaPlugin implements CommandExecutor {

    List<Tag<Material>> blockTags = Arrays.asList(
            Tag.ALL_SIGNS,
            Tag.BASE_STONE_NETHER,
            Tag.BASE_STONE_OVERWORLD,
            Tag.BUTTONS,
            Tag.DOORS,
            Tag.FENCES,
            Tag.FENCE_GATES,
            Tag.LOGS,
            Tag.PLANKS,
            Tag.PRESSURE_PLATES,
            Tag.SLABS,
            Tag.STAIRS,
            Tag.TRAPDOORS,
            Tag.WALLS
    );

    @Override
    public void onEnable() {
        this.getCommand("/replacetype").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can run this command");
            return true;
        }

        if (args.length < 2) {
            return false;
        }

        String argOne = args[0].toUpperCase(Locale.ROOT);
        String argTwo = args[1].toUpperCase(Locale.ROOT);

        for (Tag<Material> tag : blockTags) {
            Material typeOne = null;
            Material typeTwo = null;

            for (Material mat : tag.getValues()) {
                if (mat.isBlock() && mat.name().contains(argOne)) {
                    if (typeOne == null || typeOne.name().length() > mat.name().length()) {
                        typeOne = mat;
                    }
                }
                if (mat.isBlock() && mat.name().contains(argTwo)) {
                    if (typeTwo == null || typeTwo.name().length() > mat.name().length()) {
                        typeTwo = mat;
                    }
                }
            }

            if (typeOne != null && typeTwo != null) {
                player.performCommand("/replace " + typeOne.name() + " ^" + typeTwo.name());
            }
        }

        return true;
    }
}
