#!/bin/bash

echo "******* preprocessing *****"
# let's get the latest git repo
env
cd "${WORKSPACE}"

git pull origin main
git submodule foreach git pull origin main


