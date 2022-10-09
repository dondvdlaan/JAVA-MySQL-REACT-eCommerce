package com.manyroadsdev.server.services;

import com.manyroadsdev.server.model.Cart;

public interface CartServices {

    public abstract Cart getCartByID(int cartID);
    public abstract Cart adjustCartSubTotal(double changeValue, Cart currentCart);
}
