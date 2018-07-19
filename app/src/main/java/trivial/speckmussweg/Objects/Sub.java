//* #################################### FÜR DICH CHRIS





/*

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
        String jsonStr = sh.makeServiceCall(url); //die url ist die url der json datei

        //Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject sportJsonObject = new JSONObject(jsonStr);
                JSONArray sports = breadJsonObj.getJSONArray("sport");
                for (int i = 0; i < sports.length(); i++) {

                    JSONObject s = sports.getJSONObject(i);


                    String name = s.getString("name");


                    sportList.add(art); // die musste anlegen als klassenvariable ArrayList<String> sportList;!!
                    // ab dann kannst du immer mit sportList.get(positionListView) den Namen herbekommen
                    // die kcal müssen wir selbst von hand machen

                } catch( final JSONException e){
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
            } else{
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
} */