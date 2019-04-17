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
import java.util.UUID;

@Service
public class FileStorageService {
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
            logger.info("Could not create the directory where the uploaded files will be stored.");
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {       // Create unique file name, check invalid characters, check if file is empty and copy file to directory
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uuidFile = UUID.randomUUID().toString();
        String finalFileName = uuidFile + "." + fileName;

        try {
            // Check if the file's name contains invalid characters
            if(file.isEmpty()){
                logger.info("File is empty");
                throw new FileStorageException("Empty file" + finalFileName);
            }
            if(finalFileName.contains("..")) {
                logger.info("Sorry! Filename contains invalid path sequence");
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + finalFileName);
            }

            // Copy file to the target location
            Path targetLocation = this.fileStorageLocation.resolve(finalFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);


            return finalFileName;
        } catch (IOException ex) {
            logger.info("Could not store file " + finalFileName + ". Please try again!", ex);
            throw new FileStorageException("Could not store file " + finalFileName + ". Please try again!", ex);
        }
    }
    public boolean checkFileType(String fileName){
        String fileType = "";
        Path path = Paths.get(fileStorageLocation + File.separator + fileName);
        try {
            fileType = tika.detect(path.toFile());
            logger.info("filetype: " + fileType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!(fileType.startsWith("image/"))){
            return false;
        }
        return true;
    }

    public boolean checkFileSize(MultipartFile file){
        long fileSize= file.getSize();
        logger.info("file size "+ fileSize);
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
                logger.info("File not found " + fileName);
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            logger.info("File not found " + fileName);
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
        Path path = Paths.get(fileStorageLocation + File.separator + fileName);
        try {
            Files.delete(path);
            logger.info("deletes successfully  " + path );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
