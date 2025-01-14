package com.ssawallafy.workalone_backend.global.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
	@Value("${spring.cloud.aws.credentials.access-key}")
	private String accessKeyId;

	@Value("${spring.cloud.aws.credentials.secret-key}")
	private String accessKeySecret;

	@Value("${spring.cloud.aws.region.static}")
	private String s3RegionName;

	@Bean
	public AmazonS3 getAmazonS3Client() {
		final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
		// Get Amazon S3 client and return the s3 client object

		return AmazonS3ClientBuilder
			.standard()
			.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
			.withRegion(s3RegionName)
			.build();
	}
}
