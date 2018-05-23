package com.example.mumo.parkingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mumo.parkingapp.R;
import com.example.mumo.parkingapp.model.BookedSlot;

import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ItemViewHolder> {

    private Context mContext;
    private List<BookedSlot> mBookedSlots;
    public SlotAdapter(Context context, List<BookedSlot> list){
        mContext = context;
        mBookedSlots = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.slot_item_view, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        BookedSlot bookedSlot = mBookedSlots.get(position);
        holder.mRefno.setText(bookedSlot.getRefno());
        holder.mTime.setText(bookedSlot.getTime() +" hrs");
        holder.mBookedTime.setText(bookedSlot.getTimeStamp());
        holder.mSlotId.setText(String.valueOf(bookedSlot.getSlotId()));
    }

    @Override
    public int getItemCount() {
        return mBookedSlots.size();
    }

    public void setBookedSlots(List<BookedSlot> bookedSlots) {
        mBookedSlots = bookedSlots;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView mRefno, mSlotId, mBookedTime,mTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mRefno = itemView.findViewById(R.id.tv_refno);
            mSlotId = itemView.findViewById(R.id.tv_slot_id);
            mBookedTime = itemView.findViewById(R.id.tv_time_booked);
            mTime = itemView.findViewById(R.id.tv_time);

        }
    }
}
