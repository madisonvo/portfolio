package com.streamingapp;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.sql.impl.BeamSqlPipelineOptions;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.bigtable.BigtableIO;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.services.bigquery.model.TableRow;
import com.google.cloud.bigtable.data.v2.models.ChangeStreamMutation;
import com.google.cloud.bigtable.data.v2.models.Entry;
import com.google.cloud.bigtable.data.v2.models.SetCell;
import com.google.protobuf.ByteString;

public class BigTableToBigQueryChangeStream {
    private static final Logger log = LoggerFactory.getLogger(BigTableToBigQueryChangeStream.class);
    
     public interface Options extends DataflowPipelineOptions, BeamSqlPipelineOptions {
        @Description("GCP Project ID")
        String getProjectId();
        void setProjectId(String projectId);

        @Description("BigTable Instance ID")
        String getBigTableInstanceId();
        void setBigTableInstanceId(String bigTableInstanceId);

        @Description("BigTable table name")
        String getBigTableTableName();
        void setBigTableTableName(String bigTableTableName);

        @Description("BigQuery Table Name")
        String getBigQueryTableName();
        void setBigQueryTableName(String bigQueryTableName);
    }

    public static void main( String[] args ) {
        Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
        options.setPlannerName("org.apache.beam.sdk.extensions.sql.zetasql.ZetaSQLQueryPlanner");
        run(options);
    }

    public static class ChangeStreamToTableRowFn extends DoFn<KV<ByteString, ChangeStreamMutation>, TableRow> {
        @ProcessElement
        public void processElement(ProcessContext r) {
            KV<ByteString, ChangeStreamMutation> changeStreamRec = r.element();
            ChangeStreamMutation changeStreamValues = changeStreamRec.getValue();

            TableRow BQTableRow = new TableRow();
            for (Entry entry : changeStreamValues.getEntries()) {
                String columnName = ((SetCell)entry).getQualifier().toStringUtf8();
                String columnValue = ((SetCell)entry).getValue().toStringUtf8();
                BQTableRow.set(columnName, columnValue);
            }

            r.output(BQTableRow);
        }
    }

    private static void run(Options options) {
        Pipeline pipeline = Pipeline.create(options);
        options.setJobName("iot-streaming-app-pipeline-" + System.currentTimeMillis());

        PCollection<KV<ByteString, ChangeStreamMutation>> cs = pipeline
            .apply("ReadFromBigTable", BigtableIO.readChangeStream()
                .withProjectId(options.getProjectId())
                .withInstanceId(options.getBigTableInstanceId())
                .withTableId(options.getBigTableTableName()));

        PCollection<TableRow> tableRows = cs
            .apply("ParseChangeStreamToTableRow", ParDo.of(new ChangeStreamToTableRowFn()));
    
        tableRows
            .apply("WriteToBigQuery", BigQueryIO.<TableRow>write().to(options.getBigQueryTableName()).useBeamSchema()
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED));

        log.info("Building pipeline...");
        pipeline.run().waitUntilFinish();
    }
}
