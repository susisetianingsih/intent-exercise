package id.ac.polinema.intentexercise;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.android.material.textfield.TextInputEditText;

        import java.io.IOException;

        import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private ImageView photo_profil;
    private TextInputEditText text_fullname;
    private TextInputEditText text_email;
    private TextInputEditText text_password;
    private TextInputEditText text_confirm_password;
    private TextInputEditText text_homepage;
    private TextInputEditText text_about;
    private Button btn_ok;
    private CircleImageView avatar;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final String TAG = ChangeImageProfile.class.getCanonicalName();
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        photo_profil = (ImageView) findViewById(R.id.imageView);
        text_fullname = (TextInputEditText) findViewById(R.id.text_fullname);
        text_email = (TextInputEditText) findViewById(R.id.text_email);
        text_password = (TextInputEditText) findViewById(R.id.text_password);
        text_confirm_password = (TextInputEditText) findViewById(R.id.text_confirm_password);
        text_homepage = (TextInputEditText) findViewById(R.id.text_homepage);
        text_about = (TextInputEditText) findViewById(R.id.text_about);
        btn_ok = findViewById(R.id.button_ok);
        avatar = findViewById(R.id.image_profile);

//        avatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent pindah = new Intent(RegisterActivity.this, ChangeImageProfile.class);
//                pindah.putExtra("image", R.drawable.profile_picture);
//                startActivity(pindah);
//            }
//        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(RegisterActivity.this, ProfileActivity.class);
                String fullname = text_fullname.getText().toString();
                String email = text_email.getText().toString();
                String password = text_password.getText().toString();
                String con_password = text_confirm_password.getText().toString();
                String homepage = text_homepage.getText().toString();
                String about = text_about.getText().toString();


                pindah.putExtra("fullname", fullname);
                pindah.putExtra("email", email);
                pindah.putExtra("password", password);
                pindah.putExtra("con_password", con_password);
                pindah.putExtra("homepage", homepage);
                pindah.putExtra("about", about);

                if (fullname.isEmpty()){
                    text_fullname.setError("Fullname diperlukan");
                } else if (email.isEmpty()) {
                    text_email.setError("Email diperlukan");
                } else if (password.isEmpty()) {
                    text_password.setError("Password diperlukan");
                } else if (con_password.isEmpty()) {
                    text_confirm_password.setError("Confirm Password diperlukan");
                } else if (homepage.isEmpty()) {
                    text_homepage.setError("Homepage diperlukan");
                } else if (about.isEmpty()) {
                    text_about.setError("About diperlukan");
                } else if (!password.equals(con_password)) {
                    Toast.makeText(RegisterActivity.this, "Confirm password is not correct", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(pindah);
                }
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
                    avatar.setImageBitmap(bitmap);
//                    profile.setImageURI(imgUri);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}

