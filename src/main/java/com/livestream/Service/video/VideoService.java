package com.livestream.Service.video;

import com.livestream.DTO.request.video.VideoCreationRequest;
import com.livestream.DTO.request.video.VideoUpdateRequest;
import com.livestream.DTO.response.video.VideoResponse;
import com.livestream.Entity.category.Category;
import com.livestream.Entity.channel.Channel;
import com.livestream.Entity.tag.Tag;
import com.livestream.Entity.video.Video;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.video.VideoMapper;
import com.livestream.Repository.category.CategoryRepository;
import com.livestream.Repository.channel.ChannelRepository;
import com.livestream.Repository.tag.TagRepository;
import com.livestream.Repository.video.VideoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VideoService {
    VideoRepository videoRepository;
    ChannelRepository channelRepository;
    CategoryRepository categoryRepository;
    TagRepository tagRepository;
    VideoMapper videoMapper;

    public VideoResponse createVideo(VideoCreationRequest request) {
        Channel channel = channelRepository.findById(request.getChannelId())
                .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_EXISTED));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));

        Video video = videoMapper.toVideo(request);
        video.setChannel(channel);
        video.setCategory(category);
        video.setViews(0);
        video.setUploadedAt(LocalDateTime.now());

        // Add tags if provided
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = request.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED)))
                    .collect(Collectors.toSet());
            video.setTags(tags);
        } else {
            video.setTags(new HashSet<>());
        }

        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    public VideoResponse updateVideo(int id, VideoUpdateRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));

        videoMapper.updateVideo(video, request);

        // Update tags if provided
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = request.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED)))
                    .collect(Collectors.toSet());
            video.setTags(tags);
        }

        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    public VideoResponse incrementViewCount(int id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));
        video.setViews(video.getViews() + 1);
        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    public VideoResponse incrementLikeCount(int id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));
        video.setLikes(video.getLikes() + 1);
        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    public VideoResponse incrementDislikeCount(int id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED));
        video.setDislikes(video.getDislikes() + 1);
        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    public VideoResponse getVideo(int id) {
        return videoMapper.toVideoResponse(
                videoRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_EXISTED)));
    }

    public Page<VideoResponse> getAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable)
                .map(videoMapper::toVideoResponse);
    }

    public Page<VideoResponse> getVideosByChannel(int channelId, Pageable pageable) {
        return videoRepository.findByChannelId(channelId, pageable)
                .map(videoMapper::toVideoResponse);
    }

    public Page<VideoResponse> getVideosByCategory(int categoryId, Pageable pageable) {
        return videoRepository.findByCategoryId(categoryId, pageable)
                .map(videoMapper::toVideoResponse);
    }

    public void deleteVideo(int id) {
        if (!videoRepository.existsById(id)) {
            throw new AppException(ErrorCode.VIDEO_NOT_EXISTED);
        }
        videoRepository.deleteById(id);
    }
}
