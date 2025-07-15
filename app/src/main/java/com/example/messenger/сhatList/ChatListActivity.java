package com.example.messenger.сhatList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.R;
import com.example.messenger.User;
import com.example.messenger.adapters.ChatsAdapter;
import com.example.messenger.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatListActivity extends AppCompatActivity {

    private ChatListViewModel viewModel;
    private RecyclerView rvChats;
    private ChatsAdapter chatsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setUpRecyclerView();
        viewModel = new ViewModelProvider(this).get(ChatListViewModel.class);
        observeViewModels();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            User user = new User(
                    "id" + i,
                    "Name" + i,
                    "LastName" + i,
                    new Random().nextBoolean()
            );
            users.add(user);
        }
        chatsAdapter.setUsers(users);

    }

    private void initViews(){
        rvChats = findViewById(R.id.rvChats);
    }

    private void setUpRecyclerView(){
        chatsAdapter = new ChatsAdapter();
        rvChats.setAdapter(chatsAdapter);
        rvChats.setLayoutManager(new LinearLayoutManager(this));
    }
    private void observeViewModels() {
        viewModel.getUser().observe(this, firebaseUser -> {
            if (firebaseUser == null) {
                startActivity(LoginActivity.newIntent(ChatListActivity.this));
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            viewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }
    public static Intent newIntent(Context context) {
        return new Intent(context, ChatListActivity.class);
    }
}