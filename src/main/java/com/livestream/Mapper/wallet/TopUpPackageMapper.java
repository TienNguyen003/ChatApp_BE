package com.livestream.Mapper.wallet;

import com.livestream.DTO.request.wallet.TopUpPackageRequest;
import com.livestream.DTO.response.wallet.TopUpPackageResponse;
import com.livestream.Entity.wallet.TopUpPackage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TopUpPackageMapper {
    TopUpPackage toTopUpPackage(TopUpPackageRequest request);

    @Mapping(target = "totalCoins", expression = "java(topUpPackage.getBaseCoins().add(topUpPackage.getBonusCoins()))")
    TopUpPackageResponse toTopUpPackageResponse(TopUpPackage topUpPackage);

    void updateTopUpPackage(@MappingTarget TopUpPackage topUpPackage, TopUpPackageRequest request);
}
