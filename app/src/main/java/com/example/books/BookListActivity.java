package com.example.books;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mLoadingProgress = findViewById(R.id.pbLoading);

        try {
            URL bookUrl = ApiUtil.buildURl("cooking");
            new BooksQueryTask().execute(bookUrl);
        }

        catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

    }

    public class BooksQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
           URL searchURL  = urls[0];
           String result = null;
           try {
               result =ApiUtil.getJson(searchURL);
           }

           catch (IOException e) {
               Log.e("Error", e.getMessage());
           }
           return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tvResult = findViewById(R.id.tvResp);
            TextView tvError = findViewById(R.id.tVError);
            mLoadingProgress.setVisibility(View.INVISIBLE);

            if (result == null ) {
                tvResult.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            } else {
                tvResult.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }
            ArrayList<Book> books = ApiUtil.getBooksFromJson(result);
            String resultString = "";
            for (Book book : books) {
                resultString = resultString + book.title + "\n" +
                        book.publishedDate + "\n\n";
            }
            tvResult.setText(resultString);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
