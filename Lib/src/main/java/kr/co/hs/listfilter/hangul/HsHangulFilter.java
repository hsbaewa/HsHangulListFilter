package kr.co.hs.listfilter.hangul;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 생성된 시간 2017-02-13, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsHangulListFilter
 * 패키지명 : kr.co.hs.listfilter.hangul
 */

public class HsHangulFilter<ListObject extends IHsMatcherItem> extends Filter {

    private final ArrayList<ListObject> mOriginList;
    private final ArrayList<ListObject> mFilteredList;
    private OnPublishResultListener mOnPublishResultListener;
    private final HsHangulMatcher mHsHangulMatcher;

    public HsHangulFilter() {
        mOriginList = new ArrayList<>();
        mFilteredList = new ArrayList<>();
        mHsHangulMatcher = new HsHangulMatcher();
    }

    public int add(ListObject object){
        int idx = Integer.MIN_VALUE;
        mOriginList.add(object);
        if(isMatch(object)){
            mFilteredList.add(object);
            idx = mFilteredList.indexOf(object);
        }
        return idx;
    }

    public void addAll(ArrayList<ListObject> list){
        mOriginList.addAll(list);
        for(ListObject item : list){
            if(isMatch(item))
                mFilteredList.add(item);
        }
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        ArrayList<ListObject> result = new ArrayList<>();
        if(constraint.length() == 0)
            result.addAll(mOriginList);
        else{
            mHsHangulMatcher.setSearch(constraint.toString().trim());
            for (ListObject item : mOriginList) {
                if(isMatch(item)){
                    result.add(item);
                }
            }
        }
        FilterResults results = new FilterResults();
        results.values = result;
        results.count = result.size();

        return results;
    }

    private boolean isMatch(ListObject object){
        mHsHangulMatcher.setValue(object.getChar());
        if(mHsHangulMatcher.match())
            return true;
        else
            return false;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if(getOnPublishResultListener() != null){
            mFilteredList.clear();
            mFilteredList.addAll((Collection<? extends ListObject>) results.values);
            getOnPublishResultListener().publishResults(constraint, results.values, results.count);
        }
    }

    public OnPublishResultListener getOnPublishResultListener() {
        return mOnPublishResultListener;
    }

    public void setOnPublishResultListener(OnPublishResultListener onPublishResultListener) {
        mOnPublishResultListener = onPublishResultListener;
    }

    public ArrayList<ListObject> getOriginList() {
        return mOriginList;
    }

    public ArrayList<ListObject> getFilteredList() {
        return mFilteredList;
    }

    public interface OnPublishResultListener{
        void publishResults(CharSequence constraint, Object resultValue, int resultCount);
    }
}
