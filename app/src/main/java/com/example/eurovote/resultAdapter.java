package com.example.eurovote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class resultAdapter extends RecyclerView.Adapter<resultAdapter.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public resultAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_vote_result, parent, false);
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
        // Получение суммы очков для текущего артиста
        @SuppressLint("Range") int totalPoints = mCursor.getInt(mCursor.getColumnIndex("TotalPoints"));

        holder.namesArtistTextView.setText(artistName);
        holder.songTextView.setText(songName);
        holder.countryTextView.setText(country);
        holder.SUM.setText(String.valueOf(totalPoints));

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.flagImageView.setImageBitmap(bitmap);
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
        TextView SUM;

        public ViewHolder(View itemView) {
            super(itemView);

            namesArtistTextView = itemView.findViewById(R.id.namesArtist);
            songTextView = itemView.findViewById(R.id.song);
            countryTextView = itemView.findViewById(R.id.country);
            flagImageView = itemView.findViewById(R.id.flag);
            SUM = itemView.findViewById(R.id.sum);
        }
    }
}
