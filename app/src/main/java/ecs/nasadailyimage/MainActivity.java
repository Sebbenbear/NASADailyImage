package ecs.nasadailyimage;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity {

    private static final String url = "http://www.nasa.gov/rss/dyn/image_of_the_day.rss";

    private Image image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainTask().execute();
    }

    /**
     * This method is used to reset the display on screen after
     * retrieving the image from RSS.
     *
     * @param
     * @return Nothing.
     */
    public void resetDisplay() {

        //##Missing##
        //Update the text content of the TextView widget "imageTitle"
        TextView tv = (TextView) findViewById(R.id.imageTitle);
        tv.setText("NASA Daily Image");

        //##Missing##
        //Update the text content of the TextView widget "imageDate"
        TextView dateText = (TextView) findViewById(R.id.imageDate);
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        tv.setText(formattedDate);

        //##Missing##
        //Update the text content of the TextView widget "imageDescription"
        TextView descriptionText = (TextView) findViewById(R.id.imageDescription);
        descriptionText.setText("Here is the daily image for the day.");

        ////##Missing## Here we missed a WebView widget
        // Update the content of the WebView widget "imageView"
        //Please create a WebView widget on the main layout (i.e., activity_main.xml) to
        //connect with the following commented codes.

        /*
        //Update the content of the WebView widget "imageView"
        WebView imageView = (WebView) findViewById(R.id.imageView);

        //Display the image by its url
        imageView.loadUrl(image.getUrl());
        */
        System.out.println("resetting the display");
        WebView imgView = (WebView) findViewById(R.id.imageView);
        //imgView.loadUrl(image.getUrl());
        imgView.loadUrl(url);
    }

    /**
     * This inner class inherits from AsyncTask which performs background
     * operations and publish results on the UI thread.
     */
    public class MainTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //##Missing##
            //Invoke the function to retrieve the image from NASA RSS feed.
            WebView imgView = (WebView) findViewById(R.id.imageView);
            imgView.loadUrl(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            System.out.println("post-executing");
            //##Missing##
            //Invoke the function to reset display after the latest daily image obtained.
            resetDisplay();
        }

        /**
         * This method is used to retrieve the latest daily image from NASA RSS feed.
         * @param
         * @return Nothing.
         */
        public void processFeed() {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                SAXParserFactory saxParserFactory =
                        SAXParserFactory.newInstance();
                SAXParser parser = saxParserFactory.newSAXParser();
                XMLReader reader = parser.getXMLReader();
                IotdHandler iotdHandler = new IotdHandler();
                reader.setContentHandler(iotdHandler);

                InputStream inputStream = new URL(url).openStream();
                int end = 0;
                do{
                    byte[] b = new byte[3000];
                    end = inputStream.read(b);
                    for (int i = 0; i < end; i++) {
                        stringBuffer.append((char)b[i]);
                    }
                }while(end != -1);
                reader.parse(new InputSource(new ByteArrayInputStream(stringBuffer.toString().getBytes())));

                image = iotdHandler.getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
