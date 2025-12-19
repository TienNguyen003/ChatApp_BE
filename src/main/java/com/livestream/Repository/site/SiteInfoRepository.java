package com.livestream.Repository.site;

import com.livestream.Entity.site.SiteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteInfoRepository extends JpaRepository<SiteInfo, Integer> {
    Optional<SiteInfo> findByKeyword(String keyword);
}
