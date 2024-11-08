package com.employee_management.spring_boot_employee.service;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeePublisherService {

    private final Publisher publisher;

    public EmployeePublisherService() throws IOException {
        this.publisher = Publisher.newBuilder(TopicName.of("deft-strata-431821-m5", "employee-updates")).build();
    }

    public void publishMessage(String message) throws Exception {
        ByteString msg = ByteString.copyFromUtf8(message);
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(msg).build();
        publisher.publish(pubsubMessage).get();
    }
}
