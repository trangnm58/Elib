package com.pagenguyen.elib.ui.grammar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GrammarActivity extends AppCompatActivity {
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.lvExp) ExpandableListView expListView;

    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private final String API_URL ="http://elib.esy.es/";
    private final String[] API_SUB_URL = {
            "NguPhapCoBan/",
            "TiengAnhThongDung/",
            "GiaoTiepHangNgay/"
    };
    private final String[][] API_END_URL = {
            {
                    "Mao-tu.html",
                    "Tinh-tu.html"
            },
            {
                    "Cau-TA-thong-dung.html",
                    "Thanh-ngu-meo.html"
            },
            {
                    "Chao-hoi.html",
                    "Lam-viec.html"
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);
        ButterKnife.bind(this);

        setupToolbar();
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);// Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String link = API_URL
                        + API_SUB_URL[groupPosition % 3]
                        + API_END_URL[groupPosition % 3][childPosition % 2];
                String title = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

                Intent intent = new Intent(GrammarActivity.this, GrammarDetailActivity.class);
                intent.putExtra("link", link);
                intent.putExtra("title", title);
                startActivity(intent);

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (android.R.id.home): {
                onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Ngữ pháp cơ bản");
        listDataHeader.add("Tiếng Anh thông dụng");
        listDataHeader.add("Giao tiếp hàng ngày");

        // Adding child data
        List<String> grammarBasic = new ArrayList<String>();
        grammarBasic.add("Bài 1: Mạo từ");
        grammarBasic.add("Bài 2: Tính từ");
        grammarBasic.add("Bài 3: Danh từ");
        grammarBasic.add("Bài 4: Cụm danh từ");
        grammarBasic.add("Bài 5: Động từ");
        grammarBasic.add("Bài 6: Trạng từ");
        grammarBasic.add("Bài 7: Giới từ");

        List<String> grammarCommon = new ArrayList<String>();
        grammarCommon.add("Câu tiếng Anh thông dụng");
        grammarCommon.add("Thành ngữ với \"cat\"");
        grammarCommon.add("Ca dao tiếng Anh");
        grammarCommon.add("Cụm từ phổ dụng");
        grammarCommon.add("Cụm từ trong công nghệ");
        grammarCommon.add("\"Slang words\" thông dụng");

        List<String> grammarDaily = new ArrayList<String>();
        grammarDaily.add("Chào hỏi");
        grammarDaily.add("Làm việc");
        grammarDaily.add("Tin nhắn tỏ tình");
        grammarDaily.add("Giao tiếp văn phòng");
        grammarDaily.add("Chúc năm mới");

        listDataChild.put(listDataHeader.get(0), grammarBasic); // Header, Child data
        listDataChild.put(listDataHeader.get(1), grammarCommon);
        listDataChild.put(listDataHeader.get(2), grammarDaily);
    }
}
