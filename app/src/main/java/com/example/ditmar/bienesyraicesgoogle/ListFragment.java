package com.example.ditmar.bienesyraicesgoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ditmar.bienesyraicesgoogle.DATA.DataApp;
import com.example.ditmar.bienesyraicesgoogle.ItemMenu.ItemMenuStructure;
import com.example.ditmar.bienesyraicesgoogle.ItemMenu.MenuBaseAdapter;
import com.example.ditmar.bienesyraicesgoogle.Utils.OnLoadDataComplete;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener{
    //private ArrayList<ItemMenuStructure> LISTDATA;
    private View ROOT;
    private OnLoadDataComplete event;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DataApp.LISTDATA = new ArrayList<ItemMenuStructure>();
        ROOT = inflater.inflate(R.layout.list_fragment, container, false);
        loadData();
        return ROOT;
    }
    public void setOnloadCompleteData (OnLoadDataComplete event) {
        this.event = event;
    }
    private void loadData() {
        AsyncHttpClient client = new AsyncHttpClient();
        if (DataApp.TOKEN == "") {
            //Toast.makeText()
        }
        client.addHeader("authorization", DataApp.TOKEN);
        client.get(DataApp.HOST + "/api/v1.0/home", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray listData = response.getJSONArray("info");
                    for (int i = 0; i < listData.length(); i++) {
                        JSONObject obj = listData.getJSONObject(i);
                        String street = obj.getString("street");
                        Integer price = obj.getInt("price");
                        double lat = obj.getDouble("lat");
                        double lon = obj.getDouble("lon");
                        String contact = obj.getString("contact");
                        String id = obj.getString("_id");
                        //String url = DataApp.HOST + (String)obj.getJSONArray("gallery").get(0);
                        JSONArray listGallery = obj.getJSONArray("gallery");
                        ArrayList<String> urllist =  new ArrayList<String>();
                        for (int j = 0; j < listGallery.length(); j ++) {
                            urllist.add(DataApp.HOST + listGallery.getString(j));
                        }

                        DataApp.LISTDATA.add(new ItemMenuStructure(street, urllist, price, lat, lon, contact, "", "", id,""));
                    }
                    LoadComponents();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void LoadComponents () {

        ListView list = (ListView) ROOT.findViewById(R.id.super_lista);
        MenuBaseAdapter adapter = new MenuBaseAdapter(this.getActivity(), DataApp.LISTDATA);
        list.setAdapter(adapter);
        this.event.OnLodCompleteDataResult();
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent detaild = new Intent(this.getActivity(), DetaildImg.class);
        detaild.putExtra("id", position);
        this.getActivity().startActivity(detaild);
    }
}
