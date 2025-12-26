package com.livestream.Service.reward;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.reward.MissionCreateRequest;
import com.livestream.DTO.request.reward.MissionUpdateRequest;
import com.livestream.DTO.response.reward.MissionResponse;
import com.livestream.DTO.response.reward.UserRewardResponse;
import com.livestream.Entity.gift.Gift;
import com.livestream.Entity.reward.Mission;
import com.livestream.Entity.reward.UserMissionProgress;
import com.livestream.Entity.reward.UserReward;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.reward.MissionMapper;
import com.livestream.Mapper.reward.UserRewardMapper;
import com.livestream.Repository.gift.GiftRepository;
import com.livestream.Repository.reward.MissionRepository;
import com.livestream.Repository.reward.UserMissionProgressRepository;
import com.livestream.Repository.reward.UserRewardRepository;
import com.livestream.Repository.user.UserRepository;
import com.livestream.Service.wallet.WalletService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RewardService {
    MissionRepository missionRepository;
    UserMissionProgressRepository progressRepository;
    UserRewardRepository userRewardRepository;
    UserRepository userRepository;
    GiftRepository giftRepository;
    MissionMapper missionMapper;
    UserRewardMapper userRewardMapper;
    WalletService walletService;

    public Page<MissionResponse> listMissions(Pageable pageable) {
        return missionRepository.findAll(pageable).map(missionMapper::toMissionResponse);
    }

    public Page<MissionResponse> getMyMissions(Pageable pageable) {
        Users user = getCurrentUser();
        return progressRepository.findByUserId(user.getId(), pageable)
                .map(progress -> {
                    MissionResponse response = missionMapper.toMissionResponse(progress.getMission());
                    response.setStatus(progress.getStatus());
                    response.setProgressValue(progress.getProgressValue());
                    response.setCompletedAt(progress.getCompletedAt());
                    response.setClaimedAt(progress.getClaimedAt());
                    return response;
                });
    }

    public UserRewardResponse claimMission(String code) {
        Users user = getCurrentUser();
        Mission mission = missionRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.MISSION_NOT_FOUND));

        UserMissionProgress progress = progressRepository
                .findByUserIdAndMissionId(user.getId(), mission.getId())
                .orElse(UserMissionProgress.builder()
                        .user(user)
                        .mission(mission)
                        .progressValue(mission.getTargetValue())
                        .status("COMPLETED")
                        .lastUpdatedAt(LocalDateTime.now())
                        .completedAt(LocalDateTime.now())
                        .build());

        if ("CLAIMED".equals(progress.getStatus())) {
            throw new AppException(ErrorCode.MISSION_ALREADY_CLAIMED);
        }
        if (!"COMPLETED".equals(progress.getStatus())) {
            throw new AppException(ErrorCode.MISSION_NOT_COMPLETED);
        }

        progress.setStatus("CLAIMED");
        progress.setClaimedAt(LocalDateTime.now());
        progressRepository.save(progress);

        // Create user reward record
        UserReward reward = userRewardRepository.save(UserReward.builder()
                .user(user)
                .mission(mission)
                .coinAmount(mission.getRewardCoins() == null ? BigDecimal.ZERO : mission.getRewardCoins())
                .gift(mission.getRewardGift())
                .status("CLAIMED")
                .claimedAt(LocalDateTime.now())
                .build());

        // Credit wallet
        if (reward.getCoinAmount() != null && reward.getCoinAmount().compareTo(BigDecimal.ZERO) > 0) {
            walletService.credit(user, reward.getCoinAmount(), "REWARD", "Nhận thưởng nhiệm vụ: " + mission.getCode(),
                    "WALLET");
        }

        return userRewardMapper.toUserRewardResponse(reward);
    }

    public UserRewardResponse dailyCheckin() {
        return claimMission("DAILY_CHECKIN");
    }

    // Admin CRUD Operations
    public MissionResponse createMission(MissionCreateRequest request) {
        if (missionRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.MISSION_EXISTED);
        }

        Mission mission = Mission.builder()
                .code(request.getCode())
                .title(request.getTitle())
                .description(request.getDescription())
                .actionType(request.getActionType())
                .targetValue(request.getTargetValue())
                .rewardCoins(request.getRewardCoins())
                .startAt(request.getStartAt())
                .endAt(request.getEndAt())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        if (request.getRewardGiftId() != null) {
            Gift gift = giftRepository.findById(request.getRewardGiftId())
                    .orElseThrow(() -> new AppException(ErrorCode.GIFT_NOT_EXISTED));
            mission.setRewardGift(gift);
        }

        return missionMapper.toMissionResponse(missionRepository.save(mission));
    }

    public MissionResponse updateMission(Long id, MissionUpdateRequest request) {
        Mission mission = missionRepository.findById(id.intValue())
                .orElseThrow(() -> new AppException(ErrorCode.MISSION_NOT_FOUND));

        if (request.getTitle() != null) {
            mission.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            mission.setDescription(request.getDescription());
        }
        if (request.getActionType() != null) {
            mission.setActionType(request.getActionType());
        }
        if (request.getTargetValue() != null) {
            mission.setTargetValue(request.getTargetValue());
        }
        if (request.getRewardCoins() != null) {
            mission.setRewardCoins(request.getRewardCoins());
        }
        if (request.getStartAt() != null) {
            mission.setStartAt(request.getStartAt());
        }
        if (request.getEndAt() != null) {
            mission.setEndAt(request.getEndAt());
        }
        if (request.getActive() != null) {
            mission.setActive(request.getActive());
        }
        if (request.getRewardGiftId() != null) {
            Gift gift = giftRepository.findById(request.getRewardGiftId())
                    .orElseThrow(() -> new AppException(ErrorCode.GIFT_NOT_EXISTED));
            mission.setRewardGift(gift);
        }

        mission.setUpdatedAt(LocalDateTime.now());
        return missionMapper.toMissionResponse(missionRepository.save(mission));
    }

    public void deleteMission(Long id) {
        Mission mission = missionRepository.findById(id.intValue())
                .orElseThrow(() -> new AppException(ErrorCode.MISSION_NOT_FOUND));
        missionRepository.delete(mission);
    }

    private Users getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
