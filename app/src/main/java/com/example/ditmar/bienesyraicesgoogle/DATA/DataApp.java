package com.example.ditmar.bienesyraicesgoogle.DATA;

import com.example.ditmar.bienesyraicesgoogle.ItemMenu.ItemMenuStructure;

import java.util.ArrayList;

public class DataApp {
    static public ArrayList<ItemMenuStructure> LISTDATA;
    static public String HOST = "http://192.168.43.22:7777";
    static public String REST_USER_POST = HOST + "/api/v1.0/home";
    static public String REST_USERIMG_POST = HOST + "/api/v1.0/homeimg";

    static public String REST_HOME_PATCH = HOST + "/api/v1.0/home";
}
