package com.example.mumo.parkingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mumo.parkingapp.BookingActivity;

import com.example.mumo.parkingapp.R;
import com.example.mumo.parkingapp.model.Parking;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ItemViewHolder> {

    private List<Parking> mListItems;
    private Context mContext;

    public DataAdapter(Context context, List<Parking> listItems){
        mContext = context;
        mListItems = listItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_view, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
         final Parking item = mListItems.get(position);
         holder.mAreaTextView.setText(item.getLocation());
         holder.mFeeTextView.setText(String.valueOf(item.getFee()));
         holder.mBookTextView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = BookingActivity.newIntent(mContext, item);
                 mContext.startActivity(i);
             }
         });
    }

    public void setListItems(List<Parking> listItems) {
        mListItems = listItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView mFeeTextView;
        private TextView mAreaTextView;
        private TextView mBookTextView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mAreaTextView = itemView.findViewById(R.id.item_area);
            mFeeTextView = itemView.findViewById(R.id.item_fee);
            mBookTextView = itemView.findViewById(R.id.item_book);


        }
    }
}
