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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CRA2 extends RecyclerView.Adapter<CRA2.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private List<String> mVideoUrls;
    private Spinner spinner;
    public CRA2(Context context, Cursor cursor) {
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
        View view = inflater.inflate(R.layout.list_items, parent, false);
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

        holder.namesArtistTextView.setText(artistName);
        holder.songTextView.setText(songName);
        holder.countryTextView.setText(country);

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

        public ViewHolder(View itemView) {
            super(itemView);

            namesArtistTextView = itemView.findViewById(R.id.namesArtist);
            songTextView = itemView.findViewById(R.id.song);
            countryTextView = itemView.findViewById(R.id.country);
            flagImageView = itemView.findViewById(R.id.flag);
            spinner = itemView.findViewById(R.id.spinner);
            String[] items = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "10", "12"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, items);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
        }


    }
    public String getSelectedItemValue(int i) {
        if (spinner != null) {
            return spinner.getSelectedItem().toString();
        }
        return null;
    }
}

