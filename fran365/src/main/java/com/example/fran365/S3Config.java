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

	@Value("AKIA2OMTOI42I5G3LDHB")
	private String awsAccessKey;    //AKIA2OMTOI42I5G3LDHB
	@Value("3hwWeBV5lKX2Dz+Le5QKMWCDpG+4LjwG83qUpGYz")
	private String awsSecretKey;    //3hwWeBV5lKX2Dz+Le5QKMWCDpG+4LjwG83qUpGYz
	@Value("ap-northeast-2")
	private String region;          //ap-northeast-2
	
    @Bean
    public AmazonS3 s3client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
	
}
