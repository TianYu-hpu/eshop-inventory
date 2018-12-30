package com.roncoo.eshop.inventory.vo;

import lombok.Data;

/**
 * @Auther: tianyu
 * @Date: 2018/12/30 19:54
 * @Description:
 */
@Data
public class Response {

    private String status;
    private String message;

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(String status) {
        this.status = status;
    }

    public Response() {
    }
}
