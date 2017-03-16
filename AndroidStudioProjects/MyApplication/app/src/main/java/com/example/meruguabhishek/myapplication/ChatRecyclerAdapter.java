package com.example.meruguabhishek.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by meruguabhishek on 2017-03-15.
 */

public class ChatRecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEAD = 0;
    private Context mContext;
    private ArrayList<ChatItem> items=new ArrayList<>();
    private static final int TYPE_TEXT= 1;
    private  static final int TYPE_IMAGE = 2;
    public ChatRecyclerAdapter(Context mContext,ArrayList<ChatItem> results) {
        this.mContext = mContext;
        this.items=results;
    }
    public void setItems(ArrayList<ChatItem> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TEXT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_box, null);
            MyVH vh = new MyVH(view);
            return vh;
        }  else if (viewType==TYPE_IMAGE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_box, null);
            MyVH2 vh = new MyVH2(view);
            return vh;
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_box, null);
            MyVH vh = new MyVH(view);
            return vh;
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getType().equalsIgnoreCase("text"))
            return TYPE_TEXT;
        else if (items.get(position).getType().equalsIgnoreCase("image"))
            return TYPE_IMAGE;
        else return TYPE_IMAGE;

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof MyVH) {
            MyVH holder =(MyVH) viewHolder;
            ChatItem item=items.get(position);
            holder.textView.setText(item.getMessage());
            holder.title.setText(item.getUser());

        } else if (viewHolder instanceof MyVH2){
            MyVH2 holder =(MyVH2) viewHolder;
            ChatItem item=items.get(position);
            holder.flagTxt.setText(item.getUser());
            URL url = null;
            Glide.with(mContext).load("http://i.ndtvimg.com/i/2015-12/audi-a4-827_827x510_81450185907.jpg").into(holder.imageView);
//            try {
//                url = new URL();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            Bitmap bmp = null;
//            try {
//                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            holder.imageView.setImageBitmap(bmp);
        }

    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public class MyVH2 extends RecyclerView.ViewHolder {
        private TextView flagTxt;
        private ImageView imageView;
        public MyVH2(View itemView) {
            super(itemView);
//            this.flagBtn= (Button) itemView.findViewById(R.id.flagButton);
            this.imageView= (ImageView) itemView.findViewById(R.id.img);
            this.flagTxt=(TextView)itemView.findViewById(R.id.username);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    if (handleSuggestionClick!=null){
////                        AutoSuggestResult item=items.get(getAdapterPosition());
////                        handleSuggestionClick.onSuggestionItemClick(item.getValue(),item.getSubCategory());
////                    }
//                }
//            });

        }
    }


    public class MyVH extends RecyclerView.ViewHolder  {
        private TextView title;
        private TextView textView;
        public MyVH(View itemView) {
            super(itemView);
            this.textView= (TextView) itemView.findViewById(R.id.msg);
            this.title = (TextView) itemView.findViewById(R.id.username);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (handleSuggestionClick!=null){
//                        AutoSuggestResult item=items.get(getAdapterPosition());
//                        handleSuggestionClick.onSuggestionItemClick(item.getValue(),item.getSubCategory());
//                    }
                }
            });
        }



    }
}
