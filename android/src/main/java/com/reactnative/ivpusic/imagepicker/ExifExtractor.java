package com.reactnative.ivpusic.imagepicker;

import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.util.Log;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.exifinterface.media.ExifInterface.*;

class ExifExtractor {

    static WritableMap extract(String path, Activity activity) throws IOException {

        final String logTag = LogTag.TAG;

        WritableMap exifData = new WritableNativeMap();

        List<String> attributes = getBasicAttributes();

        attributes.addAll(getLevel23Attributes());

        ExifInterface exif = null;

        try {

            exif = new ExifInterface(path);

            GeoDegree geoDegree = new GeoDegree(exif);

            if (geoDegree.getLatitude() != null && geoDegree.getLongitude() != null) {

                exifData.putDouble("Latitude", geoDegree.getLatitude());
                exifData.putDouble("Longitude", geoDegree.getLongitude());
            }

        } catch (Exception e) {

            Log.e(logTag, "Unable to open input stream", e);
        }

        if (exif != null) {

            for (String attribute : attributes) {

                String value = exif.getAttribute(attribute);
                exifData.putString(attribute, value);
            }
        }

        try {

            Metadata metadata = ImageMetadataReader.readMetadata(new File(path));

            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {

                    String name = tag.getTagName();
                    String value = tag.getDescription();

                    // exifData.putString(name, value);

                    Log.v(logTag, ">> " + name + ": " + value);
                }
            }

        } catch (Exception e) {

            Log.e(logTag, "Unable to read metadata", e);
        }

        return exifData;
    }

    private static List<String> getBasicAttributes() {

        return new ArrayList<>(Arrays.asList(

                TAG_APERTURE_VALUE,
                TAG_DATETIME,
                TAG_EXPOSURE_TIME,
                TAG_FLASH,
                TAG_FOCAL_LENGTH,
                TAG_GPS_ALTITUDE,
                TAG_GPS_ALTITUDE_REF,
                TAG_GPS_DATESTAMP,
                TAG_GPS_LATITUDE,
                TAG_GPS_LATITUDE_REF,
                TAG_GPS_LONGITUDE,
                TAG_GPS_LONGITUDE_REF,
                TAG_GPS_PROCESSING_METHOD,
                TAG_GPS_TIMESTAMP,
                TAG_IMAGE_LENGTH,
                TAG_IMAGE_WIDTH,
                TAG_ISO_SPEED,
                TAG_MAKE,
                TAG_MODEL,
                TAG_ORIENTATION,
                TAG_WHITE_BALANCE
        ));
    }

    private static List<String> getLevel23Attributes() {

        return new ArrayList<>(Arrays.asList(

                TAG_DATETIME_DIGITIZED,
                TAG_SUBSEC_TIME,
                TAG_SUBSEC_TIME_DIGITIZED,
                TAG_SUBSEC_TIME_ORIGINAL
        ));
    }
}