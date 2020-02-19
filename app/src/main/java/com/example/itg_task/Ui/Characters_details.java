package com.example.itg_task.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.itg_task.Adapters.Character_works_adapter;
import com.example.itg_task.ApiCall;
import com.example.itg_task.DTO.Character;
import com.example.itg_task.DTO.work;
import com.example.itg_task.R;
import com.example.itg_task.databinding.ActivityCharactersDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Characters_details extends AppCompatActivity {
    ApiCall Api_call;
    LinearLayoutManager layoutManager = new LinearLayoutManager(Characters_details.this, LinearLayoutManager.HORIZONTAL, false);

    ActivityCharactersDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_characters_details);
        Api_call = ApiCall.getInstance(this, this);
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        String image = extras.getString("image");
        String resource = extras.getString("resource");
        binding.charcName.setText(name);
        Picasso.get().load(image).placeholder(R.drawable.ic_launcher_background).into(binding.characterImage);

        new Character(name, image, resource);
// ================== Comics ==========
        Api_call.get_works_details(new Character(name, resource, image), 1);
        Api_call.getWork_LiveData().observe(this, new Observer<List<work>>() {
            @Override
            public void onChanged(List<work> characters) {
                Api_call.get_work_image((ArrayList<work>) characters);
                Api_call.getImage_work_Live_data().observe(Characters_details.this, new Observer<List<work>>() {
                    @Override
                    public void onChanged(List<work> works) {
                        binding.recyclerView.setLayoutManager(layoutManager);
                        binding.recyclerView.setAdapter(new Character_works_adapter(Characters_details.this, works));
                        Api_call.setWork_LiveData(new MutableLiveData<List<work>>());
                        Api_call.setImage_work_Live_data(new MutableLiveData<List<work>>());
                    }
                });

            }
        });


// ================== Events ==========

        Api_call.get_works_details(new Character(name, resource, image), 2);
        Api_call.getWork_LiveData().observe(this, new Observer<List<work>>() {
            @Override
            public void onChanged(List<work> characters) {
                Api_call.get_work_image((ArrayList<work>) characters);
                Api_call.getImage_work_Live_data().observe(Characters_details.this, new Observer<List<work>>() {
                    @Override
                    public void onChanged(List<work> works) {
                        binding.recyclerViewEvents.setLayoutManager(layoutManager);
                        binding.recyclerViewEvents.setAdapter(new Character_works_adapter(Characters_details.this, works));
                        Api_call.setWork_LiveData(new MutableLiveData<List<work>>());
                        Api_call.setImage_work_Live_data(new MutableLiveData<List<work>>());

                    }
                });

            }
        });


// ================== Series ==========


        Api_call.get_works_details(new Character(name, resource, image), 3);
        Api_call.getWork_LiveData().observe(this, new Observer<List<work>>() {
            @Override
            public void onChanged(List<work> characters) {
                Api_call.get_work_image((ArrayList<work>) characters);
                Api_call.getImage_work_Live_data().observe(Characters_details.this, new Observer<List<work>>() {
                    @Override
                    public void onChanged(List<work> works) {
                        binding.recyclerViewSeries.setLayoutManager(layoutManager);
                        binding.recyclerViewSeries.setAdapter(new Character_works_adapter(Characters_details.this, works));
                        Api_call.setWork_LiveData(new MutableLiveData<List<work>>());
                        Api_call.setImage_work_Live_data(new MutableLiveData<List<work>>());

                    }
                });

            }
        });


// ================== Stories ==========
        Api_call.get_works_details(new Character(name, resource, image), 4);
        Api_call.getWork_LiveData().observe(this, new Observer<List<work>>() {
            @Override
            public void onChanged(List<work> characters) {
                Api_call.get_work_image((ArrayList<work>) characters);
                Api_call.getImage_work_Live_data().observe(Characters_details.this, new Observer<List<work>>() {
                    @Override
                    public void onChanged(List<work> works) {
                        binding.recyclerViewStories.setLayoutManager(layoutManager);
                        binding.recyclerViewStories.setAdapter(new Character_works_adapter(Characters_details.this, works));
                        Api_call.setWork_LiveData(new MutableLiveData<List<work>>());
                        Api_call.setImage_work_Live_data(new MutableLiveData<List<work>>());

                    }
                });

            }
        });

    }
}
