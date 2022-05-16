package net.lastcraft.base.skin.services;

import net.lastcraft.base.skin.Skin;
import net.lastcraft.base.skin.SkinType;
import net.lastcraft.base.skin.exeptions.SkinRequestException;
import net.lastcraft.base.skin.exeptions.TooManyRequestsSkinException;
import net.lastcraft.base.skin.response.SkinProperty;
import net.lastcraft.base.skin.response.SkinResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

public final class ElySkinService extends SkinService {

    private final String serverKey = ""; //todo мб он нужен

    public ElySkinService(String uuidUrl, String skinUrl) {
        super(uuidUrl, skinUrl);
    }

    @Override
    protected HttpURLConnection makeConnection(String url) throws IOException, SkinRequestException {
        HttpURLConnection connection = super.makeConnection(url);
        switch (connection.getResponseCode()) {
            case 204:
                throw new SkinRequestException("Скин не загружен... кажется что-то пошло не так");
            case 429:
                throw new TooManyRequestsSkinException();
            default:
                return connection;
        }
    }

    @Override
    public SkinType getSkinType() {
        return SkinType.ELY;
    }

    @Override
    public Skin getSkinByName(String name) throws SkinRequestException {
        try {
            SkinResponse skinResponse = readResponse(skinUrl + URLEncoder.encode(name, "UTF-8")
                    + "?unsigned=false&token="
                    + URLEncoder.encode(serverKey, "UTF-8"), SkinResponse.class);
            SkinProperty property = skinResponse.getProperties();
            return property.toSkin(skinResponse.getName(), getSkinType());
        } catch (IOException ignored) {
            throw new SkinRequestException("Произошла ошибка при загрузке скина");
        }
    }
}
