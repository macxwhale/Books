package com.example.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText etTitle = findViewById(R.id.etTitle);
        final EditText etAuthor = findViewById(R.id.etAuthor);
        final EditText etPublisher = findViewById(R.id.etPublisher);
        final EditText etIsbn = findViewById(R.id.etIsbn);
        final Button button = findViewById(R.id.btSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String author = etAuthor.getText().toString().trim();
                String publisher = etPublisher.getText().toString().trim();
                String isbn = etIsbn.getText().toString().trim();

                if (title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()) {
                    String msg = getString(R.string.no_search_data);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    URL queryURL = ApiUtil.buildURL(title, author, publisher, isbn);

                    Context context = getApplicationContext();
                    int position = SpUtil.getPreferenceInt(context, SpUtil.POSITION);
                    if (position == 0 || position == 5) {
                        position = 1;
                    } else {
                        String key = SpUtil.QUERY + position;
                        String value = title + "," + author + "," + publisher + "," + isbn;
                        SpUtil.setPreferenceString(context, key, value);
                        SpUtil.setPreferenceInt(context, SpUtil.POSITION, position);
                    }

                    Intent intent = new Intent(getApplicationContext(), BookListActivity.class);
                    intent.putExtra("Query", queryURL);
                    startActivity(intent);
                }
            }
        });
    }
}
