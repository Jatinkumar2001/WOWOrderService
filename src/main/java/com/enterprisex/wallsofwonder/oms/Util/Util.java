package com.enterprisex.wallsofwonder.oms.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
public class Util {

    public static String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();
    }

//    private OrderEntity createRazorPayOrder(String amount) throws Exception {
//        JSONObject options = new JSONObject();
//        options.put("amount", amount);
//        options.put("currency", "INR");
//        options.put("receipt", "txn_123456");
//        return client.Orders.create(options);
//    }
}
