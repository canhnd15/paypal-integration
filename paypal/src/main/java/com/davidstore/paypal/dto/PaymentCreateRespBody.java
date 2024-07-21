package com.davidstore.paypal.dto;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PaymentCreateRespBody {
    private String id;
    private String state;
    private List<Links> links;
    private Payer payer;
}
