package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.FeedbackCreateDTO;
import io.softserve.goadventures.dto.FeedbackDTO;
import io.softserve.goadventures.errors.ErrorMessageManager;
import io.softserve.goadventures.errors.UserNotFoundException;
import io.softserve.goadventures.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@CrossOrigin
@RestController()
@RequestMapping("feedback")
public class FeedbackController {
  @Autowired
  private FeedbackService feedbackService;

  private final ErrorMessageManager errorMessageManager = new ErrorMessageManager();

  @GetMapping("/get-feedback/{eventId}")
  public ResponseEntity<?> getFeedback(@PathVariable("eventId") int eventId) {
    try {
      Set<FeedbackDTO> eventFeedback = feedbackService.getAllEventFeedback(eventId);
      if(eventFeedback != null) {
        return ResponseEntity.ok(eventFeedback);
      } else {
        throw new IOException("Event feedback not founded");
      }
    } catch (IOException | NullPointerException error) {
      if ( error instanceof IOException ) {
        return ResponseEntity.status(404).body(new ErrorMessageManager(
                (error).getMessage(), null));
      } else {
        return ResponseEntity.status(500).body(new ErrorMessageManager(
                (error).getMessage(), null));
      }
    }
  }

  @PostMapping("/add-feedback/{eventId}")
    public ResponseEntity<?> addFeedback(@RequestHeader(value = "Authorization") String token,
                                         @RequestBody FeedbackCreateDTO feedbackDTO) {
      try {
        FeedbackDTO newFeedback = feedbackService.addFeedbackToEvent(token, feedbackDTO);
        if(newFeedback != null) {
          return ResponseEntity.ok(newFeedback);
        } else {
          throw new IOException("Server error, try again later");
        }
      } catch (IOException | UserNotFoundException error) {
        if (error instanceof UserNotFoundException) {
          return ResponseEntity.status(404).body(
                  errorMessageManager.initError((error).getMessage(), null)
          );
        } else {
          return ResponseEntity.status(500).body(
                  errorMessageManager.initError(error.getMessage(), null));
        }
      }
    }

}
