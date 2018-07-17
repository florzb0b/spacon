package trivial.speckmussweg;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import trivial.speckmussweg.adapter.ViewpagerAdapter;


public class Training_main extends Fragment {

    FragmentPagerAdapter adapterViewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View main = inflater.inflate(R.layout.fragment_training_main_new, container,false);
//        ViewPager vpPager = main.findViewById(R.id.vpPager);
//        vpPager.setAdapter(adapterViewPager);
//        adapterViewPager = new ViewpagerAdapter.MyPagerAdapter(getActivity().getSupportFragmentManager());

        return main;
    }
}

