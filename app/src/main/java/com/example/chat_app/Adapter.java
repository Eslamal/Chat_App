package com.example.chat_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    ArrayList<Message>arrayList=new ArrayList<>();

    public Adapter(ArrayList<Message> arrayList) {
        this.arrayList = arrayList;
    }
public void addMessage(Message msg){
arrayList.add(0,msg);
notifyDataSetChanged();
}
    @NonNull
    @Override
    public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message,null,true);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewHolder holder, int position) {
holder.textViewSender.setText(arrayList.get(position).getUserEmail());
holder.textViewMessage.setText(arrayList.get(position).getMessage());
holder.textViewTime.setText(arrayList.get(position).getDateTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView textViewSender,textViewMessage,textViewTime;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSender=itemView.findViewById(R.id.textViewSender);
            textViewMessage=itemView.findViewById(R.id.textViewMessage);
            textViewTime=itemView.findViewById(R.id.textViewTime);

        }
    }
}
