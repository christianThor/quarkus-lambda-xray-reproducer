package com.software;

import javax.inject.Inject;
import javax.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Named("test")
public class TestLambda implements RequestHandler<InputObject, OutputObject> {

    @Inject
    ProcessingService service;

    @Inject
    S3AsyncClient s3AsyncClient;

    @Inject
    @ConfigProperty(name = "test.s3.bucket", defaultValue = "")
    String bucket;

    @Override
    public OutputObject handleRequest(InputObject input, Context context) {

        final String key = String.format("%s-test-file.txt", System.currentTimeMillis());

        s3AsyncClient.putObject(
                PutObjectRequest.builder().bucket(bucket).key(key).build(),
                AsyncRequestBody.fromString(String.format("name=%s, greeting=%s", input.getName(), input.getGreeting())))
                .join();

        return service.process(input).setRequestId(context.getAwsRequestId());
    }
}
