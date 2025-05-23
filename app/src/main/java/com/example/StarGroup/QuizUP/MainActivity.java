package com.example.StarGroup.QuizUP;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
// ViewModel related imports
import androidx.lifecycle.ViewModelProvider;

import com.example.StarGroup.QuizUP.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel; // Declare MainViewModel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Optional: If you want to observe userName for changes (e.g., to log or pre-fill)
        // viewModel.userName.observe(this, new Observer<String>() {
        //     @Override
        //     public void onChanged(String s) {
        //         // This would be used if MainActivity itself displayed the name
        //         // For now, we just set it in the ViewModel on button click
        //     }
        // });


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameFromInput = binding.editName.getText().toString();
                viewModel.updateUserName(nameFromInput); // Update ViewModel

                // Get name from ViewModel's LiveData to pass in Intent
                // This demonstrates fetching the "single source of truth" from ViewModel
                String nameToSend = viewModel.userName.getValue();
                if (nameToSend == null) { // Fallback if LiveData hasn't updated yet or is null
                    nameToSend = nameFromInput;
                }

                Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                intent.putExtra("myname", nameToSend);
                startActivity(intent);
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeveloperActivity.class);
                startActivity(intent);
            }
        });
    }
}
