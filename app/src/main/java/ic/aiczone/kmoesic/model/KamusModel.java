package ic.aiczone.kmoesic.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aic on 27/03/18.
 */

public class KamusModel implements Parcelable {
    private int id;
    private String key;
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.key);
        dest.writeString(this.data);
    }

    public KamusModel() {
    }

    public KamusModel(String key,String data){
        this.key = key;
        this.data = data;
    }

    public KamusModel(int id,String key,String data){
        this.id = id;
        this.key = key;
        this.data = data;
    }

    protected KamusModel(Parcel in) {
        this.id = in.readInt();
        this.key = in.readString();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<KamusModel> CREATOR = new Parcelable.Creator<KamusModel>() {
        @Override
        public KamusModel createFromParcel(Parcel source) {
            return new KamusModel(source);
        }

        @Override
        public KamusModel[] newArray(int size) {
            return new KamusModel[size];
        }
    };
}
