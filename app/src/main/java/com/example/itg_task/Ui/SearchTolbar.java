package com.example.itg_task.Ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.itg_task.Adapters.CharactersAdapter;
import com.example.itg_task.ApiCall;
import com.example.itg_task.DTO.Character;
import com.example.itg_task.R;
import com.example.itg_task.databinding.ActivitySearchTolbarBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchTolbar extends AppCompatActivity implements SearchView.OnQueryTextListener {
    List <Character> Searched = new ArrayList<>();
    ArrayList <Character> newList = new ArrayList<>();
    ArrayList <Character> temporary = new ArrayList<>();
    ExecutorService background_Thread;
    ActivitySearchTolbarBinding binding;
    ApiCall Api_call;
    CharactersAdapter arrayAdapter;
    int end =0;
boolean FIRST_TIME =false;
void add_temporary(int start,int end){
    for (int i = start+1; i <= end; i++) {
        temporary.add(newList.get(i));
    }
    FIRST_TIME=true;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_tolbar);

        Api_call =  ApiCall.getInstance(this,this);
        temporary.add(new Character(null,null,null,true));
        fillListView(temporary);
        binding.nameslist.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount==totalItemCount&&FIRST_TIME)
                {
                    if (totalItemCount<newList.size())
                    {

                        temporary.remove(temporary.size()-1);
                        add_temporary(end,end+3);
                        end+=3;
                        temporary.add(new Character(null,null,null,true));
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fillListView(temporary);
                        arrayAdapter.notifyDataSetChanged();
                    }
                    else {
                        temporary.remove(newList.size()-1);
                    }
                }
            }
        });
        background_Thread = Executors.newFixedThreadPool(2);
        background_Thread.execute(new Runnable() {
    @Override
    public void run() {

        Api_call.getData();
    }
});
      Api_call.getCharcters_LiveData().observe(this, new Observer<List<Character>>() {
    @Override
    public void onChanged(List<Character> characters) {
        temporary.remove(0);
        arrayAdapter.notifyDataSetChanged();
        if (characters!=null){
        remove_duplication(characters);
        Api_call.getCharacterImage(newList);
        Api_call.getImage_Live_data().observe(SearchTolbar.this, new Observer<List<Character>>() {
    @Override
    public void onChanged(List<Character> characters) {
        end+=3;
        add_temporary(0,end);
        fillListView(temporary);
        temporary.add(new Character("90",null,null,true));
        arrayAdapter.notifyDataSetChanged();

    }
});
        }
        else {
            Toast.makeText(SearchTolbar.this, "Failing in  Loading", Toast.LENGTH_SHORT).show();
        }
    }
});
    
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main,menu);
       androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_searchable));
       searchView.setOnQueryTextListener(this);
       return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.showAll)
        {
            fillListView(newList);
        }
        else if(item.getItemId()==R.id.Exit)
        {
            finish();
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Search(newText);
        return false;

    }
  void  fillListView( List<Character> list){

      arrayAdapter = new CharactersAdapter(this, (ArrayList<Character>) list);
      binding.nameslist.setAdapter(arrayAdapter);

  }
void Search ( String term ){
        Searched.clear();
    for (int i = 0; i < newList.size(); i++) {
        String item = newList.get(i).getName();

        if (item.contains(term))
        {
        Searched.add(newList.get(i));
        }
        fillListView(Searched);
FIRST_TIME=false;
    }
}
void remove_duplication(List<Character> characters)
 {
     for (int i = 0; i < characters.size(); i++) {
       if (!check_duplication(characters.get(i))) {
           newList.add(characters.get(i));
       }
     }

 }
   boolean check_duplication( Character character)
    {
        boolean duplicated = false;
        for (int i = 0; i < newList.size(); i++) {
            if (newList.get(i).getName().equals(character.getName())) {
                duplicated=true;
                break;
            }
        }
        return duplicated;

    }




}
