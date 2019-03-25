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
        Long count = categoryRepository.countByCategoryName("Winter");
        if (count == 0)
        this.categoryRepository.save(new Category(1, "Winter", null));
        count = categoryRepository.countByCategoryName("Summer");
        if (count == 0)
            this.categoryRepository.save(new Category(2, "Summer", null));
        count = categoryRepository.countByCategoryName("Spring");
        if (count == 0)
            this.categoryRepository.save(new Category(3, "Spring", null));
        count = categoryRepository.countByCategoryName("Autumn");
        if (count == 0)
            this.categoryRepository.save(new Category(4, "Autumn", null));
        count = userRepository.countByFullname("Josh");
        if (count == 0)
        this.userRepository.save(new User("Josh","email@gmail.com","password"));
        User user = userService.getUserByEmail("email@gmail.com");
        user.setStatusId(UserStatus.ACTIVE.getUserStatus());
        this.userService.updateUser(user);
    }
}
