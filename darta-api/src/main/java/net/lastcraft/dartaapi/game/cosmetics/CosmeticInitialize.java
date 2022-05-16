package net.lastcraft.dartaapi.game.cosmetics;

@Deprecated
public class CosmeticInitialize {


    /*//todo забрать отсюда остальное
    private void loadDeathEffects() {
        new DeathEffectSound(SOUND_API.getSound(SoundType.CREEPER_DEATH), 10000, ItemUtil.createItemStack(Material.TNT, "Смерть крипера"));
        new DeathEffectSound(SOUND_API.getSound(SoundType.BLAZE_DEATH), 10000, ItemUtil.createItemStack(Material.BLAZE_ROD, "Смерть ифрита"));
        new DeathEffectSound(SOUND_API.getSound(SoundType.ENDERMEN_DEATH), 10000, ItemUtil.createItemStack(Material.ENDER_PEARL, "Смерть эндермена"));
        new DeathEffectSound(SOUND_API.getSound(SoundType.GHAST_DEATH), 10000, ItemUtil.createItemStack(Material.GHAST_TEAR, "Смерть гаста"));
        new DeathEffectSound(SOUND_API.getSound(SoundType.IRONGOLEM_DEATH), 10000, ItemUtil.createItemStack(Material.IRON_INGOT, "Смерть голема"));
        new DeathEffectSound(SOUND_API.getSound(SoundType.SKELETON_DEATH), 10000, ItemUtil.createItemStack(Material.BONE, "Смерть скелета"));
        new DeathEffectSound(SOUND_API.getSound(SoundType.ZOMBIE_DEATH), 10000, ItemUtil.createItemStack(Material.ROTTEN_FLESH, "Смерть зомби"));
        new DeathEffectSound(SOUND_API.getSound(SoundType.DONKEY_DEATH), 10000, ItemUtil.createItemStack(Material.LEASH, "Смерть осла"));
        new DeathEffectSound(SOUND_API.getSound(SoundType.HORSE_ZOMBIE_DEATH), 10000, ItemUtil.createItemStack(Material.SADDLE, "Смерть лошади-зомби"));
        new DeathEffectSound(SOUND_API.getSound(SoundType.WITHER_DEATH), 10000, ItemUtil.createItemStack(Material.COAL, "Смерть визера"));
    }

    private void loadWinEffects() {
        new WinEffect(15000, ItemUtil.createItemStack(Material.GOLD_SWORD, "Золотой царь", Arrays.asList("§7Когда вы выигрываете игру,", "§7из вашей головы вылетает поток золота!")), new Consumer<Player>() {
            List<Material> items = Arrays.asList(
                    Material.GOLD_INGOT, Material.GOLD_BLOCK, Material.GOLD_BOOTS,
                    Material.GOLD_HELMET, Material.GOLD_HOE, Material.GOLD_CHESTPLATE,
                    Material.GOLDEN_APPLE, Material.GOLD_SWORD, Material.GOLD_ORE);
            Random random = new Random();
            @Override
            public void accept(Player player) {
                getRunable(items, random, player, 1).runTaskTimer(DartaAPI.getInstance(), 20L, 2L);
            }
        });
        new WinEffect(15000, ItemUtil.createItemStack(Material.DIAMOND, "Алмазный магнат", Arrays.asList("§7Когда вы выигрываете игру,", "§7из вашей головы вылетает поток алмазов!")), new Consumer<Player>() {
            List<Material> items = Arrays.asList(
                    Material.DIAMOND, Material.DIAMOND_BLOCK, Material.DIAMOND_BOOTS,
                    Material.DIAMOND_HELMET, Material.DIAMOND_HOE, Material.DIAMOND_CHESTPLATE,
                    Material.DIAMOND_SWORD, Material.DIAMOND_LEGGINGS, Material.DIAMOND_ORE);
            Random random = new Random();
            @Override
            public void accept(Player player) {
                getRunable(items, random, player, 1).runTaskTimer(DartaAPI.getInstance(), 20L, 2L);
            }
        });
        new WinEffect(15000, ItemUtil.createItemStack(Material.RED_ROSE, "Любовник", Arrays.asList("§7Когда вы выигрываете игру,", "§7из вашей головы вылетает поток роз и одуванчиков!")), new Consumer<Player>() {
            List<Material> items = Arrays.asList(Material.RED_ROSE, Material.YELLOW_FLOWER);
            Random random = new Random();
            @Override
            public void accept(Player player) {
                getRunable(items, random, player, 2).runTaskTimer(DartaAPI.getInstance(), 20L, 2L);
            }
        });
        new WinEffect(15000, ItemUtil.createItemStack(Material.SIGN, "Ублюдок", Arrays.asList("§7Когда вы выигрываете игру,", "§7ваши противники вас возненавидят...")), new Consumer<Player>() {
            List<String> text = Arrays.asList("§aИзи катка", "§aНе ощутил", "§aКак нубов", "§aGG EZ", "§aЕщё одну?)");
            Random random = new Random();
            @Override
            public void accept(Player player) {
                new BukkitRunnable() {

                    final double start = -1.5, end = 1.5;
                    int curText = 0, a = 1;
                    double curX = 0, curY = 5.0, curZ = 0;
                    Hologram holo = null;

                    @Override
                    public void run() {
                        if (!player.isOnline()) {
                            if (holo != null) {
                                holo.remove();
                            }
                            cancel();
                        }

                        curY += 0.05;
                        if (curY >= 2.0) {
                            curText = Integer.min(curText + 1, (curText + 1) % text.size());

                            curY = -0.5;
                            curX = start + (random.nextDouble() * (end - start));
                            curZ = start + (random.nextDouble() * (end - start));

                            if (holo == null) {
                                holo = LastCraft.getHologramAPI().createHologram(player.getLocation());
                                holo.setPublic(true);
                                holo.addTextLine(text.get(curText));
                            } else {
                                holo.removeLine(0);
                                holo.addTextLine(text.get(curText));
                            }

                        }
                        if (holo != null)
                            holo.onTeleport(player.getLocation().clone().add(curX, curY, curZ));

                        a++;
                        if (a > 20 * 20) {
                            holo.remove();
                            cancel();
                        }
                    }

                }.runTaskTimer(DartaAPI.getInstance(), 0L, 1L);

            }
        });
    }

    private BukkitRunnable getRunable(List<Material>items, Random random, Player player, int amountPerTick) {
        return new BukkitRunnable() {
            int a = 1;
            @Override
            public void run() {
                if (!player.isOnline())
                    cancel();

                for (int i = 0; i < amountPerTick; ++i) {
                    ItemStack toRain = new ItemStack(items.get(random.nextInt(items.size())));
                    Item item = player.getWorld().dropItem(player.getEyeLocation(), toRain);
                    item.setPickupDelay(Integer.MAX_VALUE);
                    double offsetX = random.nextDouble(), offsetY = random.nextDouble();
                    item.setVelocity(new Vector((offsetX - 0.5) / 5.0, 0.42135, (offsetY - 0.5) / 5.0));
                    BukkitUtil.runTaskLater(10L, item::remove);
                }

                a++;
                if (a > 10 * 20) cancel();
            }
        };
    }
    */

}
