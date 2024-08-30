export PROJECT_ID=$(gcloud config get-value project)
export PUBSUB_TOPIC=my_topic
export BIGQUERY_DATASET=event
export BIGQUERY_TABLE=event_table
export BIGQUERY_TABLE_SCHEMA=event_id:INTEGER,timestamp:STRING,event:STRING,product:STRING,browser:STRING,country:STRING,OS:STRING,city:STRING

gcloud auth login
gcloud config set project ${PROJECT_ID}

echo "Creating Pub/Sub topic: ${PUBSUB_TOPIC}"
gloud pubsub topics create ${PUBSUB_TOPIC}

echo "Creating BigQuery dataset: ${BIGQUERY_DATASET}"
bq --location=US mk --dataset ${PROJECT_ID}:${BIGQUERY_DATASET}

echo "Creating BigQuery table: ${BIGQUERY_TABLE}"
bq mk --table ${PORJECT_ID}:${BIGQUERY_DATASET}.${BIGQUERY_TABLE} ${BIGQUERY_TABLE_SCHEMA}

echo "Resources created successfully"