package com.finspire.Grandpittu.repository;

import com.finspire.Grandpittu.entity.SmsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsLogRepository extends JpaRepository<SmsLog,Integer> {
}
