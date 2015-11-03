package com.codepath.instagram.networking;


import android.content.Context;

import com.codepath.instagram.helpers.Constants;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.scribe.builder.api.Api;

import java.text.MessageFormat;

/**
 * Created by trit on 10/28/15.
 */
public class InstagramClient extends OAuthBaseClient {

    public static final Class<? extends Api> REST_API_CLASS = InstagramApi.class;
    public static final String REST_URL = "https://api.instagram.com/v1";
    public static final String REST_CONSUMER_KEY = "7f5321002cc04089b778e463cd87953f";
    public static final String REST_CONSUMER_SECRET = "a9980e6933814fd3848dba9f6b370b63";

    private static final String FEED_URL = "media/popular";
    private static final String COMMENT_URL = "media/{0}/comments";
    private static final String USER_FEED_URL = "users/self/feed/";
    private static final String USERS_SEARCH_URL = "users/search";
    private static final String TAGS_SEARCH_URL = "tags/search";
    private static final String USER_RECENT_FEED_URL = "users/{0}/media/recent";
    private static final String TAG_RECENT_FEED_URL = "tags/{0}/media/recent";

    private static SyncHttpClient syncHttpClient = new SyncHttpClient();

    public InstagramClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, Constants.REDIRECT_URI, Constants.SCOPE);
    }

    public void getPopularFeed(JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl(FEED_URL);
        client.get(url, getDefaultRequestParams(), responseHandler);
    }

    public void getUserFeed(JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl(USER_FEED_URL);
        RequestParams params = new RequestParams("access_token", client.getAccessToken().getToken());
        client.get(url, params, responseHandler);
    }

    public void getUserFeedSynchronously(JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl(USER_FEED_URL);
        RequestParams params = getDefaultRequestParams();
        params.put("access_token", client.getAccessToken().getToken());
        syncHttpClient.get(url, params, responseHandler);
    }

    public void getUserSearch(String searchTerm, JsonHttpResponseHandler responseHandler) {
        RequestParams params = getDefaultRequestParams();
        params.put("q", searchTerm);
        client.get(getApiUrl(USERS_SEARCH_URL), params, responseHandler);
    }

    public void getComments(String mediaId, JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl(MessageFormat.format(COMMENT_URL, mediaId));
        RequestParams params = getDefaultRequestParams();
        params.put("access_token", client.getAccessToken().getToken());
        client.get(url, params, responseHandler);
    }

    public void getTagSearch(String searchTerm, JsonHttpResponseHandler responseHandler) {
        RequestParams params = getDefaultRequestParams();
        params.put("q", searchTerm);
        client.get(getApiUrl(TAGS_SEARCH_URL), params, responseHandler);
    }

    public void getTagRecentMedia(String tag, JsonHttpResponseHandler responseHandler) {
        RequestParams params = getDefaultRequestParams();
        params.put("access_token", client.getAccessToken().getToken());
        client.get(getApiUrl(MessageFormat.format(TAG_RECENT_FEED_URL, tag)), params, responseHandler);
    }

    public void getUserRecentMedia(String userId, JsonHttpResponseHandler responseHandler) {
        RequestParams params = getDefaultRequestParams();
        params.put("access_token", client.getAccessToken().getToken());
        client.get(getApiUrl(MessageFormat.format(USER_RECENT_FEED_URL, userId)), params, responseHandler);
    }

    public static RequestParams getDefaultRequestParams() {
        RequestParams params = new RequestParams();
        params.put("client_id", REST_CONSUMER_KEY);
        return params;
    }
}
