package com.davidstore.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PaymentResponse<T> {
    private String code;
    private T data;
}
