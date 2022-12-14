# Komoot Challenge

## Introduction

  This application has been developed in Scala for resolving the challenge from Komoot hiring process.

## Solution approach

  This solution consists in the next:
  - A main application developed in Scala, using Ligthbend framework (Akka, Alpakka, AkkaHttp) and Cats.
  - The application consume from a auxilar SQS topic, that is subscribed to SNS topic described in the challenge.
  - The solution for retrieving the recent users is to use an in memory cache, supported by Scaffeine library.
  - The application include tests.
  - Follows DDD.

## Instructions

The project include a makefile for facilate the build, execution and execution of the test.
  - make build = Donwload dependencies, compile and build the project.
  - make run = Execute application (for executing it, it is needed to replace AWS credentials and SQS url)
  - make test = Execute test

## Test approach

The challenge include the next types of tests:

  - Behaviour test: This tests checks that the UseCase interacts with the infrastructure components as expected and produced the expected out put given
  the determinated input.
  - Integration test: Test that checks the integration with an external provider( in this case HttpClient)

 There is no Acceptance test(E2E) included because it was out of the scope for this challenge.

## Future improvements
  - Add E2E tests.
  - Recovery control in queue consumptionÃ‡(retries policy, deadletter queue ...).
  - Monitoring 


