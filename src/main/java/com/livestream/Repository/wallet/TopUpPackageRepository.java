package com.livestream.Repository.wallet;

import com.livestream.Entity.wallet.TopUpPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopUpPackageRepository extends JpaRepository<TopUpPackage, Long> {
    List<TopUpPackage> findByIsActiveTrueOrderByDisplayOrderAsc();

    List<TopUpPackage> findAllByOrderByDisplayOrderAsc();
}
