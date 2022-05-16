package net.lastcraft.base.sql.api;

public interface ResponseHandler<H, R> {

    R handleResponse(H handle) throws Exception;
}
