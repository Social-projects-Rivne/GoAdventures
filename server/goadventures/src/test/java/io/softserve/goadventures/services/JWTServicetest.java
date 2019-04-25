package io.softserve.goadventures.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class JWTServicetest {

    @InjectMocks
    JWTService jwtServiceMock;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void parseToken_Test(){

    }

    @Test
    public void createToken(){

    }

    @Test
    public void refreshToken_Test(){

    }
}
