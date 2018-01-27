package com.example.sidkathuria14.healthcare.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sidkathuria14.healthcare.R;
import com.example.sidkathuria14.healthcare.activities.MedSchedulerActivity;
import com.example.sidkathuria14.healthcare.database.MedScheduleContract;
import com.example.sidkathuria14.healthcare.database.MedScheduleDBHelper;

import java.math.MathContext;
import java.util.ArrayList;

/**
 * Created by theninetails on 28/1/18.
 */

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> medList;
    private SQLiteDatabase db;

    public MedAdapter(Context mContext, ArrayList<String> medList) {
        this.mContext = mContext;
        this.medList = medList;

        MedScheduleDBHelper helper= new MedScheduleDBHelper(mContext);
        db = helper.getWritableDatabase();
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
            Log.d("MedScheduler", "bind: "+medname);
            thisView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MedSchedulerActivity.class);
                    intent.putExtra("MedName",medname);
                    intent.putExtra("opMode","update");
                    ((Activity)mContext).startActivity(intent);
                }
            });
            thisView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Delete ?")
                            .setPositiveButton(R.string.btn_OK, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int val=db.delete(
                                            MedScheduleContract.MedScheduleEntry.TABLE_NAME,
                                            MedScheduleContract.MedScheduleEntry.COLUMN_MED_NAME+" LIKE ?",
                                            new String[]{medname}
                                    );
                                    Log.d("pui", "onLongClick: val "+val);
                                    for(int i=0;i<medList.size();i++) {
                                        if(medList.get(i)==medname){
                                            medList.remove(i);
                                            break;
                                        }
                                    }
                                    updateList(medList);
                                }
                            })
                            .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog=builder.create();
                    dialog.show();

                    return true;
                }
            });
        }
    }
}
