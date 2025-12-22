package com.livestream.Service.badge;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.badge.AssignBadgeRequest;
import com.livestream.DTO.request.badge.BadgeRequest;
import com.livestream.DTO.response.badge.BadgeResponse;
import com.livestream.DTO.response.badge.UserBadgeResponse;
import com.livestream.Entity.badge.Badge;
import com.livestream.Entity.badge.UserBadge;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.badge.BadgeMapper;
import com.livestream.Mapper.badge.UserBadgeMapper;
import com.livestream.Repository.badge.BadgeRepository;
import com.livestream.Repository.badge.UserBadgeRepository;
import com.livestream.Repository.user.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BadgeService {
    BadgeRepository badgeRepository;
    UserBadgeRepository userBadgeRepository;
    UserRepository userRepository;
    BadgeMapper badgeMapper;
    UserBadgeMapper userBadgeMapper;

    public BadgeResponse createBadge(BadgeRequest request) {
        if (badgeRepository.findByName(request.getName()).isPresent()) {
            throw new AppException(ErrorCode.BADGE_EXISTED);
        }

        Badge badge = badgeMapper.toBadge(request);
        return badgeMapper.toBadgeResponse(badgeRepository.save(badge));
    }

    public BadgeResponse updateBadge(int id, BadgeRequest request) {
        Badge badge = badgeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BADGE_NOT_EXISTED));

        badgeMapper.updateBadge(badge, request);
        return badgeMapper.toBadgeResponse(badgeRepository.save(badge));
    }

    public BadgeResponse getBadge(int id) {
        return badgeMapper.toBadgeResponse(
                badgeRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.BADGE_NOT_EXISTED)));
    }

    public Page<BadgeResponse> getAllBadges(Pageable pageable) {
        return badgeRepository.findAll(pageable)
                .map(badgeMapper::toBadgeResponse);
    }

    public List<BadgeResponse> getAllBadges() {
        return badgeRepository.findAll().stream()
                .map(badgeMapper::toBadgeResponse)
                .toList();
    }

    public void deleteBadge(int id) {
        if (!badgeRepository.existsById(id)) {
            throw new AppException(ErrorCode.BADGE_NOT_EXISTED);
        }
        badgeRepository.deleteById(id);
    }

    public UserBadgeResponse assignBadgeToUser(AssignBadgeRequest request) {
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Badge badge = badgeRepository.findById(request.getBadgeId())
                .orElseThrow(() -> new AppException(ErrorCode.BADGE_NOT_EXISTED));

        UserBadge userBadge = UserBadge.builder()
                .user(user)
                .badge(badge)
                .assignedAt(LocalDateTime.now())
                .build();

        return userBadgeMapper.toUserBadgeResponse(userBadgeRepository.save(userBadge));
    }

    public List<UserBadgeResponse> getUserBadges(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        return userBadgeRepository.findByUserId(userId).stream()
                .map(userBadgeMapper::toUserBadgeResponse)
                .toList();
    }
}
