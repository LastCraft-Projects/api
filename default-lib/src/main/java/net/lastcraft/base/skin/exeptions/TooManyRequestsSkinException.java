package net.lastcraft.base.skin.exeptions;

public class TooManyRequestsSkinException extends SkinRequestException {

    public TooManyRequestsSkinException() {
        super("Превышен лимит запросов");
    }
}
