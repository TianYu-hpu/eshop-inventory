package com.roncoo.eshop.inventory.domain;

import lombok.Data;

/**
 * 商品库存
 * @Author: tianyu
 * @Date 2018-12-29
 */
@Data
public class ProductInventory {

    private Integer productId;
    private Long inventoryCnt;

    public ProductInventory() {
    }

    public ProductInventory(Integer productId, Long inventoryCnt) {
        this.productId = productId;
        this.inventoryCnt = inventoryCnt;
    }
}