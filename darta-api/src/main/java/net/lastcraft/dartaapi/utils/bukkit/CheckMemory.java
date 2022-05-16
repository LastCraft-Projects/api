package net.lastcraft.dartaapi.utils.bukkit;

import net.lastcraft.dartaapi.utils.core.CoreUtil;
import org.bukkit.Bukkit;

import java.util.concurrent.TimeUnit;

@Deprecated
public class CheckMemory extends Thread {

    public CheckMemory(){
        start();
    }

    public void run(){
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                Runtime runtime = Runtime.getRuntime();
                if ((runtime.maxMemory() - (runtime.totalMemory() - runtime.freeMemory())) / 1048576L <= 120){
                    if (Bukkit.getOnlinePlayers().size() == 0){
                        Bukkit.shutdown();
                    } else if (!CoreUtil.restart){
                        CoreUtil.restart = true;
                    }
                }
            } catch (Exception ignore) {
            }
        }
    }

}
