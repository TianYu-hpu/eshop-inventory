package com.roncoo.eshop.inventory.request;

import com.roncoo.eshop.inventory.domain.ProductInventory;
import com.roncoo.eshop.inventory.service.ProductInventoryService;

/**
 * @Auther: tianyu
 * @Date: 2018/12/29 22:40
 * @Description:
 *
 * 重新加载商品库存缓存
 *
 */
public class ProductInventoryCacheRefreshRequest implements Request {

    private ProductInventoryService productInventoryService;
    /**
     * 商品id
     */
    private Integer productId;

    public ProductInventoryCacheRefreshRequest(Integer productId, ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {

        //查询库存
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        //更新redis缓存
        productInventoryService.updateProductInventoryCache(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }
}
