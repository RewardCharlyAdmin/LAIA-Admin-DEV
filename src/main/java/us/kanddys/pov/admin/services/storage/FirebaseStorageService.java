package us.kanddys.pov.admin.services.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Igirod0
 * @version 1.0.0
 */
public interface FirebaseStorageService {

   /**
    * Este método tiene la obligación de subir un archivo a Firebase Storage.
    * 
    * @author Igirod
    * @version 1.0.0
    * @param multipartFile
    * @param folderName
    * @return String
    */
   public String uploadFile(MultipartFile multipartFile, String imageName, String folderName);
}
