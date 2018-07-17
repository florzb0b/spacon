package trivial.speckmussweg;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

public class Training_main extends Fragment {

    Button btnDialog;
    AlertDialog.Builder alertDialog;
    ArrayList<String> myList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        View viewMain = inflater.inflate(R.layout.fragment_training_main_new, container, false);

        myList = new ArrayList<String>();
        myList.add("India");
        myList.add("China");
        myList.add("South Africa");
        myList.add("USA");
        myList.add("UK");
        myList.add("Japan ");
        myList.add("Canada");
        myList.add("Germany");
        myList.add("Thailand");
        myList.add("France");
        myList.add("Ohnoooo");
        myList.add("lololol");

        alertDialog = new AlertDialog.Builder(getActivity());

        btnDialog = viewMain.findViewById(R.id.training_btn_start_new);
        btnDialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();

                // create view for add item in dialog
                View convertView = (View) inflater.inflate(R.layout.listview_training_sport, null);

                // on dialog cancel button listner
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        });

                // add custom view in dialog
                alertDialog.setView(convertView);
                ListView lv = (ListView) convertView.findViewById(R.id.listview_sport);
                final AlertDialog alert = alertDialog.create();
                alert.setTitle("Choose your Sport you fat fuck!"); // Title
                MyAdapter myadapter = new MyAdapter(getActivity(), R.layout.listview_item, myList);
                lv.setAdapter(myadapter);

                lv.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(),"You have selected -: " + myList.get(position), Toast.LENGTH_SHORT).show();
                        alert.cancel();

                    }
                });
                // show dialog
                alert.show();
            }
        });
        return viewMain;
    }


    private class ViewHolder {
        TextView tvSname;
    }


    class MyAdapter extends ArrayAdapter<String> {

        LayoutInflater inflater;

        Context myContext;

        List<String> newList;

        public MyAdapter(Context context, int resource, List<String> list) {

            super(context, resource, list);

            // TODO Auto-generated constructor stub

            myContext = context;

            newList = list;

            inflater = LayoutInflater.from(context);

        }

        @Override

        public View getView(final int position, View view, ViewGroup parent) {

            final ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.listview_item, null);
                holder.tvSname = (TextView) view.findViewById(R.id.tvtext_item);
                view.setTag(holder);

            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tvSname.setText(newList.get(position).toString());
            return view;

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}