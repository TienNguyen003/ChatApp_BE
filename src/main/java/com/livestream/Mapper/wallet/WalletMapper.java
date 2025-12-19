package com.livestream.Mapper.wallet;

import com.livestream.DTO.response.wallet.WalletResponse;
import com.livestream.Entity.wallet.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletResponse toWalletResponse(Wallet wallet);
}
