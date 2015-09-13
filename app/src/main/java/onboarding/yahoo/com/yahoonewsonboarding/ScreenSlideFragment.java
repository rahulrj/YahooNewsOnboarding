package onboarding.yahoo.com.yahoonewsonboarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rahul.raja on 9/13/15.
 */
public class ScreenSlideFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args=getArguments();
        int position=args.getInt("position");
        int layoutId=getLayoutId(position);

        ViewGroup rootView = (ViewGroup) inflater.inflate(layoutId, container, false);

        return rootView;
    }

    private int getLayoutId(int position){

        int id=0;
        if(position==0){

            id=R.layout.first_screen;
        }
        else if(position==1){

            id=R.layout.second_screen;
        }
        else if(position==2){

            id=R.layout.third_screen;
        }
        return id;
    }
}