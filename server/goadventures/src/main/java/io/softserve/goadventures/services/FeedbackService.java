package io.softserve.goadventures.services;

import io.softserve.goadventures.dto.FeedbackCreateDTO;
import io.softserve.goadventures.dto.FeedbackDTO;
import io.softserve.goadventures.models.Feedback;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.FeedbackRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FeedbackService {
  private Logger logger = LoggerFactory.getLogger(FeedbackService.class);
  private static final ModelMapper modelMapper = new ModelMapper();
  private final JWTService jwtService;
  private final UserService userService;
  private final EventService eventService;
  private final FeedbackRepository feedbackRepository;

  @Autowired
  public FeedbackService(
                         JWTService jwtService,
                         UserService userService,
                         EventService eventService,
                         FeedbackRepository feedbackRepository) {
    this.jwtService = jwtService;
    this.userService = userService;
    this.eventService = eventService;
    this.feedbackRepository = feedbackRepository;

    PropertyMap<FeedbackCreateDTO, Feedback> feedbackTypeMap = new PropertyMap<>() {
      protected void configure() {
        skip().setId(0);
        skip().setUserId(null);
      }
    };
    modelMapper.addMappings(feedbackTypeMap);

  }

  private static Set<FeedbackDTO> convertToDto(Set<Feedback> feedback) {
    return modelMapper.map(feedback, new TypeToken<Set<FeedbackDTO>>() {}.getType());
  }


  public Set<FeedbackDTO> getAllEventFeedback(int eventId) {
    Set<Feedback> feedback = feedbackRepository.findByEventId(eventId);
    return convertToDto(feedback);
  }

  public FeedbackDTO addFeedbackToEvent(String token, FeedbackCreateDTO feedbackCreateDTO) {
      User user = userService.getUserByEmail(jwtService.parseToken(token));
      if (user != null) {
        Feedback feedback = modelMapper.map(feedbackCreateDTO, Feedback.class);
        feedback.setUserId(user);
        return modelMapper.map(feedbackRepository.save(feedback), FeedbackDTO.class);
      }
    return null;
  }

}
