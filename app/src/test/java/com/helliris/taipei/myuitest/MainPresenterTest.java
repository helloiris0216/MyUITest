package com.helliris.taipei.myuitest;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class MainPresenterTest {

    private MainContract.Presenter iPresenter;

    @Mock
    private MainContract.View iView;


    @Before
    public void setUp()  {

        MockitoAnnotations.openMocks(this);
        iPresenter = new MainPresenter(iView);

    }

    @Test
    public void testRequestUserName() {

        // Arrange

        // Act
        iPresenter.requestUserName();

        // Assert
        verify(iView).receivedUserName(any());

    }

}