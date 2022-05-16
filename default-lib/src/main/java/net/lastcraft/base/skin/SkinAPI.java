package net.lastcraft.base.skin;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.sections.SkinSection;
import net.lastcraft.base.skin.exeptions.SkinRequestException;
import net.lastcraft.base.skin.exeptions.TooManyRequestsSkinException;
import net.lastcraft.base.skin.services.ElySkinService;
import net.lastcraft.base.skin.services.MojangSkinService;
import net.lastcraft.base.skin.services.SkinService;
import net.lastcraft.base.sql.GlobalLoader;

@UtilityClass
@Getter
public class SkinAPI {

    private final SkinService ELY_API = new ElySkinService("https://account.ely.by/api/mojang/profiles/",
                                               "http://skinsystem.ely.by/textures/signed/");
    private final SkinService MOJANG_API = new MojangSkinService("https://api.mojang.com/users/profiles/minecraft/",
                                                   "https://sessionserver.mojang.com/session/minecraft/profile/");

    /**
     * ВНИМАНИЕ! Если вызывать часто, то mojang или Елу забанят!
     * получить скин по нику игрока или скина
     * @param skinName - ник игрока или скина
     * @return - скин
     */
    public Skin getSkin(String skinName) {
        try {
            return ELY_API.getSkinByName(skinName);
        } catch (SkinRequestException e) {
            if (e instanceof TooManyRequestsSkinException) {
                return GlobalLoader.getSkin(skinName);
            }
            try {
                return MOJANG_API.getSkinByName(skinName);
            } catch (SkinRequestException e1) {
                return Skin.DEFAULT_SKIN;
            }
        }
    }

    public Skin getSkin(IBaseGamer gamer, SkinType skinType) {
        String skinName = gamer.getName();
        SkinSection skinSection = gamer.getSection(SkinSection.class);
        if (skinSection != null) {
            skinName = skinSection.getSkinName();
        }

        if (skinType == SkinType.ELY) {
            try {
                ELY_API.getSkinByName(skinName);
            } catch (Exception e) {
                return GlobalLoader.getSkin(skinName);
            }
        }

        if (skinType == SkinType.MOJANG) {
            try {
                MOJANG_API.getSkinByName(skinName);
            } catch (Exception e) {
                return GlobalLoader.getSkin(skinName);
            }
        }

        return GlobalLoader.getSkin(skinName);
    }

    public Skin getSkin(IBaseGamer gamer) {  //todo тут грузить скин в зависимости от того, какая настройка стоит у игрока(мол елу или не елу)
        SkinSection skinSection = gamer.getSection(SkinSection.class);
        if (skinSection != null) {
            return getSkin(skinSection.getSkinName());
        }

        return getSkin(gamer.getName());
    }
}
