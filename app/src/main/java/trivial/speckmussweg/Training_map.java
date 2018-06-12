package trivial.speckmussweg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Training_map extends Fragment {
    private int page;
    public static Training_map newInstance(int page) {
        Training_map fragmentSecond = new Training_map();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }
    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training_map, container, false);
        Fragment zwei = new Training_map();
        if (page == 2) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().replace(R.id.container2, zwei).commit();
            }
        }

            return view;
    }

}

