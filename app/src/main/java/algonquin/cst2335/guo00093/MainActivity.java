package algonquin.cst2335.guo00093;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import algonquin.cst2335.guo00093.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        binding.emailText.setText(emailAddress);

        binding.loginButton.setOnClickListener(click -> {
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);

            String emailInput = binding.emailText.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", emailInput);
            editor.apply();

            nextPage.putExtra("EmailAddress", emailInput);
            nextPage.putExtra("Age", 30.50);
            String name = "Yuan";
            nextPage.putExtra("Name", name);
            String pCode = "A0B 1C2";
            nextPage.putExtra("PostalCode", pCode);

            startActivity(nextPage);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("MainActivity", "In onStart()- The application is now visible on screen");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "In onResume()- The application is now responding to user input");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "In onPause()- The application no longer responds to user input");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("MainActivity", "In onStop()- The application is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity", "In onDestroy()- Any memory used by the application is freed");
    }
}