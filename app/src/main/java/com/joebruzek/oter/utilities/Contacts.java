package com.joebruzek.oter.utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.joebruzek.oter.models.Contact;

/**
 * Utility class to handle contact operations
 *
 * Right now there are two methods that look almost identical except for some constants.
 * Those constants have to be different and I'm not sure why...
 * I'll refactor it later.
 *
 * Created by jbruzek on 11/20/15.
 */
public class Contacts {

    /**
     * Get a contact from a contact uri
     * @param c
     * @param uri
     * @return a contact model item
     */
    public static Contact getContact(Context c, Uri uri) {
        ContentResolver contentResolver = c.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        cursor.moveToFirst();

        try {
            Contact contact = new Contact(
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            String bitmapString = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
            if (bitmapString != null) {
                Uri imageUri = Uri.parse(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)));
                Bitmap b = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                contact.setPicture(b);
            }
            cursor.close();
            return contact;
        }
        catch (Exception e) {
            Log.e("ERROR", e.getStackTrace()[0].toString());
            e.printStackTrace();
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * Get a contact from a phone number
     * @param c
     * @param phoneNumber
     * @return a contact model item
     */
    public static Contact getContact(Context c, String phoneNumber) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String name = "?";

        ContentResolver contentResolver = c.getContentResolver();
        Cursor cursor = contentResolver.query(contactUri, null, null, null, null);
        cursor.moveToFirst();

        try {
            Contact contact = new Contact(
                    cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.NUMBER)));
            String bitmapString = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
            if (bitmapString != null) {
                Uri imageUri = Uri.parse(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)));
                Bitmap b = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                contact.setPicture(b);
            }
            cursor.close();
            return contact;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * Format a number to just just numbers
     *
     * @param number (999) 999-9999
     * @return 9999999999
     */
    public static String formatNumber(String number) {
        return number.replaceAll("[^0-9]","");
    }
}
