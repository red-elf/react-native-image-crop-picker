package com.reactnative.ivpusic.imagepicker;

import android.util.Log;

import androidx.exifinterface.media.ExifInterface;

public class GeoDegree {
    Float latitude;
    Float longitude;

    public GeoDegree(ExifInterface exif) {

        final String logTag = "GeoDegree ::";

        String attrLATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
        String attrLATITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);

        String attrLATITUDE_Dest = exif.getAttribute(ExifInterface.TAG_GPS_DEST_LATITUDE);
        String attrLATITUDE_Dest_REF = exif.getAttribute(ExifInterface.TAG_GPS_DEST_LATITUDE_REF);

        String attrLONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
        String attrLONGITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

        String attrLONGITUDE_Dest = exif.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE);
        String attrLONGITUDE_Dest_REF = exif.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF);

        int num = 1;

        Log.v(logTag, "- - - - - - - - - - - - - - - - - - - - - - - - - " + num);

        Log.v(logTag, "attrLATITUDE: " + attrLATITUDE);
        Log.v(logTag, "attrLATITUDE_REF: " + attrLATITUDE_REF);

        Log.v(logTag, "attrLATITUDE_Dest: " + attrLATITUDE_Dest);
        Log.v(logTag, "attrLATITUDE_Dest_REF: " + attrLATITUDE_Dest_REF);

        Log.v(logTag, "attrLONGITUDE: " + attrLONGITUDE);
        Log.v(logTag, "attrLONGITUDE_REF: " + attrLONGITUDE_REF);

        Log.v(logTag, "attrLONGITUDE_Dest: " + attrLONGITUDE_Dest);
        Log.v(logTag, "attrLONGITUDE_Dest_REF: " + attrLONGITUDE_Dest_REF);

        Log.v(logTag, "- - - - - - - - - - - - - - - - - - - - - - - - - " + num);

        if ((attrLATITUDE != null)
                && (attrLATITUDE_REF != null)
                && (attrLONGITUDE != null)
                && (attrLONGITUDE_REF != null)) {

            if (attrLATITUDE_REF.equals("N")) {

                latitude = convertToDegree(attrLATITUDE);

            } else {

                latitude = 0 - convertToDegree(attrLATITUDE);
            }

            if (attrLONGITUDE_REF.equals("E")) {

                longitude = convertToDegree(attrLONGITUDE);

            } else {

                longitude = 0 - convertToDegree(attrLONGITUDE);
            }
        }
    }

    private Float convertToDegree(String stringDMS) {

        float result;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = Double.valueOf(stringD[0]);
        Double D1 = Double.valueOf(stringD[1]);
        double FloatD = D0 / D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = Double.valueOf(stringM[0]);
        Double M1 = Double.valueOf(stringM[1]);
        double FloatM = M0 / M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = Double.valueOf(stringS[0]);
        Double S1 = Double.valueOf(stringS[1]);
        double FloatS = S0 / S1;

        result = (float) (FloatD + (FloatM / 60) + (FloatS / 3600));

        return result;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }
}
