# wwdata
Waste Water Data Application

This is a static site generator that uses maven to update databases of Covid waste water samples and presents the data in a simple format.  

## Disclaimer

**This presents data from authoritative sources in a digestible format but does not make any claim as to the accuracy or the meaning of data or the presentation.**


## Requirements

In order to build and update you require the following tools

* Maven 3 +  
* java JDK 11
* Github account with ssh keys set up on the account
* Write Access to this git repository and enabled to push up to this repository (if you want to build with maven below)

## Getting the Source

This contains submodules so clone with --recursive option in your git command
example

git clone --recursive <repo>

## Building

Only do this if you have write access to the repo. T
o build open a terminal, cd into the base directory and then build:
mvn clean install

The building has the following processes run in order:

1. preprocess.sh.  This pulls the latest from the repo and updates data submodules
2. The java class ca.maplepost.wwdata.Wwdata. This translates the data into json format for use on the static site
3. postprocess.sh  This commits and pushes the submodule updates and the process update from step 2 (if it makes it)

[![Java CI with Maven](https://github.com/MaplePost/wwdata/actions/workflows/maven.yml/badge.svg)](https://github.com/MaplePost/wwdata/actions/workflows/maven.yml)

