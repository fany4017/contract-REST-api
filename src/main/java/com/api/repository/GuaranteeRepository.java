package com.api.repository;

import com.api.entity.Guarantee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuaranteeRepository extends JpaRepository<Guarantee, String> {
    List<Guarantee> findBysubGuaranteeCd(String subGuaranteeCd);
}
