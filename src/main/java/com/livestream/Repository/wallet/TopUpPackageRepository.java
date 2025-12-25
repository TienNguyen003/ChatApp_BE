package com.livestream.Repository.wallet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livestream.Entity.wallet.TopUpPackage;

@Repository
public interface TopUpPackageRepository extends JpaRepository<TopUpPackage, Long> {
    Page<TopUpPackage> findByIsActiveTrueOrderByDisplayOrderAsc(Pageable pageable);

    Page<TopUpPackage> findAllByOrderByDisplayOrderAsc(Pageable pageable);
}
