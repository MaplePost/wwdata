# wwdata
Waste Water Data Application

This is a static site generator that uses maven to update databases of Covid waste water samples.  

Requirements

In order to build and update you require the following tools

Maven 3 +  
java JDK 11
Github account with ssh keys set up on the account
Access to the git repository and enabled to push up to this repository

Building

To build open a terminal, cd into the base directory and then build:
mvn clean install

The building has the following processes run in order

1. preprocess.sh.  This pulls the latest from the repo and updates data submodules
2. The java class ca.maplepost.wwdata.Wwdata. This translates the data into json format for use on the static site
3. postprocess.sh  This commitds and pushes the submodule updates and the process update from step 2 (if it makes it)

Disclaimer

This presents data from authoritative sources but does not make any claim as to the accuracy or the meaning of data.


