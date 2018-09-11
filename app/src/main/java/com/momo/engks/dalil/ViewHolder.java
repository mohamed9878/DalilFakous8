package com.momo.engks.dalil;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mvView;
    Typeface typeface, typeface1;

    ViewHolder(View itemView) {
        super(itemView);

        mvView = itemView;
    }

    public void setDetails(Context ctx, String title, String desc, String image) {
        typeface = Typeface.createFromAsset(ctx.getAssets(), "Cairo.ttf");
        typeface1 = Typeface.createFromAsset(ctx.getAssets(), "semibold.ttf");
        TextView txt_title = mvView.findViewById(R.id.txt_title);
        ImageView imageView = mvView.findViewById(R.id.image_card);
        TextView txt_desc = mvView.findViewById(R.id.txt_desc);

        txt_title.setText(title);
        txt_desc.setText(desc);
        Picasso.get().load(image).into(imageView);
        txt_title.setTypeface(typeface1);
        txt_desc.setTypeface(typeface);


    }


}
