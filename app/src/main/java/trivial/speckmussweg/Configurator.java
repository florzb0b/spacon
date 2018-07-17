package trivial.speckmussweg;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewStub;
import android.widget.Button;
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

import trivial.speckmussweg.Objects.Sub;
import trivial.speckmussweg.adapter.RecyclerViewAdapter;
import trivial.speckmussweg.internet.*;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;
import static android.view.View.VISIBLE;

public class Configurator extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    int currentSum = 0, layoutId = 1, focusIdMeal = 1;
    View viewMain;
    private String TAG = Home.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    // http://thelegendsrising.de/test.json
    // https://api.androidhive.info/contacts/
    private static String url = "http://thelegendsrising.de/subs.json";

    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> breadList;
    int[][] kcalList = new int[6][10];

    private RecyclerViewAdapter adapter;

    LinearLayout linearLayoutFirstMeal;

    List<String> breadNameList, cheeseNameList, meatNameList, saladNameList, extrasNameList, sauceNameList;
    List<String> breadkcalList, cheesekcalList, meatkcalList, saladkcalList, extraskcalList, saucekcalList;
    //List<String> sumCaloriesFirstMeal, sumCaloriesSecondMeal;

    LinearLayoutManager horizontalLayoutManager;
    List<Integer> subListId;
    List<String> objectBreadList, objectBreadListCalories, objectCheeseList,
            objectCheeseListCalories, objectMeatList, objectMeatListCalories, objectSaladList,
            objectSaladListCalories, objectExtraList, objectExtraListCalories, objectSauceList,
            objectSauceListCalories;


    RecyclerView recyclerView;
    ImageView leftArrow, rightArrow;
    Button buttonLast, buttonNext;
    FloatingActionButton fab;


    Boolean breadIsChoosed = false;
    int buttonCounter = 1;
    LinearLayout linearLayoutConfiguratorBreadHeader, linearLayoutConfiguratorBreadContent,
            linearLayoutConfiguratorCheeseHeader, linearLayoutConfiguratorCheeseContent,
            linearLayoutConfiguratorMeatHeader, linearLayoutConfiguratorMeatContent,
            linearLayoutConfiguratorSaladHeader, linearLayoutConfiguratorSaladContent,
            linearLayoutConfiguratorExtraHeader, linearLayoutConfiguratorExtraContent,
            linearLayoutConfiguratorSauceHeader, linearLayoutConfiguratorSauceContent,
            linearLayoutConfiguratorMainTab,
            linearLayoutMealLayoutClickableLayout1, linearLayoutMealLayoutClickableLayout2,
            linearLayoutMealLayoutClickableLayout3, linearLayoutMealLayoutClickableLayout4;
    TextView textViewConfiguratorBreadContent, textViewConfiguratorBreadSizeContent,
            textViewConfiguratorHeaderArtContent, footerConfiguratorCaloriesContent,
            textViewMealLayoutId1, textViewMealLayoutId2, textViewMealLayoutId3,
            textViewMealLayoutId4,
            textViewMealLayoutBread1, textViewMealLayoutBread2, textViewMealLayoutBread3, textViewMealLayoutBread4;
    LinearLayout.LayoutParams llParams1, llParams2, llParams3, llParams4;
    View viewParams1, viewParams2, viewParams3, viewParams4;
    ConstraintLayout constraintLayoutScrollviewConfigurator;
    View viewIncludeLayout;

    RelativeLayout relativeLayoutScrollviewMainLeft, relativeLayoutScrollviewMainRight,
            relativelayoutConfiguratorFooterSelected, relativeLayoutConfiguratorFooterFirstMeal;

    ImageView imageViewConfiguratorDeleteBreadContent, imageViewConfiguratorDeleteCheeseContent, imageViewConfiguratorDeleteMeatContent,
            imageViewConfiguratorDeleteSaladContent, imageViewConfiguratorDeleteExtraContent,
            imageViewConfiguratorDeleteSauceContent;
    ImageView imageViewMealLayoutDeleteContentId1, imageViewMealLayoutDeleteContentId2,
            imageViewMealLayoutDeleteContentId3, imageViewMealLayoutDeleteContentId4;
    View selectedView;
    boolean firstMealIsOn = true, firstAttempt = false, subIsLong = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Enable Optionsmenu
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        viewMain = inflater.inflate(R.layout.fragment_configurator, container, false);

        initializeViews();


        recyclerView = viewMain.findViewById(R.id.recylcerview_configurator);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        leftArrow = viewMain.findViewById(R.id.recyclerViewLeftArrow);
        rightArrow = viewMain.findViewById(R.id.recyclerViewRightArrow);
        buttonLast = viewMain.findViewById(R.id.button_configurator_last);
        buttonNext = viewMain.findViewById(R.id.button_configurator_next);
        linearLayoutFirstMeal = viewMain.findViewById(R.id.linearlayout_configurator_cheese_content);
        fab = viewMain.findViewById(R.id.fab_configurator);
        contactList = new ArrayList<>();
        breadList = new ArrayList<>();

        horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        breadNameList = new ArrayList<>();
        cheeseNameList = new ArrayList<>();
        meatNameList = new ArrayList<>();
        saladNameList = new ArrayList<>();
        extrasNameList = new ArrayList<>();
        sauceNameList = new ArrayList<>();
        breadkcalList = new ArrayList<>();
        cheesekcalList = new ArrayList<>();
        meatkcalList = new ArrayList<>();
        saladkcalList = new ArrayList<>();
        extraskcalList = new ArrayList<>();
        saucekcalList = new ArrayList<>();
        subListId = new ArrayList<>();

        viewMain.findViewById(R.id.relativelayout_scrollview_configurator_meal_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMain.findViewById(R.id.include_first_meal).findViewById(R.id.relativelayout_configurator_footer_selected).setVisibility(View.VISIBLE);
                firstMealIsOn = true;
                initializeViews();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                createMeal();
            }
        });

        buttonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (buttonCounter) {
                    case 1:
                        break;
                    case 2:
                        buttonCounter--;
                        break;
                    case 3:
                        buttonCounter--;
                        break;
                    case 4:
                        buttonCounter--;
                        break;
                    case 5:
                        buttonCounter--;
                        break;
                    case 6:
                        buttonCounter--;
                        break;
                    default:
                        buttonCounter = 6;
                }
                fillList();
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (breadIsChoosed) {
                    switch (buttonCounter) {
                        case 1:
                            ++buttonCounter;
                            fillList();
                            break;
                        case 2:
                            ++buttonCounter;
                            fillList();
                            break;
                        case 3:
                            ++buttonCounter;
                            fillList();
                            break;
                        case 4:
                            ++buttonCounter;
                            fillList();
                            break;
                        case 5:
                            ++buttonCounter;
                            fillList();
                            break;
                        case 6:
                            buttonCounter = 1;
                            fillList();
                            break;
                        default:
                            buttonCounter = 1;
                            fillList();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please choose a Bread first to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewConfiguratorDeleteBreadContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutConfiguratorBreadHeader.getVisibility() == View.VISIBLE &&
                        linearLayoutConfiguratorBreadContent.getVisibility() == View.VISIBLE) {

                    textViewConfiguratorBreadContent.setText("");
                    textViewConfiguratorBreadSizeContent.setText("");
                    linearLayoutConfiguratorBreadHeader.setVisibility(View.GONE);
                    linearLayoutConfiguratorBreadContent.setVisibility(View.GONE);

                    for (int i = 0; i < 10; i++) {
                        kcalList[0][i] = 0;
                    }

                    currentSum = setSum();
                    setKcalText();
                }
            }
        });

        imageViewConfiguratorDeleteCheeseContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutConfiguratorCheeseHeader.getVisibility() == View.VISIBLE &&
                        linearLayoutConfiguratorCheeseContent.getVisibility() == View.VISIBLE) {

                    linearLayoutConfiguratorCheeseContent.removeAllViews();
                    linearLayoutConfiguratorCheeseHeader.setVisibility(View.GONE);
                    linearLayoutConfiguratorCheeseContent.setVisibility(View.GONE);

                    for (int i = 0; i < 10; i++) {
                        kcalList[1][i] = 0;
                    }

                    currentSum = setSum();
                    setKcalText();
                }

            }
        });

        imageViewConfiguratorDeleteMeatContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutConfiguratorMeatHeader.getVisibility() == View.VISIBLE &&
                        linearLayoutConfiguratorMeatContent.getVisibility() == View.VISIBLE) {

                    linearLayoutConfiguratorMeatContent.removeAllViews();
                    linearLayoutConfiguratorMeatHeader.setVisibility(View.GONE);
                    linearLayoutConfiguratorMeatContent.setVisibility(View.GONE);

                    for (int i = 0; i < 10; i++) {
                        kcalList[2][i] = 0;
                    }

                    currentSum = setSum();
                    setKcalText();
                }

            }
        });

        imageViewConfiguratorDeleteSaladContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutConfiguratorSaladHeader.getVisibility() == View.VISIBLE &&
                        linearLayoutConfiguratorSaladContent.getVisibility() == View.VISIBLE) {

                    linearLayoutConfiguratorSaladContent.removeAllViews();
                    linearLayoutConfiguratorSaladHeader.setVisibility(View.GONE);
                    linearLayoutConfiguratorSaladContent.setVisibility(View.GONE);

                    for (int i = 0; i < 10; i++) {
                        kcalList[3][i] = 0;
                    }

                    currentSum = setSum();
                    setKcalText();
                }

            }
        });

        imageViewConfiguratorDeleteExtraContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutConfiguratorExtraHeader.getVisibility() == View.VISIBLE &&
                        linearLayoutConfiguratorExtraContent.getVisibility() == View.VISIBLE) {

                    linearLayoutConfiguratorExtraContent.removeAllViews();
                    linearLayoutConfiguratorExtraHeader.setVisibility(View.GONE);
                    linearLayoutConfiguratorExtraContent.setVisibility(View.GONE);

                    for (int i = 0; i < 10; i++) {
                        kcalList[4][i] = 0;
                    }

                    currentSum = setSum();
                    setKcalText();
                }

            }
        });

        imageViewConfiguratorDeleteSauceContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayoutConfiguratorSauceHeader.getVisibility() == View.VISIBLE &&
                        linearLayoutConfiguratorSauceContent.getVisibility() == View.VISIBLE) {

                    linearLayoutConfiguratorSauceContent.removeAllViews();
                    linearLayoutConfiguratorSauceHeader.setVisibility(View.GONE);
                    linearLayoutConfiguratorSauceContent.setVisibility(View.GONE);

                    for (int i = 0; i < 10; i++) {
                        kcalList[5][i] = 0;
                    }

                    currentSum = setSum();
                    setKcalText();
                }

            }
        });
//HERERHEHREHEHEHE#######################################################################################################################################################################

        linearLayoutMealLayoutClickableLayout1 = viewMain.findViewById(R.id.linearlayout_tablayout_meallayout);
        viewParams1 = linearLayoutMealLayoutClickableLayout1;
        llParams1 = (LinearLayout.LayoutParams) viewParams1.getLayoutParams();
        linearLayoutMealLayoutClickableLayout2 = viewMain.findViewById(R.id.linearlayout_tablayout_meallayout2);
        viewParams2 = linearLayoutMealLayoutClickableLayout2;
        llParams2 = (LinearLayout.LayoutParams) viewParams2.getLayoutParams();
        linearLayoutMealLayoutClickableLayout3 = viewMain.findViewById(R.id.linearlayout_tablayout_meallayout3);
        viewParams3 = linearLayoutMealLayoutClickableLayout3;
        llParams3 = (LinearLayout.LayoutParams) viewParams3.getLayoutParams();
        linearLayoutMealLayoutClickableLayout4 = viewMain.findViewById(R.id.linearlayout_tablayout_meallayout4);
        viewParams4 = linearLayoutMealLayoutClickableLayout4;
        llParams4 = (LinearLayout.LayoutParams) viewParams4.getLayoutParams();


        linearLayoutMealLayoutClickableLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "1", Toast.LENGTH_LONG).show();
                getFocusOnMeal(1);
            }
        });

        linearLayoutMealLayoutClickableLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "2", Toast.LENGTH_LONG).show();
                getFocusOnMeal(2);
            }
        });

        linearLayoutMealLayoutClickableLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "3", Toast.LENGTH_LONG).show();
                getFocusOnMeal(3);
            }
        });

        linearLayoutMealLayoutClickableLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "4", Toast.LENGTH_LONG).show();
                getFocusOnMeal(4);
            }
        });

        imageViewMealLayoutDeleteContentId1 = viewMain.findViewById(R.id.layout_meallist_delete_meal);
        imageViewMealLayoutDeleteContentId2 = viewMain.findViewById(R.id.layout_meallist_delete_meal2);
        imageViewMealLayoutDeleteContentId3 = viewMain.findViewById(R.id.layout_meallist_delete_meal3);
        imageViewMealLayoutDeleteContentId4 = viewMain.findViewById(R.id.layout_meallist_delete_meal4);

        imageViewMealLayoutDeleteContentId1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutId = 1;
                textViewMealLayoutBread1.setText("");
                textViewMealLayoutBread1.setVisibility(View.GONE);
                linearLayoutMealLayoutClickableLayout1.setVisibility(View.GONE);
            }
        });
        imageViewMealLayoutDeleteContentId2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutId = 2;
                textViewMealLayoutBread2.setText("");
                textViewMealLayoutBread2.setVisibility(View.GONE);
                linearLayoutMealLayoutClickableLayout2.setVisibility(View.GONE);
            }
        });
        imageViewMealLayoutDeleteContentId3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutId = 3;
                textViewMealLayoutBread3.setText("");
                textViewMealLayoutBread3.setVisibility(View.GONE);
                linearLayoutMealLayoutClickableLayout3.setVisibility(View.GONE);
            }
        });
        imageViewMealLayoutDeleteContentId4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutId = 4;
                textViewMealLayoutBread4.setText("");
                textViewMealLayoutBread4.setVisibility(View.GONE);
                linearLayoutMealLayoutClickableLayout4.setVisibility(View.GONE);
            }
        });

        new getData().execute();

        return viewMain;
    }

    private void deleteBreadTemporary() {
        textViewConfiguratorBreadContent.setText("");
        textViewConfiguratorBreadSizeContent.setText("");

        for (int i = 0; i < 10; i++) {
            kcalList[0][i] = 0;
        }

        currentSum = setSum();
        setKcalText();
    }


    private void fillList() {

        recyclerView.setLayoutManager(horizontalLayoutManager);
        if (!breadIsChoosed || buttonCounter == 1) {
            adapter = new RecyclerViewAdapter(getActivity(), breadNameList, breadkcalList, buttonCounter);
            textViewConfiguratorHeaderArtContent.setText("Bread");
        }
        if (buttonCounter == 2) {
            adapter = new RecyclerViewAdapter(getActivity(), cheeseNameList, cheesekcalList, buttonCounter);
            textViewConfiguratorHeaderArtContent.setText("Cheese");
        }
        if (buttonCounter == 3) {
            adapter = new RecyclerViewAdapter(getActivity(), meatNameList, meatkcalList, buttonCounter);
            textViewConfiguratorHeaderArtContent.setText("Meat");
        }
        if (buttonCounter == 4) {
            adapter = new RecyclerViewAdapter(getActivity(), saladNameList, saladkcalList, buttonCounter);
            textViewConfiguratorHeaderArtContent.setText("Salad");
        }
        if (buttonCounter == 5) {
            adapter = new RecyclerViewAdapter(getActivity(), extrasNameList, extraskcalList, buttonCounter);
            textViewConfiguratorHeaderArtContent.setText("Extra");
        }
        if (buttonCounter == 6) {
            adapter = new RecyclerViewAdapter(getActivity(), sauceNameList, saucekcalList, buttonCounter);
            textViewConfiguratorHeaderArtContent.setText("Sauce");
        }
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

    private void getFocusOnMeal(int id) {
        if (id == 1) {
            llParams1.weight = .3f;
            viewParams1.setLayoutParams(llParams1);
            llParams2.weight = .2f;
            viewParams2.setLayoutParams(llParams2);
            llParams3.weight = .2f;
            viewParams3.setLayoutParams(llParams3);
            llParams4.weight = .2f;
            viewParams4.setLayoutParams(llParams4);
            linearLayoutMealLayoutClickableLayout1.setBackgroundColor(0);
            linearLayoutMealLayoutClickableLayout1.setBackground(getResources().getDrawable(R.drawable.layout_shadow_darkyellow));
            linearLayoutMealLayoutClickableLayout2.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
            linearLayoutMealLayoutClickableLayout3.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
            linearLayoutMealLayoutClickableLayout4.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
        }
        if (id == 2) {
            llParams2.weight = .3f;
            viewParams2.setLayoutParams(llParams2);
            llParams1.weight = .2f;
            viewParams1.setLayoutParams(llParams1);
            llParams3.weight = .2f;
            viewParams3.setLayoutParams(llParams3);
            llParams4.weight = .2f;
            viewParams4.setLayoutParams(llParams4);
            linearLayoutMealLayoutClickableLayout2.setBackgroundColor(0);
            linearLayoutMealLayoutClickableLayout2.setBackground(getResources().getDrawable(R.drawable.layout_shadow_darkyellow));
            linearLayoutMealLayoutClickableLayout1.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
            linearLayoutMealLayoutClickableLayout3.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
            linearLayoutMealLayoutClickableLayout4.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
        }
        if (id == 3) {
            llParams3.weight = .3f;
            viewParams3.setLayoutParams(llParams3);
            llParams1.weight = .2f;
            viewParams1.setLayoutParams(llParams1);
            llParams2.weight = .2f;
            viewParams2.setLayoutParams(llParams2);
            llParams4.weight = .2f;
            viewParams4.setLayoutParams(llParams4);
            linearLayoutMealLayoutClickableLayout3.setBackgroundColor(0);
            linearLayoutMealLayoutClickableLayout3.setBackground(getResources().getDrawable(R.drawable.layout_shadow_darkyellow));
            linearLayoutMealLayoutClickableLayout1.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
            linearLayoutMealLayoutClickableLayout2.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
            linearLayoutMealLayoutClickableLayout4.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
        }
        if (id == 4) {
            llParams4.weight = .3f;
            viewParams4.setLayoutParams(llParams4);
            llParams1.weight = .2f;
            viewParams1.setLayoutParams(llParams1);
            llParams2.weight = .2f;
            viewParams2.setLayoutParams(llParams2);
            llParams3.weight = .2f;
            viewParams3.setLayoutParams(llParams3);
            linearLayoutMealLayoutClickableLayout4.setBackgroundColor(0);
            linearLayoutMealLayoutClickableLayout4.setBackground(getResources().getDrawable(R.drawable.layout_shadow_darkyellow));
            linearLayoutMealLayoutClickableLayout1.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
            linearLayoutMealLayoutClickableLayout2.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
            linearLayoutMealLayoutClickableLayout3.setBackgroundColor(getResources().getColor(R.color.backgroundColorDarkerYellow));
        }
    }

    //####################################################################################################################################
    private void saveObject() {

    }

    private void deleteObject(int id) {

    }

    @Override
    public void onItemClick(View view, int position) {

        if (buttonCounter == 1) {
            createSub(adapter.getItem(position), position);
        }
        if (buttonCounter == 2) {
            setCheese(adapter.getItem(position));
            prepareSum(position);
        }
        if (buttonCounter == 3) {
            setMeat(adapter.getItem(position));
            prepareSum(position);
        }
        if (buttonCounter == 4) {
            setSalad(adapter.getItem(position));
            prepareSum(position);
        }
        if (buttonCounter == 5) {
            setExtras(adapter.getItem(position));
            prepareSum(position);
        }
        if (buttonCounter == 6) {
            setSauce(adapter.getItem(position));
            prepareSum(position);
        }

    }

    private void setKcalText() {
        footerConfiguratorCaloriesContent.setText(String.valueOf(currentSum));
    }

    private void prepareSum(int position){
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            //for bread
            if (buttonCounter - 1 == 0) {
                if (kcalList[buttonCounter - 1][0] != 0) {
                    deleteBreadTemporary();
                }
                if (subIsLong) {
                    kcalList[buttonCounter - 1][i] =
                            Integer.parseInt(adapter.getCalories(position)) * 2;

                } else {
                    kcalList[buttonCounter - 1][i] = Integer.parseInt(adapter.getCalories(position));
                }
                sum += setSum();
                currentSum = sum;
                break;
            } else { //everything else
                if (kcalList[buttonCounter - 1][i] == 0) {
                    kcalList[buttonCounter - 1][i] = Integer.parseInt(adapter.getCalories(position));
                    sum += setSum();
                    currentSum = sum;
                    break;
                }
            }
        }
        setKcalText();
    }
    private int setSum() {
        int sum = 0;
        for (int k = 0; k < 6; k++) {
            for (int j = 0; j < 10; j++) {
                sum += kcalList[k][j];
            }
        }
        return sum;
    }

    //TODO: noch machen - oder eher nicht
    @Override
    public void onLongClick(View view, int position) {
        //view.animate().translationZ(300).scaleX(1.1f).scaleY(1.1f).setInterpolator(new AnticipateOvershootInterpolator()).start();
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

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject breadJsonObj = new JSONObject(jsonStr);
                    JSONArray bread = breadJsonObj.getJSONArray("bread");
                    for (int i = 0; i < bread.length(); i++) {

                        JSONObject s = bread.getJSONObject(i);

                        String id = s.getString("id");
                        String art = s.getString("name");
                        String kcal = s.getString("kcal");
                        String fett = s.getString("fett");

                   /*     HashMap<String, String> tempSubs = new HashMap<>();
                        tempSubs.put("id", id);
                        tempSubs.put("art", art);
                        tempSubs.put("kcal", kcal);
                        tempSubs.put("fett", fett);*/


                        //breadList.add(tempSubs);
                        breadNameList.add(art);
                        breadkcalList.add(kcal);
                    }
                    JSONObject cheeseJsonObj = new JSONObject(jsonStr);
                    JSONArray cheese = cheeseJsonObj.getJSONArray("cheese");
                    for (int i = 0; i < cheese.length(); i++) {
                        JSONObject s = cheese.getJSONObject(i);

                        String id = s.getString("id");
                        String art = s.getString("name");
                        String kcal = s.getString("kcal");
                        String fett = s.getString("fett");


                        cheeseNameList.add(art);
                        cheesekcalList.add(kcal);
                    }

                    JSONObject meatJsonObj = new JSONObject(jsonStr);
                    JSONArray meat = meatJsonObj.getJSONArray("meat");
                    for (int i = 0; i < meat.length(); i++) {
                        JSONObject s = meat.getJSONObject(i);

                        String id = s.getString("id");
                        String art = s.getString("name");
                        String kcal = s.getString("kcal");
                        String fett = s.getString("fett");


                        meatNameList.add(art);
                        meatkcalList.add(kcal);
                    }

                    JSONObject saladJsonObj = new JSONObject(jsonStr);
                    JSONArray salad = saladJsonObj.getJSONArray("salad");
                    for (int i = 0; i < salad.length(); i++) {
                        JSONObject s = salad.getJSONObject(i);

                        String id = s.getString("id");
                        String art = s.getString("name");
                        String kcal = s.getString("kcal");
                        String fett = s.getString("fett");


                        saladNameList.add(art);
                        saladkcalList.add(kcal);
                    }

                    JSONObject extrasJsonObj = new JSONObject(jsonStr);
                    JSONArray extras = extrasJsonObj.getJSONArray("extra");
                    for (int i = 0; i < extras.length(); i++) {
                        JSONObject s = extras.getJSONObject(i);

                        String id = s.getString("id");
                        String art = s.getString("name");
                        String kcal = s.getString("kcal");
                        String fett = s.getString("fett");


                        extrasNameList.add(art);
                        extraskcalList.add(kcal);
                    }

                    JSONObject sauceJsonObj = new JSONObject(jsonStr);
                    JSONArray sauce = sauceJsonObj.getJSONArray("sauce");
                    for (int i = 0; i < sauce.length(); i++) {
                        JSONObject s = sauce.getJSONObject(i);

                        String id = s.getString("id");
                        String art = s.getString("name");
                        String kcal = s.getString("kcal");
                        String fett = s.getString("fett");

                        sauceNameList.add(art);
                        saucekcalList.add(kcal);
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
    private void createSub(final String content, final int position) {
        final CharSequence subSize[] = new CharSequence[]{"Small (15 cm)", "Long (30 cm)"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Choose the size of your Sub");
        builder.setItems(subSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                if (index == 0) {
                    subIsLong = false;
                    prepareSum(position);
                    textViewConfiguratorBreadSizeContent.setText("15");
                    linearLayoutConfiguratorBreadHeader.setVisibility(View.VISIBLE);
                    linearLayoutConfiguratorBreadContent.setVisibility(View.VISIBLE);
                    textViewConfiguratorBreadContent.setText(content);

                }
                if (index == 1) {
                    subIsLong = true;
                    prepareSum(position);
                    textViewConfiguratorBreadSizeContent.setText("30");
                    linearLayoutConfiguratorBreadHeader.setVisibility(View.VISIBLE);
                    linearLayoutConfiguratorBreadContent.setVisibility(View.VISIBLE);
                    textViewConfiguratorBreadContent.setText(content);

                }
            }
        });
        builder.show();

        if (!breadIsChoosed) {
            breadIsChoosed = true;
        }
    }

    private void setCheese(String content) {
//TextView textView = new TextView(getActivity());
        //textView.setText(content);
        //linearLayoutFirstMeal.addView(textView);

        if (linearLayoutConfiguratorCheeseHeader.getVisibility() == View.GONE &&
                linearLayoutConfiguratorCheeseContent.getVisibility() == View.GONE) {
            linearLayoutConfiguratorCheeseHeader.setVisibility(View.VISIBLE);
            linearLayoutConfiguratorCheeseContent.setVisibility(View.VISIBLE);
        }
        int num = linearLayoutConfiguratorCheeseContent.getChildCount();

        if (!(num >= 9)) {
            TextView cheeseTextView = new TextView(getActivity());
            cheeseTextView.setText(content);

            linearLayoutConfiguratorCheeseContent.addView(cheeseTextView);
        } else {
            Toast.makeText(getActivity(), "et is jetz mo genuch du haufen scheisse", Toast.LENGTH_SHORT).show();
        }

    }

    private void setMeat(String content) {
//TextView textView = new TextView(getActivity());
        //textView.setText(content);
        //linearLayoutFirstMeal.addView(textView);
        if (linearLayoutConfiguratorMeatHeader.getVisibility() == View.GONE &&
                linearLayoutConfiguratorMeatContent.getVisibility() == View.GONE) {
            linearLayoutConfiguratorMeatHeader.setVisibility(View.VISIBLE);
            linearLayoutConfiguratorMeatContent.setVisibility(View.VISIBLE);
        }
        TextView meatTextView = new TextView(getActivity());
        meatTextView.setText(content);
        linearLayoutConfiguratorMeatContent.addView(meatTextView);
    }

    private void setSalad(String content) {
//TextView textView = new TextView(getActivity());
        //textView.setText(content);
        //linearLayoutFirstMeal.addView(textView);
        if (linearLayoutConfiguratorSaladHeader.getVisibility() == View.GONE &&
                linearLayoutConfiguratorSaladContent.getVisibility() == View.GONE) {
            linearLayoutConfiguratorSaladHeader.setVisibility(View.VISIBLE);
            linearLayoutConfiguratorSaladContent.setVisibility(View.VISIBLE);
        }
        TextView saladTextView = new TextView(getActivity());
        saladTextView.setText(content);
        linearLayoutConfiguratorSaladContent.addView(saladTextView);
    }

    private void setExtras(String content) {
//TextView textView = new TextView(getActivity());
        //textView.setText(content);
        //linearLayoutFirstMeal.addView(textView);
        if (linearLayoutConfiguratorExtraHeader.getVisibility() == View.GONE &&
                linearLayoutConfiguratorExtraContent.getVisibility() == View.GONE) {
            linearLayoutConfiguratorExtraHeader.setVisibility(View.VISIBLE);
            linearLayoutConfiguratorExtraContent.setVisibility(View.VISIBLE);
        }
        TextView extraTextView = new TextView(getActivity());
        extraTextView.setText(content);
        linearLayoutConfiguratorExtraContent.addView(extraTextView);
    }

    private void setSauce(String content) {
//TextView textView = new TextView(getActivity());
        //textView.setText(content);
        //linearLayoutFirstMeal.addView(textView);
        if (linearLayoutConfiguratorSauceHeader.getVisibility() == View.GONE &&
                linearLayoutConfiguratorSauceContent.getVisibility() == View.GONE) {
            linearLayoutConfiguratorSauceHeader.setVisibility(View.VISIBLE);
            linearLayoutConfiguratorSauceContent.setVisibility(View.VISIBLE);
        }
        TextView sauceTextView = new TextView(getActivity());
        sauceTextView.setText(content);
        linearLayoutConfiguratorSauceContent.addView(sauceTextView);
    }

    @SuppressLint("CutPasteId")
    private void initializeViews() {


        //relativeLayoutScrollviewMainLeft = viewMain.findViewById(R.id.include_first_meal).findViewById(R.id.relativelayout_scrollview_main);
        //relativeLayoutScrollviewMainRight =  viewMain.findViewById(R.id.include_second_meal).findViewById(R.id.relativelayout_scrollview_main);

        if (firstMealIsOn) {
            selectedView = viewMain.findViewById(R.id.include_first_meal);
        }
        relativelayoutConfiguratorFooterSelected = selectedView.findViewById(R.id.relativelayout_configurator_footer_selected);
        relativeLayoutConfiguratorFooterFirstMeal = selectedView.findViewById(R.id.relativelayout_configurator_footer_first_meal);
        linearLayoutConfiguratorBreadHeader = selectedView.findViewById(R.id.linearlayout_configurator_bread_header);
        linearLayoutConfiguratorBreadContent = selectedView.findViewById(R.id.linearlayout_configurator_bread_content);
        textViewConfiguratorBreadContent = selectedView.findViewById(R.id.textview_configurator_bread_content);
        textViewConfiguratorBreadSizeContent = selectedView.findViewById(R.id.textview_configurator_bread_size_content);
        linearLayoutConfiguratorCheeseHeader = selectedView.findViewById(R.id.linearlayout_configurator_cheese_header);
        linearLayoutConfiguratorCheeseContent = selectedView.findViewById(R.id.linearlayout_configurator_cheese_content);
        linearLayoutConfiguratorMeatHeader = selectedView.findViewById(R.id.linearlayout_configurator_meat_header);
        linearLayoutConfiguratorMeatContent = selectedView.findViewById(R.id.linearlayout_configurator_meat_content);
        linearLayoutConfiguratorSaladHeader = selectedView.findViewById(R.id.linearlayout_configurator_salad_header);
        linearLayoutConfiguratorSaladContent = selectedView.findViewById(R.id.linearlayout_configurator_salad_content);
        linearLayoutConfiguratorExtraHeader = selectedView.findViewById(R.id.linearlayout_configurator_extras_header);
        linearLayoutConfiguratorExtraContent = selectedView.findViewById(R.id.linearlayout_configurator_extras_content);
        linearLayoutConfiguratorSauceHeader = selectedView.findViewById(R.id.linearlayout_configurator_sauce_header);
        linearLayoutConfiguratorSauceContent = selectedView.findViewById(R.id.linearlayout_configurator_sauce_content);
        textViewConfiguratorHeaderArtContent = viewMain.findViewById(R.id.textview_configurator_header_art_content);
        footerConfiguratorCaloriesContent = selectedView.findViewById(R.id.footer_configurator_calories_content);

        linearLayoutConfiguratorMainTab = viewMain.findViewById(R.id.linearlayout_configurator_tablayout);

        imageViewConfiguratorDeleteBreadContent = viewMain.findViewById(R.id.imageview_configurator_delete_bread);
        imageViewConfiguratorDeleteCheeseContent = viewMain.findViewById(R.id.imageview_configurator_delete_cheese);
        imageViewConfiguratorDeleteMeatContent = viewMain.findViewById(R.id.imageview_configurator_delete_meat);
        imageViewConfiguratorDeleteSaladContent = viewMain.findViewById(R.id.imageview_configurator_delete_salad);
        imageViewConfiguratorDeleteExtraContent = viewMain.findViewById(R.id.imageview_configurator_delete_extras);
        imageViewConfiguratorDeleteSauceContent = viewMain.findViewById(R.id.imageview_configurator_delete_sauce);


        textViewMealLayoutBread1 = viewMain.findViewById(R.id.layout_meallist_subart);
        textViewMealLayoutBread2 = viewMain.findViewById(R.id.layout_meallist_subart2);
        textViewMealLayoutBread3 = viewMain.findViewById(R.id.layout_meallist_subart3);
        textViewMealLayoutBread4 = viewMain.findViewById(R.id.layout_meallist_subart4);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.optionsmenu_configurator, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.optionsmenu_configurator_starttraining:
                return true;
            case R.id.optionsmenu_configurator_showinfo:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Create new Meal
    @SuppressLint("CutPasteId")
    public void createMeal() {


        //subListId.add(sub.getmId());

        if (firstAttempt) {
            if (linearLayoutMealLayoutClickableLayout4.getVisibility() == View.GONE) {
                layoutId = 4;
            }
            if (linearLayoutMealLayoutClickableLayout3.getVisibility() == View.GONE) {
                layoutId = 3;
            }
            if (linearLayoutMealLayoutClickableLayout2.getVisibility() == View.GONE) {
                layoutId = 2;
            }
            if (linearLayoutMealLayoutClickableLayout1.getVisibility() == View.GONE) {
                layoutId = 1;
            }
        }

        if (layoutId == 1) {

            Sub sub1 = new Sub(1, "", false, null, null, null, null);
            linearLayoutMealLayoutClickableLayout1.setVisibility(View.VISIBLE);
            textViewMealLayoutId1 = viewMain.findViewById(R.id.layout_meallist_id);
            textViewMealLayoutId1.setText(String.valueOf(layoutId));
            getFocusOnMeal(1);

        }
        if (layoutId == 2) {
            Sub sub2 = new Sub(2, "", false, null, null, null, null);
            linearLayoutMealLayoutClickableLayout2.setVisibility(View.VISIBLE);
            textViewMealLayoutId2 = viewMain.findViewById(R.id.layout_meallist_id2);
            textViewMealLayoutId2.setText(String.valueOf(layoutId));
            getFocusOnMeal(2);
        }
        if (layoutId == 3) {
            Sub sub3 = new Sub(3, "", false, null, null, null, null);
            linearLayoutMealLayoutClickableLayout3.setVisibility(View.VISIBLE);
            textViewMealLayoutId3 = viewMain.findViewById(R.id.layout_meallist_id3);
            textViewMealLayoutId3.setText(String.valueOf(layoutId));
            getFocusOnMeal(3);
        }
        if (layoutId == 4) {
            Sub sub4 = new Sub(4, "", false, null, null, null, null);
            linearLayoutMealLayoutClickableLayout4.setVisibility(View.VISIBLE);
            textViewMealLayoutId4 = viewMain.findViewById(R.id.layout_meallist_id4);
            textViewMealLayoutId4.setText(String.valueOf(layoutId));
            getFocusOnMeal(4);
            //first time all meals are visible
            if (!firstAttempt) {
                firstAttempt = true;
            }


        }

        if (!(linearLayoutMealLayoutClickableLayout1.getVisibility() == View.VISIBLE
                && linearLayoutMealLayoutClickableLayout2.getVisibility() == View.VISIBLE
                && linearLayoutMealLayoutClickableLayout3.getVisibility() == View.VISIBLE
                && linearLayoutMealLayoutClickableLayout4.getVisibility() == View.VISIBLE)) {
            layoutId += 1;
            firstAttempt = true;
        } else {
            Toast.makeText(getActivity(), "Its enough", Toast.LENGTH_LONG).show();
        }

    }


}
