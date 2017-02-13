package kr.co.hs.listfilter.hangul;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * 생성된 시간 2017-02-13, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsHangulListFilter
 * 패키지명 : kr.co.hs.listfilter.hangul
 */

public class HsHangulFilter<ListObject extends HsMatcherItem> extends Filter {

    private final ArrayList<ListObject> mOriginList;
    private OnPublishResultListener<ListObject> mListObjectOnPublishResultListener;

    public HsHangulFilter(ArrayList<ListObject> originList, OnPublishResultListener<ListObject> listObjectOnPublishResultListener) {
        mOriginList = new ArrayList<>();
        mOriginList.addAll(originList);
        this.mListObjectOnPublishResultListener = listObjectOnPublishResultListener;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        ArrayList<ListObject> result = new ArrayList<>();
        if(constraint.length() == 0)
            result.addAll(mOriginList);
        else{
            HsHangulMatcher hangulMatcher = new HsHangulMatcher();
            hangulMatcher.setSearch(constraint.toString().toLowerCase().trim());
            for (ListObject item : mOriginList) {
                hangulMatcher.setValue(item.compareWith());
                if(hangulMatcher.match())
                    result.add(item);
            }
        }
        FilterResults results = new FilterResults();
        results.values = result;
        results.count = result.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if(mListObjectOnPublishResultListener != null){
            ArrayList<ListObject> mItems = (ArrayList<ListObject>) results.values;
            mListObjectOnPublishResultListener.onPublishResults(mItems);
        }
    }

    public ArrayList<ListObject> getOriginList() {
        return mOriginList;
    }

    public interface OnPublishResultListener<ListObject extends HsMatcherItem>{
        void onPublishResults(ArrayList<ListObject> list);
    }
}
