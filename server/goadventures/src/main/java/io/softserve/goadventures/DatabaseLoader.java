package io.softserve.goadventures;
// TODO remove unused imports
import io.softserve.goadventures.controllers.EventController;
import io.softserve.goadventures.dto.UserAuthDto;
import io.softserve.goadventures.enums.EventStatus;
import io.softserve.goadventures.errors.UserNotFoundException;
import io.softserve.goadventures.models.Category;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.repositories.CategoryRepository;
import io.softserve.goadventures.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final EventController eventController;

    @Autowired
    public DatabaseLoader(CategoryRepository categoryRepository, UserService userService,
                          EventController eventController){
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.eventController = eventController;
    }

    @Override
    public void run(String... strings) throws Exception{
        Set<String> categories = new HashSet<>();
        categories.add("Skateboarding");
        categories.add("Motocross");
        categories.add("Mountain biking");
        categories.add("Rock climbing");
        categories.add("Parkour");
        categories.add("Surfing");
        categories.add("Kayaking");
        categories.add("Bungee jumpnig");
        categories.add("Sky diving");
        categories.add("Snowboarding");
        categories.add("Other");

        for (String s : categories) {
            if (this.categoryRepository.countByCategoryName(s) == 0) {
                this.categoryRepository.save(new Category(s));
            }
        }

        Set<UserAuthDto> users = new HashSet<>();
        users.add(new UserAuthDto("hirotaka@scrum.com", "password", "Hirotaka Takeuchi"));
        users.add(new UserAuthDto("nonaka@scrum.com", "password", "Ikujiro Nonaka"));
        users.add(new UserAuthDto("eragon@gmail.com", "password", "Christopher Paolini"));
        users.add(new UserAuthDto("lord.of.the.rings@gmail.com", "password", "John R.R. Tolkien"));

        for (UserAuthDto authDto : users) {
            userService.addUser(authDto);
            userService.confirmUser(authDto.getEmail());
        }
    }

}
