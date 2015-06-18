package com.exia.puydufou.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exia.puydufou.R;
import com.exia.puydufou.entity.Boutique;
import com.exia.puydufou.fragments.MapsFragment;

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

        Button button = (Button) findViewById(R.id.buttonboutique);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(BoutiqueActivity.this, MapsFragment.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("object", boutique);
                t.putExtras(mBundle);
                BoutiqueActivity.this.startActivity(t);
            }
        });
    }
}
