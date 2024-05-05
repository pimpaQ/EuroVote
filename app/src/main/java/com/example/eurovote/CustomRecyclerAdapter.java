package com.example.eurovote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private List<String> mVideoUrls;
    public CustomRecyclerAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mVideoUrls = new ArrayList<>();
    }
    public Cursor getCursor() {
        return mCursor;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        @SuppressLint("Range") String artistName = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.ARTIST_NAME));
        @SuppressLint("Range") String songName = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.ARTIST_SONG));
        @SuppressLint("Range") String country = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.ARTIST_COUNTRY));
        @SuppressLint("Range") byte[] imageBytes = mCursor.getBlob(mCursor.getColumnIndex(DatabaseHelper.ARTIST_PHOTO));
        @SuppressLint("Range") String videoUrl = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.ARTIST_URL));
        mVideoUrls.add(videoUrl);

        holder.namesArtistTextView.setText(artistName);
        holder.songTextView.setText(songName);
        holder.countryTextView.setText(country);

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.flagImageView.setImageBitmap(bitmap);
        holder.button.setTag(position);
        holder.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получите тег кнопки, чтобы определить номер элемента
                int itemPosition = (int) v.getTag();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));

                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namesArtistTextView;
        TextView songTextView;
        TextView countryTextView;
        ImageView flagImageView;
        Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            namesArtistTextView = itemView.findViewById(R.id.namesArtist);
            songTextView = itemView.findViewById(R.id.song);
            countryTextView = itemView.findViewById(R.id.country);
            flagImageView = itemView.findViewById(R.id.flag);
            button = itemView.findViewById(R.id.button2);
        }

    }
}
