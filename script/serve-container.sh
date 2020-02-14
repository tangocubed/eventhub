#!/usr/bin/env bash

SCRIPT_DIR=$(cd $"${BASH_SOURCE%/*}" && pwd)
BASE_DIR=$(cd $SCRIPT_DIR && cd .. && pwd)

PROJECT_NAME=tangocubed
ARTIFACT_NAME='eventhub'
ARTIFACT_VERSION='1.0.0'

CONTAINER_IMAGE_REGISTRY='gcr.io'
CONTAINER_IMAGE_TAG="$CONTAINER_IMAGE_REGISTRY/$PROJECT_NAME/$ARTIFACT_NAME:$ARTIFACT_VERSION"

CONTAINER_NAME="$ARTIFACT_NAME"

set -x

(cd $BASE_DIR \
  && docker build \
    -t $CONTAINER_IMAGE_TAG \
    . \
  && (docker rm -f $CONTAINER_NAME || true) \
  && docker run \
    --name $CONTAINER_NAME \
    --rm \
    -it \
    -p 8080:8080 \
    $CONTAINER_IMAGE_TAG \
)