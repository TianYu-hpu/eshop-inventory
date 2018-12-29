package com.roncoo.eshop.inventory.service.impl;

import com.roncoo.eshop.inventory.constants.Constants;
import com.roncoo.eshop.inventory.dao.RedisDao;
import com.roncoo.eshop.inventory.domain.ProductInventory;
import com.roncoo.eshop.inventory.mapper.ProductInventoryMapper;
import com.roncoo.eshop.inventory.service.ProductInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: tianyu
 * @Date: 2018/12/29 23:08
 * @Description:
 */
@Service
@Slf4j
public class ProductInventoryServiceImpl implements ProductInventoryService {

    @Resource
    private ProductInventoryMapper productInventoryMapper;
    @Resource
    private RedisDao redisDao;

    /**
     * 更新数据库商品库存
     * @param productInventory
     */
    @Override
    public void updateProductInventory(ProductInventory productInventory) {
        productInventoryMapper.updateByPrimaryKey(productInventory);
    }

    /**
     * 查找数据库商品库存
     * @param productInventory
     * @return
     */
    @Override
    public ProductInventory findProductInventory(ProductInventory productInventory) {
        return productInventoryMapper.selectByPrimaryKey(productInventory.getProductId());
    }

    /**
     * 删除redis商品库存缓存
     * @param productInventory
     */
    @Override
    public void removeProductInventoryCache(ProductInventory productInventory) {
        String key = Constants.PRODUCT_INVENTORY_CNT_KEY + productInventory.getProductId();
        redisDao.delete(key);
    }

    /**
     * 更新数据库商品库存
     * @param productInventory
     */
    @Override
    public void updateProductInventoryCache(ProductInventory productInventory) {
        String key = Constants.PRODUCT_INVENTORY_CNT_KEY + productInventory.getProductId();
        redisDao.set(key, String.valueOf(productInventory.getInventoryCnt()));
    }

}
