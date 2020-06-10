package com.cly.mineworld.service.vo.userItem;

/**
 * 我的道具返回
 */
public class UserItemVo {
    private String userStrId; //用户ID
    private String itemCategoryStrId; //道具类别StrId
    private String itemStrId;  //道具strId
    private String itemName;  //道具名称
    private String itemUseCount; //剩余使用次数
    private Double itemPrice; //道具名称

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getUserStrId() {
        return userStrId;
    }

    public void setUserStrId(String userStrId) {
        this.userStrId = userStrId;
    }

    public String getItemCategoryStrId() {
        return itemCategoryStrId;
    }

    public void setItemCategoryStrId(String itemCategoryStrId) {
        this.itemCategoryStrId = itemCategoryStrId;
    }

    public String getItemStrId() {
        return itemStrId;
    }

    public void setItemStrId(String itemStrId) {
        this.itemStrId = itemStrId;
    }

    public String getItemUseCount() {
        return itemUseCount;
    }

    public void setItemUseCount(String itemUseCount) {
        this.itemUseCount = itemUseCount;
    }
}
