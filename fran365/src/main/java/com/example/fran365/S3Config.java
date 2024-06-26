package com.example.fran365;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


@Configuration
public class S3Config {

	
	@Value("AKIA6FYJA5ZOOFBRAF57")
	private String awsAccessKey; //AKIA4SVRILQ7HTGVXEP4
	
	@Value("bZ3jzRGyajrHYL/35NT2u+tCcWieWnko1LkrRjTZ")
	private String awsSecretKey; //8ps8ip67D3iZynxxt9v4ue11B/PK+0ObhgTt3hS1
	
	@Value("ap-northeast-2")
	private String region;  //ap-northeast-2
	
    @Bean
    public AmazonS3 s3client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}

