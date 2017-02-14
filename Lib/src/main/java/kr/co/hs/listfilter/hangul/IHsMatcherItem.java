package kr.co.hs.listfilter.hangul;

/**
 * 생성된 시간 2017-02-13, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsHangulListFilter
 * 패키지명 : kr.co.hs.listfilter.hangul
 */

public interface IHsMatcherItem {
    String compareWith();
    HsHangulMatcher.HsHangul[] getChar();
}
