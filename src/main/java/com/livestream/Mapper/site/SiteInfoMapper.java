package com.livestream.Mapper.site;

import com.livestream.DTO.response.site.SiteInfoResponse;
import com.livestream.Entity.site.SiteInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteInfoMapper {
    SiteInfoResponse toSiteInfoResponse(SiteInfo siteInfo);
}
