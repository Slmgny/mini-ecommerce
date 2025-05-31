package com.github.daymoon;

public class Favorites {
    
    private int productId;
    private int userId;


    public Favorites(int productId, int userId) {
        this.productId = productId;
        this.userId = userId;
    }


    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }


}
