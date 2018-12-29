package com.roncoo.eshop.inventory.mapper;

import com.roncoo.eshop.inventory.domain.ProductInventory;
import com.roncoo.eshop.inventory.domain.ProductInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductInventoryMapper {
    long countByExample(ProductInventoryExample example);

    int deleteByExample(ProductInventoryExample example);

    int deleteByPrimaryKey(Integer productId);

    int insert(ProductInventory record);

    int insertSelective(ProductInventory record);

    List<ProductInventory> selectByExample(ProductInventoryExample example);

    ProductInventory selectByPrimaryKey(Integer productId);

    int updateByExampleSelective(@Param("record") ProductInventory record, @Param("example") ProductInventoryExample example);

    int updateByExample(@Param("record") ProductInventory record, @Param("example") ProductInventoryExample example);

    int updateByPrimaryKeySelective(ProductInventory record);

    int updateByPrimaryKey(ProductInventory record);
}