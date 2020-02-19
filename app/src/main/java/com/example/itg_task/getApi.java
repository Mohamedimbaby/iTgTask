package com.example.itg_task;

import com.example.itg_task.DTO.Character;
import com.example.itg_task.DTO.work;

import java.util.ArrayList;

public interface getApi {
    public void getData();
    public void getCharacterImage(ArrayList<Character> list);
    public void get_works_details(Character c1, final int flag);
    public void get_work_image(final ArrayList<work> list);
      String Public_Key="cdb8653c2f480017805a38dd0469f2dc";
      String Private_Key="118dd9621a3e1565c4dd0f4bff2c9095a767be74";
      String Api_Link="http://gateway.marvel.com/v1/public/comics?ts=1&apikey=cdb8653c2f480017805a38dd0469f2dc&hash=3a75eb8e5d7dd1ee1325f2cfd4099d88";
      String suffex="comics?ts=1&apikey=cdb8653c2f480017805a38dd0469f2dc&hash=3a75eb8e5d7dd1ee1325f2cfd4099d88";



}
