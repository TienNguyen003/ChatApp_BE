package com.livestream.Mapper.gift;

import com.livestream.DTO.request.gift.GiftRequest;
import com.livestream.DTO.response.gift.GiftResponse;
import com.livestream.Entity.gift.Gift;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GiftMapper {
    Gift toGift(GiftRequest request);

    GiftResponse toGiftResponse(Gift gift);

    void updateGift(@MappingTarget Gift gift, GiftRequest request);
}
