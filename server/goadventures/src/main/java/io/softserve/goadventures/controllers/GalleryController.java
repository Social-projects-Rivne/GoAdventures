package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.EventDTO;
import io.softserve.goadventures.dto.GalleryDto;
import io.softserve.goadventures.errors.ErrorMessageManager;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.Gallery;
import io.softserve.goadventures.repositories.GalleryRepository;
import io.softserve.goadventures.services.EventService;
import io.softserve.goadventures.services.FileStorageService;
import io.softserve.goadventures.services.GalleryCRUDService;
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

@CrossOrigin
@RestController
@RequestMapping("event/gallery")
public class GalleryController {
    private final static String ServerErrorMsg = "Server error, try again later";
    private Logger logger = LoggerFactory.getLogger(GalleryController.class);
    private final GalleryRepository galleryRepository;
    private final EventService eventService;
    private final GalleryCRUDService galleryCRUDService;
    private final FileStorageService fileStorageService;
    private final ModelMapper modelMapper;

    @Autowired
    public GalleryController(GalleryRepository galleryRepository,
                             EventService eventService,
                             FileStorageService fileStorageService,
                             ModelMapper modelMapper,
                             GalleryCRUDService galleryCRUDService) {
        this.galleryRepository = galleryRepository;
        this.eventService = eventService;
        this.fileStorageService = fileStorageService;
        this.modelMapper = modelMapper;
        this.galleryCRUDService = galleryCRUDService;
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
                HashSet<String> imageUrls = new HashSet<>();
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
                    return ResponseEntity.ok().body(modelMapper.map(gallery, GalleryDto.class));
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

    @PutMapping("/deattach/{eventId}")
    public ResponseEntity<?> deactivateGallery(@PathVariable(value = "eventId") int eventId) {
        try {
            Event event = eventService.getEventById(eventId);
            if (event != null)
            {
                galleryRepository.findByEventId(eventId).setIsDeleted(true);
                event.setGallery(null);
                return ResponseEntity.ok().body(modelMapper.map(eventService.updateEvent(event), EventDTO.class));
            } else {
                throw new IOException(String.format("Event with %s id does not exist", eventId));
            }
        } catch (IOException error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(500).body(
                    new ErrorMessageManager(ServerErrorMsg, error.getMessage()));
        }
    }

    @PutMapping("/remove-one/{galleryId}/{galleryImageIndex}")
    public  ResponseEntity<?> removeOneImage(
            @PathVariable(value = "galleryId") long galleryId,
            @RequestBody GalleryDto mutatedGallery) {
        try {
            Gallery gallery = galleryRepository.findById(galleryId);
            if (gallery != null) {
                galleryCRUDService.updateGallery( modelMapper.createTypeMap(  ) );
                /* typeMap.addMapping(src -> src.getPerson().getFirstName(), (dest, v) -> dest.getCustomer().setName(v));*/
                return ResponseEntity.ok().body("val");
            } else {
                throw new IOException(String.format("Gallery with id %s does not exist", galleryId));
            }
        } catch (IOException error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(500).body(new ErrorMessageManager(ServerErrorMsg, error.toString()));
        }
    }

}