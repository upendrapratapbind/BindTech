package com.elearning.bindtech.ui_activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.elearning.bindtech.R;
import com.elearning.bindtech.adapters.NewsListAdapter;
import com.elearning.bindtech.models.NewsData;
import com.elearning.bindtech.utils.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContentsActivity extends AppCompatActivity implements NewsListAdapter.ItemClickListener {

    NewsListAdapter adapter;
    ArrayList<NewsData> newsArray;
    NewsData news;
    RecyclerView recyclerView_General, recyclerView_Health, recyclerView_Sports, recyclerView_Technology;
    String url_General, url_Health, url_Sports, url_Technology;
    TextView tv_general, tv_sports, tv_health, tv_technology;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);
        fetchGeneralData();
        init();
    }

    public void init() {
        tv_general = findViewById(R.id.tv_general);
        tv_sports = findViewById(R.id.tv_sports);
        tv_health = findViewById(R.id.tv_health);
        tv_technology = findViewById(R.id.tv_technology);
        tv_general.setOnClickListener(this::onClick);
        tv_sports.setOnClickListener(this::onClick);
        tv_health.setOnClickListener(this::onClick);
        tv_technology.setOnClickListener(this::onClick);
        recyclerView_General = findViewById(R.id.recyclerView_General);
        recyclerView_Health = findViewById(R.id.recyclerView_Health);
        recyclerView_Sports = findViewById(R.id.recyclerView_Sports);
        recyclerView_Technology = findViewById(R.id.recyclerView_Technology);

    }

    public void initMethodGeneral() {
        tv_general.setBackgroundColor(getColor(R.color.colorWhite));
        tv_general.setTextColor(getColor(R.color.colorOrange));
        tv_sports.setBackgroundColor(getColor(R.color.colorOrange));
        tv_health.setBackgroundColor(getColor(R.color.colorOrange));
        tv_technology.setBackgroundColor(getColor(R.color.colorOrange));
        tv_sports.setTextColor(getColor(R.color.colorWhite));
        tv_health.setTextColor(getColor(R.color.colorWhite));
        tv_technology.setTextColor(getColor(R.color.colorWhite));

        recyclerView_General.setVisibility(View.VISIBLE);
        recyclerView_Health.setVisibility(View.GONE);
        recyclerView_Sports.setVisibility(View.GONE);
        recyclerView_Technology.setVisibility(View.GONE);
        recyclerView_General.setLayoutManager(new LinearLayoutManager(ContentsActivity.this, RecyclerView.VERTICAL, false));
        adapter = new NewsListAdapter(ContentsActivity.this, newsArray);
        adapter.setClickListener(ContentsActivity.this);
        recyclerView_General.setAdapter(adapter);

    }

    public void initMethodHealth() {
        tv_general.setBackgroundColor(getColor(R.color.colorOrange));
        tv_general.setTextColor(getColor(R.color.colorWhite));
        tv_sports.setBackgroundColor(getColor(R.color.colorOrange));
        tv_health.setBackgroundColor(getColor(R.color.colorWhite));
        tv_technology.setBackgroundColor(getColor(R.color.colorOrange));
        tv_sports.setTextColor(getColor(R.color.colorWhite));
        tv_health.setTextColor(getColor(R.color.colorOrange));
        tv_technology.setTextColor(getColor(R.color.colorWhite));
        recyclerView_General.setVisibility(View.GONE);
        recyclerView_Health.setVisibility(View.VISIBLE);
        recyclerView_Sports.setVisibility(View.GONE);
        recyclerView_Technology.setVisibility(View.GONE);
        recyclerView_Health.setLayoutManager(new LinearLayoutManager(ContentsActivity.this, RecyclerView.VERTICAL, false));
        adapter = new NewsListAdapter(ContentsActivity.this, newsArray);
        adapter.setClickListener(ContentsActivity.this);
        recyclerView_Health.setAdapter(adapter);

    }

    public void initMethodSports() {
        tv_general.setBackgroundColor(getColor(R.color.colorOrange));
        tv_general.setTextColor(getColor(R.color.colorWhite));
        tv_sports.setBackgroundColor(getColor(R.color.colorWhite));
        tv_health.setBackgroundColor(getColor(R.color.colorOrange));
        tv_technology.setBackgroundColor(getColor(R.color.colorOrange));
        tv_sports.setTextColor(getColor(R.color.colorOrange));
        tv_health.setTextColor(getColor(R.color.colorWhite));
        tv_technology.setTextColor(getColor(R.color.colorWhite));
        recyclerView_General.setVisibility(View.GONE);
        recyclerView_Health.setVisibility(View.GONE);
        recyclerView_Sports.setVisibility(View.VISIBLE);
        recyclerView_Technology.setVisibility(View.GONE);
        recyclerView_Sports.setLayoutManager(new LinearLayoutManager(ContentsActivity.this, RecyclerView.VERTICAL, false));
        adapter = new NewsListAdapter(ContentsActivity.this, newsArray);
        adapter.setClickListener(ContentsActivity.this);
        recyclerView_Sports.setAdapter(adapter);

    }

    public void initMethodTechnology() {
        tv_general.setBackgroundColor(getColor(R.color.colorOrange));
        tv_general.setTextColor(getColor(R.color.colorWhite));
        tv_sports.setBackgroundColor(getColor(R.color.colorOrange));
        tv_health.setBackgroundColor(getColor(R.color.colorOrange));
        tv_technology.setBackgroundColor(getColor(R.color.colorWhite));
        tv_sports.setTextColor(getColor(R.color.colorWhite));
        tv_health.setTextColor(getColor(R.color.colorWhite));
        tv_technology.setTextColor(getColor(R.color.colorOrange));
        recyclerView_General.setVisibility(View.GONE);
        recyclerView_Health.setVisibility(View.GONE);
        recyclerView_Sports.setVisibility(View.GONE);
        recyclerView_Technology.setVisibility(View.VISIBLE);
        recyclerView_Technology.setLayoutManager(new LinearLayoutManager(ContentsActivity.this, RecyclerView.VERTICAL, false));
        adapter = new NewsListAdapter(ContentsActivity.this, newsArray);
        adapter.setClickListener(ContentsActivity.this);
        recyclerView_Technology.setAdapter(adapter);

    }


    public void fetchGeneralData() {
        url_General = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_General, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                Log.e("success1", "success1");
                                JSONArray newsJsonArray = response.getJSONArray("articles");
                                newsArray = new ArrayList<NewsData>();
                                for (int i = 0; i <= newsJsonArray.length(); i++) {
                                    Log.e("success", "success ful");
                                    JSONObject jsonObject = newsJsonArray.getJSONObject(i);
                                    news = new NewsData(jsonObject.getString("title"), jsonObject.getString("author"), jsonObject.getString("url"), jsonObject.getString("urlToImage"));
                                    newsArray.add(news);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            initMethodGeneral();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void fetchHealthData() {
        url_Health = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_Health, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                Log.e("success1", "success1");
                                JSONArray newsJsonArray = response.getJSONArray("articles");
                                newsArray = new ArrayList<NewsData>();
                                for (int i = 0; i <= newsJsonArray.length(); i++) {
                                    Log.e("success", "success ful");
                                    JSONObject jsonObject = newsJsonArray.getJSONObject(i);
                                    news = new NewsData(jsonObject.getString("title"), jsonObject.getString("author"), jsonObject.getString("url"), jsonObject.getString("urlToImage"));
                                    newsArray.add(news);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            initMethodHealth();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void fetchSportsData() {
        url_Sports = "https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_Sports, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                Log.e("success1", "success1");
                                JSONArray newsJsonArray = response.getJSONArray("articles");
                                newsArray = new ArrayList<NewsData>();
                                for (int i = 0; i <= newsJsonArray.length(); i++) {
                                    Log.e("success", "success ful");
                                    JSONObject jsonObject = newsJsonArray.getJSONObject(i);
                                    news = new NewsData(jsonObject.getString("title"), jsonObject.getString("author"), jsonObject.getString("url"), jsonObject.getString("urlToImage"));
                                    newsArray.add(news);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            initMethodSports();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void fetchTechnologyData() {
        url_Technology = "https://saurav.tech/NewsAPI/top-headlines/category/technology/in.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_Technology, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                Log.e("success1", "success1");
                                JSONArray newsJsonArray = response.getJSONArray("articles");
                                newsArray = new ArrayList<NewsData>();
                                for (int i = 0; i <= newsJsonArray.length(); i++) {
                                    Log.e("success", "success ful");
                                    JSONObject jsonObject = newsJsonArray.getJSONObject(i);
                                    news = new NewsData(jsonObject.getString("title"), jsonObject.getString("author"), jsonObject.getString("url"), jsonObject.getString("urlToImage"));
                                    newsArray.add(news);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            initMethodTechnology();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemClick(View view, int position) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(news.getUrl()));
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_general:

                fetchGeneralData();
                break;

            case R.id.tv_sports:
                tv_sports.setBackgroundColor(getColor(R.color.colorWhite));
                tv_sports.setTextColor(getColor(R.color.colorOrange));
                fetchSportsData();
                break;
            case R.id.tv_health:
                tv_health.setBackgroundColor(getColor(R.color.colorWhite));
                tv_health.setTextColor(getColor(R.color.colorOrange));
                fetchHealthData();
                break;
            case R.id.tv_technology:
                tv_technology.setBackgroundColor(getColor(R.color.colorWhite));
                tv_technology.setTextColor(getColor(R.color.colorOrange));
                fetchTechnologyData();
                break;
        }
    }
}