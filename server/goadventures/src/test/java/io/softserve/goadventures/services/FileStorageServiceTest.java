package io.softserve.goadventures.services;

import io.softserve.goadventures.configurations.FileStorageProperties;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.repositories.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
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
        user = new User();
        user.setEmail("service@test.com");
        user.setFullname("Junit Mockito");
        user.setPassword("$2a$10$WOqCikzFnLWdm9u67yrfGOUqjCWx7Z9kvjlqY7rqQZgEQ/SqEBccu");
    }

    @Test
    public void checkFileType_Test() throws Exception{

    }

    @Test
    public void storeFile(){

    }
}
