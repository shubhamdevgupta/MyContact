package app.zapurse.mycontacts;

import androidx.recyclerview.widget.RecyclerView;

import app.zapurse.mycontacts.models.UserModel;

public interface UserItemEventListner {
    void onDelete(UserModel model, int position);
    void onEdit(UserModel model, int position);
}
