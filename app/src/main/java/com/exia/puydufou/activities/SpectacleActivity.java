package com.exia.puydufou.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exia.puydufou.R;
import com.exia.puydufou.entity.Spectacle;
import com.exia.puydufou.fragments.MapsFragment;

/**
 * Created by Iseldore on 15/06/2015.
 */
public class SpectacleActivity extends Activity{
    Spectacle spectacle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spectacle);
        spectacle = (Spectacle) getIntent().getSerializableExtra("spectacle");

        TextView nom = (TextView) findViewById(R.id.nomSpectacle);
        TextView infos = (TextView) findViewById(R.id.infoSpectacle);
        Button button = (Button) findViewById(R.id.button);

        nom.setText(spectacle.getNom_spectacle());
        infos.setText(spectacle.getInfo_spectacle());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(SpectacleActivity.this, MapsFragment.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("enlevement", "..");
                t.putExtras(mBundle);
                SpectacleActivity.this.startActivity(t);
            }
        });
    }
}
