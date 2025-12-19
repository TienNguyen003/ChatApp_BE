package com.livestream.Controller;

import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.wallet.WalletResponse;
import com.livestream.Service.wallet.WalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}wallet")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletController {
    WalletService walletService;

    @GetMapping("/me")
    ApiResponse<WalletResponse> getMyWallet() {
        return ApiResponse.<WalletResponse>builder()
                .result(walletService.getMyWallet())
                .build();
    }
}
