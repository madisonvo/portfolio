package com.streamingapp;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.sql.impl.BeamSqlPipelineOptions;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;

import org.joda.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.bigtable.beam.CloudBigtableIO;
import com.google.cloud.bigtable.beam.CloudBigtableTableConfiguration;
import com.google.gson.Gson;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.Mutation;

public class IotStreamingAppPubSubToBigTable {
    private static final Logger log = LoggerFactory.getLogger(IotStreamingAppPubSubToBigQuery.class);

    public interface Options extends DataflowPipelineOptions, BeamSqlPipelineOptions {
        @Description("Input topic name")
        String getInputTopic();
        void setInputTopic(String inputTopic);

        @Description("Project ID")
        String getProjectId();
        void setProjectId(String projectId);

        @Description("BigTable instance ID")
        String getInstanceId();
        void setInstanceId(String instanceId);

        @Description("BigTable table ID")
        String getTableId();
        void setTableId(String tableId);

        @Description("BigTable family ID")
        String getFamilyId();
    }

    public static void main( String[] args ) {
        Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
        options.setPlannerName("org.apache.beam.sdk.extensions.sql.zetasql.ZetaSQLQueryPlanner");
        run(options);
    }

    public static class JsonToBigtableMutation extends DoFn<String, Mutation> {
        private final String familyId;

        public JsonToBigtableMutation(String familyId) {
            this.familyId = familyId;
        }

        @ProcessElement
        public void processElement(@Element String json, OutputReceiver<Mutation> r) {
            Gson gson = new Gson();
            CommonLog commonLog = gson.fromJson(json, CommonLog.class);
            Put row = new Put(Bytes.toBytes(String.valueOf(commonLog.event_id)));

            row.addColumn(
                Bytes.toBytes(familyId),
                Bytes.toBytes("timestamp"),
                Bytes.toBytes(String.valueOf(commonLog.timestamp)));

            row.addColumn(
                Bytes.toBytes(familyId),
                Bytes.toBytes("event"),
                Bytes.toBytes(String.valueOf(commonLog.event)));

            row.addColumn(
                Bytes.toBytes(familyId),
                Bytes.toBytes("product"),
                Bytes.toBytes(String.valueOf(commonLog.product)));

            row.addColumn(
                Bytes.toBytes(familyId),
                Bytes.toBytes("browser"),
                Bytes.toBytes(String.valueOf(commonLog.browser)));

            row.addColumn(
                Bytes.toBytes(familyId),
                Bytes.toBytes("country"),
                Bytes.toBytes(String.valueOf(commonLog.country)));
    
            row.addColumn(
                Bytes.toBytes(familyId),
                Bytes.toBytes("OS"),
                Bytes.toBytes(String.valueOf(commonLog.OS)));
    
            row.addColumn(
                Bytes.toBytes(familyId),
                Bytes.toBytes("city"),
                Bytes.toBytes(String.valueOf(commonLog.city)));

            r.output(row);
        }
    }

    public static void run(Options options) {
        Pipeline pipeline = Pipeline.create(options);
        options.setJobName("iot-streaming-app-pipeline-" + System.currentTimeMillis());

        CloudBigtableTableConfiguration bigtableTableConfig =
            new CloudBigtableTableConfiguration.Builder()
                .withProjectId(options.getProjectId())
                .withInstanceId(options.getInstanceId())
                .withTableId(options.getTableId())
                .build();

        pipeline
            .apply("ReadFromPubSub", PubsubIO.readStrings()
                .fromTopic(options.getInputTopic()))
            .apply("WindowByMinute", Window.into(FixedWindows.of(Duration.standardSeconds(60))))
            .apply("ConvertToBigtableMutation", ParDo.of(new JsonToBigtableMutation(options.getFamilyId())))
            .apply("WriteToBigtable", CloudBigtableIO.writeToTable(bigtableTableConfig));

        log.info("Building pipeline...");
        pipeline.run().waitUntilFinish();
    }


}
