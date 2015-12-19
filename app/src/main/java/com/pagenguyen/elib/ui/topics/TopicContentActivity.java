package com.pagenguyen.elib.ui.topics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.OneTextviewAdapter;
import com.pagenguyen.elib.adapter.TopicAdapter;
import com.pagenguyen.elib.ui.dictionary.VocabContentActivity;
import com.pagenguyen.elib.ui.exercise.MultipleChoiceListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopicContentActivity extends AppCompatActivity {

    @Bind(R.id.vocabularyListView) ListView mVocabularyListView;
    @Bind(R.id.my_toolbar) Toolbar mToolbar;

    public String mTopicName;

    private String[] giao_duc = {   "teacher",
            "friendship",
            "school",
            "university",
            "scholarship",
            "college",
            "math",
            "physic",
            "geography",
            "biology",
            "exam",
            "exercise",
            "question",
            "homework",
            "blackboard",
            "desk",
            "book",
            "pencil",
            "canteen",
            "classroom",
            "library",
            "playground",
            "laboratory",
            "professor",
            "headmaster",
            "chalk",
            "textbook",
            "notebook"
            };

    private String[] mean_giao_duc={
            "giáo viên",
            "tình bạn",
            "trường học",
            "đại học",
            "học bổng",
            "cao đẳng",
            "toán",
            "vật lý",
            "địa lý",
            "sinh học",
            "kỳ thi",
            "bài tập",
            "câu hỏi",
            "bài về nhà",
            "bảng đen",
            "bàn học",
            "sách",
            "bút chì",
            "căng-tin",
            "lớp học",
            "thư viện",
            "sân chơi",
            "phòng thí nghiệm",
            "giáo sư",
            "hiệu trưởng",
            "phấn",
            "sách giáo khoa",
            "vở"
    };

    private String[] gia_dinh = {   "family",
            "mother",
            "father",
            "son",
            "daughter",
            "parent",
            "house",
            "cousin",
            "grandfather",
            "grandmother",
            "grandparents",
            "stepbrother",
            "stepsister",
            "stepson",
            "stepdaughter",
            "stepmother",
            "stepfather",
            "godson",
            "goddaughter",
            "godmother",
            "godfather",
            "nephew",
            "uncle",
            "aunt",
            "niece",
            "honeymoon",
            "wedding",
            "divorcement",
            "separation",
            };

    private String[] mean_gia_dinh = {
            "gia đình",
            "mẹ",
            "bố (cha)",
            "con trai",
            "con gái",
            "bố mẹ",
            "nhà",
            "anh(em) họ",
            "ông",
            "bà",
            "ông bà",
            "anh(em) trai kế",
            "chị(em) gái kế",
            "con trai kế",
            "con gái kế",
            "mẹ kế",
            "cha kế",
            "con trai đỡ đầu",
            "con gái đỡ đầu",
            "mẹ đỡ đầu",
            "cha đỡ đầu",
            "cháu trai",
            "chú (cậu)",
            "cô (dì)",
            "cháu gái",
            "tuần trăng mật",
            "đám cưới",
            "ly hôn",
            "ly thân",
    };

    private String[] tinh_cach = {  "aggressive",
            "beneficent",
            "clever",
            "deceptive",
            "generous",
            "loyal",
            "polite",
            "sensitive"    };

    private String[] mean_tinh_cach = {  "năng nổ",
            "từ thiện",
            "lanh lợi",
            "dối trá",
            "bao dung",
            "đáng tin",
            "tử tế",
            "nhạy cảm"    };

    private String[] nghe_nghiep = {    "doctor",
            "teacher",
            "worker",
            "hacker",
            "designer",
            "engineer",
            "athlete",
            "president"    };

    private String[] mean_nghe_nghiep = {    "bác sĩ",
            "giáo viên",
            "công nhân",
            "hacker",
            "nhà thiết kế",
            "kĩ sư",
            "vận động viên",
            "tổng thống"    };

    private String[] nau_an = {         "food",
            "stuff",
            "fried",
            "dressed",
            "cured",
            "steamed",
            "pan",
            "marinated"    };

    private String[] mean_nau_an = {         "thức ăn",
            "chất liệu",
            "đồ chiên",
            "làm tưởi",
            "đồ hộp",
            "đồ hấp",
            "cái chảo",
            "ướp"    };

    private String[] the_thao = {       "athlete",
            "sport",
            "volleyball",
            "badminton",
            "soccer",
            "tennis",
            "coach",
            "champion"    };

    private String[] mean_the_thao = {       "vận động viên",
            "môn thể thao",
            "bóng chuyền",
            "cầu lông",
            "bóng đá",
            "tennis",
            "sân bóng",
            "vô địch"    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_content);
        ButterKnife.bind(this);

        setupToolbar();

        setVocabularyListView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercises, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_exercises):{
                Intent intent = new Intent(TopicContentActivity.this, MultipleChoiceListActivity.class);
                intent.putExtra("topic_name", mTopicName);
                startActivity(intent);
                return true;
            }
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
        Intent intent=getIntent();
        mTopicName=intent.getStringExtra("topic_name");

        ab.setTitle("Chủ đề: " + mTopicName);
    }

    private void setVocabularyListView(){
        String[] mTopics={"GIÁO DỤC", "GIA ĐÌNH", "TÍNH CÁCH", "NGHỀ NGHIỆP", "NẤU ĂN", "THỂ THAO"};;
        String[] tempVoca=new String[30];
        String[] tempMean=new String[30];

        int i;

        for (i=0; i<6; i++){
            if (mTopics[i].toLowerCase().equals(mTopicName.toLowerCase())){
                break;
            }
        }

        if (i==0){
            tempVoca=giao_duc;
            tempMean=mean_giao_duc;
        }
        else if (i==1){
            tempVoca=gia_dinh;
            tempMean=mean_gia_dinh;
        }
        else if (i==2){
            tempVoca=tinh_cach;
            tempMean=mean_tinh_cach;
        }
        else if (i==3){
            tempVoca=nghe_nghiep;
            tempMean=mean_nghe_nghiep;
        }
        else if (i==4){
            tempVoca=nau_an;
            tempMean=mean_nau_an;
        }
        else if (i==5){
            tempVoca=the_thao;
            tempMean=mean_the_thao;
        }

        TopicAdapter adapter=new TopicAdapter(TopicContentActivity.this,
                tempVoca,
                tempMean);

        mVocabularyListView.setAdapter(adapter);

        setItemOnClick();
    }

    private void setItemOnClick(){
        mVocabularyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vocabText = (TextView) view.findViewById(R.id.itemVocabulary);
                String name = vocabText.getText().toString().toLowerCase();

                Intent intent = new Intent(TopicContentActivity.this, VocabContentActivity.class);
                intent.putExtra("vocab", name);
                startActivity(intent);
            }
        });
    }
}
