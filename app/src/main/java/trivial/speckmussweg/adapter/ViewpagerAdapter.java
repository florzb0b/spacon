package trivial.speckmussweg.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import trivial.speckmussweg.Training_map;
import trivial.speckmussweg.Training_pig;

public class ViewpagerAdapter extends AppCompatActivity {



    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public int getCount() {
            return NUM_ITEMS;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Training_pig.newInstance(0);
                case 1:
                    return Training_map.newInstance(1);
                default:
                    return null;
            }
        }

    }

}
