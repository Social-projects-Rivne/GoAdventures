package io.softserve.goadventures.services;

import io.softserve.goadventures.models.Gallery;
import io.softserve.goadventures.repositories.GalleryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GalleryCRUDServiceTest {

    @InjectMocks
    GalleryCRUDService galleryCRUDServiceMock;

    @Mock
    GalleryRepository galleryRepositoryMock;

    private static final int eventId = 1;
    private Gallery gallery = new Gallery();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        gallery.setId(eventId);
    }

    @Test
    public void updateGallery_Test() throws Exception{
        when(galleryRepositoryMock.save(gallery)).thenReturn(gallery);

        Gallery addGallery = galleryCRUDServiceMock.updateGallery(gallery);

        assertNotNull(addGallery);
        assertEquals(eventId, addGallery.getId());
        verify(galleryRepositoryMock, times(1)).save(gallery);
    }
}
