package com.davidstore.paypal.controller;

import com.davidstore.paypal.consts.PaymentConst;
import com.davidstore.paypal.dto.*;
import com.paypal.api.payments.Links;
import com.davidstore.paypal.service.PaypalService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class PaypalController {
    private final Logger LOGGER = LoggerFactory.getLogger(PaypalRestController.class);
    private final PaypalService paypalService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/web/payment/create")
    public RedirectView createPayment(
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description
    ) {
        try {
            PaymentCreateReq paymentRequest = PaymentCreateReq.builder()
                    .total(Double.valueOf(amount))
                    .currency(currency)
                    .method(method)
                    .description(description)
                    .build();

            ResponseEntity<PaymentResponse<?>> resp = paypalService.createPayment(paymentRequest, PaymentConst.TYPE_WEB);
            PaymentCreateRespBody body = (PaymentCreateRespBody) Objects.requireNonNull(resp.getBody()).getData();

            if(Objects.nonNull(body) && Objects.nonNull(body.getLinks())){
                for (Links link: body.getLinks()) {
                    if (link.getRel().equals("approval_url")) {
                        return new RedirectView(link.getHref());
                    }
                }
            }
        } catch (PayPalRESTException e) {
            LOGGER.error("Error occurred:: ", e);
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/web/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            PaymentExecuteReq executeReq = PaymentExecuteReq.builder()
                    .paymentId(paymentId)
                    .payerId(payerId)
                    .build();

            ResponseEntity<PaymentResponse<?>> resp = paypalService.executePayment(executeReq);
            PaymentCreateRespBody body = (PaymentCreateRespBody) Objects.requireNonNull(resp.getBody()).getData();

            if (Objects.nonNull(body.getState()) && PaymentRespCode.APPROVED.equals(body.getState())) {
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            LOGGER.error("Error occurred:: ", e);
        }
        return "paymentSuccess";
    }

    @GetMapping("/web/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/web/payment/error")
    public String paymentError() {
        return "paymentError";
    }
}
