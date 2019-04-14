package io.softserve.goadventures.services;

import io.softserve.goadventures.configurations.FileStorageProperties;
import io.softserve.goadventures.errors.FileStorageException;
import io.softserve.goadventures.errors.MyFileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.spi.FileTypeDetector;
import java.util.UUID;

@Service
public class FileStorageService extends FileTypeDetector {
    private final Path fileStorageLocation;
    private final Tika tika = new Tika();
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    public String probeContentType(Path path) throws IOException {
        return tika.detect(path.toFile());
    }

    public String storeFile(MultipartFile file) {       // Create unique file name, check invalid characters and copy file to directory
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uuidFile = UUID.randomUUID().toString();
        String finalFileName = uuidFile + "." + fileName;

        try {
            // Check if the file's name contains invalid characters
            if(finalFileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + finalFileName);
            }

            // Copy file to the target location
            Path targetLocation = this.fileStorageLocation.resolve(finalFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);


            return finalFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + finalFileName + ". Please try again!", ex);
        }
    }
    public boolean checkFileType(String fileType){    //check file type
        if(!(fileType.startsWith("image/"))){
            return false;
        }
        return true;
    }
    public boolean checkFileSize(MultipartFile file){
     long fileSize= file.getSize();
     logger.info("file size "+ fileSize);
     if(file.isEmpty()){
            return false;
        }
     if(fileSize > 5242880){
         return false;
     }
     return true;

    }
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public void deleteFileByUri(String fileUri) {
        String fileName = FilenameUtils.getName(fileUri);
        String filePath = fileStorageLocation + File.separator + fileName;
        logger.info("file path  " + filePath );
        try {
            Files.delete(Paths.get(filePath));
            logger.info("deletes successfully  " + filePath );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteFileByFileName(String fileName){
       String filePath = fileStorageLocation + File.separator + fileName;
        logger.info("file path  " + filePath );
        try {
            Files.delete(Paths.get(filePath));
            logger.info("deletes successfully  " + filePath );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
