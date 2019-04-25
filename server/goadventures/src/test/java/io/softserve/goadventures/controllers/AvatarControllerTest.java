package io.softserve.goadventures.controllers;

import io.softserve.goadventures.errors.FileSizeException;
import io.softserve.goadventures.errors.WrongImageTypeException;
import io.softserve.goadventures.models.User;
import io.softserve.goadventures.services.FileStorageService;
import io.softserve.goadventures.services.JWTService;
import io.softserve.goadventures.services.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AvatarControllerTest {

    @InjectMocks
    AvatarController avatarControllerMock;

    @Mock
    UserService userServiceMock;

    @Mock
    JWTService jwtServiceMock;

    @Mock
    FileStorageService fileStorageServiceMock;

    @Mock
    HttpServletRequest httpServletRequestMock;

    @Mock
    Resource resourceMock;

    @Mock
    ServletContext servletContextMock;

    @Mock
    File fileMock;

    private MockMvc mockMvc;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private static final MediaType TEXT_PLAIN = new MediaType(MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("ISO-8859-1"));
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        String uploadPath = folder.getRoot().getAbsolutePath();
        mockMvc = MockMvcBuilders.standaloneSetup(avatarControllerMock)
                .build();
    }

    @Test
    public void uploadAvatar_ShouldReturnResponseEntityWithFileDownloadUri_Test() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("uploads/" + "maxresdefault.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg",
                "image/jpg", fileInputStream);//"test image content".getBytes());
        String token = "ds4567basbj/sasdas-";
        String email = "email";
        String fileName = "dsa324das.test";

        User user = new User();
        user.setEmail(email);

        when(jwtServiceMock.parseToken(token)).thenReturn(email);
        when(userServiceMock.getUserByEmail(email)).thenReturn(user);
        when(fileStorageServiceMock.checkFileType(file)).thenReturn(true);
        when(fileStorageServiceMock.checkFileSize(file)).thenReturn(true);
        when(fileStorageServiceMock.storeFile(file)).thenReturn(fileName);

        mockMvc.perform(multipart("/uploadAvatar")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Authorization", token))
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("http://localhost/downloadAvatar/" + fileName))
                .andExpect(status().isOk());
    }

    @Test
    public void uploadAvatar_WrongType_ShouldReturnErrorMessage_Test() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("uploads/" + "maxresdefault.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg",
                "image/jpg", fileInputStream);//"test image content".getBytes());
        String token = "ds4567basbj/sasdas-";

        when(fileStorageServiceMock.checkFileType(file)).thenReturn(false);
        WrongImageTypeException error = new WrongImageTypeException();

        mockMvc.perform(multipart("/uploadAvatar")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Authorization", token))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.publicError").value("Could not be uploaded, it is not an image!"))
                .andExpect(jsonPath("$.innerError").value(error.toString()))
                .andExpect(status().is(403));
    }

    @Test
    public void uploadAvatar_WrongSize_ShouldReturnErrorMessage_Test() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("uploads/" + "maxresdefault.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg",
                "image/jpg", fileInputStream);//"test image content".getBytes());
        String token = "ds4567basbj/sasdas-";

        when(fileStorageServiceMock.checkFileType(file)).thenReturn(true);
        when(fileStorageServiceMock.checkFileSize(file)).thenReturn(false);
        FileSizeException error = new FileSizeException();

        mockMvc.perform(multipart("/uploadAvatar")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Authorization", token))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.publicError").value("Maximum file size is 5mb!"))
                .andExpect(jsonPath("$.innerError").value(error.toString()))
                .andExpect(status().is(403));
    }

    @Test
    public void downloadAvatar() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("uploads/" + "maxresdefault.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg",
                "image/jpg", fileInputStream);//"test image content".getBytes());
        //assert uploadStream != null;
        String token = "dsfdsfds";
        String email = "email";
        String fileName = "dsadas.test";

        User user = new User();
        user.setEmail(email);

        Path fileStorageLocation;

        fileStorageLocation = Path.of("uri");
        Path filePath = fileStorageLocation.resolve(fileName).normalize();

        when(httpServletRequestMock.getServletContext()).thenReturn(servletContextMock);
        when(servletContextMock.getMimeType("dsf")).thenReturn("dsf");
        when(resourceMock.getFile()).thenReturn(fileMock);
        when(resourceMock.getFilename()).thenReturn("aaaa");
        when(fileMock.getAbsolutePath()).thenReturn("dsf");
        when(jwtServiceMock.parseToken(token)).thenReturn(email);
        when(userServiceMock.getUserByEmail(email)).thenReturn(user);
        when(fileStorageServiceMock.checkFileType(file)).thenReturn(true);
        when(fileStorageServiceMock.loadFileAsResource(fileName)).thenReturn(resourceMock);

    }
}
