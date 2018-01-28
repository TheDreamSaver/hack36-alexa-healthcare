package com.example.sidkathuria14.healthcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sidkathuria14.healthcare.R;
import com.example.sidkathuria14.healthcare.interfaces.LongClickListener;

import java.util.ArrayList;

/**
 * Created by theninetails on 27/1/18.
 */

public class TimeScheduleAdapter extends RecyclerView.Adapter<TimeScheduleAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> timeScheduleList;
    private LongClickListener longClickListener;

    public void setLongClickListener(LongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }



    public TimeScheduleAdapter(Context mContext, ArrayList<String> timeScheduleList) {
        this.mContext = mContext;
        this.timeScheduleList = timeScheduleList;
    }

    public void updateList(ArrayList<String> newList){
        this.timeScheduleList=newList;
        notifyDataSetChanged();
    }

    @Override
    public TimeScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = li.inflate(R.layout.layout_timers,parent,false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TimeScheduleAdapter.ViewHolder holder, int position) {
        holder.bind(timeScheduleList.get(position));
    }

    @Override
    public int getItemCount() {
        return timeScheduleList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private View thisView;
        private TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            thisView=itemView;
            time=itemView.findViewById(R.id.tvTimeRange);
        }

        public void bind(final String timeString){
            this.time.setText(timeString);
            thisView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // implement long click listenet
                    if(longClickListener!=null){
                        longClickListener.onLongClick(timeString);
                    }

                }
            });
        }
    }
}
