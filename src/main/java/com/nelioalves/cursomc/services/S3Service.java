package com.nelioalves.cursomc.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nelioalves.cursomc.exceptions.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);
	
	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public URI uploadFile(MultipartFile multipartFile) {
		
		try {
			String fileName = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();
			
			return uploadFile(is, fileName, contentType);
		
		} catch (IOException e) {
			LOG.info("AmazonClientException: " + e.getMessage());
			throw new FileException("Erro IOException: " + e.getMessage());
		}
	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			
			LOG.info("Iniciando Upload");
			
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			
			s3client.putObject(new PutObjectRequest(bucketName, fileName, is, meta));
			
			LOG.info("Fim Upload");
			
			return s3client.getUrl(bucketName, fileName).toURI();

		} catch (URISyntaxException e) {
			LOG.info("URISyntaxException: " + e.getMessage());
			throw new FileException("Erro ao converter URL para URI");
		}
	}
	
	public void uploadFile(String localFilePath) {
		
		File arquivo = new File(localFilePath);
		
		try {
			
			LOG.info("Iniciando Upload");
			
			s3client.putObject(new PutObjectRequest(bucketName, arquivo.getName(), arquivo));
			
			LOG.info("Fim Upload");
			
		} catch (AmazonServiceException e) {
			LOG.info("AmazonServiceException: " + e.getErrorMessage());
			LOG.info("Status Code: " + e.getErrorCode());
		} catch (AmazonClientException e) {
			LOG.info("AmazonClientException: " + e.getMessage());
		}
	}
}
