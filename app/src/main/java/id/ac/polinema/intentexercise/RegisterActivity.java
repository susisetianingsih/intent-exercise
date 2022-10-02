package id.ac.polinema.intentexercise;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.android.gms.cast.framework.media.ImagePicker;
        import com.google.android.material.textfield.TextInputEditText;

        import java.io.ByteArrayInputStream;
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
    private CircleImageView avatar, change_Avatar;
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private Bitmap bitmap;
    private Uri imgUri = null;
    private boolean change_img = false;

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

        photo_profil.setOnClickListener(new View.OnClickListener() {
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

                if (!change_img){
                    Toast.makeText(RegisterActivity.this, "Image must be change", Toast.LENGTH_SHORT).show();
                } else if (fullname.isEmpty()){
                    text_fullname.setError("Fullname required");
                } else if (email.isEmpty()) {
                    text_email.setError("Email required");
                } else if (password.isEmpty()) {
                    text_password.setError("Password required");
                } else if (con_password.isEmpty()) {
                    text_confirm_password.setError("Confirm Password required");
                } else if (homepage.isEmpty()) {
                    text_homepage.setError("Homepage required");
                } else if (about.isEmpty()) {
                    text_about.setError("About required");
                } else if (!password.equals(con_password)) {
                    Toast.makeText(RegisterActivity.this, "Confirm password is not correct", Toast.LENGTH_SHORT).show();
                } else {
                    String image = imgUri.toString();
                    pindah.putExtra("image", image);
                    pindah.putExtra("fullname", fullname);
                    pindah.putExtra("email", email);
                    pindah.putExtra("password", password);
                    pindah.putExtra("con_password", con_password);
                    pindah.putExtra("homepage", homepage);
                    pindah.putExtra("about", about);
                    startActivity(pindah);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancel input image", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (requestCode == GALLERY_REQUEST_CODE){
                if (data != null){
                    try {
                        change_img = true;
                        imgUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imgUri);
                        avatar.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }
}

