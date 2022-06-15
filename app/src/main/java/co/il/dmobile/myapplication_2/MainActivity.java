package co.il.dmobile.myapplication_2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<User> users = new ArrayList<User>();
        users.add(new User(R.drawable.avatar1, "Contact 1", "contact1@post.idc.ac.il"));
        users.add(new User(R.drawable.avatar2, "Contact 2", "contact2@post.idc.ac.il"));
        users.add(new User(R.drawable.avatar3, "Contact 3", "contact3@post.idc.ac.il"));
        users.add(new User(R.drawable.avatar4, "Contact 4", "contact4@post.idc.ac.il"));
        users.add(new User(R.drawable.avatar5, "Contact 5", "contact5@post.idc.ac.il"));
        users.add(new User(R.drawable.avatar6, "Contact 6", "contact6@post.idc.ac.il"));
        users.add(new User(R.drawable.avatar7, "Contact 7", "contact7@post.idc.ac.il"));
        users.add(new User(R.drawable.avatar8, "Contact 8", "contact8@post.idc.ac.il"));

        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setHasFixedSize(false);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 1);
        recycler.setLayoutManager(manager);

        adapter = new UserAdapter(users);
        recycler.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        helper.attachToRecyclerView(recycler);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                         Intent data = result.getData();
                         newContactResult(data);
                    }
                });

        FloatingActionButton btn = findViewById(R.id.floatingActionButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AddContactActivity.class);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(),
                                btn,
                                "bg"
                        );
                activityResultLauncher.launch(i, options);
            }
        });
    }

    protected void newContactResult (Intent data) {
            Bundle b = data.getExtras();
            User user = (User) b.getSerializable("user");
            adapter.AddContact(user);
    }
}