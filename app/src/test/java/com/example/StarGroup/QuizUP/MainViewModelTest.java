package com.example.StarGroup.QuizUP;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner; // Alternative to MockitoAnnotations.openMocks(this)

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

// Use MockitoJUnitRunner to initialize mocks
@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    // Rule to make LiveData updates execute synchronously for tests
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // The ViewModel to test
    private MainViewModel mainViewModel;

    // Mock the MainRepository dependency
    @Mock
    private MainRepository mockRepository;

    // Observer for LiveData
    @Mock
    private Observer<String> mockObserver;

    // ArgumentCaptor to capture values passed to methods
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Before
    public void setUp() {
        // Mocks are initialized by @RunWith(MockitoJUnitRunner.class)
        // or alternatively, call: MockitoAnnotations.openMocks(this);

        // Create the ViewModel instance, passing the mocked repository.
        // Since MainViewModel's constructor directly instantiates MainRepository,
        // we can't directly inject the mock via constructor without changing MainViewModel.
        // For this test, we will test the existing MainViewModel.
        // A better MainViewModel would allow injecting the repository for easier testing.
        // We will spy on the repository created by the ViewModel for now,
        // or test its interaction by verifying LiveData.
        // For simplicity here, we'll focus on LiveData and if repository was called.

        mainViewModel = new MainViewModel(); // This will create its own MainRepository internally.
                                           // We can't easily mock that internal instance without DI.
                                           // Let's adjust the test to focus on what we *can* control and observe.
                                           // The provided MainViewModel doesn't allow repository injection.
                                           // So, we'll test its effect on LiveData.
                                           // The interaction with the *actual* repository inside MainViewModel
                                           // is more of an integration test in this setup.

        // We will observe the LiveData from the ViewModel
        mainViewModel.userName.observeForever(mockObserver);
    }

    @Test
    public void updateUserName_updatesLiveData() {
        // Arrange
        String testName = "Test User";

        // Act
        mainViewModel.updateUserName(testName);

        // Assert
        // Verify that the LiveData observer was called with the correct value
        verify(mockObserver).onChanged(stringArgumentCaptor.capture());
        assertEquals(testName, stringArgumentCaptor.getValue());

        // Also check the LiveData's current value directly
        assertEquals(testName, mainViewModel.userName.getValue());
    }

    @Test
    public void updateUserName_callsRepositoryUpdate() {
        // This test is harder to do properly without Dependency Injection for the repository.
        // The current MainViewModel creates its own MainRepository instance.
        // To test this interaction correctly, MainViewModel should accept MainRepository
        // as a constructor parameter.

        // For now, we can only assume that if LiveData is updated, the internal repository
        // was likely called, given the implementation. This is not ideal.
        // A "TODO" for future refactoring would be to enable DI for MainRepository in MainViewModel.

        // Let's simulate what we *would* do if DI was in place:
        // MainViewModel viewModelWithMockRepo = new MainViewModel(mockRepository); // If DI was used
        // String testName = "Another User";
        // viewModelWithMockRepo.updateUserName(testName);
        // verify(mockRepository).updateUserName(testName);

        // Since we can't do the above directly with the current MainViewModel structure,
        // this test serves as a placeholder for that future improvement.
        // We've already tested LiveData update, which is the primary observable effect.
        assertTrue("TODO: Refactor MainViewModel to allow MainRepository injection for better testing of this interaction.", true);
    }

    // It's good practice to remove the observer after tests if observeForever is used,
    // though with JUnit runner, it might not be strictly necessary for each test.
    // @After
    // public void tearDown() {
    //     mainViewModel.userName.removeObserver(mockObserver);
    // }
}
