# HsHangulListFilter

[![](https://jitpack.io/v/hsbaewa/HsHangulListFilter.svg)](https://jitpack.io/#hsbaewa/HsHangulListFilter)

###한글 입력 필터 라이브러리 추가 방법

root 수준의 build.gradle에 추가
<pre><code>
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
</code></pre>

app 수준의 build.gradle에 추가
<pre><code>
	dependencies {
	        compile 'com.github.hsbaewa:HsHangulListFilter:0.0.2'
	}
</code></pre>

<pre><code>
//초기화
HsHangulFilter&lt;IHsMatcherItem&gt; mHsHangulFilter = new HsHangulFilter<>();
</code></pre>
먼저 IHsMatcherItem을 구현하여 compareWith 함수 안에 비교 할 문자열을 리턴시킨다.

<pre><code>
public class BlackListItem implements IHsMatcherItem{
    @Override
    public String compareWith() {
        //HsHangulFilter 내부에서 비교 될 문자열 리턴
        return getName();
    }
}
</code></pre>

필터링이 끝나면 OnPublishResultListener의 publishResults함수가 호출되므로 HsHangulFilter 인스턴스에 이벤트 리스너로 OnPublishResultListener을 등록하여 준다.
<pre><code>
mHsHangulFilter.setOnPublishResultListener(this);
</code></pre>

RecyclerView를 사용하여 전체 예제 소스
<pre><code>
    class Adapter extends HsRecyclerView.HsAdapter<Holder> implements Filterable, HsHangulFilter.OnPublishResultListener{
        private HsHangulFilter<Item> mHsHangulFilter = new HsHangulFilter<>();

        public BlackListAdapter() {
            mHsHangulFilter.setOnPublishResultListener(this);
        }

        @Override
        public BlackListHolder onCreateHsViewHolder(ViewGroup viewGroup, int i) {
            return new BlackListHolder(LayoutInflater.from(getContext()).inflate(R.layout.viewholder_blacklist, viewGroup, false));
        }

        @Override
        public void onBindHsViewHolder(BlackListHolder holder, int i, boolean b) {
            holder.onBind(getItem(i));
        }

        @Override
        public int getHsItemCount() {
            return mHsHangulFilter.getFilteredList().size();
        }

        @Override
        protected BlackListItem getItem(int position) {
            return mHsHangulFilter.getFilteredList().get(position);
        }

        public int addItem(BlackListItem item){
            return mHsHangulFilter.add(item);
        }

        @Override
        public Filter getFilter() {
            return mHsHangulFilter;
        }

        @Override
        public void publishResults(CharSequence constraint, Object resultValue, int resultCount) {
            notifyDataSetChanged();
        }
    }
</code></pre>