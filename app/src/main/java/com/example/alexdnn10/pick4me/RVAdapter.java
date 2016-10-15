package com.example.alexdnn10.pick4me;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView userName;
        TextView userTime;
        ImageView userPhoto;
        ImageView userPhoto_1;
        ImageView userPhoto_2;
        ImageView userPhoto_3;
        ImageView userPhoto_4;


        PersonViewHolder(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.newsfeed_username);
            userTime = (TextView)itemView.findViewById(R.id.newsfeed_time);
            userPhoto = (ImageView)itemView.findViewById(R.id.newsfeed_user_photo);
            userPhoto_1 = (ImageView)itemView.findViewById(R.id.image_one);
            userPhoto_2 = (ImageView)itemView.findViewById(R.id.image_two);
            userPhoto_3 = (ImageView)itemView.findViewById(R.id.image_three);
            userPhoto_4 = (ImageView)itemView.findViewById(R.id.image_four);
        }
    }
    List<Person> persons;
    Context ctx;
    int x=300;

    RVAdapter(List<Person> persons, Context context){
        this.persons = persons;
        this.ctx=context;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.userName.setText(persons.get(i).username);
        personViewHolder.userTime.setText(persons.get(i).time);
        personViewHolder.userPhoto.setImageResource(persons.get(i).userphoto);
        personViewHolder.userPhoto_1.setImageBitmap(decodeSampledBitmapFromResource(ctx.getResources(), persons.get(i).photoId_1, x, x));
        personViewHolder.userPhoto_2.setImageBitmap(decodeSampledBitmapFromResource(ctx.getResources(), persons.get(i).photoId_2, x, x));
        personViewHolder.userPhoto_3.setImageBitmap(decodeSampledBitmapFromResource(ctx.getResources(), persons.get(i).photoId_3, x, x));
        personViewHolder.userPhoto_4.setImageBitmap(decodeSampledBitmapFromResource(ctx.getResources(), persons.get(i).photoId_4, x, x));
    }
    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}

