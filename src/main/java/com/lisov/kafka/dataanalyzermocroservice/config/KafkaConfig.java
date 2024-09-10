package com.lisov.kafka.dataanalyzermocroservice.config;

import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String servers;

  @Value("${topics}")
  protected List<String> topics;

  private final XML settings;

  @Bean
  public Map<String, Object> receiverProperties() {
    Map<String, Object> props = Map.of(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers,
        ConsumerConfig.GROUP_ID_CONFIG, new TextXpath(this.settings, "//groupId").toString(),
        JsonDeserializer.TRUSTED_PACKAGES, new TextXpath(this.settings, "//trustedPackages").toString(),
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, new TextXpath(this.settings, "//keyDeserializer").toString(),
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, new TextXpath(this.settings, "//valueDeserializer").toString()
    );
    return props;
  }

  @Bean
  public ReceiverOptions<String, Object> receiverOptions() {
    return ReceiverOptions.<String, Object>create(receiverProperties())
        .subscription(topics)
        .addAssignListener(partitions -> System.out.println("assigned: " + partitions))
        .addRevokeListener(partitions -> System.out.println("revoked: " + partitions));
  }

  @Bean
  public KafkaReceiver<String, Object> receiver(ReceiverOptions<String, Object> receiverOptions) {
    return KafkaReceiver.create(receiverOptions);
  }

}
