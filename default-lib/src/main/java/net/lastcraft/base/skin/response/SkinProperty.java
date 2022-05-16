package net.lastcraft.base.skin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.base.skin.Skin;
import net.lastcraft.base.skin.SkinType;

@AllArgsConstructor
@Getter
public class SkinProperty {

    private final String name;
    private final String value;
    private final String signature;

    public Skin toSkin(String skinName, SkinType skinType) {
        return new Skin(skinName, value, signature, skinType, System.currentTimeMillis());
    }
}
