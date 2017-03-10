package com.david.doubleprocess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //启动MainService
    Intent mainIntent = new Intent(this, MainService.class);
    startService(mainIntent);
  }
}
