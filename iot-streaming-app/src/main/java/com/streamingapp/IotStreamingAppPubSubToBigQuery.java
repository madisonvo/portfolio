package com.streamingapp;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.sql.impl.BeamSqlPipelineOptions;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
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

import com.google.gson.Gson;

public class IotStreamingAppPubSubToBigQuery {
    private static final Logger log = LoggerFactory.getLogger(IotStreamingAppPubSubToBigQuery.class);

    public interface Options extends DataflowPipelineOptions, BeamSqlPipelineOptions {
        @Description("Input topic name")
        String getInputTopic();
        void setInputTopic(String inputTopic);

        @Description("BigTable table name")
        String getTableName();
        void setTableName(String tableName);
    }

    public static void main( String[] args ) {
        Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
        options.setPlannerName("org.apache.beam.sdk.extensions.sql.zetasql.ZetaSQLQueryPlanner");
        run(options);
    }

    public static class JsonToRow extends DoFn<String, CommonLog> {
        @ProcessElement
        public void processElement(@Element String json, OutputReceiver<CommonLog> r) throws Exception {
            Gson gson = new Gson();
            CommonLog commonLog = gson.fromJson(json, CommonLog.class);
            log.info("Parsed JSON: " + commonLog);
            r.output(commonLog);
        }
    }

    public static void run(Options options) {
        Pipeline pipeline = Pipeline.create(options);
        options.setJobName("iot-streaming-app-pipeline-" + System.currentTimeMillis());

        pipeline
            .apply("ReadFromPubSub", PubsubIO.readStrings()
                .fromTopic(options.getInputTopic()))
            .apply("WindowByMinute", Window.into(FixedWindows.of(Duration.standardSeconds(60))))
            .apply("ParseJson", ParDo.of(new JsonToRow()))
            .apply("WriteToBigQuery", BigQueryIO.<CommonLog>write().to(options.getTableName()).useBeamSchema()
                 .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                 .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED));

        log.info("Building pipeline...");
        pipeline.run().waitUntilFinish();
    }
}
