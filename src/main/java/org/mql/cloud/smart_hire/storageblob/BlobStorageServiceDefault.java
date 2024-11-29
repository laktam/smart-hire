package org.mql.cloud.smart_hire.storageblob;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;

import jakarta.annotation.PostConstruct;

@Service
public class BlobStorageServiceDefault implements BlobStorageService {

	@Value("${azure.storage.connection-string}")
	private String connectionString;

	private BlobServiceClient blobServiceClient;
	private BlobContainerClient containerClient;

	@PostConstruct
	public void init() {
		blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
		containerClient = blobServiceClient.getBlobContainerClient("smart-hire-container");
	}

	public BlobStorageServiceDefault() {
	}

	public String uploadFile(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID() + ".pdf";
		BlobClient blobClient = containerClient.getBlobClient(fileName);

		try (InputStream inputStream = file.getInputStream()) {
			blobClient.upload(inputStream, file.getSize(), true);
			blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType("application/pdf"));
		}

		return blobClient.getBlobUrl();
	}
}