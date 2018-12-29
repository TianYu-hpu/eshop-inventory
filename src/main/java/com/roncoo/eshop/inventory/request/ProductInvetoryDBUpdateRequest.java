package com.roncoo.eshop.inventory.request;

import com.roncoo.eshop.inventory.dao.impl.RedisDaoImpl;
import com.roncoo.eshop.inventory.domain.ProductInventory;
import com.roncoo.eshop.inventory.service.ProductInventoryService;

import javax.annotation.Resource;

/**
 * @Auther: tianyu
 * @Date: 2018/12/29 22:40
 * @Description:
 *
 * 比如说一个商品发生了交易，那么久要修改这个商品对应的库存
 * 此时就会发哦少年宫其你去过来，要求修改库存，那么这个额可能就时所谓的data update request ，数据更新请求
 *
 * cache aside pattern
 * 1.删除缓存
 * 2.更新数据库
 *
 */
public class ProductInvetoryDBUpdateRequest implements Request {

    private ProductInventoryService productInventoryService;
    /**
     * 商品id
     */
    private ProductInventory productInventory;

    public ProductInvetoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        //删除redis缓存
        productInventoryService.removeProductInventoryCache(productInventory);
        //更新库存
        productInventoryService.updateProductInventory(productInventory);
    }
}
