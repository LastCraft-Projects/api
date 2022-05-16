package net.lastcraft.api.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import net.lastcraft.base.skin.Skin;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

@Getter
public enum Head {
    JOIN_MESSAGE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWFmNzgxZjNlMTI4YmE3NzFkY2ZkZDExNjgyNzU1YjQ5MGM2MWYxNzNiY2YzN2E5YzZiOGE0Nzg3ZTRmOSJ9fX0="),
    MONKEY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ViZmRlNjZhN2JiOTU3ZDRlYWFlMjk4ZmNiYWQ1Y2ExNzFhNTNhYTIxNTNmZDA5ZjI5N2M0MTc0OGNiNjI4In19fQ=="),
    PARTY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDAyYzEwYWRjMzFiMWMyMWNjOThlY2Y4MDkyMjVmODdlMjVlNzIzNzhjZjQxN2RiNGJlM2Y2N2U5MWJiMSJ9fX0="),
    UKRAINE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjhiOWY1MmUzNmFhNWM3Y2FhYTFlN2YyNmVhOTdlMjhmNjM1ZThlYWM5YWVmNzRjZWM5N2Y0NjVmNWE2YjUxIn19fQ=="),
    PREFIX("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDI5NWE5MjkyMzZjMTc3OWVhYjhmNTcyNTdhODYwNzE0OThhNDg3MDE5Njk0MWY0YmZlMTk1MWU4YzZlZTIxYSJ9fX0="),
    NOT_FOUND("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZiNTQzNDY0OTMwZThkMzg2NzZkYjZiZTk5MjBkZDgyYTU0ODU5YzM0OTdkOWZlMzFlZjkyYTg4NGM4NCJ9fX0="),
    LOBBY_ANOTHER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NjI5NDAxMWNiZGNkMmU5Mjk0MWRhZmU2YjM3MjZkZmYwMmMzZTFmODRkZmE1N2M2YWJhYjZmYzMzY2U2In19fQ=="),
    LOBBY_ON("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19"),
    LOBBY_FRIEND("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4NTZjN2IzNzhkMzUwMjYyMTQzODQzZDFmOWZiYjIxOTExYTcxOTgzYmE3YjM5YTRkNGJhNWI2NmJlZGM2In19fQ=="),
    FASTSTART("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQzZDRiN2FjMjRhMWQ2NTBkZGY3M2JkMTQwZjQ5ZmMxMmQyNzM2ZmMxNGE4ZGMyNWMwZjNmMjlkODVmOGYifX19"),
    CREATIVE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxMGQzZmQ0Mjk3NzhmM2U5NzIxZGY4NjVlNTY2ZTU0NjE3ZDU5MDZhODkzY2EwZTdhZmQ3NzE3MWZkOTAifX19"),
    MAINLOBBY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmMyYmUzYmM2MDc3NzMyNjRjZWVlMGVkZDYxNTMxNjM0MWVjMmI0NjVhMTZjZGI1NzhiZGFlZGQ2NDNkIn19fQ=="),
    SHULKER_DONATE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg3OTQzNjM4NWJkYjBlZmQxM2IxZDJjMDk0YTMxMWVhODFlM2I4MzUyMGY5MTkyZDg5ZTY0NDJiNmZkZjIifX19"),
    GOLD_DONATE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2JiNjEyZWI0OTVlZGUyYzVjYTUxNzhkMmQxZWNmMWNhNWEyNTVkMjVkZmMzYzI1NGJjNDdmNjg0ODc5MWQ4In19fQ=="),
    DIAMOND_DONATE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBiOGViMzMzNjIyYWU3ZGU5YjUzYjM2MDJmNDFmNjNkYjljMjUyOGI1YmUyMzFhYzk2NTE2NjExZmIxYSJ9fX0="),
    EMERALD_DONATE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2NkYTA0NzYyMzBlNjRhOTE3ZWMxY2YyNmRmOWI2OTZlMjcxNmVmZTk2YWUzNGI5NjY5YTZlYzUyNWJhYWEifX19"),
    MAGMA_DONATE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZiMTk0MDU5YTBlYmNmMjI3Zjg4ZjIyMjNkZTFlYWY1Mzk3Yzg4MzdlODRlNGIzOWU4YzdmYmUxMjhmMzIifX19"),
    LUCKYWARS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUyM2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ=="),
    ENGLISH(Head.getHeadByTextures("7d15d566202ac0e76cd897759df5d01c11f991bd46c5c9a04357ea89ee75")),
    RUSSIA("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZlYWZlZjk4MGQ2MTE3ZGFiZTg5ODJhYzRiNDUwOTg4N2UyYzQ2MjFmNmE4ZmU1YzliNzM1YTgzZDc3NWFkIn19fQ=="),
    WORLD("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjFkZDRmZTRhNDI5YWJkNjY1ZGZkYjNlMjEzMjFkNmVmYTZhNmI1ZTdiOTU2ZGI5YzVkNTljOWVmYWIyNSJ9fX0="),
    GADGETS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNlZWI3N2Q0ZDI1NzI0YTljYWYyYzdjZGYyZDg4Mzk5YjE0MTdjNmI5ZmY1MjEzNjU5YjY1M2JlNDM3NmUzIn19fQ=="),
    BACK("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM3NjQ4YWU3YTU2NGE1Mjg3NzkyYjA1ZmFjNzljNmI2YmQ0N2Y2MTZhNTU5Y2U4YjU0M2U2OTQ3MjM1YmNlIn19fQ=="),
    COMPUTER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTA5Y2RlMWFmYzk1YTQ3NGQyMjI1NTQwOTdlZDZkMzkxZTdjYzdhZTFmMjAyZmRiZmQyZDZkYmM5ODMwOTM3MCJ9fX0="),
    GAME("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzhlZjA2YTRjOTUzMjM2Yzg3NzVhMjAxY2EwMmZlOWRmNmNiYmE4N2Q3NmIyYThiNmExZDBmZGZlMzE4YmRlIn19fQ=="),
    MAILBOX("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFjYmJjYTU2NzM3MmE5YjJiMzZjOGY2ODE1NDg1MWJkYTVlZTFkNTNlMmJjMjA4YTExNTJkOWExOGQyY2IifX19"),
    CHEST("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmY2OGQ1MDliNWQxNjY5Yjk3MWRkMWQ0ZGYyZTQ3ZTE5YmNiMWIzM2JmMWE3ZmYxZGRhMjliZmM2ZjllYmYifX19"),
    TURTLE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJlNTQ4NDA4YWI3NWQ3ZGY4ZTZkNWQyNDQ2ZDkwYjZlYzYyYWE0ZjdmZWI3OTMwZDFlZTcxZWVmZGRmNjE4OSJ9fX0="),
    GUARDIAN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzI1YWY5NjZhMzI2ZjlkOTg0NjZhN2JmODU4MmNhNGRhNjQ1M2RlMjcxYjNiYzllNTlmNTdhOTliNjM1MTFjNiJ9fX0="),
    EMOJI("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2JhYWJlNzI0ZWFlNTljNWQxM2Y0NDJjN2RjNWQyYjFjNmI3MGMyZjgzMzY0YTQ4OGNlNTk3M2FlODBiNGMzIn19fQ=="),
    BOTTLE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRhYTlhZWFiYWYyNTY4Yjk3YTJlOGNmYTlhNTNiYWNkNGM4ZDg5ZGFkNGJhMzg3ZjZjNGI3NjFhZTA0YTE4In19fQ=="),
    VAMPIRE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ0NDc1NmUwYjRlY2U4ZDc0NjI5NmEzZDVlMjk3ZTE0MTVmNGJhMTc2NDdmZmUyMjgzODUzODNkMTYxYTkifX19"),
    LEFT("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWY3YjdhNjk0OWVlNjk0NmVhNjFiZDY5OTg3YjhhYTM5YTUwYTFkYzVjY2FjZTg1YmVlYzU4NmUzMmM1NWVjMyJ9fX0="),
    RIGHT("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjIxYmIxNTNhYTgyN2FiYjM3ODgyYzFmOThmOWVlN2RiYmE4Zjc1YWIxOWMzM2RlZjE2NDZmOWY1OGVmYmY2MyJ9fX0="),
    NOTEBLOCK("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNlZWI3N2Q0ZDI1NzI0YTljYWYyYzdjZGYyZDg4Mzk5YjE0MTdjNmI5ZmY1MjEzNjU5YjY1M2JlNDM3NmUzIn19fQ=="),
    SKELETON("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWNkNzEzYzVmNWU0NmRhNDM2YThmNTRiNTIzZDQzYWYyOWY3YWU4ZmIxODQ3OTJjY2E3M2IxNzE3ZmVhYTYxIn19fQ=="),
    JAKE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNkMTg3N2JlOTVhOWVkYjg2ZGYyMjU2ZjIzOTU4MzI0YzJlYzE5ZWY5NDI3N2NlMmZiNWMzMzAxODQxZGMifX19"),
    COIN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDViYmFhMmIyN2UwZTJkOGJlYjc4ZDRlNmNlYTNhNmM5MjdhMmMxNDMyNTlhOWMzY2M4N2JlZGRmNzhlNDY2In19fQ=="),
    LUCKY("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUyM2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ=="),
    BATMAN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjVmYThkZTk1ZjU3ZjNhNjk1MTEzNzYxZTQzMTdkZmQ4YWJlMTIxYWE4NWIwMjNkYjIxNGVkNGZhMmY4YyJ9fX0="),
    BOOKS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjMzNTk4NDM3ZTMxMzMyOWViMTQxYTEzZTkyZDliMDM0OWFhYmU1YzY0ODJhNWRkZTdiNzM3NTM2MzRhYmEifX19"),
    PERKS("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTRmMDNhNzQ2YWFiZGMzYWJjNTE1OGVhYTEwNDY2YTlmYTc3MGMyYmIxZDRkYWI3NjQ0ODQzOThlOWY4ZDQifX19"),
    SHOP("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM2ZTk0ZjZjMzRhMzU0NjVmY2U0YTkwZjJlMjU5NzYzODllYjk3MDlhMTIyNzM1NzRmZjcwZmQ0ZGFhNjg1MiJ9fX0="),
    BELARUSIAN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzdlNDc2YWUzNTJmNDhlMTZmMThkNzU2ZWIyNTk1N2ViYWVmNDI4ZTZjNGQxNDY2YWU1OTFiMzg0OThlIn19fQ"),
    AZERBAIJANI("eyJ0aW1lc3RhbXAiOjE1MTgwOTQzMDI2NTEsInByb2ZpbGVJZCI6IjdkYTJhYjNhOTNjYTQ4ZWU4MzA0OGFmYzNiODBlNjhlIiwicHJvZmlsZU5hbWUiOiJHb2xkYXBmZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzdhYmY1MzcxOWZkZTBkODE5YTM3MmUyMTg2N2Q3OTY4NGY2Yjg5MmQxOTk5ZWJlODg0MDM2MTYxZDZkYjcifX19"),
    LETTER_A(Head.getHeadByTextures("9c60da2944a177dd08268fbec04e40812d1d929650be66529b1ee5e1e7eca")),
    LETTER_B(Head.getHeadByTextures("8041f5e86983d36eaec4e167b2bbb5a3727607cde88f7555ca1b522a039bb")),
    LETTER_C(Head.getHeadByTextures("d945996c8ae91e376196d4dc676fec31feac790a2f195b2981a703ca1d16cb6")),
    LETTER_D(Head.getHeadByTextures("1641150f481e8492f7128c948996254d2d91fc90f5a8ff4d8ac5c39a6a88a")),
    LETTER_E(Head.getHeadByTextures("db251487ff8eef2ebc7a57dab6e3d9f1db7fc926ddc66fea14afe3dff15a45")),
    LETTER_F(Head.getHeadByTextures("7e433656b443668ed03dac8c442722a2a41221be8bb48e23b35bd8c2e59f63")),
    LETTER_G(Head.getHeadByTextures("995863b73637605feacbb173b77d5e155e65204c78d5c7911f738f28deb60")),
    LETTER_H(Head.getHeadByTextures("3c1d358d927074289cc26bff5b1240746f9f4f0cc46f942f5981c6595f72dd")),
    LETTER_I(Head.getHeadByTextures("8f2295865bda4e47979d36b8a887a75a13b034e6988f78670b64a1e6442c")),
    LETTER_J(Head.getHeadByTextures("e34462b55d7f5823680ad13f2adbd7d1ed46ba5101017ed4b37aeeeb775d")),
    LETTER_K(Head.getHeadByTextures("773325a935c067b6ef227367f62ca4bf49f67adb9f6da32091e2d32c5dde328")),
    LETTER_L(Head.getHeadByTextures("25a1e3328c571aa495d9c5f494815cca176c3acb184feb5a7b9c96ce8e52fce")),
    LETTER_M(Head.getHeadByTextures("d467bf6be95e5c8e9d01977a2f0c487ed5b0de5c87963a2eb15411c442fb2b")),
    LETTER_N(Head.getHeadByTextures("823e434d6395fe7e63492431bdee5782bd5ee5bc8cab7559467bdd1f93b925a")),
    LETTER_O(Head.getHeadByTextures("88445466bdc5ad5bcea82239c4e1b510f6ea5262d82d8a96d7291c342fb89")),
    LETTER_P(Head.getHeadByTextures("f9de601dee3ffeca4d54595f844201d0ed2091acec4548c696bb16a8a158f6")),
    LETTER_Q(Head.getHeadByTextures("66ca769bde25d4cc41e19e42adc35ab4c1557b76af232649acc9967ff198f13")),
    LETTER_R(Head.getHeadByTextures("67a188805162ca5dd4f4649c661d3f6d23c42662aef01645b1a97f78b3f13219")),
    LETTER_S(Head.getHeadByTextures("60d09dfd9f5de6243233e0e3325b6c3479335e7ccf13f2448d4e1f7fc4a0df")),
    LETTER_T(Head.getHeadByTextures("64c75619b91d241f678350ad9237c134c5e08d87d6860741ede306a4ef91")),
    LETTER_U(Head.getHeadByTextures("e9f6d2c6d5285f882ae55d1e91b8f9efdfc9b377208bf4c83f88dd156415e")),
    LETTER_V(Head.getHeadByTextures("dce27a153635f835237d85c6bf74f5b1f2e638c48fee8c83038d0558d41da7")),
    LETTER_W(Head.getHeadByTextures("aedcf4ffcb53b56d42baac9d0dfb118e343462327442dd9b29d49f50a7d38b")),
    LETTER_X(Head.getHeadByTextures("83618ff1217640bec5b525fa2a8e671c75d2a7d7cb2ddc31d79d9d895eab1")),
    LETTER_Y(Head.getHeadByTextures("d9c1d29a38bcf113b7e8c34e148a79f9fe41edf41aa8b1de873bb1d433b3861")),
    LETTER_Z(Head.getHeadByTextures("b9295734195d2c7fa389b98757e9686ce6437c16c58bdf2b4cd538389b5912")),
    SYMBOL_0(Head.getHeadByTextures("3769fc56dc8f1acaaa2f1c598b4b6269f16e58eb1171c20783a86c23454abe7")),
    SYMBOL_1(Head.getHeadByTextures("6148891f75c6502b7cc48ea3d1f58752da4fe389efe7324f2b4446aaf2a823")),
    SYMBOL_2(Head.getHeadByTextures("e8ed5fff389cf8151b2bebac8513bccc58ec10a4fe3819c35efb340b8f2")),
    SYMBOL_3(Head.getHeadByTextures("3bf03e919cd7ac69ac214baffad998a5bd4b1d11ede333f1ce92d6da417b5c4")),
    SYMBOL_4(Head.getHeadByTextures("c2d164a1a96318cd6471d9a64a48ce02341fb126c3452bdcd4be188f2f973ce")),
    SYMBOL_5(Head.getHeadByTextures("7c5418df281e5bb2e175cc804b9244cdc95914c8d28deded5769f65b7ce02d5")),
    SYMBOL_6(Head.getHeadByTextures("7326af92298364546955377491ad8ac99db40528fbe3cdb817f768a7c9e63b8")),
    SYMBOL_7(Head.getHeadByTextures("a6d97c32a04c5b9f57712873bbade868488acb9ef6d9b5118debd06abec9a2")),
    SYMBOL_8(Head.getHeadByTextures("942e2979d1497c31ee03e19b088684ab155957527411f31b279b2a8dbb96ee")),
    SYMBOL_9(Head.getHeadByTextures("ee5553d1a51dd719c99e8e86d1b9b3901b11889a839ccd7129c1bd128264e1a8")),
    SYMBOL_PLUS(Head.getHeadByTextures("f8c874651f7124d142b707afd33b6c336ce09cea38cdcb5596e76ac757bf")),
    SYMBOL_GRID(Head.getHeadByTextures("b23578a8d6e4b5f2a53ca70d4925716bc8e56839596cc17be726c50252ccd")),
    SYMBOL_QUESTION(Head.getHeadByTextures("8b85be7db31e879ab75216b55414e1f3856f72f29b62d1f6bb945a8251cf7fb")),
    SYMBOL_EXCLAMATION(Head.getHeadByTextures("7a492fff53c47b5ec388aaee56ada7f4c60b65576b4161d66f53b5e63017bd"))

    ;

    private final ItemStack head;

    Head(ItemStack itemStackTexture) {
        this.head = itemStackTexture;
    }

    Head(String value) {
        this.head = Head.getHeadByValue(value);
    }

    public ItemStack getHead() {
        return head.clone();
    }

    public static ItemStack getHeadBySkin(Skin skin) {
        return getHeadByValue(skin.getValue());
    }

    public static ItemStack getHeadByPlayerName(String playerName) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(playerName);
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

    public static ItemStack getHeadByTextures(String signature) {
        String texture = "http://textures.minecraft.net/texture/" + signature;
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        setProfile(skullMeta, profile);
        item.setItemMeta(skullMeta);

        return item;
    }

    public static ItemStack getHeadByValue(String value) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);

        if (value == null || value.isEmpty()) {
            return head;
        }

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", value));
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        setProfile(skullMeta, profile);
        head.setItemMeta(skullMeta);

        return head;
    }

    public static void setProfile(SkullMeta skullMeta, GameProfile gameProfile) {
        try {
            Field field = Class.forName("org.bukkit.craftbukkit.v1_12_R1.inventory.CraftMetaSkull")
                    .getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, gameProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setProfile(Skull skull, GameProfile gameProfile) {
        try {
            Field field = Class.forName("org.bukkit.craftbukkit.v1_12_R1.block.CraftSkull")
                    .getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skull, gameProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}