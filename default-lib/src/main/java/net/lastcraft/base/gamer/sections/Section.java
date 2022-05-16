package net.lastcraft.base.gamer.sections;

import lombok.Getter;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.IBaseGamerImpl;

public abstract class Section {

    @Getter
    protected final IBaseGamerImpl baseGamer;

    public Section(IBaseGamer baseGamer) {
        this.baseGamer = (IBaseGamerImpl) baseGamer;
    }

    public abstract boolean loadData();
}