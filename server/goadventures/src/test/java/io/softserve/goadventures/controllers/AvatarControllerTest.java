package io.softserve.goadventures.controllers;

import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.FileStorageService;
import io.softserve.goadventures.services.UserService;
import org.apache.catalina.webresources.FileResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AvatarControllerTest {

    @InjectMocks
    AvatarController avatarController;

    @Mock
    UserService userService;

    @Mock
    FileStorageService fileStorageService;

    private MockMvc mockMvc;
    private InputStream uploadStream;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private static final MediaType TEXT_PLAIN = new MediaType(MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("ISO-8859-1"));

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        String uploadPath = folder.getRoot().getAbsolutePath();
        mockMvc = MockMvcBuilders.standaloneSetup(avatarController)
                .build();
        uploadStream = avatarController.getClass().getClassLoader().getResourceAsStream("");
    }

    @Test
    public void uploadAvatar() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "", "multipart/form-data", uploadStream);
        assert uploadStream != null;

        User user = new User();

        when(userService.getUserByEmail("email")).thenReturn(user);
        when(fileStorageService.checkFileType(file)).thenReturn(true);
        when(fileStorageService.checkFileSize(file)).thenReturn(true);

        mockMvc.perform(multipart("/uploadAvatar")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", "scxvbnew"))
                .andExpect(status().isOk());
    }
}
