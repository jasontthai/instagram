package com.codepath.instagram.networking;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.text.MessageFormat;

/**
 * Created by trit on 10/28/15.
 */
public class InstagramClient {

    private static final String API_BASE_URL = "https://api.instagram.com/";
    private AsyncHttpClient client;
    private static final String clientId = "02eb101e01884bd0a90e82acdeea3d60";

    private static final String FEED_URL = "v1/media/popular?client_id={0}";
    private static final String COMMENT_URL = "v1/media/{0}/comments?client_id={1}";

    public InstagramClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    public void getPopularFeed(JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl(MessageFormat.format(FEED_URL, clientId));
        client.get(url, responseHandler);
    }

    public void getComments(String mediaId, JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl(MessageFormat.format(COMMENT_URL, mediaId, clientId));
        client.get(url, responseHandler);
    }
}
