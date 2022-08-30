package com.example.cryptoscholartracker;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class historyRVAdapter extends RecyclerView.Adapter<historyRVAdapter.MyViewHolder> {

    private ArrayList<historyRVModal> historyRVModalArrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }

    public historyRVAdapter(ArrayList<historyRVModal>historyRVModalArrayList){
        this.historyRVModalArrayList = historyRVModalArrayList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView percentageTV, roninTV;

        public MyViewHolder(final View view, OnItemClickListener listener){
            super(view);
            percentageTV = view.findViewById(R.id.Percentage);
            roninTV = view.findViewById(R.id.historyRonin);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){

                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public void setHistoryRVModalArrayList(ArrayList<historyRVModal> historyRVModalArrayList) {
        this.historyRVModalArrayList = historyRVModalArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public historyRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.axiehistory_cardview, parent,false);
        historyRVAdapter.MyViewHolder evh  = new historyRVAdapter.MyViewHolder(itemView,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull historyRVAdapter.MyViewHolder holder, int position) {


        float scholar= historyRVModalArrayList.get(position).getScholar();
        float manager = historyRVModalArrayList.get(position).getManager();
        historyRVModalArrayList.get(position).getManager();
        String managerPercentage = String.valueOf(manager);
        String scholarPercentage = String.valueOf(scholar);
        String historyRonin = historyRVModalArrayList.get(position).getRoninAddress();

        holder.percentageTV.setText(scholarPercentage + " : "+ managerPercentage);
        holder.roninTV.setText(historyRonin);

    }

    @Override
    public int getItemCount() {

        return historyRVModalArrayList.size();
    }

}
