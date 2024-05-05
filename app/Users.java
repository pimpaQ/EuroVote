import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {
    public int UserID;
    public String Login;
    public  String Password;

    public Users(int userID, String login, String password) {
        UserID = userID;
        Login = login;
        Password = password;
    }

    protected Users(Parcel in) {
        UserID = in.readInt();
        Login = in.readString();
        Password = in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(UserID);
        dest.writeString(Login);
        dest.writeString(Password);
    }
}
