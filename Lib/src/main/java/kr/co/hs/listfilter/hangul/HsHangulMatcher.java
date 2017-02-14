package kr.co.hs.listfilter.hangul;


/**
 * 생성된 시간 2017-02-13, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsHangulListFilter
 * 패키지명 : kr.co.hs.listfilter.hangul
 */

public class HsHangulMatcher implements IHsHangulMatcher {

    private HsHangul[] mDes;
    private HsHangul[] mSearch;

    public HsHangulMatcher() {
        super();
    }

    public HsHangul[] getValue() {
        return mDes;
    }

    public void setValue(HsHangul[] mValue) {
        this.mDes = mValue;
    }

    public HsHangul[] getSearch() {
        return mSearch;
    }

    public void setSearch(String search) {
        mSearch = new HsHangul[search.length()];
        for(int i=0;i<search.length();i++){
            char ch = search.charAt(i);

            if(ch >= 0xAC00 && ch <= 0xD7A3){
                int a,b,c;
                c = ch - 0xAC00;
                a = c / (21 * 28);
                c = c % (21 * 28);
                b = c / 28;
                c = c % 28;

                char first;
                char mid;
                char last = 0;

                first = IHsHangulMatcher.CONST_FIRST_CH[a];
                mid = IHsHangulMatcher.CONST_MID_CH[b];
                if(c != 0)
                    last = IHsHangulMatcher.CONST_LAST_CH[c];

                mSearch[i] = new HsHangulMatcher.HsHangul(first, mid, last);
            }else{
                mSearch[i] = new HsHangulMatcher.HsHangul(ch);
            }
        }
    }

    @Override
    public boolean match() {
        if(mSearch == null){
            //검색어가 아예 없다는것은 필터를 안하겠다는것이므로 전부타 true
            return true;
        }

        //길이 체크
        int length = mSearch.length;
        if(length == 0)
        {
            //검색어가 아예 없다는것은 필터를 안하겠다는것이므로 전부타 true
            return true;
        }
        if(mDes.length < length){
            //찾으려는 문자열 보다 길이가 짧은것은 false
            return false;
        }


        for(int i=0;i<length;i++){
            if(mSearch[i].getFirst() == mDes[i].getFirst()){
                //초성이 같다.

                if(mSearch[i].getMid() == 0){
                    //검색어의 중성이 없다? 보류
                }else{
                    //검색어의 중성이 있다.
                    if(mSearch[i].getMid() == mDes[i].getMid()){
                        //중성이 같다.

                        if(mSearch[i].getLast() == 0){
                            //종성이 없다?
                            if(mDes[i].getLast() == 0){
                                //찾으려는 대상에도 종성이 없으면 넘어감
                            }else{
                                //찾으려는 대상에는 종성이 있다. 다르지만 다음 에 오는것이 종성이 될지도 모르니 넘어가려고 했으나
                                //종성이 아니라 검색어의 다음글자의 초성이되는경우는 false로 리턴한다.
                                if(mSearch.length > i+1)
                                    return false;
                            }
                        }else{
                            //종성이 있다?
                            if(mDes[i].getLast() == 0){
                                //비교 대상은 종성이 없다?
                                if(i+1 < mDes.length){
                                    //다음 문자가 있으면 다음 문자의 초성과 비교한다.
                                    if(mSearch[i].getLast() != mDes[i+1].getFirst())
                                        return false;
                                }else{
                                    //다음문자가 없으면 다른거다
                                    return false;
                                }
                            }else{
                                //비교 대상이 종성이 있다.
                                if(mSearch[i].getLast() != mDes[i].getLast())
                                    return false;
                            }
                        }
                    }else{
                        //중성이 다르다. 탈락
                        return false;
                    }
                }

            }else{
                //초성이 다르다. 탈락
                return false;
            }
        }

        return true;
    }


    public static class HsChar{
        char mFirst = 0;
        public HsChar(char first) {
            mFirst = first;
        }

        public char getFirst() {
            return mFirst;
        }
    }

    public static class HsHangul extends HsChar{
        char mMid = 0;
        char mLast = 0;

        public HsHangul(char first) {
            super(first);
        }

        public HsHangul(char first, char mid) {
            super(first);
            mMid = mid;
        }

        public HsHangul(char first, char mid, char last) {
            super(first);
            mMid = mid;
            mLast = last;
        }

        public char getMid() {
            return mMid;
        }

        public char getLast() {
            return mLast;
        }
    }
}
