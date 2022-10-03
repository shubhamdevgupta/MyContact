package app.zapurse.mycontacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.zapurse.mycontacts.R;
import app.zapurse.mycontacts.UserItemEventListner;
import app.zapurse.mycontacts.models.UserModel;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {

    public UserAdapter(ArrayList<UserModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    ArrayList<UserModel> arrayList;
    Context context;

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.showcontacts, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_mobile.setText(arrayList.get(position).getMobile());
        holder.tv_email.setText(arrayList.get(position).getEmail());
        UserModel model = arrayList.get(position);

        holder.edit.setOnClickListener(v -> listner.onEdit(model, position));
        holder.delete.setOnClickListener(v -> listner.onDelete(model, position));
    }

    @Override
    public int getItemCount() { return arrayList.size(); }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView edit, delete;
        TextView tv_name, tv_mobile, tv_email;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            tv_name = itemView.findViewById(R.id.show_user_name);
            tv_mobile = itemView.findViewById(R.id.show_user_mobile);
            tv_email = itemView.findViewById(R.id.show_user_email);
        }
    }

    private UserItemEventListner listner;

    public void setupUserItemListner(UserItemEventListner listner) {
        this.listner = listner;
    }

}
