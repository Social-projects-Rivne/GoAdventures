package io.softserve.goadventures.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
  private long id;
  private UserFeedbackDTO userId;
  private int eventId;
  private String comment;
}
