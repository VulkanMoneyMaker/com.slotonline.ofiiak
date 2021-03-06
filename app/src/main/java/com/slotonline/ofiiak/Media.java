package com.slotonline.ofiiak;

import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

public class Media implements Parcelable {

    private String path = null;
    private long dateModified = -1;
    private String mimeType = "unknown";
    private int orientation = 0;

    private String uriString = null;

    private long size = -1;
    private boolean selected = false;

    public Media() { }

    public Media(String path, long dateModified) {
        this.path = path;
        this.dateModified = dateModified;
    }

    public Media(File file) {
        this(file.getPath(), file.lastModified());
        this.size = file.length();
    }

    public Media(String path) {
        this(path, -1);
    }

    public Media(Uri mediaUri) {
        this.uriString = mediaUri.toString();
        this.path = null;
    }

    public Media(Cursor cur) {
        this.path = cur.getString(0);
        this.dateModified = cur.getLong(1);
        this.mimeType = cur.getString(2);
        this.size = cur.getLong(3);
        this.orientation = cur.getInt(4);
    }

    public Media handle(Cursor cu) throws SQLException {
        return new Media(cu);
    }

    public static String[] getProjection() {
        return new String[]{
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.ORIENTATION
        };
    }

    public void setUri(String uriString) {
        this.uriString = uriString;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean setSelected(boolean selected) {
        if (this.selected == selected) return false;
        this.selected = selected;
        return true;
    }

    public boolean toggleSelected() {
        selected = !selected;
        return selected;
    }

    public boolean isGif() { return mimeType.endsWith("gif"); }

    public boolean isImage() { return mimeType.startsWith("image"); }

    public boolean isVideo() { return mimeType.startsWith("video"); }

    public Uri getUri() {
        return uriString != null ? Uri.parse(uriString) : Uri.fromFile(new File(path));
    }

    public String getDisplayPath() {
        return path != null ? path : getUri().getEncodedPath();
    }

    public String getName() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public Long getDateModified() {
        return dateModified;
    }



    public int getOrientation() {
        return orientation;
    }

    //<editor-fold desc="Exif & More">
// TODO remove from here!
    @Deprecated
    public Bitmap getBitmap(){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);
        return bitmap;
    }


    @Deprecated
    public boolean setOrientation(final int orientation) {
        this.orientation = orientation;
        new Thread(new Runnable() {
            public void run() {
                int exifOrientation = -1;
                try {
                    ExifInterface exif = new ExifInterface(path);
                    switch (orientation) {
                        case 90: exifOrientation = ExifInterface.ORIENTATION_ROTATE_90; break;
                        case 180: exifOrientation = ExifInterface.ORIENTATION_ROTATE_180; break;
                        case 270: exifOrientation = ExifInterface.ORIENTATION_ROTATE_270; break;
                        case 0: exifOrientation = ExifInterface.ORIENTATION_NORMAL; break;
                    }
                    if (exifOrientation != -1) {
                        exif.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(exifOrientation));
                        exif.saveAttributes();
                    }
                }
                catch (IOException ignored) {  }
            }
        }).start();
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Media)
            return getPath().equals(((Media) obj).getPath());

        return super.equals(obj);
    }

    @Deprecated
    private long getDateTaken() {
        return 1;
    }

    @Deprecated
    public boolean fixDate(){
        long newDate = getDateTaken();
        if (newDate != -1){
            File f = new File(path);
            if (f.setLastModified(newDate)) {
                dateModified = newDate;
                return true;
            }
        }
        return false;
    }

    //</editor-fold>

    public File getFile() {
        if (path != null) {
            File file = new File(path);
            if (file.exists()) return file;
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeLong(this.dateModified);
        dest.writeString(this.mimeType);
        dest.writeInt(this.orientation);
        dest.writeString(this.uriString);
        dest.writeLong(this.size);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected Media(Parcel in) {
        this.path = in.readString();
        this.dateModified = in.readLong();
        this.mimeType = in.readString();
        this.orientation = in.readInt();
        this.uriString = in.readString();
        this.size = in.readLong();
        this.selected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}