package io.softserve.goadventures.controllers;

import io.softserve.goadventures.errors.ErrorMessageManager;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.Gallery;
import io.softserve.goadventures.repositories.GalleryRepository;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.services.FileStorageService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("event/gallery")
public class GalleryController {
  private Logger logger = LoggerFactory.getLogger(GalleryController.class);
  private final GalleryRepository galleryRepository;
  private final EventService eventService;
  private final FileStorageService fileStorageService;
  private final ModelMapper modelMapper;

  @Autowired
  public GalleryController(GalleryRepository galleryRepository,
                           EventService eventService,
                           FileStorageService fileStorageService,
                           ModelMapper modelMapper) {
    this.galleryRepository = galleryRepository;
    this.eventService = eventService;
    this.fileStorageService = fileStorageService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/all/{eventId}")
  public Gallery getAllGalleryByEventId(@PathVariable(value = "eventId") int eventId) {
    Event event = eventService.getEventById(eventId);
    return galleryRepository.findByEventId(event.getId());
  }

  @PostMapping("/add-new/{eventId}")
  public ResponseEntity<?> addNewGallery(
          @PathVariable("eventId") int eventId, @RequestParam("images") MultipartFile[] images) {
    try {
      Event event = eventService.getEventById(eventId);
      if (event != null) {
        Set<String> imageUrls = new HashSet<>();
        String newFileName;
        String fileUrl;
        for (MultipartFile uploadedImage : images) {
          newFileName = fileStorageService.storeFile(uploadedImage);
          fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                  .path("/galleries/")
                  .path(newFileName)
                  .toUriString();
          imageUrls.add(fileUrl);
        }
          logger.info(imageUrls.toString());
        Gallery gallery = new Gallery(0, event, imageUrls, false);
        gallery = galleryRepository.save(gallery);
        if (gallery != null) {
          event.setGallery(gallery);
          eventService.updateEvent(event);
          return ResponseEntity.ok().body(modelMapper.map(gallery, Gallery.class));
        } else {
          throw new IOException("Gallery malformed");
        }
      } else {
        throw new IOException("Event doesn't founded or doesn't exist");
      }
    } catch (IOException error) {
      logger.debug(error.toString());
      return ResponseEntity.status(500).body(new ErrorMessageManager(
              "Something went wrong while uploading your files", error.toString()));
    }
  }
}