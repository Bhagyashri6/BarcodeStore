package com.atminfotech.barcodestorenew;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
        TextView txtModel,txtRate;
        ImageView cancel;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_cart, null);
            holder = new ViewHolder();
            holder.txtDesc   = (TextView) convertView.findViewById(R.id.txt_qty);
            holder.txtModel  = (TextView) convertView.findViewById(R.id.txt_model);
            holder.txtTitle  = (TextView) convertView.findViewById(R.id.txt_title);
            holder.txtRate   = (TextView) convertView.findViewById(R.id.txt_rate);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_prod);
            holder.cancel    = (ImageView) convertView.findViewById(R.id.img_cancel) ;

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

                holder.txtDesc.setText(rowItem.getQty()+"");
                holder.txtTitle.setText(rowItem.getTitle());
                holder.txtModel.setText(rowItem.getDesc());
                holder.txtRate.setText(rowItem.getRate()+"");

                Picasso.with(context)
                        .load(rowItem.getImageId())
                        .resize(120, 120)
                        .into(holder.imageView);
                //holder.imageView.setImageBitmap(BitmapFactory.decodeStream(new URL(rowItem.getImageId()).openConnection().getInputStream()));

        return convertView;
    }
}