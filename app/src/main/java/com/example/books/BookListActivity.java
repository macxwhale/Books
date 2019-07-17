package com.example.books;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private ProgressBar mLoadingProgress;
    private RecyclerView rvBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mLoadingProgress = findViewById(R.id.pbLoading);
        rvBooks = findViewById(R.id.rv_BooksList);

        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        rvBooks.setLayoutManager(booksLayoutManager);

        try {
            URL bookUrl = ApiUtil.buildURl("science");
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

            TextView tvError = findViewById(R.id.tVError);
            mLoadingProgress.setVisibility(View.INVISIBLE);

            if (result == null ) {
                rvBooks.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            } else {
                rvBooks.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }
            ArrayList<Book> books = ApiUtil.getBooksFromJson(result);
            String resultString = "";

            BooksAdapter adapter = new BooksAdapter(books);
            rvBooks.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
