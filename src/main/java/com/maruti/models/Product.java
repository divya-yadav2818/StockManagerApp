package com.maruti.models;

import java.sql.Timestamp;

public class Product {
    private int productId, userId, qty, minQty;
    private String name, unit, type; // added type
    private Timestamp lastUpdated;

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
    public int getMinQty() { return minQty; }
    public void setMinQty(int minQty) { this.minQty = minQty; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public Timestamp getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Timestamp lastUpdated) { this.lastUpdated = lastUpdated; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
