package net.lastcraft.base.skin.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.lastcraft.base.skin.Skin;
import net.lastcraft.base.skin.SkinType;
import net.lastcraft.base.skin.exeptions.SkinRequestException;
import net.lastcraft.base.skin.response.SkinProperty;
import net.lastcraft.base.skin.response.SkinSerializer;
import net.lastcraft.base.skin.response.SkinsResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class SkinService {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(SkinProperty.class, new SkinSerializer())
            .create();

    protected final String uuidUrl;
    protected final String skinUrl;

    protected SkinService(String uuidUrl, String skinUrl) {
        this.uuidUrl = uuidUrl;
        this.skinUrl = skinUrl;
    }

    protected <T extends SkinsResponse> T readResponse(String url, Class<T> responseClass) throws IOException, SkinRequestException {
        InputStream inputStream = makeConnection(url).getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int r;
        while((r = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, r);
        }

        String read = byteArrayOutputStream.toString("UTF-8");

        try {
            T response = GSON.fromJson(read, responseClass);
            if (response == null) {
                throw new NullPointerException("response");
            } else {
                response.check();
                return response;
            }
        } catch (RuntimeException e) {
            throw new IOException("Ошибка при парсинге... сосатб: " + read, e);
        }
    }

    protected HttpURLConnection makeConnection(String urlString) throws IOException, SkinRequestException {
        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "LastCraft");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);
            return connection;
        } catch (MalformedURLException e) {
            throw new IOException("Произошла ошибка при подключении: " + urlString, e);
        }
    }

    public abstract SkinType getSkinType();

    public abstract Skin getSkinByName(String name) throws SkinRequestException;
}
