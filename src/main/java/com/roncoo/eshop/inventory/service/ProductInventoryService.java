package com.roncoo.eshop.inventory.service;

import com.roncoo.eshop.inventory.domain.ProductInventory;

/**
 * @Auther: tianyu
 * @Date: 2018/12/29 23:07
 * @Description:
 */
public interface ProductInventoryService {

    void updateProductInventory(ProductInventory productInventory);

    void removeProductInventoryCache(ProductInventory productInventory);

    ProductInventory findProductInventory(ProductInventory productInventory);

    void updateProductInventoryCache(ProductInventory productInventory);
}
