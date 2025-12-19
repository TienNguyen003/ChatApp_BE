package com.livestream.Service.wallet;

import com.livestream.DTO.response.wallet.WalletResponse;
import com.livestream.Entity.payment.Transaction;
import com.livestream.Entity.user.Users;
import com.livestream.Entity.wallet.Wallet;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.wallet.WalletMapper;
import com.livestream.Repository.payment.TransactionRepository;
import com.livestream.Repository.user.UserRepository;
import com.livestream.Repository.wallet.WalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletService {
    WalletRepository walletRepository;
    UserRepository userRepository;
    TransactionRepository transactionRepository;
    WalletMapper walletMapper;

    public WalletResponse getMyWallet() {
        Users user = getCurrentUser();
        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseGet(() -> walletRepository.save(Wallet.builder()
                        .user(user)
                        .balance(BigDecimal.ZERO)
                        .lockedBalance(BigDecimal.ZERO)
                        .currency("Xu")
                        .updatedAt(LocalDateTime.now())
                        .build()));
        return walletMapper.toWalletResponse(wallet);
    }

    public void credit(Users user, BigDecimal amount, String type, String description) {
        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);

        transactionRepository.save(Transaction.builder()
                .user(user)
                .transactionType(type)
                .amount(amount.doubleValue())
                .currency(wallet.getCurrency())
                .paymentMethod("WALLET")
                .status("COMPLETED")
                .description(description)
                .createdAt(LocalDateTime.now())
                .completedAt(LocalDateTime.now())
                .build());
    }

    public void debit(Users user, BigDecimal amount, String type, String description) {
        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new AppException(ErrorCode.INSUFFICIENT_BALANCE);
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setUpdatedAt(LocalDateTime.now());
        walletRepository.save(wallet);

        transactionRepository.save(Transaction.builder()
                .user(user)
                .transactionType(type)
                .amount(amount.doubleValue())
                .currency(wallet.getCurrency())
                .paymentMethod("WALLET")
                .status("COMPLETED")
                .description(description)
                .createdAt(LocalDateTime.now())
                .completedAt(LocalDateTime.now())
                .build());
    }

    private Users getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
