package net.lastcraft.base.gamer.sections;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.skin.Skin;
import net.lastcraft.base.sql.GlobalLoader;

@Getter
public class SkinSection extends Section {

    private String skinName;
    @Setter
    private Skin skin = Skin.DEFAULT_SKIN;

    public SkinSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        skinName = GlobalLoader.getSkinName(baseGamer.getName(), baseGamer.getPlayerID());
        if (!skinName.isEmpty()) {
            skin = GlobalLoader.getSkin(skinName);
        }

        return true;
    }

    public void updateSkinName(String skinName) {
        if (skinName == null || skinName.length() < 3 || skinName.equalsIgnoreCase(this.skinName)) {
            return;
        }

        this.skinName = skinName;
        GlobalLoader.setSkinName(baseGamer.getPlayerID(), skinName);
    }
}
