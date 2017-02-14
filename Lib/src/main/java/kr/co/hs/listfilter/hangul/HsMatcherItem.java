package kr.co.hs.listfilter.hangul;

/**
 * 생성된 시간 2017-02-13, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsHangulListFilter
 * 패키지명 : kr.co.hs.listfilter.hangul
 */

public abstract class HsMatcherItem implements IHsMatcherItem{

    HsHangulMatcher.HsHangul[] mHsChar;

    @Override
    public HsHangulMatcher.HsHangul[] getChar() {
        if(mHsChar == null){
            String value = compareWith();
            mHsChar = new HsHangulMatcher.HsHangul[value.length()];

            for(int i=0;i<value.length();i++){
                char ch = value.charAt(i);

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

                    mHsChar[i] = new HsHangulMatcher.HsHangul(first, mid, last);
                }else{
                    mHsChar[i] = new HsHangulMatcher.HsHangul(ch);
                }
            }
        }
        return mHsChar;
    }
}
