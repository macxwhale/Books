package com.example.books;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// Contains static methods and constants
public class ApiUtil {
    // Create Constructor because this class will never be instantiated
    private ApiUtil() {}
    //Base URL Constant
    public static final String BASE_API_URL =
            "https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY = "q";
    public static final String KEY = "key";
    public static final String API_KEY = "AIzaSyCUtJSwSuH5NN-Ljoaxu-cadQhrq15dPIc";
    public static final URL buildURl (String title) {
        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, title)
                .appendQueryParameter(KEY, API_KEY)
                .build();
        try {
            url = new URL(uri.toString());
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    // Connect to API
    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");

            boolean hasData= scanner.hasNext();

            if (hasData){
                return scanner.next();
            } else {
                return null;
            }
        }

        catch (Exception e) {
            Log.d("Error", e.toString());
            return null;
        }

        finally {
            connection.disconnect();
        }

    }


}