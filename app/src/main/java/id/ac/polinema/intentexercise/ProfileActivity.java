package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {
    private TextView lbl_about, lbl_fullname, lbl_email, lbl_homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        lbl_about = findViewById(R.id.label_about);
        lbl_fullname = findViewById(R.id.label_fullname);
        lbl_email = findViewById(R.id.label_email);
        lbl_homepage = findViewById(R.id.label_homepage);

        String labelAbout = getIntent().getExtras().getString("about");
        String labelFullname = getIntent().getExtras().getString("fullname");
        String labelEmail = getIntent().getExtras().getString("email");
        String labelHomepage = getIntent().getExtras().getString("homepage");

        lbl_about.setText(labelAbout);
        lbl_fullname.setText(labelFullname);
        lbl_email.setText(labelEmail);
        lbl_homepage.setText(labelHomepage);
    }
}