package net.lastcraft.base.skin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.lastcraft.base.skin.exeptions.SkinRequestException;

@AllArgsConstructor
@ToString
public abstract class SkinsResponse {

    @Getter
    private final String id;
    @Getter
    private final String name;

    private final String message;
    private final String type;
    private final String error;
    private final int status;

    public void check() throws SkinRequestException {
        if (error != null) {
            throw new SkinRequestException("Произошла ошибка при получении данных скина " + "\nError: " + error + "");
        } else if (name != null && message != null && type != null && status != 200) {
            throw new SkinRequestException("Произошла ошибка при получении данных скина" + "\nMessage: " + message + "");
        } else if (id != null && !id.isEmpty()) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name");
            }
        } else {
            throw new IllegalArgumentException("id");
        }
    }
}
