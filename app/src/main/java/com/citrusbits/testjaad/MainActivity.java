package com.citrusbits.testjaad;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.jaad.mp4.GoProUtil;
import net.sourceforge.jaad.mp4.boxes.impl.GoProTagsBox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder stringBuilder = new StringBuilder();
                try {
                    String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GOPR0175.MP4";
                    GoProTagsBox tags = GoProUtil.getHilights(new RandomAccessFile(videoPath, "r"));
//                    InputStream inputStream = new BufferedInputStream(new URL("http://localhost:6582?BRIDGE&%2FGOPR0175.MP4&GOPR0175.MP4&80898399").openConnection().getInputStream());
//                    GoProTagsBox tags = GoProUtil.getHilights(inputStream);
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//use one of overloaded setDataSource() functions to set your data source
                    retriever.setDataSource(getApplicationContext(), Uri.fromFile(new File(videoPath)));
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long timeInMillisec = Long.parseLong(time );
                    stringBuilder.append("DURATION : "+timeInMillisec);
                    stringBuilder.append("\nGoPro Hi light count: "+tags.getCount());
                    if(tags.getHiLights() != null){
                        for (long l : tags.getHiLights()) {
                            stringBuilder.append("\nHiLight: "+l);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    final StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    stringBuilder.append("Exception: "+sw.toString());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(stringBuilder.toString());
                    }
                });

            }
        }).start();
    }
}
