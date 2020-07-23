package com.iit.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iit.kafka.service.LibraryEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryEventsConsumer {

    @Autowired
    private LibraryEventService libraryEventService;

    @KafkaListener(topics = {"#{'${kafka.topic}'}"}, groupId = "#{'${kafka.group-id}'}")
    public void consume(ConsumerRecord<Integer, String> consumerRecord, Acknowledgment acknowledgment) throws JsonProcessingException {
        log.info("Consumer Record: {}", consumerRecord);
        libraryEventService.processLibraryEvent(consumerRecord);
        acknowledgment.acknowledge();
    }
}
