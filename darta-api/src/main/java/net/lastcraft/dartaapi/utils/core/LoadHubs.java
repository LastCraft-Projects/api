package net.lastcraft.dartaapi.utils.core;

import net.lastcraft.api.game.GameSettings;
import net.lastcraft.connector.Core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Deprecated
class LoadHubs {

    public LoadHubs() {
        startThread();
    }

    private void startThread() {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    GameSettings.hubs = loadServers(GameSettings.hubPrefix);
                    Thread.sleep(TimeUnit.MINUTES.toMillis(5));
                } catch (Exception ignore) {
                }
            }
        }).start();
    }

    private static Set<String> getServers(String prefixName) {
        if (Core.getDataConnection() != null) {
            return Core.getDataConnection().getServers(prefixName);
        } else {
            return new HashSet<>();
        }
    }

    private static Collection<String> loadServers(String prefix) {
        Set<String> servers = getServers(prefix);

        StringBuilder log = new StringBuilder("Загружено %" + prefix + "%: [");
        int count = 0;
        for (String lobbys : servers) {
            if (count == servers.size() - 1) {
                log.append(lobbys);
                break;
            }

            log.append(lobbys).append(", ");

            count++;
        }
        log.append("]");

        MessageUtil.sendConsole("§e" + log);

        return servers;
    }
    /*
    private static final FetchSubscription SUBSCRIPTION = BukkitConnector.getInstance().getServerCollector().subscribe(
                                                        GameSettings.hubPrefix + "-[0-9+]*",
                                                        (subscription, datas) ->
                                                            GameSettings.hubs = Arrays.stream(datas).map(ServerData::getName).collect(Collectors.toList()));

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    LoadHubs() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(SUBSCRIPTION::sendRequest, 5, 5, TimeUnit.SECONDS);
    }
     */

}
