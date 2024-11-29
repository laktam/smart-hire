package org.mql.cloud.smart_hire.dataextraction;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class AzureDocumentIntelligenceService {

    @Value("${azure.form.recognizer.endpoint}")
    private String endpoint;

    @Value("${azure.form.recognizer.api-key}")
    private String apiKey;

    public AnalyzeResult analyzeResume(byte[] resumeFile) throws InterruptedException {
        // Convert the byte array into BinaryData
        BinaryData resumeData = BinaryData.fromStream(new ByteArrayInputStream(resumeFile));

        // Initialize the DocumentAnalysisClient with the endpoint and credentials
        DocumentAnalysisClient client = new DocumentAnalysisClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(apiKey))
                .buildClient();

        // Begin analyzing the document with the prebuilt model
        com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult result = client.beginAnalyzeDocument("prebuilt-document", resumeData)
                .getFinalResult(); // Get the final result after the analysis
        
        System.out.println(" resultat d'analyze du cv : " + result);
        return result;
    }
}
