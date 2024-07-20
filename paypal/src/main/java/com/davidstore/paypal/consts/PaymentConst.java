package com.davidstore.paypal.consts;

public class PaymentConst {
    public static final String INTENT_SALE = "sale"; // Makes an immediate payment.
    public static final String INTENT_AUTHORIZE = "authorize"; // Authorizes a payment for capture later.
    public static final String INTENT_ORDER = "order"; // Create an order.

    public static final String TYPE_WEB = "WEB";
    public static final String TYPE_API = "API";
}
