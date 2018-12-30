package com.roncoo.eshop.inventory.controller;

import com.roncoo.eshop.inventory.constants.Constants;
import com.roncoo.eshop.inventory.domain.ProductInventory;
import com.roncoo.eshop.inventory.request.*;
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
        try {
            //尝试去redis中读取一次商品库存的缓存数据
            ProductInventory productInventory = productInventoryService.getProductInventoryCache(productId);
            if(!ObjectUtils.isEmpty(productInventory)) {
                return productInventory;
            }
            productInventory = productInventoryService.findProductInventory(productId);
            if(!ObjectUtils.isEmpty(productInventory)) {
                //刷新缓存
                Request request = new ProductInventoryCacheRefreshRequest(productInventory, productInventoryService);
                requestAsyncProcessService.process(request);
                /**
                 * 这个过程实际上是一个读操作的过程，但是没有放到队列中穿行去处理，还是有数据不一致的问题
                 * 代码走到这里会有三种情况
                 * 1.上一次也是读请求，数据刷入了redis,但是redis算法给清掉了，标志位还是false,所以此时下一个请求是从缓存中拿不到数据的，再放一个读Request进队列，让数据去刷新一下
                 * 2.可能在200ms内,就是读请求在队列中一致积压着，没有等待到它执行，在实际生产环境中，基本是比较坑了，需要扩容机器了
                 * 3. 数据库中本身就没有，缓存穿透
                 */
                return productInventory;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(productId, -1L);
    }

}
