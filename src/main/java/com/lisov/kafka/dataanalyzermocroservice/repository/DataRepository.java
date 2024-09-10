package com.lisov.kafka.dataanalyzermocroservice.repository;

import com.lisov.kafka.dataanalyzermocroservice.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Data, Long> {
}
