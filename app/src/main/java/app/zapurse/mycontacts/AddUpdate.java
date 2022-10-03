package app.zapurse.mycontacts;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import app.zapurse.mycontacts.databinding.ActivityAddUpdateBinding;
import app.zapurse.mycontacts.helper.DBhelper;

public class AddUpdate extends AppCompatActivity {

    ActivityAddUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        binding = ActivityAddUpdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.submitButton.setOnClickListener(v -> {
            String name = binding.textInputName.getText().toString();
            String email = binding.textInputeEmail.getText().toString();
            String phone = binding.textInputMobile.getText().toString();
            if (name.isEmpty() ||
                    email.isEmpty() ||
                    phone.isEmpty()) {
                Toast.makeText(AddUpdate.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                processInsert(name,email,phone);
            }
        });
    }

    private void processInsert(String name, String mobile, String email) {
        String res = new DBhelper(this).insertContact(name, mobile, email);
        binding.textInputMobile.setText("");
        binding.textInputeEmail.setText("");
        binding.textInputName.setText("");
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
        finish();
    }
}