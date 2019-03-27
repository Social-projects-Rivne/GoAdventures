package io.softserve.goadventures;

import io.softserve.goadventures.auth.enums.UserStatus;
import io.softserve.goadventures.event.category.Category;
import io.softserve.goadventures.event.repository.CategoryRepository;
import io.softserve.goadventures.event.repository.EventRepository;
import io.softserve.goadventures.user.model.User;
import io.softserve.goadventures.user.service.UserService;
import io.softserve.goadventures.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Autowired
    public DatabaseLoader(UserRepository userRepository, EventRepository eventRepository, CategoryRepository categoryRepository, UserService userService){
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... strings) throws Exception{
        Long count = categoryRepository.countByCategoryName("Skateboarding");
        if (count == 0)
        this.categoryRepository.save(new Category(1, "Skateboarding", null));
        count = categoryRepository.countByCategoryName("Motocross");
        if (count == 0)
            this.categoryRepository.save(new Category(2, "Motocross", null));
        count = categoryRepository.countByCategoryName("Mountain biking");
        if (count == 0)
            this.categoryRepository.save(new Category(3, "Mountain biking", null));
        count = categoryRepository.countByCategoryName("Rock climbing");
        if (count == 0)
            this.categoryRepository.save(new Category(4, "Rock climbing", null));
        count = categoryRepository.countByCategoryName("Parkour");
        if (count == 0)
            this.categoryRepository.save(new Category(5, "Parkour", null));
        count = categoryRepository.countByCategoryName("Surfing");
        if (count == 0)
            this.categoryRepository.save(new Category(6, "Surfing", null));
        count = categoryRepository.countByCategoryName("Kayaking");
        if (count == 0)
            this.categoryRepository.save(new Category(7, "Kayaking", null));
        count = categoryRepository.countByCategoryName("Bungee jumpnig");
        if (count == 0)
            this.categoryRepository.save(new Category(8, "Bungee jumping", null));
        count = categoryRepository.countByCategoryName("Sky diving");
        if (count == 0)
            this.categoryRepository.save(new Category(9, "Sky diving", null));
        count = categoryRepository.countByCategoryName("Snowboarding");
        if (count == 0)
            this.categoryRepository.save(new Category(10, "Snowboarding", null));
        count = categoryRepository.countByCategoryName("Other");
        if (count == 0)
            this.categoryRepository.save(new Category(11, "Other", null));
        count = userRepository.countByFullname("Ioann");
        if (count == 0)
        this.userRepository.save(new User("Ioann","email@gmail.com","password"));
        User user = userService.getUserByEmail("email@gmail.com");
        user.setStatusId(UserStatus.ACTIVE.getUserStatus());
        this.userService.updateUser(user);
    }
}
