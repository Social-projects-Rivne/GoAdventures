package io.softserve.goadventures.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
  private String id;
  private String email;
  private String password;
  private String newPassword;
  private String userName;
  private String fullName;
}
