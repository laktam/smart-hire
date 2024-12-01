package org.mql.cloud.smart_hire.service;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder;
//import com.azure.ai.formrecognizer.documentanalysis.implementation.models.DocumentField;
import com.azure.ai.formrecognizer.documentanalysis.models.DocumentField;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.core.util.polling.SyncPoller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class AzureDocumentIntelligenceService {

	@Value("${azure.form.recognizer.endpoint}")
	private String endpoint;

	@Value("${azure.form.recognizer.api-key}")
	private String apiKey;

	public AnalyzeResult analyzeResume(MultipartFile resumeFile) throws InterruptedException, IOException {

		if (resumeFile == null) {
			throw new IllegalArgumentException("MultipartFile is null");
		}

		System.out.println("Analyzing resume file");
		System.out.println("File name: " + resumeFile.getOriginalFilename());
		System.out.println("File size: " + resumeFile.getSize());
		System.out.println("File is empty: " + resumeFile.isEmpty());

		// Additional check before getting bytes
		if (resumeFile.getSize() == 0) {
			throw new IllegalArgumentException("File is empty");
		}

		// Convert MultipartFile to byte array
		byte[] resumeBytes = resumeFile.getBytes();
		// Convert byte array into BinaryData for Azure SDK
//		BinaryData resumeData = BinaryData.fromStream(new ByteArrayInputStream(resumeBytes));
	    BinaryData resumeData = BinaryData.fromBytes(resumeBytes);

		// Initialize the DocumentAnalysisClient with the endpoint and credentials
		DocumentAnalysisClient client = new DocumentAnalysisClientBuilder().endpoint(endpoint)
				.credential(new AzureKeyCredential(apiKey)).buildClient();

		// Begin analyzing the document with the prebuilt model
		AnalyzeResult result = client.beginAnalyzeDocument("resume-model-2", resumeData).getFinalResult(); // Get
																													// the
																													// final
																													// result
																													// after
																													// the
																													// analysis

//        SyncPoller<OperationResult, AnalyzeResult> analyzeReceiptPoller =
//        	    client.beginAnalyzeDocument("prebuilt-receipt", resumeData);
//
//        	AnalyzeResult result = analyzeReceiptPoller.getFinalResult();

//		System.out.println(" resultat d'analyze du cv : " + result);
//		System.out.println(" content :::: " + result.getContent());
		// Access labeled fields
	    
	    
		return result;
	}
}
