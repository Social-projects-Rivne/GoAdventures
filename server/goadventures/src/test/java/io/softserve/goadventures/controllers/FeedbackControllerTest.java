package io.softserve.goadventures.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.softserve.goadventures.dto.FeedbackDTO;
import io.softserve.goadventures.services.FeedbackService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FeedbackControllerTest {

    @InjectMocks
    FeedbackController feedbackControllerMock;

    @Mock
    FeedbackService feedbackServiceMock;

    private MockMvc mockMvc;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private static final MediaType TEXT_PLAIN = new MediaType(MediaType.TEXT_PLAIN.getType(), MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("ISO-8859-1"));


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackControllerMock)
                .build();
    }

    @Test
    public void getFeedback_Test() throws Exception{
        List<FeedbackDTO> eventFeedBack = new ArrayList<>();
        FeedbackDTO feed = new FeedbackDTO();
        feed.setId(1);
        eventFeedBack.add(feed);
        Page<FeedbackDTO> feedbackSlice = new PageImpl<FeedbackDTO>(eventFeedBack);
        int eventId = 1;

        when(feedbackServiceMock.getAllEventFeedback(eventId)).thenReturn(feedbackSlice);

        mockMvc.perform(get("/feedback/get-feedback/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].id").value("1"));
    }

    @Test
    public void addFeedback_eventIdNull_Test() throws Exception{
        Slice<FeedbackDTO> eventFeedBack;
        FeedbackDTO feedDTO = new FeedbackDTO();
        feedDTO.setId(0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(feedDTO);

        mockMvc.perform(post("/feedback/add-feedback/")
                .header("Authorization", "ghjgjh")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.publicError").value("Invalid data provided"));
    }


}
