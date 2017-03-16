package com.example.meruguabhishek.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 2;
    ImageView sendButton;
    ImageView addImage;
    ChatRecyclerAdapter chatAdapter;
    EditText messageArea;
    RecyclerView recyclerView;
//    ScrollView scrollView;
    Firebase reference1, reference2;
    private ArrayList<ChatItem> chatItems=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        chatAdapter=new ChatRecyclerAdapter(ChatActivity.this,chatItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        recyclerView.setAdapter(chatAdapter);
        addImage= (ImageView) findViewById(R.id.addImage);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://chat-84c18.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chat-84c18.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("type","text");
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                }
                messageArea.getText().clear();
            }
        });
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                if(userName.equals(UserDetails.username)){
                    userName="You :";
                }
                else {
                    userName=userName+" :";
                }
                String type="";
                try {
                    type = map.get("type").toString();
                }catch (Exception e){
                    type="text";
                }
                ChatItem chatItem=new ChatItem(message,userName,type);
                chatItems.add(chatItem);
                chatAdapter.setItems(chatItems);
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Intent intent=new Intent(ChatActivity.this,UploadImageActivity.class);
            intent.setData(uri);
            startActivityForResult(intent,3);

        }
        if (requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", uri.toString());
            map.put("user", UserDetails.username);
            map.put("type","image");
            reference1.push().setValue(map);
            reference2.push().setValue(map);
        }
    }
//    public void addMessageBox(String message, int type){
//        TextView textView = new TextView(ChatActivity.this);
//        textView.setText(message);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 0, 0, 10);
//        textView.setLayoutParams(lp);
//
//        if(type == 1) {
//            textView.setBackgroundResource(R.drawable.rounded_corner1);
//        }
//        else{
//            textView.setBackgroundResource(R.drawable.rounded_corner2);
//        }
//
//        layout.addView(textView);
//        scrollView.fullScroll(View.FOCUS_DOWN);
//    }
//    public void addImageBox(String message, int type){
//        ImageView imageView = new ImageView(ChatActivity.this);
////        textView.setText(message);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(0, 0, 0, 10);
//        imageView.setLayoutParams(lp);
//
//        if(type == 1) {
//            URL url = null;
//            try {
//                url = new URL("http://i.ndtvimg.com/i/2015-12/audi-a4-827_827x510_81450185907.jpg");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            Bitmap bmp = null;
//            try {
//                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            imageView.setImageBitmap(bmp);
//        }
//        else{
//            URL url = null;
//            try {
//                url = new URL("http://i.ndtvimg.com/i/2015-12/audi-a4-827_827x510_81450185907.jpg");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            Bitmap bmp = null;
//            try {
//                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            imageView.setImageBitmap(bmp);
//        }
//
//        layout.addView(imageView);
//        scrollView.fullScroll(View.FOCUS_DOWN);
//    }
}