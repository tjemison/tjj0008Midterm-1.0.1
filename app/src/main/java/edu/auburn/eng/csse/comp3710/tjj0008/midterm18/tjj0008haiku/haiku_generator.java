package edu.auburn.eng.csse.comp3710.tjj0008.midterm18.tjj0008haiku;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class haiku_generator extends FragmentActivity implements HaikuInteractionFragment.OnButtonSelectedListener,
        HaikuDisplayFragment.OnFragmentInteractionListener{

    private HaikuInteractionFragment haikuInteraction;
    private HaikuDisplayFragment haikuDisplay;
    private final String haikuInteractionTag = "INTERACTION";
    private final String haikuDisplayTag = "DISPLAY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haiku_generator);
        haikuInteraction = new HaikuInteractionFragment();
        haikuDisplay = new HaikuDisplayFragment();
        if (savedInstanceState != null) {
            haikuInteraction = (HaikuInteractionFragment) getSupportFragmentManager().findFragmentByTag(haikuInteractionTag);
        }
        else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
            fragTransaction.add(R.id.haiku_fragment_container, haikuInteraction, haikuInteractionTag);
            fragTransaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDisplayButtonPressed(Line line1, Line line2, Line line3) {

        //Use bundles to display the below haiku lines in HaikuDisplay

        Bundle bundle = new Bundle();
        bundle.putParcelable("line1", haikuInteraction.returnLine1());
        bundle.putParcelable("line2", haikuInteraction.returnLine2());
        bundle.putParcelable("line3", haikuInteraction.returnLine3());
        haikuDisplay.setArguments(bundle);

        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.addToBackStack(haikuInteractionTag);
        //fragTransaction.hide(haikuInteraction);
        fragTransaction.replace(R.id.haiku_fragment_container, haikuDisplay);
        fragTransaction.commit();

        //haikuDisplay.displayHaiku(line1, line2, line3);
    }

    @Override
    public void onRestartButtonPressed() {
        HaikuInteractionFragment newHaikuInteraction = new HaikuInteractionFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.haiku_fragment_container, newHaikuInteraction);
        fragmentTransaction.commit();
    }


}
