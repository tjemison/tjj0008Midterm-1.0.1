package edu.auburn.eng.csse.comp3710.tjj0008.midterm18.tjj0008haiku;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HaikuDisplayFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView haiku;
    private View rootView;
    private Line line1;
    private Line line2;
    private Line line3;

    public HaikuDisplayFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_haiku_display, container, false);

        //Required to display haiku if using bundles...

        Bundle args = getArguments();
        haiku = rootView.findViewById(R.id.haiku_display);
        line1 = (Line) args.getParcelable("line1");
        line2 = (Line) args.getParcelable("line2");
        line3 = (Line) args.getParcelable("line3");
        displayHaiku(line1, line2, line3);



        return rootView;
    }

    public void displayHaiku(Line line1, Line line2, Line line3) {

        //Display haiku - bundle implementation....
        haiku.append(line1.getHaikuLine().getText().toString().substring(2, line1.getHaikuLine().length()));
        haiku.append("\n");
        haiku.append(line2.getHaikuLine().getText().toString().substring(2, line2.getHaikuLine().length()));
        haiku.append("\n");
        haiku.append(line3.getHaikuLine().getText().toString().substring(2, line3.getHaikuLine().length()));

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
