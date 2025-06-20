package com.github.daymoon;

import static com.github.daymoon.Utils.TextColors.*;

import com.github.daymoon.Models.*;
import com.github.daymoon.Utils.AppSession;

public class Buy {
    
    //CART
    public void buyCart(){
        double totalPrice = 0;
        boolean canBuy = true;
        for(Cart c : carts.getCartProductsByUserId(AppSession.currentUserId)){
            Product p = products.getProductById(c.getProductId());
            totalPrice += p.getPrice() * c.getAmount();
            if(p.getStock() < c.getAmount()){
                System.out.println(YELLOW + "Insufficient stock. Only " + p.getStock() + " " + p.getName() + " is available" + RESET);
                canBuy = false;
            }
        }
        if(totalPrice > AppSession.currentUser.getMoney()){
            System.out.println(RED + "Failed to Purchase. Insufficient balance" + RESET);
            canBuy = false;
        }
        if(canBuy){
            for(Cart c : carts.getCartProductsByUserId(AppSession.currentUserId)){
                Product p = products.getProductById(c.getProductId());
                User seller = users.getUserById(p.getSellerId());
                double price = p.getPrice() * c.getAmount();
                Purchase pur = new Purchase(c.getAmount(), AppSession.currentUserId , p.getSellerId() , p.getId() );
                pur.AddToDataBase();
                purchaseList.add(pur);
                AppSession.currentUser.Pay(price);
                p.SellProduct(c.getAmount());
                seller.depositMoney(price);
                c.DeleteFromDataBase();
                cartList.remove(c);
            }
            System.out.println(GREEN +"Successful! Items in your cart have been purchased." + RESET);
            System.out.println(RED +"Total Price: " + totalPrice + RESET);
            System.out.printf(GREEN + "New Balance: %.2f" + RESET + "\n", AppSession.currentUser.getMoney());

            }
    }

    //PRODUCT
    public void buyProduct(Product p , int amount){
        double price = p.getPrice() * amount;
        if(AppSession.currentUser.getMoney() < price){
            System.out.println(RED + "Failed to Purchase. Insufficient balance" + RESET);
        }else if(p.getStock() < amount){
            System.out.println(YELLOW + "Insufficient stock. Only " + p.getStock() + " " + p.getName() + " is available" + RESET);
        }else{
            Purchase pur = new Purchase(amount, AppSession.currentUserId, p.getSellerId(), p.getId());
            pur.AddToDataBase();
            purchaseList.add(pur);
            p.SellProduct(amount);
            AppSession.currentUser.Pay(price);
            users.getUserById(p.getSellerId()).depositMoney(price);
            System.out.println(GREEN + "Purchase successful!" + RESET);
            System.out.println(RED +"Total Price: " + price + RESET);
            System.out.printf(GREEN + "New Balance: %.2f" + RESET + "\n", AppSession.currentUser.getMoney());

        }
    }
}
