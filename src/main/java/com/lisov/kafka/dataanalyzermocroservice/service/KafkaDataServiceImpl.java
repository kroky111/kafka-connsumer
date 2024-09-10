package com.lisov.kafka.dataanalyzermocroservice.service;

import com.lisov.kafka.dataanalyzermocroservice.model.Data;
import com.lisov.kafka.dataanalyzermocroservice.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaDataServiceImpl implements KafkaDataService {

  private final DataRepository dataRepository;

  @Override
  public void handle(Data data) {
    dataRepository.save(data);
    log.info("Data object is saved: {}", data);
  }
}
