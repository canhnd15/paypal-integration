package com.davidstore.paypal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentCreateReq {
    private Double total;
    private String currency;
    private String method;
    private String description;
    private String successUrl;
    private String cancelUrl;
    private String intent;
}
