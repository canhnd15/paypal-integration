package com.davidstore.paypal.service;

import com.davidstore.paypal.controller.PaypalRestController;
import com.davidstore.paypal.consts.PaymentConst;
import com.davidstore.paypal.dto.*;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaypalService {
    private final Logger LOGGER = LoggerFactory.getLogger(PaypalRestController.class);

    private final APIContext apiContext;

    @Value("${store.url.web.success}")
    private String WEB_SUCCESS_URL;

    @Value("${store.url.web.cancel}")
    private String WEB_CANCEL_URL;

    @Value("${store.url.api.success}")
    private String API_SUCCESS_URL;

    @Value("${store.url.api.cancel}")
    private String API_CANCEL_URL;

    public ResponseEntity<PaymentResponse<?>> create(PaymentCreateReq req, String type)
            throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(req.getCurrency());
        amount.setTotal(String.format(Locale.forLanguageTag(req.getCurrency()), "%.2f", req.getTotal()));

        Transaction transaction = new Transaction();
        transaction.setDescription(req.getDescription());
        transaction.setAmount((amount));

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payment payment = this.getPayment(req, type, transactions);

        payment = payment.create(apiContext);
        LOGGER.info("State of created payment {} is {}", payment.getId(), payment.getState());

        PaymentCreateRespBody respBody = PaymentCreateRespBody.builder()
                .id(payment.getId())
                .state(payment.getState())
                .links(payment.getLinks())
                .payer(payment.getPayer())
                .build();

        return ResponseEntity.ok(PaymentResponse.builder()
                .code(PaymentRespCode.SUCCESS)
                .data(respBody)
                .build());
    }

private Payment getPayment(PaymentCreateReq req, String type, List<Transaction> transactions) {
    Payer payer = new Payer();
    payer.setPaymentMethod(req.getMethod());

    RedirectUrls redirectUrls = new RedirectUrls();
    if (PaymentConst.TYPE_WEB.equals(type)) {
        redirectUrls.setCancelUrl(WEB_CANCEL_URL);
        redirectUrls.setReturnUrl(WEB_SUCCESS_URL);
    } else {
        redirectUrls.setCancelUrl(API_CANCEL_URL);
        redirectUrls.setReturnUrl(API_SUCCESS_URL);
    }

    Payment payment = new Payment();
    payment.setIntent(PaymentConst.INTENT_SALE);
    payment.setPayer(payer);
    payment.setTransactions(transactions);
    payment.setRedirectUrls(redirectUrls);
    return payment;
}

public ResponseEntity<PaymentResponse<?>> execute(PaymentExecuteReq req) throws PayPalRESTException {
    Payment payment = new Payment();
    payment.setId(req.getPaymentId());

    PaymentExecution paymentExecution = new PaymentExecution();
    paymentExecution.setPayerId(req.getPayerId());

    payment = payment.execute(apiContext, paymentExecution);
    LOGGER.info("State of executed payment {} is {}", payment.getId(), payment.getState());

    PaymentExecuteRespBody respBody = PaymentExecuteRespBody.builder()
            .id(payment.getId())
            .state(payment.getState())
            .fullName(payment.getPayer().getPayerInfo().getFirstName().concat(" ").concat(payment.getPayer().getPayerInfo().getLastName()))
            .email(payment.getPayer().getPayerInfo().getEmail())
            .build();

    return ResponseEntity.ok(PaymentResponse.builder()
            .code(PaymentRespCode.SUCCESS)
            .data(respBody)
            .build());
}
}
