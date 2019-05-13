package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.FeedbackCreateDTO;
import io.softserve.goadventures.dto.FeedbackDTO;
import io.softserve.goadventures.errors.ErrorMessageManager;
import io.softserve.goadventures.services.FeedbackService;
import io.softserve.goadventures.services.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin
@RestController()
@RequestMapping("feedback")
public class FeedbackController {
    private final Logger logger = LoggerFactory.getLogger(FeedbackController.class);
    private final FeedbackService feedbackService;
    private final JWTService jwtService;
    private final ErrorMessageManager errorMessageManager = new ErrorMessageManager();
    @Autowired
    public FeedbackController(FeedbackService feedbackService,
                              JWTService jwtService) {
        this.feedbackService = feedbackService;
        this.jwtService = jwtService;


    }

    @GetMapping("/get-feedback/{eventId}")
    public ResponseEntity<?> getFeedback(@PathVariable("eventId") int eventId) {
        Slice<FeedbackDTO> eventFeedback = feedbackService.getAllEventFeedback(eventId);
        if(eventFeedback != null) {
            return ResponseEntity.ok(eventFeedback);
        } else {
            return new ResponseEntity<>(errorMessageManager.initError(
                    "There is no feedback to your event", ""), HttpStatus.OK);
        }
    }

    @PostMapping("/add-feedback/")
    public ResponseEntity<?> addFeedback(@RequestHeader(value = "Authorization") String token,
                                         @RequestBody FeedbackCreateDTO feedbackDTO) {
        try {
            if (feedbackDTO.getEventId() == 0 || (feedbackDTO.getComment() == null)) {
                return ResponseEntity.badRequest().body(
                        errorMessageManager.initError("Invalid data provided", null));
            } else {
                FeedbackDTO newFeedback = feedbackService.addFeedbackToEvent(token, feedbackDTO);
                if (newFeedback != null) {
                    return ResponseEntity.ok(newFeedback);
                } else {
                    throw new IOException("Server error, try again later");
                }
            }
        } catch (IOException error) {
            return ResponseEntity.status(500).body(
                    errorMessageManager.initError(error.getMessage(), null));
        }
    }


    @DeleteMapping("remove-feedback/{feedbackId}")
    public ResponseEntity<?> removeFeedback(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable("feedbackId") long feedbackId) {
       String userEmail = jwtService.parseToken(token);
       if (userEmail != null) {
           if(feedbackService.removeFeedback(feedbackId, userEmail)) {
               return new ResponseEntity<>(HttpStatus.OK);
           } else  {
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
       }
       return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
