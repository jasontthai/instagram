package com.codepath.instagram.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.SimpleVerticalSpacerItemDecoration;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramPost;
import com.codepath.instagram.networking.InstagramClient;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private ArrayList<InstagramPost> posts;
    private InstagramPostsAdapter adapter;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Fresco.initialize(this);
        posts = new ArrayList<>();
        // Get RecyclerView Reference
        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        RecyclerView.ItemDecoration itemDecoration = new
                SimpleVerticalSpacerItemDecoration(12);
        rvPosts.addItemDecoration(itemDecoration);
        // Create Adapter
        adapter = new InstagramPostsAdapter(posts);

        // Set Adapter
        rvPosts.setAdapter(adapter);

        // Set layout
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        fetchPosts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchPosts() {
        InstagramClient client = new InstagramClient();
        client.getPopularFeed(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response != null) {
                    posts.clear();
                    posts.addAll(Utils.decodePostsFromJsonResponse(response));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Error connecting to network")
                        .setTitle("Network Error");
                builder.create().show();
            }


        });
    }

    public void onCommentsCountClick(View view) {
        Intent i = new Intent();

    }
}
