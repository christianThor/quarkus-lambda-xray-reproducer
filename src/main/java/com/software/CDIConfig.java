package com.software;

import com.amazonaws.xray.interceptors.TracingInterceptor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class CDIConfig {

    @Produces
    public S3AsyncClient asyncS3Client(@ConfigProperty(name = "aws.region", defaultValue = "us-west-2") final String awsRegion) {
        return S3AsyncClient.builder()
                .region(Region.of(awsRegion))
                .httpClient(NettyNioAsyncHttpClient.builder().build())
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                        // manual instrumentation as described here https://aws.amazon.com/blogs/developer/x-ray-support-for-the-aws-sdk-for-java-v2/
                        .addExecutionInterceptor(new TracingInterceptor())
                        .build())
                .build();
    }

}
