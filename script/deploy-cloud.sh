#!/usr/bin/env bash

SCRIPT_DIR=$(cd $"${BASH_SOURCE%/*}" && pwd)
BASE_DIR=$(cd $SCRIPT_DIR && cd .. && pwd)

PROJECT_NAME=tangocubed
ARTIFACT_NAME='eventhub'
ARTIFACT_VERSION='1.0.0'

CONTAINER_IMAGE_REGISTRY='gcr.io'
CONTAINER_IMAGE_TAG="$CONTAINER_IMAGE_REGISTRY/$PROJECT_NAME/$ARTIFACT_NAME:$ARTIFACT_VERSION"

CONTAINER_NAME="$ARTIFACT_NAME"

REGION='asia-northeast1'

set -x

gcloud config set run/region $REGION

(cd $BASE_DIR \
  && gcloud builds submit \
    --tag $CONTAINER_IMAGE_TAG \
  && gcloud run deploy \
    --image $CONTAINER_IMAGE_TAG \
    --platform managed \
)