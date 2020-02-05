# quarkus-lambda-xray-reproducer

## Intro

This repository reproduces a specific use case where we need to trace AWS SDK client calls in an AWS lambda using AWS xray.
It includes AWS V2 S3 client for demonstration purposes.

This repository is based on this [thread](https://github.com/quarkusio/quarkus/issues/6785).

## Build

1. Build quarkus branch as described here: https://github.com/quarkusio/quarkus/issues/6785#issuecomment-582103400

2. `mvn clean install -Pnative -Dnative-image.docker-build=true`

## Deploy native lambda

I used a custom manage bash script to deploy the lambda to our group's AWS account,
but anybody should be able to deploy the `appTemplate.yaml` to demonstrate the use
of this lambda with xray. This was most convenient for me to use in order to cobble this together quickly, 
but I can try to set it up and test with SAM local if needed.

## Things we haven't tested...
 * [Automatic AWS SDK client tracing using the instrumentor dependency](https://aws.amazon.com/blogs/developer/x-ray-support-for-the-aws-sdk-for-java-v2/). Checkout `CDIConfig.java` to see how the tracing interceptor is set up in the S3 client.
 
 * [Creating custom subsegments](https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java-subsegments.html)
 
 * [Tracing outgoing HTTP calls](https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java-httpclients.html)
 
 * I think AWS Xray has more configurability but only outside the realm of Lambdas, 
 such as [tracing incoming requests](https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java-filters.html), and possibly
 more.
 
  
