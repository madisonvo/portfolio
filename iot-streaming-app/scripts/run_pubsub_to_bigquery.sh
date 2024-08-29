export PROJECT_ID=deft-strata-431821-m5
export REGION=us-west1
export PIPELINE_FOLDER=gs://my-bucket-202405
export MAIN_CLASS_NAME=com.streamingapp.IotStreamingApp
export RUNNER=DataflowRunner
export PUBSUB_TOPIC=projects/deft-strata-431821-m5/topics/my_topic
export TABLE_NAME=${PROJECT_ID}:event.event_table

mvn compile exec:java \
-Dexec.mainClass=${MAIN_CLASS_NAME} \
-Dexec.cleanupDaemonThreads=false \
-Dexec.args=" \
--project=${PROJECT_ID} \
--region=${REGION} \
--stagingLocation=${PIPELINE_FOLDER}/staging \
--tempLocation=${PIPELINE_FOLDER}/temp \
--runner=${RUNNER} \
--inputTopic=${PUBSUB_TOPIC} \
--tableName=${TABLE_NAME}"