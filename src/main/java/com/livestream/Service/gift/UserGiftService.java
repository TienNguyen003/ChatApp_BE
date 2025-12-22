package com.livestream.Service.gift;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.livestream.DTO.response.gift.UserGiftResponse;
import com.livestream.Mapper.gift.UserGiftMapper;
import com.livestream.Repository.gift.UserGiftRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserGiftService {
    UserGiftRepository userGiftRepository;
    UserGiftMapper userGiftMapper;

    public Page<UserGiftResponse> getUserGiftHistory(int userId, Pageable pageable) {
        return userGiftRepository.findByUserId(userId, pageable)
                .map(userGiftMapper::toUserGiftResponse);
    }

    public Page<UserGiftResponse> getChannelGiftHistory(int channelId, Pageable pageable) {
        return userGiftRepository.findByChannelId(channelId, pageable)
                .map(userGiftMapper::toUserGiftResponse);
    }
}
