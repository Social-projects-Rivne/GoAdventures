package io.softserve.goadventures.services;

import io.softserve.goadventures.configurations.FileStorageProperties;
import io.softserve.goadventures.models.Event;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;

public class FileStorageServiceTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    File fileMock;

    @Mock
    Path fileStorageLocation;

    @Mock
    FileStorageProperties fileStoragePropertiesMock;

    private static final int ID = 1;
    private static final String topic = "topic";
    private User user;

    @Before
    public void setUp() throws Exception {
        FileStorageService fileStorageServiceMock = new FileStorageService(fileStoragePropertiesMock);

        user = new User();
        user.setEmail("service@test.com");
        user.setFullname("Junit Mockito");
        user.setPassword("$2a$10$WOqCikzFnLWdm9u67yrfGOUqjCWx7Z9kvjlqY7rqQZgEQ/SqEBccu");
    }

    @Test
    public void checkFileType_Test() throws Exception{
        FileInputStream fileInputStream = new FileInputStream("uploads/" + "maxresdefault.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg",
                "image/jpg", fileInputStream);
        System.out.println(file.getContentType());
    }
}
