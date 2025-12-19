package com.livestream.Service.site;

import com.livestream.DTO.response.site.SiteInfoResponse;
import com.livestream.Entity.site.SiteInfo;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.site.SiteInfoMapper;
import com.livestream.Repository.site.SiteInfoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SiteInfoService {
    SiteInfoRepository siteInfoRepository;
    SiteInfoMapper siteInfoMapper;

    public SiteInfoResponse getByKeyword(String keyword) {
        SiteInfo info = siteInfoRepository.findByKeyword(keyword)
                .orElseThrow(() -> new AppException(ErrorCode.SITE_INFO_NOT_FOUND));
        return siteInfoMapper.toSiteInfoResponse(info);
    }

    public SiteInfoResponse create(String keyword, String content, Integer updatedBy) {
        if (siteInfoRepository.findByKeyword(keyword).isPresent()) {
            throw new AppException(ErrorCode.INVALID);
        }
        SiteInfo info = SiteInfo.builder()
                .keyword(keyword)
                .content(content)
                .updatedAt(LocalDateTime.now())
                .updatedBy(updatedBy)
                .build();
        return siteInfoMapper.toSiteInfoResponse(siteInfoRepository.save(info));
    }

    public SiteInfoResponse upsert(String keyword, String content, Integer updatedBy) {
        SiteInfo info = siteInfoRepository.findByKeyword(keyword).orElse(SiteInfo.builder()
                .keyword(keyword)
                .build());
        info.setContent(content);
        info.setUpdatedAt(LocalDateTime.now());
        info.setUpdatedBy(updatedBy);
        return siteInfoMapper.toSiteInfoResponse(siteInfoRepository.save(info));
    }
}
