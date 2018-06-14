package com.example.ditmar.bienesyraicesgoogle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.ditmar.cameraapp.utils.ParamsConnection;
//import com.example.ditmar.cameraapp.utils.UserData;
import com.example.ditmar.bienesyraicesgoogle.DATA.DataApp;
import com.example.ditmar.bienesyraicesgoogle.DATA.UserData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FormHomes extends AppCompatActivity implements View.OnClickListener {

    private Context root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        root = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_homes);
        LoadComponents();
    }
    private void LoadComponents() {
        Button btn = (Button)this.findViewById(R.id.sendbtn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView street = (TextView)this.findViewById(R.id.street);
        TextView price = (TextView)this.findViewById(R.id.price);
        TextView description = (TextView)this.findViewById(R.id.description);
        TextView city = (TextView)this.findViewById(R.id.ciudad);
        TextView neighborhood = (TextView)this.findViewById(R.id.barrio);


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("street", street.getText());
        params.put("description", description.getText());
        params.put("price", price.getText());
        params.put("neighborhood", neighborhood.getText());
        params.put("city", city.getText());
        params.put("contact", "75721227");
        params.put("lat", "");
        params.put("lon", "");


        client.post(DataApp.REST_USER_POST, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String msn = response.getString("msn");
                    String id = response.getString("id");
                    UserData.ID = id;
                    if (msn != null) {

                        Intent camera = new Intent(root, CameraForm.class);
                        root.startActivity(camera);
                    } else {
                        Toast.makeText(root, "ERROR AL enviar los datos", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
            }
        });

    }
}
