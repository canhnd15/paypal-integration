package com.davidstore.paypal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PaymentExecuteReq {
    private String paymentId;
    private String payerId;
}
