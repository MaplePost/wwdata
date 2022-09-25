#!/bin/bash

echo "******* preprocessing *****"
# let's get the latest git repo
# workspace is passed from the maven builder
#
env

cd "${WORKSPACE}"

# get the latest from our git just in case
git pull origin main
# this updates the remote to the latest data
git submodule foreach git pull origin main


