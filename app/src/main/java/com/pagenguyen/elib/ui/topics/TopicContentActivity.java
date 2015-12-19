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
import com.pagenguyen.elib.ui.dictionary.VocabContentActivity;
import com.pagenguyen.elib.ui.exercise.MultipleChoiceListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopicContentActivity extends AppCompatActivity {

    @Bind(R.id.topicNameView) TextView mTopicNameView;
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
            "cloakroom",
            "laboratory",
            "professor",
            "headmaster",
            "chalk",
            "textbook",
            "notebook"
            };

    private String[] gia_dinh = {   "family",
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
            "half-sister",
            "half-brother"
            };

    private String[] tinh_cach = {  "aggressive",
            "beneficent",
            "clever",
            "deceptive",
            "generous",
            "loyal",
            "polite",
            "sensitive"    };

    private String[] nghe_nghiep = {    "doctor",
            "teacher",
            "worker",
            "hacker",
            "designer",
            "engineer",
            "athlete",
            "president"    };

    private String[] nau_an = {         "food",
            "stuffed",
            "fried",
            "dressed",
            "cured",
            "steamed",
            "pan",
            "marinated"    };

    private String[] the_thao = {       "athlete",
            "sport",
            "volleyball",
            "badminton",
            "soccer",
            "tennis",
            "coach",
            "champion"    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_content);
        ButterKnife.bind(this);

        setupToolbar();

        loadTopicName();

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
    }

    private void loadTopicName(){
        Intent intent=getIntent();
        mTopicName=intent.getStringExtra("topic_name");

        mTopicNameView.setText("Chủ đề: " + mTopicName);
    }

    private void setVocabularyListView(){
        String[] mTopics={"GIÁO DỤC", "GIA ĐÌNH", "TÍNH CÁCH", "NGHỀ NGHIỆP", "NẤU ĂN", "THỂ THAO"};;
        String[] temp=new String[20];

        int i;

        for (i=0; i<6; i++){
            if (mTopics[i].equals(mTopicName)){
                break;
            }
        }

        if (i==0)   temp=giao_duc;
        else if (i==1)  temp=gia_dinh;
        else if (i==2)  temp=tinh_cach;
        else if (i==3)  temp=nghe_nghiep;
        else if (i==4)  temp=nau_an;
        else if (i==5)  temp=the_thao;

        OneTextviewAdapter adapter=new OneTextviewAdapter(TopicContentActivity.this,
                R.layout.item_one_textview,
                R.id.itemContent,
                temp);

        mVocabularyListView.setAdapter(adapter);

        setItemOnClick();
    }

    private void setItemOnClick(){
        mVocabularyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vocabText = (TextView) view.findViewById(R.id.itemContent);
                String name = vocabText.getText().toString().toLowerCase();

                Intent intent = new Intent(TopicContentActivity.this, VocabContentActivity.class);
                intent.putExtra("vocab", name);
                startActivity(intent);
            }
        });
    }
}
