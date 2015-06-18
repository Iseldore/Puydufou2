package com.exia.puydufou.business;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.text.method.DateTimeKeyListener;

import com.exia.puydufou.R;
import com.exia.puydufou.activities.SpectacleActivity;
import com.exia.puydufou.common.AsyncResponse;
import com.exia.puydufou.entity.Spectacle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

/**
 * Created by Iseldore on 17/06/2015.
 */
public class ServiceNotif extends Service implements AsyncResponse{
    private LoadSpectacleFragment asyncTask = new LoadSpectacleFragment();
    public final static String ACTION = "NotifyServiceAction";
    final static String STOP_SERVICE = "";
    public final static int RQS_STOP_SERVICE = 1;

    private Context context;

    private NotifyServiceReceiver notifyServiceReceiver;

    @Override
    public void onLoadList(Object liste) {
        final List<Spectacle> list = (List<Spectacle>) liste;

        new Thread(new Runnable(){
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void run() {
                // TODO Auto-generated method stub
                Intent intent1;
                while (true) {
                    System.out.println("Connexion OK");
                    if (list != null && list.size() > 0) {
                        //System.out.println("Liste > 0");
                        for (int i = 0; i < list.size(); i++) {
                            Spectacle spectacle = list.get(i);
                            if(!spectacle.getIsNotified()) {
                                android.text.format.DateFormat df = new android.text.format.DateFormat();
                                String heure = df.format("HH:mm", spectacle.getHoraires()).toString();

                                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                                Date oldDate = null;
                                try {
                                    oldDate = dateFormat.parse(heure);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date cDate = new Date();
                                Long timeDiff = cDate.getTime() - oldDate.getTime();
                                int hh, mm, day;
                                day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
                                hh = (int) (TimeUnit.MILLISECONDS.toHours(timeDiff) + 1 - TimeUnit.DAYS.toHours(day));
                                mm = (int) (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));

                                if (hh >= 24)
                                    hh = 0;
                                System.err.println(hh + " hour " + mm + " min");
                                if (hh == 23 && mm == 55) {
                                    spectacle.setIsNotified(true);
                                    System.err.println("Notif pour : " + heure);
                                    IntentFilter intentFilter = new IntentFilter();
                                    intentFilter.addAction(ACTION);
                                    registerReceiver(notifyServiceReceiver, intentFilter);

                                    intent1 = new Intent(ServiceNotif.this, SpectacleActivity.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent1.putExtra("spectacle", spectacle);

                                    PendingIntent pIntent = PendingIntent.getActivity(ServiceNotif.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                                    // Build notification
                                    Notification noti = new Notification.Builder(ServiceNotif.this)
                                            .setContentTitle("Puy Du Fou")
                                            .setContentText(spectacle.getNom_spectacle() + " commence dans 5 minutes !")
                                            .setSmallIcon(R.drawable.ic_launcher)
                                            .setContentIntent(pIntent)
                                            .build();
                                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    // Hide the notification after its selected
                                    noti.flags |= Notification.FLAG_AUTO_CANCEL;
                                    notificationManager.notify(0, noti);
                                }
                            }
                        }
                    }
                    else{
                        //System.out.println("Liste vide");
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public class NotifyServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            int rqs = arg1.getIntExtra("RQS", 0);
            if (rqs == RQS_STOP_SERVICE){
                stopSelf();
            }
        }
    }


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        notifyServiceReceiver = new NotifyServiceReceiver();
        context = ServiceNotif.this;
        this.asyncTask.delegate = this;
        this.asyncTask.execute();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private class LoadSpectacleFragment extends AsyncTask<Void, Void, List<Spectacle>> {
        private ProgressDialog dialog;
        public AsyncResponse delegate=null;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<Spectacle> doInBackground(Void... params) {
            InfosPDF infos = new InfosPDF(context);
            List<Spectacle> list = infos.getAllSpectacles();
            return list;
        }

        @Override
        protected void onPostExecute(List<Spectacle> result) {
            delegate.onLoadList(result);
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        this.unregisterReceiver(notifyServiceReceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
