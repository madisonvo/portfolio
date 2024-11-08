package com.timesheet_management.spring_boot_timesheet.service;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

public class EmployeeSubscriberService {

    public EmployeeSubscriberService() {
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of("deft-strata-431821-m5", "employee-updates-sub");
        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, new MessageReceiver() {
            @Override
            public void receiveMessage(PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {
                System.out.println("Received message: " + pubsubMessage.getData().toStringUtf8());
                ackReplyConsumer.ack();
            }
        }).build();
        subscriber.startAsync().awaitRunning();
    }
}
