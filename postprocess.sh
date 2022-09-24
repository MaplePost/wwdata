#!/bin/bash
DATE=`date`

echo "******* post-processing *****"
# let's get the latest git repo
env
cd "${WORKSPACE}"
git commit -am "maven build source update ${DATE}"
git push



