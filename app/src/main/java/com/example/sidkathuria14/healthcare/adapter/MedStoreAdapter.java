package com.example.sidkathuria14.healthcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sidkathuria14.healthcare.R;
import com.example.sidkathuria14.healthcare.models.shops;
import com.example.sidkathuria14.healthcare.models.stores;

import java.util.ArrayList;

/**
 * Created by theninetails on 27/1/18.
 */

public class MedStoreAdapter extends RecyclerView.Adapter<MedStoreAdapter.MedStoreViewHolder> {

    private Context mContext;
    private ArrayList<shops> medStoreList;

    public MedStoreAdapter(Context mContext, ArrayList<shops> medStoreList) {
        this.mContext = mContext;
        this.medStoreList = medStoreList;
    }

    public void updateList(ArrayList<shops> newList) {
        this.medStoreList = newList;
        notifyDataSetChanged();
    }

    @Override
    public MedStoreAdapter.MedStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = li.inflate(R.layout.layout_medstore_view, parent, false);
        return new MedStoreViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MedStoreAdapter.MedStoreViewHolder holder, int position) {
        holder.bind(medStoreList.get(position));
    }

    @Override
    public int getItemCount() {
        return medStoreList.size();
    }

    public class MedStoreViewHolder extends RecyclerView.ViewHolder {
        View thisView;
        TextView tvShopName, tvMedName, tvAddress;

        public MedStoreViewHolder(View itemView) {
            super(itemView);
            thisView = itemView.findViewById(R.id.medStoreView);
            tvShopName = itemView.findViewById(R.id.tvShopName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvMedName = itemView.findViewById(R.id.tvMedName);
        }

        public void bind(shops thisStore) {
            thisView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // to open maps
                }
            });
//            tvMedName.setText(thisStore.getAlternative_medicine());
            tvAddress.setText(thisStore.getAddress());
            tvShopName.setText(thisStore.getName());
        }
    }
}

