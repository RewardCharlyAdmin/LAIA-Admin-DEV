package us.kanddys.pov.admin.services.storage.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import us.kanddys.pov.admin.services.storage.FirebaseStorageService;
import us.kanddys.pov.admin.services.storage.utils.ImageFormat;

/**
 * @author Igirod0
 * @version 1.0.0
 */
@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

   public String uploadFile(MultipartFile multipartFile, String imageName, String folderName) {
      try {
         StorageOptions storageOptions = StorageOptions.newBuilder()
               .setProjectId("laia-c5d59")
               .setCredentials(
                     GoogleCredentials.fromStream(new ClassPathResource("firebase_admin.json").getInputStream()))
               .build();
         Storage storage = storageOptions.getService();
         String objectName = folderName + "/" + imageName;
         BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of("laia-c5d59.appspot.com", objectName))
               .setContentType("image/png")
               .build();
         storage.create(blobInfo, ImageFormat.resizeImage(multipartFile));
         return storage.signUrl(blobInfo, 3650, TimeUnit.DAYS).toString();
      } catch (IOException e) {
         throw new RuntimeException(e.getMessage());
      }
   }
}
