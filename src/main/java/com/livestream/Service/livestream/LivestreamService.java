package com.livestream.Service.livestream;

import com.livestream.DTO.request.livestream.LivestreamCreationRequest;
import com.livestream.DTO.request.livestream.LivestreamUpdateRequest;
import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.Entity.category.Category;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.livestream.Livestream;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.livestream.LivestreamMapper;
import com.livestream.Repository.category.CategoryRepository;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.livestream.LivestreamRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LivestreamService {
    LivestreamRepository livestreamRepository;
    ChannelRepository channelRepository;
    CategoryRepository categoryRepository;
    LivestreamMapper livestreamMapper;

    public LivestreamResponse createLivestream(LivestreamCreationRequest request) {
        Channel channel = channelRepository.findById(request.getChannelId())
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        Livestream livestream = livestreamMapper.toLivestream(request);
        livestream.setChannel(channel);
        livestream.setCategory(category);
        livestream.setStatus("LIVE");
        livestream.setViewersCount(0);
        livestream.setStartedAt(LocalDateTime.now());

        return livestreamMapper.toLivestreamResponse(livestreamRepository.save(livestream));
    }

    public LivestreamResponse updateLivestream(int id, LivestreamUpdateRequest request) {
        Livestream livestream = livestreamRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED));

        livestreamMapper.updateLivestream(livestream, request);

        if ("ENDED".equals(request.getStatus()) && livestream.getEndedAt() == null) {
            livestream.setEndedAt(LocalDateTime.now());
        }

        return livestreamMapper.toLivestreamResponse(livestreamRepository.save(livestream));
    }

    public LivestreamResponse getLivestream(int id) {
        return livestreamMapper.toLivestreamResponse(
                livestreamRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED)));
    }

    public Page<LivestreamResponse> getAllLivestreams(Pageable pageable) {
        return livestreamRepository.findAll(pageable)
                .map(livestreamMapper::toLivestreamResponse);
    }

    public Page<LivestreamResponse> getLivestreamsByChannel(int channelId, Pageable pageable) {
        return livestreamRepository.findByChannelId(channelId, pageable)
                .map(livestreamMapper::toLivestreamResponse);
    }

    public Page<LivestreamResponse> getLivestreamsByStatus(String status, Pageable pageable) {
        return livestreamRepository.findByStatus(status, pageable)
                .map(livestreamMapper::toLivestreamResponse);
    }

    public Page<LivestreamResponse> getLivestreamsByCategory(int categoryId, Pageable pageable) {
        return livestreamRepository.findByCategoryId(categoryId, pageable)
                .map(livestreamMapper::toLivestreamResponse);
    }

    public void deleteLivestream(int id) {
        if (!livestreamRepository.existsById(id)) {
            throw new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED);
        }
        livestreamRepository.deleteById(id);
    }
}
