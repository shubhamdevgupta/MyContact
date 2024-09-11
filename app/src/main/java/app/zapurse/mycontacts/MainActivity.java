package app.zapurse.mycontacts;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.zapurse.mycontacts.adapter.UserAdapter;
import app.zapurse.mycontacts.databinding.ActivityMainBinding;
import app.zapurse.mycontacts.helper.DBhelper;
import app.zapurse.mycontacts.models.UserModel;

public class MainActivity extends AppCompatActivity implements UserItemEventListner {

    ActivityMainBinding binding;
    ArrayList<UserModel> userList;
    DBhelper dBhelper;
    UserAdapter adapter;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.floatingButoon.setOnClickListener(v -> {
            startActivity(new Intent(this, AddUpdate.class));
        });
    }

    private void showContacts() {
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleview.setHasFixedSize(true);
        dBhelper = new DBhelper(this);
        userList = dBhelper.showContact();

        if (userList.size() > 0) {
            adapter = new UserAdapter(userList, this);
            binding.recycleview.setAdapter(adapter);
            adapter.setupUserItemListner(this);
        } else {
            Toast.makeText(this, "Please Add some contact !!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        dBhelper = new DBhelper(this);
        showContacts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dBhelper != null)
            dBhelper.close();
    }

    @Override
    public void onDelete(UserModel model, int position) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to delete ").setPositiveButton("Yes", (dialog, which) -> {
            dBhelper.deleteContact(model.getId());
            userList.remove(position);
            adapter.notifyDataSetChanged();
        }).setNegativeButton("No", (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alert = builder.create();
        alert.setTitle("Delete " + model.getName());
        alert.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onEdit(UserModel model,int position) {
        EditText name, mobile, email;
        Button subBtn;
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.activity_add_update);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(false);

        dialog.show();
        name = dialog.findViewById(R.id.textInputName);
        name.setText(model.getName());

        mobile = dialog.findViewById(R.id.textInputMobile);
        mobile.setText(model.getMobile());

        email = dialog.findViewById(R.id.textInputeEmail);
        email.setText(model.getEmail());

        subBtn = dialog.findViewById(R.id.submit_button);
        subBtn.setText("Update");
        subBtn.setOnClickListener(v -> {
            if (name.getText().toString().isEmpty() ||
                    email.getText().toString().isEmpty() ||
                    mobile.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Fill all fields", Toast.LENGTH_SHORT).show();
            } else {
               dBhelper.updateContact(new UserModel(name.getText().toString(),mobile.getText().toString(),email.getText().toString(),model.getId()));

                userList.get(position).setName(name.getText().toString());
                userList.get(position).setMobile(mobile.getText().toString());
                userList.get(position).setEmail(email.getText().toString());

                dialog.cancel();
                showContacts();
            }
        });
    }
}
