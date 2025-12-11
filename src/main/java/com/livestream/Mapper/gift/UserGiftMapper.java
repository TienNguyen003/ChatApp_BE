package com.livestream.Mapper.gift;

import com.livestream.DTO.response.gift.UserGiftResponse;
import com.livestream.Entity.gift.UserGift;
import com.livestream.Mapper.channel.ChannelMapper;
import com.livestream.Mapper.user.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class, ChannelMapper.class, GiftMapper.class })
public interface UserGiftMapper {
    UserGiftResponse toUserGiftResponse(UserGift userGift);
}
