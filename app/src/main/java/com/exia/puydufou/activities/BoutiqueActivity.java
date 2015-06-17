package com.exia.puydufou.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.exia.puydufou.R;
import com.exia.puydufou.entity.Boutique;

/**
 * Created by Iseldore on 16/06/2015.
 */
public class BoutiqueActivity extends Activity {
    Boutique boutique;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boutique);
        boutique = (Boutique) getIntent().getSerializableExtra("boutique");

        TextView nom = (TextView) findViewById(R.id.nomBoutique);
        TextView infos = (TextView) findViewById(R.id.infoBoutique);
        nom.setText(boutique.getNomBoutique());
        infos.setText(boutique.getDescriptionBoutique());
    }
}
