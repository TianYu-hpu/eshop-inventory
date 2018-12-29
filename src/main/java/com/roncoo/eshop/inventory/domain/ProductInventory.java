package com.roncoo.eshop.inventory.domain;

/**
 * 商品库存
 * @Author: tianyu
 * @Date 2018-12-29
 */
public class ProductInventory {
    private Integer productId;

    private Long inventoryCnt;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getInventoryCnt() {
        return inventoryCnt;
    }

    public void setInventoryCnt(Long inventoryCnt) {
        this.inventoryCnt = inventoryCnt;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ProductInventory other = (ProductInventory) that;
        return (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getInventoryCnt() == null ? other.getInventoryCnt() == null : this.getInventoryCnt().equals(other.getInventoryCnt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getInventoryCnt() == null) ? 0 : getInventoryCnt().hashCode());
        return result;
    }
}