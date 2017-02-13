package kr.co.hs.listfilter.hangul;

/**
 * 생성된 시간 2017-02-13, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsHangulListFilter
 * 패키지명 : kr.co.hs.listfilter.hangul
 */

public class HsHangulMatcher implements IHsHangulMatcher {

    private String mValue = "";
    private String mSearch = "";

    public HsHangulMatcher(String mValue, String mSearch) {
        super();
        this.mValue = mValue;
        this.mSearch = mSearch;
    }

    public HsHangulMatcher() {
        super();
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

    public String getSearch() {
        return mSearch;
    }

    public void setSearch(String mSearch) {
        this.mSearch = mSearch;
    }

    private boolean isHangul(char ch){
        if(ch >= 0xAC00 && ch <= 0xD7A3)
            return true;
        else
            return false;
    }

    private void divide(StringBuffer dest, String src){
        if(src != null && dest != null){
            for(int i=0;i<src.length();i++){
                char ch = src.charAt(i);
                if(isHangul(ch)){
                    int a,b,c;
                    c = ch - 0xAC00;
                    a = c / (21 * 28);
                    c = c % (21 * 28);
                    b = c / 28;
                    c = c % 28;

                    dest.append(CONST_FIRST_CH[a]);
                    dest.append(CONST_MID_CH[b]);
                    if(c != 0)
                        dest.append(CONST_LAST_CH[c]);
                }else{
                    dest.append(ch);
                }
            }
        }
    }


    @Override
    public boolean match() {
        StringBuffer dividedValue = new StringBuffer();
        StringBuffer dividedSearch = new StringBuffer();

        if(getValue() != null){
            divide(dividedValue, getValue());
        }
        if(getSearch() != null){
            divide(dividedSearch, getSearch());
        }

        if(dividedValue.toString().contains(dividedSearch.toString()))
            return true;
        else
            return false;
    }
}
