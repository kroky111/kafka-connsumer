package com.lisov.kafka.dataanalyzermocroservice.service;

import com.lisov.kafka.dataanalyzermocroservice.model.Data;

public interface KafkaDataService {

  void handle(Data data);
}
