package org.mql.cloud.smart_hire.storageblob;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface BlobStorageService {
	
	 public String uploadFile(MultipartFile file)throws IOException;

}
