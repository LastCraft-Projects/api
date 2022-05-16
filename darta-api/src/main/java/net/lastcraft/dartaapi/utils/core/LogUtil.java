package net.lastcraft.dartaapi.utils.core;

import lombok.experimental.UtilityClass;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.Spigot;

import java.io.*;
import java.util.List;

@UtilityClass
public class LogUtil {

    private final Spigot SPIGOT = LastCraft.getGamerManager().getSpigot();

    public void log(List<String> log) {
        try {
            File path = new File("/home/logs/" + SPIGOT.getName() + "/");
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File("/home/logs/" + SPIGOT.getName() + "/log-"
                    + net.lastcraft.base.util.StringUtil.getDate().replace('/', '-') + ".txt");
            if (!file.isFile()) {
                file.createNewFile();
            }
            try (FileWriter fw = new FileWriter("/home/logs/" + SPIGOT.getName() + "/log-"
                    + net.lastcraft.base.util.StringUtil.getDate().replace('/', '-') + ".txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter pw = new PrintWriter(bw)) {
                log.forEach(pw::println);
            } catch (IOException ignored) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
