package com.example.messenger.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.R;
import com.example.messenger.User;

import java.util.ArrayList;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

    private List<User> users = new ArrayList<>();
    private OnChatClickListener onChatClickListener;

    public void setOnChatClickListener(OnChatClickListener onChatClickListener) {
        this.onChatClickListener = onChatClickListener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.chat_item,
                parent,
                false
        );
        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {
        User user = users.get(position);
        String userInfo = String.format("%s %s", user.getName(), user.getLastName());
        holder.tvUserName.setText(userInfo);
        int bgResId;
        if(user.isOnline()){
            bgResId = android.R.drawable.presence_online;
        } else {
            bgResId = android.R.drawable.presence_offline;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), bgResId);
        holder.ivStatus.setImageDrawable(background);
        holder.itemView.setOnClickListener( view -> {
            if(onChatClickListener != null){
                onChatClickListener.onChatClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

     interface  OnChatClickListener{
        void onChatClick(User user);
    }

    public static class ChatsViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvUserName;
        private ImageView ivStatus;

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivStatus = itemView.findViewById(R.id.ivStatus);
        }
    }
}
