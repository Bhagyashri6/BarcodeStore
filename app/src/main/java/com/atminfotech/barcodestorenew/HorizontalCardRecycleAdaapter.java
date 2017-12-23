package com.atminfotech.barcodestorenew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by ABC on 08/12/2017.
 */

public class HorizontalCardRecycleAdaapter extends RecyclerView.Adapter<HorizontalCardRecycleAdaapter.DataHolder> {

    private Context context;
    ArrayList<GridViewData> dataa = new ArrayList<>();
    private int lastPosition = -1;

    public HorizontalCardRecycleAdaapter(Context context, ArrayList<GridViewData> dataa) {
        this.context = context;
        this.dataa = dataa;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card, parent, false);


        return new DataHolder(v);
    }

    @Override
    public void onBindViewHolder(final DataHolder holder, final int position) {

        holder.namehor.setText(dataa.get(position).getName());
        holder.ratehor.setText(dataa.get(position).getRate());

        Glide.with(context)
                .load(dataa.get(position).getImage())
                .crossFade()
                .placeholder(R.mipmap.imageplaceholder)
                .into(holder.imghor);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(HomeActivity.this,ProductDetails.class);
                i.putExtra("position",0);
                i.putExtra("Class","kiosk");
                i.putExtra("CompanyName","Zebra Accessories");
                i.putExtra("ModelNo","Zebra 336 IS");
                startActivity(i);*/
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("position", ListInfoData.getListDHorizontalList().get(position).getPosition());
                intent.putExtra("Class", ListInfoData.getListDHorizontalList().get(position).getClass1());
                intent.putExtra("CompanyName", ListInfoData.getListDHorizontalList().get(position).getColor());
                intent.putExtra("ModelNo", ListInfoData.getListDHorizontalList().get(position).getName());
               /* intent.putExtra("rate", ListInfoData.getListDHorizontalList().get(position).getRate());
                intent.putExtra("img", ListInfoData.getListDHorizontalList().get(position).getImage());*/
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.imghor, "vv");
                context.startActivity(intent);
            }
        });



    }
    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
            itemView.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return dataa.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {
        ImageView imghor;
        TextView namehor, ratehor;

        public DataHolder(View itemView) {
            super(itemView);


            ratehor = (TextView) itemView.findViewById(R.id.horizontalcardrate);
            namehor = (TextView) itemView.findViewById(R.id.horizontalcardname);
            imghor = (ImageView) itemView.findViewById(R.id.horizontalcardimg);


        }
    }
}

