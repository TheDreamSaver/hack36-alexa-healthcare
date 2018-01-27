package com.example.sidkathuria14.healthcare.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sidkathuria14.healthcare.R;
import com.example.sidkathuria14.healthcare.activities.MedSchedulerActivity;

import java.math.MathContext;
import java.util.ArrayList;

/**
 * Created by theninetails on 28/1/18.
 */

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> medList;

    public MedAdapter(Context mContext, ArrayList<String> medList) {
        this.mContext = mContext;
        this.medList = medList;
    }

    public void updateList(ArrayList<String> newList){
        this.medList=newList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.layout_medview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(medList.get(position));
    }

    @Override
    public int getItemCount() {
        return medList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View thisView;
        private TextView medName;
        public ViewHolder(View itemView) {
            super(itemView);
            thisView=itemView.findViewById(R.id.MedView);
            medName=itemView.findViewById(R.id.tvMed);
        }

        public void bind(final String medname){
            medName.setText(medname);
            thisView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MedSchedulerActivity.class);
                    intent.putExtra("MedName",medname);
                    intent.putExtra("opMode","update");
                    ((Activity)mContext).startActivity(intent);
                }
            });
        }
    }
}
