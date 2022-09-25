#!/bin/bash

echo "******* preprocessing *****"
# let's get the latest git repo
# workspace is passed from the maven builder
#
env



cd "${WORKSPACE}/rawdata/PHESD"
# some codes to test if we need to pull
# just for show at this time
UPSTREAM=${1:-'@{u}'}
LOCAL=$(git rev-parse @)
REMOTE=$(git rev-parse "$UPSTREAM")
BASE=$(git merge-base @ "$UPSTREAM")

if [ $LOCAL = $REMOTE ]; then
    echo "Up-to-date"
elif [ $LOCAL = $BASE ]; then
    echo "Need to pull"
elif [ $REMOTE = $BASE ]; then
    echo "Need to push"
else
    echo "Diverged"
fi

cd "${WORKSPACE}"

# get the latest from our git just in case
git pull origin main
# this updates the remote to the latest data
git submodule foreach git pull origin main


