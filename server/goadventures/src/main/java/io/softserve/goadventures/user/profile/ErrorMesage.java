package io.softserve.goadventures.user.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ErrorMesage {
    private String ErrorMesage = "Current password is wrong!";
}
