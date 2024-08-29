package com.streamingapp;

import org.apache.beam.sdk.schemas.JavaFieldSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

@DefaultSchema(JavaFieldSchema.class)
public class CommonLog {
    Long event_id;
    String timestamp;
    String event;
    String product;
    String browser;
    String country;
    String OS;
    String city;
}
