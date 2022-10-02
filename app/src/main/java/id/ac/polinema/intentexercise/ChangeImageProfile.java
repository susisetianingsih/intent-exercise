package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.UriMatcher;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ChangeImageProfile extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final String TAG = ChangeImageProfile.class.getCanonicalName();
    private Button btn_edit;
    private ImageView profile;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_image_profile);
        profile = (ImageView) findViewById(R.id.changeprofile);
        btn_edit = (Button) findViewById(R.id.btn_profile);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            int res_image = bundle.getInt("image");
            profile.setImageResource(res_image);
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });
    }

    private void onActivityForResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancel input image", Toast.LENGTH_SHORT).show();
            return;
        } else if (requestCode == GALLERY_REQUEST_CODE){
            if (data != null){
                try {
                    Uri imgUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imgUri);
                    profile.setImageBitmap(bitmap);
//                    profile.setImageURI(imgUri);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}