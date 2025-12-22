package com.livestream.Service.clip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.livestream.DTO.request.clip.ClipCreationRequest;
import com.livestream.DTO.response.clip.ClipResponse;
import com.livestream.Entity.clip.Clip;
import com.livestream.Entity.livestream.Livestream;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.clip.ClipMapper;
import com.livestream.Repository.clip.ClipRepository;
import com.livestream.Repository.livestream.LivestreamRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClipService {
    ClipRepository clipRepository;
    LivestreamRepository livestreamRepository;
    ClipMapper clipMapper;

    public ClipResponse createClip(ClipCreationRequest request) {
        Livestream livestream = livestreamRepository.findById(request.getLivestreamId())
                .orElseThrow(() -> new AppException(ErrorCode.LIVESTREAM_NOT_EXISTED));

        Clip clip = clipMapper.toClip(request);
        clip.setLivestream(livestream);
        clip.setChannel(livestream.getChannel());

        return clipMapper.toClipResponse(clipRepository.save(clip));
    }

    public ClipResponse getClip(int id) {
        return clipMapper.toClipResponse(
                clipRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.CLIP_NOT_EXISTED)));
    }

    public Page<ClipResponse> getAllClips(Pageable pageable) {
        return clipRepository.findAll(pageable)
                .map(clipMapper::toClipResponse);
    }

    public Page<ClipResponse> getClipsByLivestream(int livestreamId, Pageable pageable) {
        return clipRepository.findByLivestreamId(livestreamId, pageable)
                .map(clipMapper::toClipResponse);
    }

    public Page<ClipResponse> getClipsByChannel(int channelId, Pageable pageable) {
        return clipRepository.findByChannelId(channelId, pageable)
                .map(clipMapper::toClipResponse);
    }

    public void deleteClip(int id) {
        if (!clipRepository.existsById(id)) {
            throw new AppException(ErrorCode.CLIP_NOT_EXISTED);
        }
        clipRepository.deleteById(id);
    }
}
