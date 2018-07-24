package trivial.speckmussweg;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import com.github.jorgecastillo.FillableLoader;
import trivial.speckmussweg.database.MyDatabase;
import trivial.speckmussweg.database.SVGPath;
import trivial.speckmussweg.internet.*;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import static android.support.constraint.Constraints.TAG;

public class Training_main extends Fragment {



    Button btnStart;
    Button btnPause;
    Button btnResume;
    AlertDialog.Builder alertDialog;
    TextView sportTextView;
    TextView trainingTime;
    TextView trainingCalories;
    TextView burnedCalories;
    private ProgressDialog pDialog;
    private static String url = "http://thelegendsrising.de/sports.json";
    ArrayList<String> sportNameList;
    ArrayList<String> sportMultiList;
    MyDatabase database;
    Cursor cursor;
    FillableLoader fillableLoader;
    CountDownTimer timer;
    long milliLeft;
    boolean isPause;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View viewMain = inflater.inflate(R.layout.fragment_training_main_new, container, false);
        fillableLoader = viewMain.findViewById(R.id.home_fillableLoader);

        sportNameList = new ArrayList<String>();
        sportMultiList = new ArrayList<String>();
        database = new MyDatabase(getActivity());
        cursor = database.selectProfile(1);
        isPause = false;

        alertDialog = new AlertDialog.Builder(getActivity());
        sportTextView = viewMain.findViewById(R.id.training_sport_textview);
        btnStart = viewMain.findViewById(R.id.training_btn_start_new);
        btnPause = viewMain.findViewById(R.id.training_btn_pause);
        btnResume = viewMain.findViewById(R.id.training_btn_resume);
        trainingTime = viewMain.findViewById(R.id.training_time);
        trainingCalories = viewMain.findViewById(R.id.training_calories);
        burnedCalories = viewMain.findViewById(R.id.burnedCalories);

        trainingCalories.setText(String.valueOf(Home.kcalSum));

        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
                LayoutInflater inflater = getLayoutInflater();

                // create view for add item in dialog
                View convertView = (View) inflater.inflate(R.layout.listview_training_sport, null);

                // on dialog cancel button listener
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                // add custom view in dialog
                alertDialog.setView(convertView);
                ListView lv = (ListView) convertView.findViewById(R.id.listview_sport);
                final AlertDialog alert = alertDialog.create();
                alert.setTitle("Choose your Sport you fat fuck!");
                MyAdapter myadapter = new MyAdapter(getActivity(), R.layout.listview_item, sportNameList);
                lv.setAdapter(myadapter);

                lv.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        sportTextView.setText(sportNameList.get(position));
                        btnStart.setVisibility(View.GONE);
                        btnResume.setVisibility(View.GONE);
                        btnPause.setVisibility(View.VISIBLE);
                        long timeForSport = calcTime();
                        fillableLoader.reset();
                        fillableLoader.start();
                        timerStart(timeForSport);
                        alert.cancel();
                    }
                });
                // show dialog
                alert.show();
            }
        });

        btnPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPause.setVisibility(View.GONE);
                btnResume.setVisibility(View.VISIBLE);
                timerPause();
            }
        });

        btnResume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResume.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                timerResume();
            }
        });

        fillableLoader.setSvgPath(SVGPath.NEW_FAT_PIG);
        fillableLoader.start();

        new Training_main.getData().execute();
        return viewMain;

    }

    public void timerPause(){
        timer.cancel();
        isPause = true;
    }

    public void timerResume(){
        timerStart(milliLeft);
        isPause = false;
    }

    public void timerStart(long timeForSport){
        //if(!isPause){
            timer = new CountDownTimer (timeForSport, 1000) {
                @Override
                public void onTick(long timeForSport) {
                    milliLeft = timeForSport;
                    final String text = String.format(Locale.getDefault(), "%d min %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(timeForSport),
                            TimeUnit.MILLISECONDS.toSeconds(timeForSport) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeForSport)));
                    trainingTime.setText(text);
                    fillableLoader.setFillDuration((int) (calcTime()));
                    int sumkcal = Home.kcalSum;
                    double timepast = sumkcal - (double)burnedkcal()*(double) (timeForSport/1000)/60; //Calculate difference between Mealcalories and burdnedcalories
                    burnedCalories.setText(String.format(Locale.getDefault(), "%.2f", timepast));
                }

                public void onFinish() {
                    Toast.makeText(getActivity(), "Yeah, you finished you fat shit", Toast.LENGTH_LONG).show();
                    btnPause.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                }
            };
            timer.start();
        //} else {
            //TODO Flo ich hab kein plan...
       // }

    }



    private class ViewHolder {
        TextView tvSname;
    }


    class MyAdapter extends ArrayAdapter<String> {

        LayoutInflater inflater;

        Context myContext;

        List<String> newList;

        private MyAdapter(Context context, int resource, List<String> list) {

            super(context, resource, list);

            // TODO Auto-generated constructor stub

            myContext = context;

            newList = list;

            inflater = LayoutInflater.from(context);

        }

        @Override
        @NonNull
        public View getView(final int position, View view,@NonNull ViewGroup parent) {

            final ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.listview_item, null);
                holder.tvSname = view.findViewById(R.id.tvtext_item);
                view.setTag(holder);

            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tvSname.setText(newList.get(position));
            return view;

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @SuppressLint("StaticFieldLeak")
    public class getData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject sportJsonObject = new JSONObject(jsonStr);
                    JSONArray sports = sportJsonObject.getJSONArray("sports");
                    for (int i = 0; i < sports.length(); i++) {

                        JSONObject s = sports.getJSONObject(i);

                        String name = s.getString("name");
                        String multi = s.getString("multi");

                        sportNameList.add(name);
                        sportMultiList.add(multi);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }else{
                    Log.e(TAG, "Couldn't get json from server.");
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
                return null;
            }

            @Override
            protected void onPostExecute (Void result){
                super.onPostExecute(result);
                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }

        //Function calculating the time for training
        public long calcTime(){
            long timeinmilli;
            int kcalSum = Home.kcalSum;
            int weight = Integer.parseInt(cursor.getString(6));
            double multi = 0.0;

            //getting position of listview for setting multiplicator
            for (int position = 0; position < sportMultiList.size(); position++){
                multi = Double.parseDouble(sportMultiList.get(position));
            }
            double time = ((kcalSum /(weight*multi))*60)*1000; // Calculating time and convert to milliseconds
            timeinmilli = (long) time; // casting to long because CountDownTimer expect a long value
            return timeinmilli;
        }


        //Function calculation burned Calories per minute
        public int burnedkcal(){
            double burnedCalories=0;
            int burnedCaloriesret= 0;
            double multi = 0.0;
            int weight = Integer.parseInt(cursor.getString(6));

            //getting position of listview for setting multiplicator
            for (int position = 0; position < sportMultiList.size(); position++){
               multi = Double.parseDouble(sportMultiList.get(position));
         }

            burnedCalories= ((weight * multi));
            burnedCaloriesret= (int) burnedCalories;

            return burnedCaloriesret;
    }


}
