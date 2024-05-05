import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eurovote.R;

import java.util.List;

public class UsersAdapter extends BaseAdapter{
    private Context mContext;
    public List<Users> mUsersList;

    public UsersAdapter(Context mContext, List<Users> mUsersList) {
        this.mContext = mContext;
        this.mUsersList = mUsersList;
    }

    @Override
    public int getCount() {
        return mUsersList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mUsersList.get(position).getUserID();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.activity_hub, null);
        TextView textView = view.findViewById(R.id.textView4);
        Users current_user = mUsersList.get(position);
        textView.setText(current_user.Login);
        return view;
    }
}
