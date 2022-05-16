package net.lastcraft.dartaapi.game.cosmetics;

@Deprecated
public class CosmeticShop { //todo удалить потом

    /*
    private static final InventoryAPI INVENTORY_API = LastCraft.getInventoryAPI();

    private static Map<String, CosmeticShop> cosmeticShop = new ConcurrentHashMap<>();
    private Player player;
    private DInventory inventory;
    private DeathEffectShop deathEffectShop;
    private WinEffectShop winShop;

    private CosmeticShop(Player player) {
        cosmeticShop.put(player.getName(), this);
        this.player = player;
        this.inventory = INVENTORY_API.createInventory(player, "Магазин эффектов", 5, new InventoryAction() {
            @Override
            public void onOpen(Player player) {
                update();
            }
        });
    }
    public void update() {

        int bought = 0;


        deathEffectShop = new DeathEffectShop(player);

        for (DeathEffectSound death: DeathEffectSound.getDeathEffects())
            if (death.have(player.getName()))
                bought++;
        DeathEffectSound choosedDeath = (DeathEffectSound) CosmeticData.getData(player.getName()).getChoosed(CosmeticDB.Cosmetic.DeathEffectSound.type);
        DItem death = new DItem(ItemUtil.createItemStack(Material.BONE, "§aПредсмертные хрипы", Arrays.asList(
                "§7Покупка и активация звуков",
                "§7при вашей смерти, которые",
                "§7действуют на аренах всех мини-игр.",
                "",
                "§7Открыто: §a" + bought + "/" + DeathEffectSound.getDeathEffects().size() +" §6" + StringUtil.onPercent(bought, DeathEffectSound.getDeathEffects().size()) + "%",
                "§7Выбран эффект: ",
                "§a" + ((choosedDeath == null) ? "Не выбрано" : choosedDeath.getIcon(player.getName()).getItemMeta().getDisplayName()),
                "",
                "§e▸ Открыть магазин"
        )),
                (player1, clickType, slot) -> deathEffectShop.openShop(player1));

        this.inventory.setItem(19, death);
        winShop = new WinEffectShop(player);

        bought = 0;
        for (WinEffect win : WinEffect.getWinEffects())
            if (win.have(player.getName()))
                bought++;
        WinEffect choosedWin = (WinEffect) CosmeticData.getData(player.getName()).getChoosed(CosmeticDB.Cosmetic.WinEffect.type);
        DItem win = new DItem(ItemUtil.createItemStack(Material.GOLDEN_APPLE, "§aЭффекты победы", Arrays.asList(
                "§7Покупка и активация эффектов",
                "§7победителя после игры, которые",
                "§7действуют на аренах всех мини-игр.",
                "",
                "§7Открыто: §a" + bought + "/" + WinEffect.getWinEffects().size() +" §6" + StringUtil.onPercent(bought, WinEffect.getWinEffects().size()) + "%",
                "§7Выбран эффект: ",
                "§a" + ((choosedWin == null) ? "Не выбрано" : choosedWin.getIcon(player.getName()).getItemMeta().getDisplayName()),
                "",
                "§e▸ Открыть магазин"
        )),
                ((player1, clickType, slot) -> winShop.openShop(player)));

        this.inventory.setItem(25, win);
    }

    public static void openShop(Player player) {
        if (cosmeticShop.get(player.getName()) == null) {
            new CosmeticShop(player);
        }
        CosmeticShop shop = cosmeticShop.get(player.getName());
        shop.inventory.openInventory(player);
    }

    private static ItemStack getBack(Language language) {
        return net.lastcraft.api.util.ItemUtil.getBuilder(Head.BACK)
                .setName(language.getMessage("PROFILE_BACK_ITEM_NAME"))
                .setLore(language.getList("PROFILE_BACK_ITEM_LORE"))
                .build();
    }

    public static void deletePlayer(Player player) {
        cosmeticShop.remove(player.getName());
    }

     */
}
