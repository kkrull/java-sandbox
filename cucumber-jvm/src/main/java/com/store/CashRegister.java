package com.store;

import java.util.HashMap;
import java.util.Map;

public class CashRegister {
    private Map<String, Integer> itemPrices = new HashMap<>();
    private int totalInCents;

    public void setPrice(String itemName, int priceInCents) {
        itemPrices.put(itemName, priceInCents);
    }

    public void checkout(int numberOf, String itemName) {
        int itemPriceInCents = itemPrices.get(itemName);
        for(int i = 0; i < numberOf; i++)
            totalInCents += itemPriceInCents;
    }

    public int getTotalInCents() {
        return totalInCents;
    }
}
