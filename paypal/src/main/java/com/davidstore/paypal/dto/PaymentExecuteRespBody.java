package com.davidstore.paypal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentExecuteRespBody {
    private String id;
    private String state;
    private String fullName;
    private String email;
}
