package io.softserve.goadventures.controllers;

import io.softserve.goadventures.configurations.FileStorageProperties;
import io.softserve.goadventures.errors.*;
import io.softserve.goadventures.services.UserService;
import io.softserve.goadventures.services.FileStorageService;
import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.models.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

//TODO add logging to the all controllers, both to the valid case and to the invalid/exception case
@RestController
public class AvatarController {

    private final Logger logger = LoggerFactory.getLogger(AvatarController.class);
    private FileStorageService fileStorageService;
    private final Path fileStorageLocation;
    private final UserService userService;
    private final JWTService jwtService;

    @Autowired
    public AvatarController(FileStorageService fileStorageService, FileStorageProperties fileStorageProperties, UserService userService, JWTService jwtService) {
        this.fileStorageService = fileStorageService;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "/uploadAvatar", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadAvatar(@RequestHeader(value = "Authorization") String token,
                                          @RequestParam("file") MultipartFile file) throws UserNotFoundException {

        try {
            if (!fileStorageService.checkFileSize(file)) {
                throw new FileSizeException();

            }
        }catch (FileSizeException err){
            return ResponseEntity.status(403).body(new ErrorMessageManager("Maximum file size is 5mb!",err.toString()));

        }
        User user = userService.getUserByEmail(jwtService.parseToken(token));
        String fileName = fileStorageService.storeFile(file);

        try {
            String fileType = fileStorageService.probeContentType(Paths.get(fileStorageLocation + File.separator + fileName));
            if(!(fileStorageService.checkFileType(fileType))){
                throw new WrongImageTypeException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WrongImageTypeException e){
            fileStorageService.deleteFileByFileName(fileName);  //delete file if failed check type
            return ResponseEntity.status(403).body(new ErrorMessageManager("Could not be uploaded, it is not an image!",e.toString()));
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadAvatar/")
                .path(fileName)
                .toUriString();
        if(user.getAvatarUrl()!=null){                                              //delete current avatar image
            fileStorageService.deleteFileByUri(user.getAvatarUrl());
        }

        user.setAvatarUrl(fileDownloadUri);
        userService.updateUser(user);

        return ResponseEntity.ok(fileDownloadUri);
    }

    @GetMapping("/deleteAvatar")
    public ResponseEntity<?> deleteUserAvatar(@RequestHeader(value = "Authorization") String token ) throws UserNotFoundException {

        User user = userService.getUserByEmail(jwtService.parseToken(token));
        if(user.getAvatarUrl() == null){
            return ResponseEntity.badRequest().body("Delete fail");
        }
        fileStorageService.deleteFileByUri(user.getAvatarUrl());
        user.setAvatarUrl(null);
        userService.updateUser(user);
        return ResponseEntity.ok("Delete_Success");

    }

    @GetMapping("/downloadAvatar/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName); //find img in storage

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}