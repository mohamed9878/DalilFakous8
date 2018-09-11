package com.momo.engks.dalil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Rate_Dialoge extends AppCompatDialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialoge_layout, null);
        builder.setView(view)

                .setNegativeButton("لا شكرا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "للأسف حتى يستمر الدعم يرجى التقيم ب5 نجوم وشكرا", Toast.LENGTH_LONG).show();
                    }
                }).setPositiveButton("تقيم التطبيق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

                Intent intent = goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
                }


            }
        });
        Animation animation = AnimationUtils.loadAnimation(getActivity() , R.anim.animation);
        ImageView imageView = view.findViewById(R.id.image_rate);
        imageView.setAnimation(animation);
        TextView textView = view.findViewById(R.id.text_rate);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "CairoBold.ttf");
        textView.setTypeface(typeface);


        return builder.create();
    }


}
