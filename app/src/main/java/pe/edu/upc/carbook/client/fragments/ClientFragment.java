package pe.edu.upc.carbook.client.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.carbook.R;
import pe.edu.upc.carbook.provider.fragments.LocalFragment;
import pe.edu.upc.carbook.provider.fragments.PersonalInfoFragment;
import pe.edu.upc.carbook.provider.fragments.ProfileFragment;
import pe.edu.upc.carbook.provider.fragments.ServiceTabFragment;

/**
 * Created by hypnotic on 18/11/2016.
 */

public class ClientFragment extends Fragment {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ClientFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager,container,false);
        if(savedInstanceState == null){
            viewPager = (ViewPager) view.findViewById(R.id.pager);
            tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
            tabLayout.setupWithViewPager(viewPager);
            populateViewPager(viewPager);
        }
        return view;
    }

    private void populateViewPager(ViewPager viewPager) {
        ClientFragment.SectionsAdapter adapter = new ClientFragment.SectionsAdapter(getFragmentManager());
        adapter.addFragment(new DataClientFragment(), getString(R.string.tab_title_personal_info));
        //adapter.addFragment(new CarFragment(), "Carros");
        //adapter.addFragment(new ServiceTabFragment(), getString(R.string.tab_title_services));
        viewPager.setAdapter(adapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public class SectionsAdapter extends FragmentStatePagerAdapter {
        private final List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentsTitles = new ArrayList<>();

        SectionsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentsTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentsTitles.get(position);
        }
    }
}
