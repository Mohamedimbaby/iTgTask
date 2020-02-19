package com.example.itg_task.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.itg_task.Ui.Characters_details;
import com.example.itg_task.DTO.Character;
import com.example.itg_task.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharactersAdapter extends BaseAdapter {
List <Character> Characters ;
Context context;
LayoutInflater inflater ;
    public CharactersAdapter(Context con, ArrayList<Character> Characters_list) {
        Characters = Characters_list;
        context=con;
        inflater = LayoutInflater.from(con);
    }

    @Override
    public int getCount() {
        return Characters.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
if (Characters.get(position).isLoading()){
    View view = inflater.inflate(R.layout.loading_character_item,null);
    return view;
}
 else {
       View view = inflater.inflate(R.layout.character_item,null);
        ImageView Character_image = view.findViewById(R.id.character_image);
        TextView Character_name = view.findViewById(R.id.character_name);
        Picasso.get().load(Characters.get(position).getPhoto_img()).placeholder(R.drawable.ic_launcher_background).into(Character_image);
        Character_name.setText(Characters.get(position).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CharactersIntent = new Intent(context, Characters_details.class);
                CharactersIntent.putExtra("name", Characters.get(position).getName());
                CharactersIntent.putExtra("image", Characters.get(position).getPhoto_img());
                CharactersIntent.putExtra("resource", Characters.get(position).getResourceURI());
                context.startActivity(CharactersIntent);
            }
        });
        return view;
    }

}
}
