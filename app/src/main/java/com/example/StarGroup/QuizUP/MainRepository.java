package com.example.StarGroup.QuizUP;

// In a real app, this might interact with SharedPreferences, Database, or Network.
// For MainActivity's current needs, it will just hold the name in memory as an example.
public class MainRepository {

    private String userName;

    // Simulates fetching user name (could be from SharedPreferences in a real case)
    public String getUserName() {
        return userName;
    }

    // Simulates saving user name (could be to SharedPreferences)
    public void updateUserName(String name) {
        this.userName = name;
        // In a real app, you might save it to SharedPreferences here or perform other actions.
    }

    // Optional: A method to clear it if needed
    public void clearUserName() {
        this.userName = null;
    }
}
