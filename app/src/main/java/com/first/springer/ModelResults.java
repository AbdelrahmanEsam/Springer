package com.first.springer;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ModelResults")
public class ModelResults implements Parcelable {
    private String Uri;
    private String MoldorDie;
    private String MoldorDieName;
    private String Image;
    private String history;
    String Color;
    private int MoldorDieCode;
    double percentprogressfinal;
    double maxprogressfinal;
    double compressionDistancefinal;
    @PrimaryKey(autoGenerate = true)
    int id ;

    public ModelResults() {
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getMoldorDie() {
        return MoldorDie;
    }

    public void setMoldorDie(String moldorDie) {
        MoldorDie = moldorDie;
    }

    public String getMoldorDieName() {
        return MoldorDieName;
    }

    public void setMoldorDieName(String moldorDieName) {
        MoldorDieName = moldorDieName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getMoldorDieCode() {
        return MoldorDieCode;
    }

    public void setMoldorDieCode(int moldorDieCode) {
        MoldorDieCode = moldorDieCode;
    }

    public double getPercentprogressfinal() {
        return percentprogressfinal;
    }

    public void setPercentprogressfinal(double percentprogressfinal) {
        this.percentprogressfinal = percentprogressfinal;
    }

    public double getMaxprogressfinal() {
        return maxprogressfinal;
    }

    public void setMaxprogressfinal(double maxprogressfinal) {
        this.maxprogressfinal = maxprogressfinal;
    }

    public double getCompressionDistancefinal() {
        return compressionDistancefinal;
    }

    public void setCompressionDistancefinal(double compressionDistancefinal) {
        this.compressionDistancefinal = compressionDistancefinal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelResults(String uri, String moldorDie, String moldorDieName, String image, String history, String color, int moldorDieCode, double percentprogressfinal, double maxprogressfinal, double compressionDistancefinal) {
        Uri = uri;
        MoldorDie = moldorDie;
        MoldorDieName = moldorDieName;
        Image = image;
        this.history = history;
        Color = color;
        MoldorDieCode = moldorDieCode;
        this.percentprogressfinal = percentprogressfinal;
        this.maxprogressfinal = maxprogressfinal;
        this.compressionDistancefinal = compressionDistancefinal;
    }


    public ModelResults(double percentprogressfinal, double maxprogressfinal, double compressionDistancefinal) {
        this.percentprogressfinal = percentprogressfinal;
        this.maxprogressfinal = maxprogressfinal;
        this.compressionDistancefinal = compressionDistancefinal;
    }

    protected ModelResults(Parcel in) {
        Uri = in.readString();
        MoldorDie = in.readString();
        MoldorDieName = in.readString();
        Image = in.readString();
        history = in.readString();
        Color = in.readString();
        MoldorDieCode = in.readInt();
        percentprogressfinal = in.readDouble();
        maxprogressfinal = in.readDouble();
        compressionDistancefinal = in.readDouble();
        id = in.readInt();
    }

    public static final Creator<ModelResults> CREATOR = new Creator<ModelResults>() {
        @Override
        public ModelResults createFromParcel(Parcel in) {
            return new ModelResults(in);
        }

        @Override
        public ModelResults[] newArray(int size) {
            return new ModelResults[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Uri);
        dest.writeString(MoldorDie);
        dest.writeString(MoldorDieName);
        dest.writeString(Image);
        dest.writeString(history);
        dest.writeString(Color);
        dest.writeInt(MoldorDieCode);
        dest.writeDouble(percentprogressfinal);
        dest.writeDouble(maxprogressfinal);
        dest.writeDouble(compressionDistancefinal);
        dest.writeInt(id);
    }
}
