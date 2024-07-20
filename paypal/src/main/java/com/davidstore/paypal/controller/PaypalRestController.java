package com.davidstore.paypal.controller;

import com.davidstore.paypal.consts.PaymentConst;
import com.davidstore.paypal.dto.PaymentCreateReq;
import com.davidstore.paypal.dto.PaymentExecuteReq;
import com.davidstore.paypal.dto.PaymentRespCode;
import com.davidstore.paypal.dto.PaymentResponse;
import com.davidstore.paypal.service.PaypalService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api/v1")
public class PaypalRestController {
    private final PaypalService paypalService;

    @PostMapping("/payment/create")
    public ResponseEntity<PaymentResponse<?>> createPayment(@RequestBody PaymentCreateReq createReq)
            throws PayPalRESTException {
        return paypalService.createPayment(createReq, PaymentConst.TYPE_API);
    }

    @PostMapping("/payment/execute")
    public ResponseEntity<PaymentResponse<?>> paymentSuccess(@RequestBody PaymentExecuteReq executionReq)
            throws PayPalRESTException {
        return paypalService.executePayment(executionReq);
    }

//    @GetMapping("/payment/cancel")
//    public ResponseEntity<PaymentResponse<?>> paymentCancel() {
//        return ResponseEntity.ok(PaymentResponse.builder()
//                .code(PaymentRespCode.SUCCESS)
//                .build());
//    }
//
//    @GetMapping("/payment/error")
//    public ResponseEntity<PaymentResponse<?>> paymentError() {
//        return ResponseEntity.ok(PaymentResponse.builder()
//                .code(PaymentRespCode.FAIL)
//                .build());
//    }
}
