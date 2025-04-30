package com.example.chat_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
Adapter adapter ;
RecyclerView recyclerView;
ArrayList<Message>arrayList;
EditText editText;
FloatingActionButton button ;
DatabaseReference db ;
FirebaseAuth firebaseAuth ;
FirebaseUser firebaseUser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.sendMessage_edit);
        button=findViewById(R.id.sendMessage_button);
        recyclerView=findViewById(R.id.rv);
        arrayList=new ArrayList<>();
        db= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        String uId=firebaseUser.getUid();
        String uEmail=firebaseUser.getEmail();
        String timeStamp=new SimpleDateFormat("dd-MM-yy HH:mm a").format(Calendar.getInstance().getTime());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=editText.getText().toString();
db.child("Messages").push().setValue(new Message(uEmail,msg,timeStamp)).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
editText.setText("");

    }
});
            }
        });
adapter=new Adapter(arrayList);
recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
recyclerView.setHasFixedSize(true);
recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiveMessage();
    }

    private void receiveMessage(){
        db.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Message message=snapshot1.getValue(Message.class);
                   adapter.addMessage(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}