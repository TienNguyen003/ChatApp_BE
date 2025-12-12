package com.livestream.Service.payment;

import com.livestream.DTO.request.payment.PaymentRequest;
import com.livestream.DTO.response.payment.PaymentResponse;
import com.livestream.DTO.response.payment.TransactionResponse;
import com.livestream.Entity.gift.Gift;
import com.livestream.Entity.payment.Transaction;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.payment.TransactionMapper;
import com.livestream.Repository.gift.GiftRepository;
import com.livestream.Repository.payment.TransactionRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentService {
    final TransactionRepository transactionRepository;
    final UserRepository userRepository;
    final GiftRepository giftRepository;
    final TransactionMapper transactionMapper;

    @Value("${payment.vnpay.url:https://sandbox.vnpayment.vn/paymentv2/vpcpay.html}")
    String vnpayUrl;

    @Value("${payment.return.url:http://localhost:1999/api/v1/payment/callback}")
    String defaultReturnUrl;

    public PaymentResponse createPayment(PaymentRequest request) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        double amount = calculateAmount(request);
        String transactionId = "TXN_" + UUID.randomUUID().toString().replace("-", "").substring(0, 20);

        // Create transaction record
        Transaction transaction = Transaction.builder()
                .user(user)
                .transactionType(request.getTransactionType())
                .amount(amount)
                .currency("VND")
                .paymentMethod(request.getPaymentMethod())
                .status("PENDING")
                .transactionId(transactionId)
                .description(buildDescription(request))
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        // Generate payment URL based on payment method
        String paymentUrl = generatePaymentUrl(request, transactionId, amount);

        return PaymentResponse.builder()
                .paymentUrl(paymentUrl)
                .transactionId(transactionId)
                .amount(amount)
                .currency("VND")
                .message("Chuyển hướng đến cổng thanh toán")
                .build();
    }

    public TransactionResponse handlePaymentCallback(String transactionId, String status) {
        Transaction transaction = transactionRepository.findAll().stream()
                .filter(t -> t.getTransactionId().equals(transactionId))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));

        transaction.setStatus(status);
        transaction.setCompletedAt(LocalDateTime.now());

        if ("COMPLETED".equals(status)) {
            processSuccessfulPayment(transaction);
        }

        return transactionMapper.toTransactionResponse(transactionRepository.save(transaction));
    }

    public Page<TransactionResponse> getMyTransactions(Pageable pageable) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return transactionRepository.findByUserId(user.getId(), pageable)
                .map(transactionMapper::toTransactionResponse);
    }

    public TransactionResponse getTransaction(int id) {
        return transactionMapper.toTransactionResponse(
                transactionRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)));
    }

    private double calculateAmount(PaymentRequest request) {
        if ("GIFT".equals(request.getTransactionType())) {
            Gift gift = giftRepository.findById(request.getItemId())
                    .orElseThrow(() -> new AppException(ErrorCode.GIFT_NOT_EXISTED));
            return gift.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())).doubleValue();
        } else if ("SUBSCRIPTION".equals(request.getTransactionType())) {
            // Default subscription price (should get from channel subscription tier)
            return 49000.0;
        }
        throw new AppException(ErrorCode.INVALID);
    }

    private String buildDescription(PaymentRequest request) {
        if ("GIFT".equals(request.getTransactionType())) {
            return "Mua quà tặng x" + request.getQuantity();
        } else if ("SUBSCRIPTION".equals(request.getTransactionType())) {
            return "Đăng ký kênh";
        }
        return "Giao dịch";
    }

    private String generatePaymentUrl(PaymentRequest request, String transactionId, double amount) {
        String returnUrl = request.getReturnUrl() != null ? request.getReturnUrl() : defaultReturnUrl;

        switch (request.getPaymentMethod()) {
            case "VNPAY":
                return String.format("%s?vnp_TxnRef=%s&vnp_Amount=%s&vnp_ReturnUrl=%s",
                        vnpayUrl, transactionId, (int) (amount * 100), returnUrl);
            case "STRIPE":
                return "https://checkout.stripe.com/pay/" + transactionId;
            case "PAYPAL":
                return "https://www.paypal.com/checkout/" + transactionId;
            default:
                throw new AppException(ErrorCode.INVALID);
        }
    }

    private void processSuccessfulPayment(Transaction transaction) {
        // Process gift purchase or subscription activation
        // This would integrate with UserGiftService or SubscriptionService
        // For now, just mark as completed
    }
}
