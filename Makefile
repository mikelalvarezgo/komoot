all: help

SHELL := /bin/bash

EXECUTABLES=docker docker-compose sbt npm widdershins


ifeq (logs, $(firstword $(MAKECMDGOALS)))
   logargs := $(wordlist 2, $(words $(MAKECMDGOALS)), $(MAKECMDGOALS))
   $(eval $(logargs):;@true)
endif

##	build:			Build or rebuild hermes services
.PHONY : build
build:
	sbt "clean update compile"


## 	buildMongo:		Build mongo environment
 .PHONY : run
 run:
	sbt "run"

## 	dropMongo:		Drop mongo environment
 .PHONY : test
 test:
	sbt "test"
