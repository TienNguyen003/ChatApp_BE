package com.livestream.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.request.payment.PaymentRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.payment.PaymentResponse;
import com.livestream.DTO.response.payment.TransactionResponse;
import com.livestream.Service.payment.PaymentService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @PostMapping("/create")
    ApiResponse<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createPayment(request))
                .build();
    }

    @GetMapping("/callback")
    ApiResponse<TransactionResponse> handlePaymentCallback(
            @RequestParam String transactionId,
            @RequestParam String status) {
        return ApiResponse.<TransactionResponse>builder()
                .result(paymentService.handlePaymentCallback(transactionId, status))
                .build();
    }

    @GetMapping("/my-transactions")
    ApiResponse<List<TransactionResponse>> getMyTransactions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<TransactionResponse> pageData = paymentService.getMyTransactions(pageable);
        return ApiResponse.<List<TransactionResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/transaction/{id}")
    ApiResponse<TransactionResponse> getTransaction(@PathVariable int id) {
        return ApiResponse.<TransactionResponse>builder()
                .result(paymentService.getTransaction(id))
                .build();
    }
}
