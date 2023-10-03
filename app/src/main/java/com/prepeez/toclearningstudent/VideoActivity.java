package com.prepeez.toclearningstudent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    public static String TEST_URL;

    private VideoView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Grabs a reference to the player view
        player = findViewById(R.id.player);

        // Sets the source to the HTTP URL held in the TEST_URL variable.
        // To play files, you can use Uri.fromFile(new File("..."))
        player.setVideoURI(Uri.parse("android.resource://" +getPackageName()+ "/"+R.raw.breadboarding));

        if (savedInstanceState == null) {
            int seekposition = this.getIntent().getIntExtra("seekposition", 2);
            player.seekTo(seekposition);
        }

        // From here, the player view will show a progress indicator until the player is prepared.
        // Once it's prepared, the progress indicator goes away and the controls become enabled for the user to begin playback.
//        float vieoProportion = (float) player.getMeasuredHeight() / (float) player.getMeasuredWidth();
//        ViewGroup.LayoutParams lp = player.getLayoutParams();
//        lp.height = (int) ((float) lp.width * vieoProportion);
//        player.setLayoutParams(lp);
    }
}
