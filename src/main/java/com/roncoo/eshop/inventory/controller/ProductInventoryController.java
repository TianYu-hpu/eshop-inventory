package com.roncoo.eshop.inventory.controller;

import com.roncoo.eshop.inventory.constants.Constants;
import com.roncoo.eshop.inventory.domain.ProductInventory;
import com.roncoo.eshop.inventory.request.ProductInventoryCacheRefreshRequest;
import com.roncoo.eshop.inventory.request.ProductInvetoryDBUpdateRequest;
import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.request.RequestQueue;
import com.roncoo.eshop.inventory.service.ProductInventoryService;
import com.roncoo.eshop.inventory.service.RequestAsyncProcessService;
import com.roncoo.eshop.inventory.vo.Response;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Auther: tianyu
 * @Date: 2018/12/30 19:52
 * @Description:  商品库存Controller
 */
@Controller
public class ProductInventoryController {

    @Resource
    private ProductInventoryService productInventoryService;
    @Resource
    private RequestAsyncProcessService requestAsyncProcessService;

    @RequestMapping("updateProductInventory")
    @ResponseBody
    public Response updateProductInventory(ProductInventory productInventory) {
        Response response = null;
        try {
            Request request = new ProductInvetoryDBUpdateRequest(productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
            response = new Response(Constants.SUCCESS);
        } catch(Exception e) {
            e.printStackTrace();
            response = new Response(Constants.FAILURE);
        }
        return response;
    }

    @RequestMapping("getProductInventory")
    @ResponseBody
    public ProductInventory getProductInventory(Integer productId) {
        ProductInventory productInventory1 = null;
        try {
            Request request = new ProductInventoryCacheRefreshRequest(productId, productInventoryService);

            requestAsyncProcessService.process(request);
            //将请求扔给service异步处理后，就需要while(true)一会儿,在这里hang住去尝试等待前面有商品库存更新的操作
            //同时缓存刷新的操作，将最新的数据刷新到缓存中
            long startTime = System.currentTimeMillis();
            long waitTime = 0L;
            while(true) {
                //尝试去redis中读取一次商品库存的缓存数据
                productInventory1 = productInventoryService.getProductInventoryCache(productId);
                if(!ObjectUtils.isEmpty(productInventory1)) {
                    return productInventory1;
                } else {
                    Thread.sleep(20);
                    long endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;

                }
                if(waitTime > Constants.REQUEST_MAX_WAIT_TIME) {
                    break;
                }
                productInventory1 = productInventoryService.findProductInventory(productId);
                if(!ObjectUtils.isEmpty(productInventory1)) {
                    //刷新缓存
                    productInventoryService.updateProductInventoryCache(productInventory1);
                    return productInventory1;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(productId, -1L);
    }

}
