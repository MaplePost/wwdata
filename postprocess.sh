#!/bin/bash
# get a date stamp for use in git push and updates
DATE=`date`

echo "******* post-processing *****"

env
# WORKSPACE is passed from maven build
cd "${WORKSPACE}"
# commit all changes , this will commit the changes from both any new data
# from the data submodule and also updates from our java processor
git commit -am "maven build source update ${DATE}"
git push



