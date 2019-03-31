package io.softserve.goadventures.avatarUploadDownload;


import io.softserve.goadventures.auth.service.JWTService;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserNotFoundException;
import io.softserve.goadventures.user.service.UserService;
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
import java.io.IOException;

@RestController
public class AvatarController {

    private final Logger logger = LoggerFactory.getLogger(AvatarController.class);


    @Autowired
    private FileStorageService fileStorageService;
    private final UserService userService;
    private final JWTService jwtService;

    public AvatarController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping(path = "/uploadAvatar", consumes = {"multipart/form-data"})
    public UploadFileResponse uploadAvatar(@RequestHeader(value = "Authorization") String authorizationHeader,
                                         @RequestParam("file") MultipartFile file) throws UserNotFoundException {

        fileStorageService.checkFileType(file);

        User user = userService.getUserByEmail(jwtService.parseToken(authorizationHeader));

        if(user.getAvatarUrl()!=null){                                              //delete current avatar image
            fileStorageService.deleteFile(user.getAvatarUrl());
        }
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadAvatar/")
                .path(fileName)
                .toUriString();
        user.setAvatarUrl(fileDownloadUri);
        userService.updateUser(user);

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());

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