package com.timesheet_management.spring_boot_timesheet.service;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TimesheetPublisherService {

    private final Publisher publisher;

    public TimesheetPublisherService() throws IOException {
        this.publisher = Publisher.newBuilder(TopicName.of("deft-strata-431821-m5", "timesheet-updates")).build();
    }

    public void publishMessage(String message) throws Exception {
        ByteString msg = ByteString.copyFromUtf8(message);
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(msg).build();
        publisher.publish(pubsubMessage).get();
    }
}
