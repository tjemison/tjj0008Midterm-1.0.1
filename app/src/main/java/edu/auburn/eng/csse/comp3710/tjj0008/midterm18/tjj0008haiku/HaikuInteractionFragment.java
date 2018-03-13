package edu.auburn.eng.csse.comp3710.tjj0008.midterm18.tjj0008haiku;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class HaikuInteractionFragment extends Fragment {


    private RadioGroup wordTypeRadioGroup;
    private View spinner;
    private View rootView;
    private Spinner populatingSpinner;
    private ArrayAdapter spinnerAdapter;
    private ArrayAdapter formattedAdapter;
    private Button addWordButton;
    private Button deleteWordButton;
    private Button restartButton;
    private Button displayButton;
    private Line line1 = new Line(5);
    private Line line2 = new Line(7);
    private Line line3 = new Line(5);
    private ArrayList<Word> entries = new ArrayList<Word>();
    private ArrayList<Word> removedNouns = new ArrayList<Word>();
    private ArrayList<Word> removedVerbs = new ArrayList<Word>();
    private ArrayList<Word> removedAdjectives = new ArrayList<Word>();
    private ArrayList<Word> removedOthers = new ArrayList<Word>();
    private String wordToAdd;
    private int totalNumberOfSyllables = 0;
    OnButtonSelectedListener mCallback;

    public HaikuInteractionFragment() {
        // Required empty public constructor
    }

    public interface OnButtonSelectedListener {
        void onDisplayButtonPressed (Line line1, Line line2, Line line3);
        void onRestartButtonPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_haiku_interaction, container, false);

        if (savedInstanceState != null) {

        }
        else {
            spinner = rootView.findViewById(R.id.wordSpinner);
            wordTypeRadioGroup = rootView.findViewById(R.id.wordTypes);
            initializeLines(line1, line2, line3);

            wordTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.adjective_rbutton) {
                        populateSpinner("adjective");
                    } else if (checkedId == R.id.noun_rbutton) {
                        populateSpinner("noun");
                    } else if (checkedId == R.id.verb_rbutton) {
                        populateSpinner("verb");
                    } else if (checkedId == R.id.other_rbutton) {
                        populateSpinner("other");
                    }
                }
            });
        }
       // setSpinnerListener(populatingSpinner);
        return rootView;
    }

    public void populateSpinner(String wordType) {
        //ArrayList entries = new ArrayList();
        populatingSpinner = (Spinner) rootView.findViewById(R.id.wordSpinner);

        if (wordType.equalsIgnoreCase("noun")) {
            spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.nouns,
                    android.R.layout.simple_spinner_dropdown_item);

            entries.clear();

            createWordObjectsFromWordList();
            updateSpinnerUponAddition(getCurrentLine(), "nouns");
            populatingSpinner.setAdapter(formattedAdapter);

        }

        if (wordType.equalsIgnoreCase("adjective")) {
            spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.adjectives,
                    android.R.layout.simple_spinner_dropdown_item);

            entries.clear();
            createWordObjectsFromWordList();
            updateSpinnerUponAddition(getCurrentLine(), "adjectives");
            populatingSpinner.setAdapter(formattedAdapter);
        }

        if (wordType.equalsIgnoreCase("verb")) {
            spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.verbs,
                    android.R.layout.simple_spinner_dropdown_item);

            entries.clear();
            createWordObjectsFromWordList();
            updateSpinnerUponAddition(getCurrentLine(), "verbs");
            populatingSpinner.setAdapter(formattedAdapter);
        }

        if (wordType.equalsIgnoreCase("other")) {
            spinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.other,
                    android.R.layout.simple_spinner_dropdown_item);

            entries.clear();
            createWordObjectsFromWordList();
            updateSpinnerUponAddition(getCurrentLine(), "other");
            populatingSpinner.setAdapter(formattedAdapter);
        }

        spinner.setVisibility(View.VISIBLE);
        setSpinnerListener(populatingSpinner);
    }

    public void setSpinnerListener(final Spinner spinInNeedOfListener) {
        spinInNeedOfListener.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addWordButton = rootView.findViewById(R.id.addWordToHaiku);
                deleteWordButton = rootView.findViewById(R.id.delete_button);
                restartButton = rootView.findViewById(R.id.restart_button);
                displayButton = rootView.findViewById(R.id.display_button);


                wordToAdd = parent.getItemAtPosition(position).toString();
                String buttonText = getString(R.string.addToHaiku, wordToAdd);
                addWordButton.setText(buttonText);
                addWordButton.setVisibility(View.VISIBLE);

                addWordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //updateHaiku(findWord(wordToAdd));
                        updateHaiku(findWord(wordToAdd));
                        deleteWordButton.setVisibility(View.VISIBLE);
                        restartButton.setVisibility(View.VISIBLE);
                        displayButton.setVisibility(View.VISIBLE);
                    }
                });

                displayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mCallback.onDisplayButtonPressed(line1, line2, line3);
                    }
                });

                deleteWordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //deleteWordFromHaiku(findWord(wordToAdd));
                        deleteWordFromHaiku(getCurrentLine().getListOfAddedWords().
                                get(getCurrentLine().getListOfAddedWords().size() - 1));
                        if (line1.getRemainingSyllables() == 5) {
                            deleteWordButton.setVisibility(View.GONE);
                            displayButton.setVisibility(View.GONE);
                            restartButton.setVisibility(View.GONE);
                        }
                    }
                });

                restartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onRestartButtonPressed();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinInNeedOfListener.setSelection(0);
            }
        });
    }


    public Word findWord(String wordToFind) {
       // wordToFind = wordToFind.trim();
        for (int i = 0; i < entries.size(); i++) {
            if (wordToFind.equals(entries.get(i).toString())) {
                return entries.get(i);
            }
        }

        for (int i = 0; i < removedVerbs.size(); i++) {
            if (wordToFind.equals(removedVerbs.get(i).toString())) {
                return removedVerbs.get(i);
            }
        }

        for (int i = 0; i < removedAdjectives.size(); i++) {
            if (wordToFind.equals(removedAdjectives.get(i).toString())) {
                return removedAdjectives.get(i);
            }
        }

        for (int i = 0; i < removedNouns.size(); i++) {
            if (wordToFind.equals(removedNouns.get(i).toString())) {
                return removedNouns.get(i);
            }
        }

        for (int i = 0; i < removedOthers.size(); i++) {
            if (wordToFind.equals(removedOthers.get(i).toString())) {
                return removedOthers.get(i);
            }
        }
        return null;
    }

    public void createWordObjectsFromWordList() {
        ArrayList<String> wordNoSyllableCount = new ArrayList<String>();
        for (int i = 0; i < spinnerAdapter.getCount(); i++) {

            Word currentWord = new Word();
            String stringRepresentationOfWord = spinnerAdapter.getItem(i).toString();
            currentWord.setNumberOfSyllables(Integer.parseInt(stringRepresentationOfWord.substring(0,1)));
            currentWord.setRepresentedWord(stringRepresentationOfWord.toString().substring(1, stringRepresentationOfWord.length()));

            int radioButtonID = wordTypeRadioGroup.getCheckedRadioButtonId();
            View radioButton = rootView.findViewById(radioButtonID);
            int index = wordTypeRadioGroup.indexOfChild(radioButton);
            RadioButton selected = (RadioButton) wordTypeRadioGroup.getChildAt(index);
            currentWord.setType(selected.getText().toString());

            entries.add(currentWord);
            wordNoSyllableCount.add(currentWord.getRepresentedWord());
            if (i == spinnerAdapter.getCount() - 1) {
                formattedAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, wordNoSyllableCount);
            }

        }
    }

    public void updateSpinnerUponAddition(Line currentLine, String typeOfNewWord) {
        ArrayList<Word> updatedEntries = new ArrayList<Word>();
        ArrayList<Word> wordsUnableToFitLine = new ArrayList<Word>();

        for (int i = 0; i < formattedAdapter.getCount(); i++) {

            String stringRepresentationOfWord = formattedAdapter.getItem(i).toString();
            Word currentWord = findWord(stringRepresentationOfWord);
            if (currentWord.getNumberOfSyllables() <= currentLine.getRemainingSyllables()) {
                  updatedEntries.add(currentWord);


            }
            else {
                if (typeOfNewWord.equalsIgnoreCase("nouns")) {
                    removedNouns.add(currentWord);
                    wordsUnableToFitLine.add(currentWord);
                }
                else if (typeOfNewWord.equalsIgnoreCase("verbs")) {
                    removedVerbs.add(currentWord);
                    wordsUnableToFitLine.add(currentWord);
                }
                else if (typeOfNewWord.equalsIgnoreCase("adjectives")) {
                    removedAdjectives.add(currentWord);
                    wordsUnableToFitLine.add(currentWord);
                }
                else if (typeOfNewWord.equalsIgnoreCase("other")) {
                    removedOthers.add(currentWord);
                    wordsUnableToFitLine.add(currentWord);
                }
            }
        }

        if (wordsUnableToFitLine.isEmpty()) {
            formattedAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, entries);
        }
        else {
            entries.removeAll(wordsUnableToFitLine);
        }
        //formattedAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, entries);
        populatingSpinner.setAdapter(formattedAdapter);
    }

    public void updateSpinnerUponDeletion(Line currentLine, String wordType) {
        if (wordType.equalsIgnoreCase("nouns")) {
            for (int i = 0; i < removedNouns.size(); i++) {
                if (removedNouns.get(i).getNumberOfSyllables() <= currentLine.getRemainingSyllables()) {
                    if (!entries.contains(removedNouns.get(i))) {
                        entries.add(removedNouns.get(i));
                    }

                }
            }
        }
        else if (wordType.equalsIgnoreCase("verbs")) {
            for (int i = 0; i < removedVerbs.size(); i++) {
                if (removedVerbs.get(i).getNumberOfSyllables() <= currentLine.getRemainingSyllables()) {
                    if (!entries.contains(removedVerbs.get(i))) {
                        entries.add(removedVerbs.get(i));
                    }

                }
            }
        }
        else if (wordType.equalsIgnoreCase("adjectives")) {
            for (int i = 0; i < removedAdjectives.size(); i++) {
                if (removedAdjectives.get(i).getNumberOfSyllables() <= currentLine.getRemainingSyllables()) {
                    if (!entries.contains(removedAdjectives.get(i))) {
                        entries.add(removedAdjectives.get(i));
                    }

                }
            }
        }
        else if (wordType.equalsIgnoreCase("other")) {
            for (int i = 0; i < removedOthers.size(); i++) {
                if (removedOthers.get(i).getNumberOfSyllables() <= currentLine.getRemainingSyllables()) {
                    if (!entries.contains(removedOthers.get(i))) {
                        entries.add(removedOthers.get(i));
                    }
                }
            }
        }
        formattedAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, entries);
        populatingSpinner.setAdapter(formattedAdapter);
    }

    public Line returnLine1() {
        return line1;
    }

    public Line returnLine2() {
        return line2;
    }

    public Line returnLine3() {
        return line3;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnButtonSelectedListener) activity;
        }
        catch (ClassCastException cce) {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void initializeLines(Line line1, Line line2, Line line3) {
        line1.setAllowedSyllables(5);
        line1.setIsComplete(false);
        line1.setHaikuLine(rootView.findViewById(R.id.haikuLine1));

        line2.setAllowedSyllables(7);
        line2.setIsComplete(false);
        line2.setHaikuLine(rootView.findViewById(R.id.haikuLine2));

        line3.setAllowedSyllables(5);
        line3.setIsComplete(false);
        line3.setHaikuLine(rootView.findViewById(R.id.haikuLine3));
    }

    public Line getCurrentLine() {
        //Causes problems with spinner...
//        if (totalNumberOfSyllables <= 5) {
//            return line1;
//        }
        if (totalNumberOfSyllables >= 5 && totalNumberOfSyllables <= 12) {
            return line2;
        }
        else if (totalNumberOfSyllables >= 12) {
            return line3;
        }
        else {
            return line1;
        }
    }

    public void updateHaiku(Word wordToAdd) {
        if (!line1.getIsComplete()) {
            line1.getHaikuLine().append(" ");
            line1.addWordToLine(wordToAdd);
            line1.calculateLengthOfLine(wordToAdd);
            //updateSpinnerUponAddition(line1, wordToAdd.getType());

            if (line1.getRemainingSyllables() == 0) {
                line1.setIsComplete(true);
            }
        }
        else if (line1.getIsComplete() && !line2.getIsComplete()) {
            line2.getHaikuLine().append(" ");
            line2.addWordToLine(wordToAdd);
            line2.calculateLengthOfLine(wordToAdd);
           // updateSpinnerUponAddition(line2, wordToAdd.getType());

            if (line2.getRemainingSyllables() == 0) {
                line2.setIsComplete(true);
            }
        }
        else if (line2.getIsComplete()) {
            line3.getHaikuLine().append(" ");
            line3.addWordToLine(wordToAdd);
            line3.calculateLengthOfLine(wordToAdd);
            //updateSpinnerUponAddition(line3, wordToAdd.getType());

            if (line3.getRemainingSyllables() == 0) {
                line3.setIsComplete(true);
            }
        }
        else {

        }

        totalNumberOfSyllables += wordToAdd.getNumberOfSyllables();
        updateSpinnerUponAddition(getCurrentLine(), wordToAdd.getType());
    }

    public void deleteWordFromHaiku(Word wordToDelete) {

        if (!(line3.getHaikuLine().getText().toString().equals("3)") ||
                line3.getHaikuLine().getText().toString().equals("3) "))) {
            line3.deleteMostRecentWord(line3);
            //updateSpinnerUponDeletion(line3, wordToDelete.getType());

            if (line3.getRemainingSyllables() == 5) {
                line3.getHaikuLine().setText("3)");
            }
        }
        else if (!(line2.getHaikuLine().getText().toString().equals("2)") ||
                line2.getHaikuLine().getText().toString().equals("2) "))) {

            line2.deleteMostRecentWord(line2);
            //updateSpinnerUponDeletion(line2, wordToDelete.getType());
            if (line2.getRemainingSyllables() == 7) {
                line2.getHaikuLine().setText("2)");
            }
        }
        else if (!(line1.getHaikuLine().getText().toString().equals("1)")) ||
                line1.getHaikuLine().getText().toString().equals("1) ")) {
            line1.deleteMostRecentWord(line1);
            //updateSpinnerUponDeletion(line1, wordToDelete.getType());
        }
        totalNumberOfSyllables -= wordToDelete.getNumberOfSyllables();
        updateSpinnerUponDeletion(getCurrentLine(), wordToDelete.getType());
    }

    public Line getCurrentLineUponAddition() {
        if (totalNumberOfSyllables >= 5 && totalNumberOfSyllables <= 12) {
            return line2;
        }
        else if (totalNumberOfSyllables >= 12) {
            return line3;
        }
        else {
            return line1;
        }
    }

    public Line getCurrentLineUponDeletion() {
        if (totalNumberOfSyllables > 12) {
            return line3;
        }
        else if (totalNumberOfSyllables > 5  && totalNumberOfSyllables <= 12) {
            return line2;
        }
        else {
            return line1;
        }
    }

}
