package trivial.speckmussweg;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import trivial.speckmussweg.adapter.RecyclerViewAdapter;
import trivial.speckmussweg.internet.*;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

public class Configurator extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    int i = 1;
    View viewMain;
    private String TAG = Home.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    // http://thelegendsrising.de/test.json
    // https://api.androidhive.info/contacts/
    private static String url = "http://thelegendsrising.de/subs.json";

    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> subList;

    private RecyclerViewAdapter adapter;

    List<String> nameList;
    List<String> kcalList;
    List<String> fettList;

    private boolean programaticallyScrolled;
    int currentVisibleItem = 0;
    RecyclerView recyclerView;
    ImageView leftArrow, rightArrow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Enable Optionsmenu
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        viewMain = inflater.inflate(R.layout.fragment_configurator, container, false);

        recyclerView = viewMain.findViewById(R.id.list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        leftArrow = viewMain.findViewById(R.id.recyclerViewLeftArrow);
        rightArrow = viewMain.findViewById(R.id.recyclerViewRightArrow);
        contactList = new ArrayList<>();
        subList = new ArrayList<>();

        nameList = new ArrayList<>();
        kcalList = new ArrayList<>();
        fettList = new ArrayList<>();

        //lv = viewMain.findViewById(R.id.list);
        new getData().execute();

        return viewMain;
    }


    private void fillList() {
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        adapter = new RecyclerViewAdapter(getActivity(), nameList, kcalList, fettList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollHorizontally(1)) {
                    rightArrow.setVisibility(View.GONE);
                    leftArrow.setVisibility(View.VISIBLE);
                } else if (!recyclerView.canScrollHorizontally(-1)) {
                    leftArrow.setVisibility(View.GONE);
                    rightArrow.setVisibility(View.VISIBLE);
                } else {
                    leftArrow.setVisibility(View.VISIBLE);
                    rightArrow.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
        createSub(adapter.getItem(position));

    }

    //TODO: noch machen
    @Override
    public void onLongClick(View view, int position) {
        view.animate().translationZ(300).scaleX(1.1f).scaleY(1.1f).setInterpolator(new AnticipateOvershootInterpolator()).start();
        Toast.makeText(getActivity(), "You clicked long on" + adapter.getItem(position) + " " + position, Toast.LENGTH_SHORT).show();
    }

    /**
     * Async task class to get json by making HTTP call
     */
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

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray bread = jsonObj.getJSONArray("bread");
                    int j = 1;
                    for (int i = 0; i < bread.length(); i++) {

                        JSONObject s = bread.getJSONObject(i);


                        //if (s.getString("id").equals("b" + Integer.toString(j))) {
                        String id = s.getString("id");
                        String art = s.getString("name");
                        String kcal = s.getString("kcal");
                        String fett = s.getString("fett");

                        HashMap<String, String> tempSubs = new HashMap<>();
                        tempSubs.put("id", id);
                        tempSubs.put("art", art);
                        tempSubs.put("kcal", kcal);
                        tempSubs.put("fett", fett);

                        subList.add(tempSubs);
                        nameList.add(art);
                        kcalList.add(kcal);
                        fettList.add(fett);
                        //}
                        //j++;
                    }

                /*try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }*/
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
            } else {
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

/*            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), subList,
                    R.layout.listview_configurator_sub, new String[]{"art", "kcal",
                    "fett"}, new int[]{R.id.name,
                    R.id.email, R.id.mobile});


            lv.setAdapter(adapter);*/

// set up the RecyclerView
            fillList();
        }

    }



    @SuppressLint("ResourceType")
    private void createSub(String content) {
        //added LInearLayout
        LinearLayout linearLayout = viewMain.findViewById(R.id.linearlayout_first_meal);

        //add textView
        TextView textView = new TextView(getActivity());
        textView.setText(content);
        textView.setId(i);

        //added the textView and the Button to LinearLayout
        linearLayout.addView(textView);
      i++;
    }
}
