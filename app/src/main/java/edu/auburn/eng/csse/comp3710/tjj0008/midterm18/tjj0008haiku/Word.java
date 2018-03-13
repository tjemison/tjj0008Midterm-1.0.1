package edu.auburn.eng.csse.comp3710.tjj0008.midterm18.tjj0008haiku;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tevin on 3/6/2018.
 */

public class Word implements Parcelable{
    private int numberOfSyllables = 0;
    private String representedWord = "";
    private String type = "";

    public Word() { }

    public int getNumberOfSyllables() {
        return numberOfSyllables;
    }

    public String getRepresentedWord() {
        return representedWord;
    }

    public String getType() {
        return type;
    }

    public void setNumberOfSyllables(int newNumberOfSyllables) {
        numberOfSyllables = newNumberOfSyllables;
    }

    public void setRepresentedWord(String newRepresentedWord) {
        representedWord = newRepresentedWord;
    }

    public void setType(String typeOfWord) {
        type = typeOfWord;
    }

    public String toString() {
        String output;
        output = representedWord + "";
        return output;
    }

    protected Word(Parcel in) {
        numberOfSyllables = in.readInt();
        representedWord = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numberOfSyllables);
        dest.writeString(representedWord);
        dest.writeString(type);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}