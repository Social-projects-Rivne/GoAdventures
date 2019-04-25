package io.softserve.goadventures.services;

import io.softserve.goadventures.configurations.FileStorageProperties;
import io.softserve.goadventures.errors.FileStorageException;
import io.softserve.goadventures.errors.MyFileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uuidFile = UUID.randomUUID().toString();
        String finalFileName = uuidFile + "." + fileName;

        try {
            if (finalFileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + finalFileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(finalFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return finalFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + finalFileName + ". Please try again!", ex);
        }
    }

    public boolean checkFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (!(contentType.startsWith("image/"))) {
            return false;
        }
        return true;
    }

    public boolean checkFileSize(MultipartFile file) {
        long fileSize = file.getSize();
        logger.info("file size" + fileSize);
        if (fileSize > 5242880) {
            return false;
        }
        return true;

    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public void deleteFile(String fileUri) {
        String fileName = FilenameUtils.getName(fileUri);
        logger.info("name" + fileName);
        String filePath = fileStorageLocation + "/" + fileName;
        logger.info("file path" + filePath);
        try {
            Files.delete(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
