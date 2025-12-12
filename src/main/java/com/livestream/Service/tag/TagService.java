package com.livestream.Service.tag;

import com.livestream.DTO.request.tag.TagRequest;
import com.livestream.DTO.response.tag.TagResponse;
import com.livestream.Entity.tag.Tag;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.tag.TagMapper;
import com.livestream.Repository.tag.TagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagService {
    TagRepository tagRepository;
    TagMapper tagMapper;

    public TagResponse createTag(TagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.TAG_EXISTED);
        }

        Tag tag = tagMapper.toTag(request);
        tag.setUsageCount(0);
        tag.setCreatedAt(LocalDateTime.now());

        return tagMapper.toTagResponse(tagRepository.save(tag));
    }

    public TagResponse updateTag(int id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED));

        tagMapper.updateTag(tag, request);
        return tagMapper.toTagResponse(tagRepository.save(tag));
    }

    public TagResponse getTag(int id) {
        return tagMapper.toTagResponse(
                tagRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED)));
    }

    public Page<TagResponse> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable)
                .map(tagMapper::toTagResponse);
    }

    public List<TagResponse> getTrendingTags(int limit) {
        return tagRepository.findTopTags().stream()
                .limit(limit)
                .map(tagMapper::toTagResponse)
                .collect(Collectors.toList());
    }

    public void deleteTag(int id) {
        if (!tagRepository.existsById(id)) {
            throw new AppException(ErrorCode.TAG_NOT_EXISTED);
        }
        tagRepository.deleteById(id);
    }

    public void incrementUsageCount(int tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED));
        tag.setUsageCount(tag.getUsageCount() + 1);
        tagRepository.save(tag);
    }
}
