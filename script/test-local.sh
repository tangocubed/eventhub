#!/usr/bin/env bash

SCRIPT_DIR=$(cd $"${BASH_SOURCE%/*}" && pwd)
BASE_DIR=$(cd $SCRIPT_DIR && cd .. && pwd)

(cd $BASE_DIR \
  && ./gradlew test --rerun-tasks \
)