package com.example.cryptoscholartracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder>{

    // creating a variable for array list and context.
    private ArrayList<CurrencyRVModal> currencyRVModalArrayList;
    private Context context;
    private SelectedItem selectedItem;
    private static DecimalFormat df2 = new DecimalFormat("#.##");


    // creating a constructor for our variables.
    public CurrencyRVAdapter(ArrayList<CurrencyRVModal> currencyRVModalArrayList, Context context,SelectedItem selectedItem) {
        this.currencyRVModalArrayList = currencyRVModalArrayList;
        this.context = context;
        this.selectedItem = selectedItem;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<CurrencyRVModal> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        currencyRVModalArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CurrencyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyRVAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        CurrencyRVModal currencyRVModal = currencyRVModalArrayList.get(position);
        holder.currencyNameTV.setText(currencyRVModal.getName());
        holder.symbolTV.setText(currencyRVModal.getSymbol());
        holder.rateTV.setText("$" + df2.format(currencyRVModal.getPrice()));
        holder.percentTV.setText("% " + df2.format(currencyRVModal.getPercent()));
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return currencyRVModalArrayList.size();
    }

    public interface SelectedItem{

        void selectedItem(CurrencyRVModal currencyRVModal);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView currencyNameTV, symbolTV, rateTV, percentTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            currencyNameTV =itemView.findViewById(R.id.idTVName);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVRate);
            percentTV = itemView.findViewById(R.id.idTVPercent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem.selectedItem(currencyRVModalArrayList.get(getAdapterPosition()));
                }
            });
        }
    }
}
