package trivial.speckmussweg.Objects;


import java.util.List;

public class Sub {

    private int mId = 0;

    private String mBread;
    private Boolean mSubIsLong;
    private List<String> mCheese;
    private List<String> mMeat;
    private List<String> mExtra;
    private List<String> mSauce;


    public Sub(int id, String bread, Boolean subIsLong, List<String> cheese, List<String> meat, List<String> extra, List<String> sauce) {
        mId = id;
        mBread = bread;
        mSubIsLong = subIsLong;
        mCheese = cheese;
        mMeat = meat;
        mExtra = extra;
        mSauce = sauce;


    }

    public int getmId() {
        return mId;
    }

    public String getmBread() {
        return mBread;
    }

    public Boolean getmSubIsLong() {
        return mSubIsLong;
    }

    public List<String> getmCheese() {
        return mCheese;
    }

    public List<String> getmMeat() {
        return mMeat;
    }

    public List<String> getmExtra() {
        return mExtra;
    }

    public List<String> getmSauce() {
        return mSauce;
    }

    public void setmBread(String mBread) {
        this.mBread = mBread;
    }

    public void setmSubIsLong(Boolean mSubIsLong) {
        this.mSubIsLong = mSubIsLong;
    }

    public void setmCheese(List<String> mCheese) {
        this.mCheese = mCheese;
    }

    public void setmMeat(List<String> mMeat) {
        this.mMeat = mMeat;
    }

    public void setmExtra(List<String> mExtra) {
        this.mExtra = mExtra;
    }

    public void setmSauce(List<String> mSauce) {
        this.mSauce = mSauce;
    }
}
