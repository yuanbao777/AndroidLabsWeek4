package algonquin.cst2335.guo00093;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import java.io.File;

import algonquin.cst2335.guo00093.databinding.ActivitySecondBinding;
import android.content.Context;
import android.graphics.BitmapFactory;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;
    private ActivityResultLauncher<Intent> cameraResult;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedPhoneNumber = prefs.getString("SavedPhoneNumber", "");
        binding.textPhone.setText(savedPhoneNumber);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        binding.textView3.setText("Welcome back " + emailAddress);
        int age = fromPrevious.getIntExtra("Age", 0);
        String name = fromPrevious.getStringExtra("Name");
        String pCode = fromPrevious.getStringExtra("PostalCode");

        File file = new File(getFilesDir(), "Picture.png");
        if(file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            binding.profileImage.setImageBitmap(theImage);
        }
        cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            binding.profileImage.setImageBitmap(thumbnail);

                            try (FileOutputStream fOut = openFileOutput("Picture.png", MODE_PRIVATE)) {
                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) { // Handle IO Exceptions
                                e.printStackTrace();
                            }
                        }
                    }
                });

        binding.callButton.setOnClickListener(click -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            String phoneNumber = binding.textPhone.getText().toString();
            call.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(call);
        });

        binding.changeButton.setOnClickListener(click -> {
            cameraResult.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        });
    }

    @Override

    protected void onPause() {
        super.onPause();

        String phoneNumber = binding.textPhone.getText().toString();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("SavedPhoneNumber", phoneNumber);
        editor.apply();
    }


}