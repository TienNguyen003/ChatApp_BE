package com.livestream.Service.gift;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.response.gift.UserGiftResponse;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.gift.UserGiftMapper;
import com.livestream.Repository.gift.UserGiftRepository;
import com.livestream.Repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserGiftService {
    UserGiftRepository userGiftRepository;
    UserGiftMapper userGiftMapper;
    UserRepository userRepository;

    public Page<UserGiftResponse> getUserGiftHistory(int userId, Pageable pageable) {
        return userGiftRepository.findByUserId(userId, pageable)
                .map(userGiftMapper::toUserGiftResponse);
    }

    public Page<UserGiftResponse> getChannelGiftHistory(int channelId, Pageable pageable) {
        return userGiftRepository.findByChannelId(channelId, pageable)
                .map(userGiftMapper::toUserGiftResponse);
    }

    public Page<UserGiftResponse> getMyGiftHistory(Pageable pageable) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userGiftRepository.findByUserId(user.getId(), pageable)
                .map(userGiftMapper::toUserGiftResponse);
    }
}
