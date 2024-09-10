package com.healthcarewebapp.repository;

import com.healthcarewebapp.model.AbnormalHealthRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RecordRepository extends MongoRepository<AbnormalHealthRecord, String> {
    List<AbnormalHealthRecord> findAllByCitizenid(Integer citizenid);
    AbnormalHealthRecord findByCitizenidAndTimestamp(Integer citizenid, String timestamp);
}
