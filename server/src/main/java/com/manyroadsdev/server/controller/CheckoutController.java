package com.manyroadsdev.server.controller;

import com.manyroadsdev.server.logic.helper;
import com.manyroadsdev.server.model.*;
import com.manyroadsdev.server.repository.CartRepository;
import com.manyroadsdev.server.repository.CheckoutRepository;
import com.manyroadsdev.server.repository.ItemRepository;
import com.manyroadsdev.server.repository.ProductRepository;
import com.manyroadsdev.server.services.CartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class CheckoutController {

    // *** Constants ***

    // *** Declaration and initialisation of attributes ***
    @Autowired
    private CheckoutRepository checkoutRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartServices cartServices;

    // *** Routing ***
    /**
     * Initialise fresh CheckoutToken between cart and new order. Most of the data
     * from current cart will be copied in CheckoutToken
     *
     * @param cartID        int            : ID of current Cart
     * @return returnToken  CheckoutToken  : new/current CheckoutToken
     */
    @GetMapping("/generateToken/{cartID}")
    public CheckOutToken generateToken(@PathVariable int cartID) {

        System.out.println("Route generateToken");

        // *** Declaration and initialisation of attributes ***
        CheckOutToken token = new CheckOutToken();
        Live live = new Live();

        // Find current Cart
        Cart currentCart = cartServices.getCartByID(cartID);

        // Configure token
        token.setLineItems(new ArrayList<>(currentCart.getCartLineItems()));

        live.setCurrency(currentCart.getCurrency());
        live.setLineItems(new ArrayList<>(currentCart.getCartLineItems()));
        live.setMerchantID(11);
        live.setSubTotal(currentCart.getCartSubTotal());
        token.setLive(live);

        token.getMerchant().setBusinessName("Many Roads Developers");

        currentCart.getCartLineItems().forEach(item -> {

            // Copy over all products to token
            Product p = productRepository.findById(item.getProdID()).get();
            token.getProducts().add(p);
        });

    return checkoutRepository.save(token);
    }
}
