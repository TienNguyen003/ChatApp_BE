package com.livestream.Service.gift;

import com.livestream.DTO.request.gift.GiftRequest;
import com.livestream.DTO.request.gift.SendGiftRequest;
import com.livestream.DTO.response.gift.GiftResponse;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.gift.Gift;
import com.livestream.Entity.gift.UserGift;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.gift.GiftMapper;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.gift.GiftRepository;
import com.livestream.Repository.gift.UserGiftRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftService {
    GiftRepository giftRepository;
    UserGiftRepository userGiftRepository;
    ChannelRepository channelRepository;
    UserRepository userRepository;
    GiftMapper giftMapper;

    public GiftResponse createGift(GiftRequest request) {
        Gift gift = giftMapper.toGift(request);
        return giftMapper.toGiftResponse(giftRepository.save(gift));
    }

    public GiftResponse updateGift(int id, GiftRequest request) {
        Gift gift = giftRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.GIFT_NOT_EXISTED));

        giftMapper.updateGift(gift, request);
        return giftMapper.toGiftResponse(giftRepository.save(gift));
    }

    public List<GiftResponse> getAllGifts() {
        return giftRepository.findAll().stream()
                .map(giftMapper::toGiftResponse)
                .toList();
    }

    public Page<GiftResponse> getAllGifts(Pageable pageable) {
        return giftRepository.findAll(pageable)
                .map(giftMapper::toGiftResponse);
    }

    public void sendGift(SendGiftRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Channel channel = channelRepository.findById(request.getChannelId())
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        Gift gift = giftRepository.findById(request.getGiftId())
                .orElseThrow(() -> new AppException(ErrorCode.GIFT_NOT_EXISTED));

        UserGift userGift = UserGift.builder()
                .user(user)
                .channel(channel)
                .gift(gift)
                .quantity(request.getQuantity())
                .sentAt(LocalDateTime.now())
                .build();

        userGiftRepository.save(userGift);
    }

    public void deleteGift(int id) {
        if (!giftRepository.existsById(id)) {
            throw new AppException(ErrorCode.GIFT_NOT_EXISTED);
        }
        giftRepository.deleteById(id);
    }
}
