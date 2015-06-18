package com.exia.puydufou.activities;

/**
 * Created by Iseldore on 18/06/2015.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.exia.puydufou.R;
import com.exia.puydufou.business.InfosPDF;
import com.exia.puydufou.common.AsyncResponse;
import com.exia.puydufou.entity.Spectacle;
import com.exia.puydufou.entity.TaskObject;

import java.util.List;

public class RatingActivity extends Activity {

    private RatingBar ratingBar;
    private TextView ratingValue;
    private Button button;
    private Context context;
    private Spectacle spectacle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);

        this.spectacle = (Spectacle) getIntent().getSerializableExtra("spectacle");

        TextView nom = (TextView) findViewById(R.id.lblRateMe);
        nom.setText("Noter le spectacle "+spectacle.getNom_spectacle());

        this.context = RatingActivity.this;

        addListenerOnRatingBar();
        addListenerOnButton();

    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                ratingValue.setText("("+String.valueOf(rating)+")");

            }
        });
    }

    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        button = (Button) findViewById(R.id.button);

        //if click on me, then display the current rating value.
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new RateSpectacle().execute(String.valueOf(ratingBar.getRating()));
                //Toast.makeText(RatingActivity.this, String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();
                finish();
            }

        });

    }

    private class RateSpectacle extends AsyncTask<String, Void, Void> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Chargement");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            InfosPDF infos = new InfosPDF(context);
            double note = infos.setNote(spectacle.getId_spectacle(),params[0]);
            spectacle.setNote_moy(note);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Intent t = new Intent(context, SpectacleActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("spectacle", spectacle);
            t.putExtras(args);
            context.startActivity(t);
        }
    }
}