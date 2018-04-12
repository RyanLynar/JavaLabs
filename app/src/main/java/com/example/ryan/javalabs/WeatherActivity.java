package com.example.ryan.javalabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class WeatherActivity extends Activity {

    private String myURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ProgressBar p = findViewById(R.id.wProgBar);
        p.setVisibility(View.VISIBLE);

        class ForecastQuery extends AsyncTask<String,Integer,String> {
            private String windSpeed, cTemp, minTemp, maxTemp;
            private Bitmap weatherImage;

            @Override
            protected String doInBackground(String... test) {
                XmlPullParser parser = Xml.newPullParser();
                try {
                    URL toConnect = new URL(myURL);
                    HttpURLConnection connection = (HttpURLConnection) toConnect.openConnection();
                    parser.setInput(connection.getInputStream(), "UTF-8");
                    while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                        if(parser.getEventType() == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("temperature")) {
                                cTemp = parser.getAttributeValue(null, "value");
                                minTemp = parser.getAttributeValue(null, "min");
                                maxTemp = parser.getAttributeValue(null, "max");
                                publishProgress(25);
                            }
                            else if(parser.getName().equals("wind")){
                                parser.next();
                                windSpeed = parser.getAttributeValue(null,"value");
                                publishProgress(50);
                            }
                            else if(parser.getName().equals("weather")){
                               File f = new File(parser.getAttributeValue(null,"icon")+".png");
                               if(!f.exists()) {

                                   Log.i("WEATHER" , "GettingImageFromWEB");
                                   String imgURL = "http://openweathermap.org/img/w/" + parser.getAttributeValue(null, "icon") + ".png";
                                   HttpURLConnection imageConnect = (HttpURLConnection) new URL(imgURL).openConnection();
                                   weatherImage = BitmapFactory.decodeStream(imageConnect.getInputStream());
                                   FileOutputStream imgOut= openFileOutput(parser.getAttributeValue(null,"icon")+".png", Context.MODE_PRIVATE);
                                   weatherImage.compress(Bitmap.CompressFormat.PNG,80,imgOut);
                                   imgOut.flush();
                                   imgOut.close();
                                   imageConnect.disconnect();
                                   publishProgress(100);
                               }else{
                                   Log.i("WEATHER" , "GettingImageFromStorage");
                                    weatherImage = BitmapFactory.decodeFile(parser.getAttributeValue(null,"icon"));
                                    publishProgress(100);

                                }
                               }
                        }
                        parser.next();
                    }

                    connection.disconnect();
                } catch (MalformedURLException e) {
                    Log.i("Connection Issue", e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return "Done";
            }

            @Override
            protected void onProgressUpdate(Integer... t) {
                super.onProgressUpdate(t);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                p.setVisibility(View.INVISIBLE);
                TextView tempCurr = findViewById(R.id.cTemp);
                TextView miTemp = findViewById(R.id.minTemp);
                TextView maTemp = findViewById(R.id.maxTemp);
                TextView wSpeed = findViewById(R.id.windSpd);
                ImageView wImg = findViewById(R.id.imageView3);
                tempCurr.setText(cTemp);
                maTemp.setText(maxTemp);
                miTemp.setText(minTemp);
                wSpeed.setText(windSpeed);
                wImg.setImageBitmap(weatherImage);

            }

        }
        new ForecastQuery().execute(myURL);
    }
}