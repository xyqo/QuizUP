package com.example.StarGroup.QuizUP;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final MainRepository repository; // Add repository instance

    private final MutableLiveData<String> _userName = new MutableLiveData<>();
    public LiveData<String> userName = _userName;

    // Constructor to instantiate the repository
    public MainViewModel() {
        repository = new MainRepository();
        // Optionally, load initial name from repository if it were persisted
        // String currentName = repository.getUserName();
        // if (currentName != null) {
        //     _userName.setValue(currentName);
        // }
    }

    public void updateUserName(String name) {
        repository.updateUserName(name); // Delegate to repository
        _userName.setValue(name); // Update LiveData for observers
    }

    // Optional: A method to get the name directly from repository if needed for non-observer cases
    // public String getCurrentUserNameFromRepository() {
    //     return repository.getUserName();
    // }
}
