package com.lisov.kafka.dataanalyzermocroservice.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lisov.kafka.dataanalyzermocroservice.config.LocalDateTimeDeserializer;
import com.lisov.kafka.dataanalyzermocroservice.model.Data;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.startup.EngineRuleSet;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaDataReceiverImpl implements KafkaDataReceiver {

  private final KafkaReceiver<String, Object> receiver;
  private final LocalDateTimeDeserializer localDateTimeDeserializer;
  private final KafkaDataService kafkaDataService;

  @PostConstruct
  public void init() {
    fetch();
  }

  @Override
  public void fetch() {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer)
        .create();
    receiver.receive()
        .subscribe(r -> {
          Data data = gson.fromJson(r.value().toString(), Data.class);
          kafkaDataService.handle(data);
          r.receiverOffset().acknowledge();
        });
  }
}
