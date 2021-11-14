package com.api.repository;

import com.api.entity.ContractGuarantee;
import com.api.entity.ContractGuaranteeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractGuaranteeRepository extends JpaRepository<ContractGuarantee, ContractGuaranteeId> {
    List<ContractGuarantee> findByContractId(String contractId);
}
