package com.example.itg_task;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.itg_task.DTO.Character;
import com.example.itg_task.DTO.work;
import com.example.itg_task.getApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ApiCall implements getApi {
    Context context;
    LifecycleOwner lifecycleOwner;
 static ApiCall Api_call;
    public MutableLiveData<List<Character>> getCharcters_LiveData() {
        return Charcters_LiveData;
    }


     RequestQueue queue;

    private MutableLiveData<List<Character>> Charcters_LiveData;

    public MutableLiveData<List<work>> getWork_LiveData() {
        return work_LiveData;
    }

    public void setWork_LiveData(MutableLiveData<List<work>> work_LiveData) {
        this.work_LiveData = work_LiveData;
    }

    private MutableLiveData<List<work>> work_LiveData;

    public MutableLiveData<List<Character>> getImage_Live_data() {
        return image_Live_data;
    }

    public  MutableLiveData<List<Character>> image_Live_data;
    public  MutableLiveData<List<work>> image_work_Live_data;

    public MutableLiveData<List<work>> getImage_work_Live_data() {
        return image_work_Live_data;
    }

    public void setImage_work_Live_data(MutableLiveData<List<work>> image_work_Live_data) {
        this.image_work_Live_data = image_work_Live_data;
    }

    private ApiCall(Context context, LifecycleOwner activivty) {
        this.context = context;
        Charcters_LiveData = new MutableLiveData<>();
        image_Live_data = new MutableLiveData<>();
        work_LiveData = new MutableLiveData<>();
        image_work_Live_data = new MutableLiveData<>();
        lifecycleOwner = activivty;
        queue = Volley.newRequestQueue(context);

    }

public static  ApiCall getInstance(Context context, LifecycleOwner activivty){
        if (Api_call==null){
            return new ApiCall( context,  activivty);
        }
        else return Api_call;
}
    @Override
    public void getData() {

        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, Api_Link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final ArrayList<Character> Characters = new ArrayList<>();

                    JSONArray results = response.getJSONObject("data").getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONArray items = results.getJSONObject(i).getJSONObject("characters").getJSONArray("items");
                        for (int j = 0; j < items.length(); j++) {
                            final String name = items.getJSONObject(j).getString("name");
                            final String resourceURI = items.getJSONObject(j).getString("resourceURI");
                            //getCharacterImage(resourceURI);
                            Characters.add(new Character(name, resourceURI,null ,false));

                        }

                    }
                    Charcters_LiveData.setValue(Characters);
                   } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Charcters_LiveData.setValue(null);
            }
        });
        queue.add(newRequest);
    }

    @Override
 public void getCharacterImage(final ArrayList<Character> list) {
        final ArrayList<String > photo_links = new ArrayList<>();
         for (int i= 0; i <list.size() ; i++) {

             final int finalI1 = i;
             final JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, list.get(i).getResourceURI() + suffex, null, new Response.Listener<JSONObject>() {
                 @Override
                 public void onResponse(JSONObject response) {
                     try {
                         JSONArray results = response.getJSONObject("data").getJSONArray("results");
                         for (int j = 0; j < results.length(); j++) {
                             JSONObject thumbnail = results.getJSONObject(j).getJSONObject("thumbnail");
                             String path = thumbnail.getString("path");
                             String extension = thumbnail.getString("extension");
                             list.get(finalI1).setPhoto_img(path+"."+extension);
                             photo_links.add(path+"."+extension);
                    }


                         if (photo_links.size()==list.size()){
                             image_Live_data.setValue(list);
                         }
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {

                 }
             });
             queue.add(newRequest);
         }

     }



    public void get_works_details(Character c1, final int flag) {
        final ArrayList<work> works_comics = new ArrayList<>();

            final JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, c1.getResourceURI() + suffex, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray results = response.getJSONObject("data").getJSONArray("results");
                        for (int j = 0; j < results.length(); j++) {
                            JSONObject categorie ;

                            if (flag==1){categorie=results.getJSONObject(j).getJSONObject("comics");}
                            else if (flag==2){categorie=results.getJSONObject(j).getJSONObject("events");}
                            else if (flag==3){categorie=results.getJSONObject(j).getJSONObject("series");}
                            else {categorie=results.getJSONObject(j).getJSONObject("stories");}
                            Integer available = categorie.getInt("available");
                            if(available>0)
                            {
                                JSONArray items = categorie.getJSONArray("items");
                                for (int i = 0; i <items.length() ; i++) {
                                    String name = items.getJSONObject(i).getString("name");
                                    String resourceURI = items.getJSONObject(i).getString("resourceURI");
                                    works_comics.add( new work(name, resourceURI));
                                }
                            }

                            }
                        work_LiveData.setValue(works_comics);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(newRequest);
        }
    public void get_work_image(final ArrayList<work> list) {
        final ArrayList<String > photo_links = new ArrayList<>();
        for (int i= 0; i <list.size() ; i++) {

            final int finalI1 = i;
            final JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, list.get(i).getResourceURI() + suffex, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray results = response.getJSONObject("data").getJSONArray("results");
                        for (int j = 0; j < results.length(); j++) {
                            JSONObject thumbnail = results.getJSONObject(j).getJSONObject("thumbnail");
                            String path = thumbnail.getString("path");
                            String extension = thumbnail.getString("extension");
                            list.get(finalI1).setImage(path+"."+extension);
                            photo_links.add(path+"."+extension);
                        }


                        if (photo_links.size()==list.size()){
                            image_work_Live_data.setValue(list);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(newRequest);
        }

    }

    }


