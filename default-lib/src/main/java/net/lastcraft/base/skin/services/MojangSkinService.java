package net.lastcraft.base.skin.services;

import net.lastcraft.base.skin.Skin;
import net.lastcraft.base.skin.SkinType;
import net.lastcraft.base.skin.exeptions.SkinRequestException;
import net.lastcraft.base.skin.response.SkinProperty;
import net.lastcraft.base.skin.response.SkinResponse;
import net.lastcraft.base.skin.response.UUIDResponse;
import net.lastcraft.base.sql.GlobalLoader;

import java.io.IOException;
import java.net.URLEncoder;

public final class MojangSkinService extends SkinService {

    public MojangSkinService(String uuidUrl, String skinUrl) {
        super(uuidUrl, skinUrl);
    }

    @Override
    public SkinType getSkinType() {
        return SkinType.MOJANG;
    }

    @Override
    public Skin getSkinByName(String name) throws SkinRequestException {
        try {
            return getSkinByUUID(getUUID(name));
        } catch (Exception e) {
            return GlobalLoader.getSkin(name);
        }
    }

    private String getUUID(String name) throws SkinRequestException {
        UUIDResponse response;
        try {
            response = readResponse(uuidUrl + URLEncoder.encode(name, "UTF-8"), UUIDResponse.class);
            if (!response.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("name");
            }
        } catch (SkinRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new SkinRequestException(name + " не имеет лицензии", e);
        }

        return response.getId();
    }

    public Skin getSkinByUUID(String name) throws SkinRequestException {
        try {
            SkinResponse skinResponse = readResponse(skinUrl + URLEncoder.encode(name, "UTF-8")
                    + "?unsigned=false", SkinResponse.class);
            SkinProperty property = skinResponse.getProperties();
            return property.toSkin(skinResponse.getName(), getSkinType());
        } catch (IOException e) {
            throw new SkinRequestException("Произошла ошибка при загрузке скина");
        }
    }
}
