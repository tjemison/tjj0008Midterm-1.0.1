package edu.auburn.eng.csse.comp3710.tjj0008.midterm18.tjj0008haiku;

import android.os.Parcelable;
import android.os.Parcel;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tevin on 3/6/2018.
 */

public class Line implements Parcelable{

    private int allowedSyllables = 0;
    private boolean isComplete = false;
    private TextView haikuLine;
    private int remainingSyllables;
    private int lengthOfLine = 3;
    private ArrayList<Word> listOfAddedWords = new ArrayList<Word>();

    public Line(int remainingSyllablesInLine) {
        remainingSyllables = remainingSyllablesInLine;
    }


    public boolean getIsComplete() {
        return isComplete;
    }

    public TextView getHaikuLine() {
        return haikuLine;
    }

    public void setAllowedSyllables(int newAllowedSyllables) {
        allowedSyllables = newAllowedSyllables;
    }

    public int getRemainingSyllables() {
        return remainingSyllables;
    }

    public ArrayList<Word> getListOfAddedWords() {
        return listOfAddedWords;
    }

    public void setIsComplete(boolean isActuallyComplete) {
        isComplete = isActuallyComplete;
    }

    public void setHaikuLine(View updatedHaikuLine) {
        haikuLine = (TextView) updatedHaikuLine;
    }

    public void addWordToLine(Word wordToAdd) {
        if (wordToAdd.getNumberOfSyllables() <= remainingSyllables) {
            haikuLine.append(wordToAdd.toString());
            returnRemainingSyllables(wordToAdd);
            listOfAddedWords.add(wordToAdd);
        }
    }

    public int returnRemainingSyllables(Word recentWord) {
        remainingSyllables -= recentWord.getNumberOfSyllables();
        return remainingSyllables;
    }

    public void setRemainingSyllables(int updatedSyllableCount) {
        remainingSyllables = updatedSyllableCount;
    }

    public int getLengthOfLine() {
        return lengthOfLine;
    }

    public int calculateLengthOfLine(Word word) {
        lengthOfLine += word.toString().length();
        return lengthOfLine;
    }


    public void deleteWord(Line lineToDeleteFrom, Word wordToDelete) {
        int lengthOfWordToDelete = wordToDelete.toString().length();

        int testText2 = lineToDeleteFrom.getHaikuLine().getText().toString().length();

        lineToDeleteFrom.getHaikuLine().setText(lineToDeleteFrom.getHaikuLine().getText().toString().substring(0,
                testText2 - lengthOfWordToDelete - 1));

        remainingSyllables += wordToDelete.getNumberOfSyllables();
        lineToDeleteFrom.setIsComplete(false);

        lineToDeleteFrom.getHaikuLine().getText().toString();
    }

    public void deleteMostRecentWord(Line lineToDeleteFrom) {
        int lengthOfWordToDelete = lineToDeleteFrom.
                getListOfAddedWords().get(listOfAddedWords.size() - 1).toString().length();
        int testText2 = lineToDeleteFrom.getHaikuLine().getText().toString().length();

        lineToDeleteFrom.getHaikuLine().setText(getHaikuLine().getText().toString().substring(0, testText2 - lengthOfWordToDelete - 1));

        if (getIsComplete()) {
            setIsComplete(false);
        }
        lineToDeleteFrom.setRemainingSyllables(lineToDeleteFrom.getListOfAddedWords()
                .get(listOfAddedWords.size() - 1).getNumberOfSyllables() + lineToDeleteFrom.getRemainingSyllables());
        lineToDeleteFrom.getListOfAddedWords().remove(listOfAddedWords.size() - 1);

    }

    protected Line(Parcel in) {
        allowedSyllables = in.readInt();
        isComplete = in.readByte() != 0x00;
        haikuLine = (TextView) in.readValue(TextView.class.getClassLoader());
        remainingSyllables = in.readInt();
        lengthOfLine = in.readInt();
        if (in.readByte() == 0x01) {
            listOfAddedWords = new ArrayList<Word>();
            in.readList(listOfAddedWords, Word.class.getClassLoader());
        } else {
            listOfAddedWords = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(allowedSyllables);
        dest.writeByte((byte) (isComplete ? 0x01 : 0x00));
        dest.writeValue(haikuLine);
        dest.writeInt(remainingSyllables);
        dest.writeInt(lengthOfLine);
        if (listOfAddedWords == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(listOfAddedWords);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Line> CREATOR = new Parcelable.Creator<Line>() {
        @Override
        public Line createFromParcel(Parcel in) {
            return new Line(in);
        }

        @Override
        public Line[] newArray(int size) {
            return new Line[size];
        }
    };

}
