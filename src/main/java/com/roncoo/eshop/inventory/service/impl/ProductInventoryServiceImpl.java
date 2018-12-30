package com.roncoo.eshop.inventory.service.impl;

import com.roncoo.eshop.inventory.constants.Constants;
import com.roncoo.eshop.inventory.dao.RedisDao;
import com.roncoo.eshop.inventory.domain.ProductInventory;
import com.roncoo.eshop.inventory.mapper.ProductInventoryMapper;
import com.roncoo.eshop.inventory.service.ProductInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

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
     * @param productId
     * @return
     */
    @Override
    public ProductInventory findProductInventory(Integer productId) {
        return productInventoryMapper.selectByPrimaryKey(productId);
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

    @Override
    public ProductInventory getProductInventoryCache(Integer productId) {
        String key = Constants.PRODUCT_INVENTORY_CNT_KEY + productId;
        String inventoryCntCacheStr =  redisDao.get(key);
        if(StringUtils.isEmpty(inventoryCntCacheStr)) {
            try {
                Long inventoryCnt = Long.parseLong(inventoryCntCacheStr);
                return new ProductInventory(productId, inventoryCnt);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
