package io.softserve.goadventures.controllers;

import io.softserve.goadventures.dto.FeedbackCreateDTO;
import io.softserve.goadventures.dto.FeedbackDTO;
import io.softserve.goadventures.errors.ErrorMessageManager;
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
    Set<FeedbackDTO> eventFeedback = feedbackService.getAllEventFeedback(eventId);
    return ResponseEntity.ok(eventFeedback);
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

}
