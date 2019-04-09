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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
        // Create mapping for Event -> Gallery
//        PropertyMap<Event, EventDTO> galleryMap = new PropertyMap<>() {
//            protected void configure() {
//                map().setGallery(source.getGallery().getId());
//            }
//        };
//        modelMapper.addMappings(galleryMap);
    }

    /**
     * This method are for develop purpose only
     *
     * @return Iterable<Gallery>
     */
    @GetMapping("/all")
    public Iterable<Gallery> getAllGalleries() {
        return galleryRepository.findAll();
    }

    @PostMapping("/add-new/{eventId}")
    public ResponseEntity<?> addNewGallery(
            @PathVariable("eventId") int eventId, @RequestParam("images") MultipartFile[] images) {
        try {
            Event event = eventService.getEventById(eventId);
            // Remove old gallery if such exist
            Gallery galleryStatus = event.getGallery();
                    if(galleryStatus != null) {
                        galleryStatus.setIsDeleted(true);
                        galleryStatus.setEventId(null);
                    }
            if (event != null) {
                Set<String> imageUrls = new HashSet<>();
                String newFileName;
                String fileUrl;
                for (MultipartFile uploadedImage : images) {
                    newFileName = fileStorageService.storeFile(uploadedImage);
                    fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("event/gallery/galleries/")
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


    @GetMapping("/galleries/{fileName:.+}")
    public ResponseEntity<?> getImages(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName); //find img in storage
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            return ResponseEntity.status(404).body(new ErrorMessageManager
                    ("File's content type is unknown", ex.getMessage()));
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


    @GetMapping("get/{galleryId}")
    public  ResponseEntity<?> getGallery(@PathVariable(value = "galleryId") int galleryId) {
        try {
            Gallery gallery = galleryRepository.findById(galleryId);
            if(gallery != null) {
                return ResponseEntity.ok().body(modelMapper.map(gallery, GalleryDto.class));
            } else  {
                throw new IOException("Gallery not founded");
            }
        } catch (IOException err) {
            logger.info(err.getMessage());
            return ResponseEntity.status(404).body(new ErrorMessageManager(
                    "Gallery doesn't exist", err.getMessage()));
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

    @PutMapping("/remove/{galleryId}")
    public  ResponseEntity<?> removeOneImage(
            @PathVariable(value = "galleryId") int galleryId,
            @RequestBody GalleryDto mutatedGallery) {
        try {

            Gallery gallery = galleryRepository.findById(galleryId);

            if (gallery != null) {
               modelMapper.map(mutatedGallery, gallery);
               logger.info("Gallery dto" + mutatedGallery.getEventId());
               logger.info("Gallery event id" + gallery.getEventId());
                return ResponseEntity.ok().body(modelMapper.map(
                        galleryCRUDService.updateGallery( gallery ), GalleryDto.class));
            } else {
                throw new IOException(String.format("Gallery with id %s does not exist", galleryId));
            }
        } catch (IOException error) {
            logger.error(error.getMessage());
            return ResponseEntity.status(500).body(new ErrorMessageManager(ServerErrorMsg, error.toString()));
        }
    }

}