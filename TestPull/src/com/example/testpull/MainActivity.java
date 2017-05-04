package com.example.testpull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.res.AssetManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AssetManager assetManager = getAssets();
		InputStream is = null;
		try {
			is = assetManager.open("test.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.e("TAG",is.toString());
		List<Person> persons = new ParserUtils().pull(is);
		for (Person person : persons) {
			Log.e("TAG", "person===" + person);
		}
	}

}
