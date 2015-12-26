package com.pagenguyen.elib.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.HomeMenuAdapter;
import com.pagenguyen.elib.api.SpeechRecognitionHelper;
import com.pagenguyen.elib.model.HomeMenuItem;
import com.pagenguyen.elib.ui.dictionary.SearchVocabActivity;
import com.pagenguyen.elib.ui.dictionary.TranslatorActivity;
import com.pagenguyen.elib.ui.dictionary.VocabContentActivity;
import com.pagenguyen.elib.ui.grammar.GrammarActivity;
import com.pagenguyen.elib.ui.notebook.MyNotebookActivity;
import com.pagenguyen.elib.ui.pronounce.PronouncePracticeActivity;
import com.pagenguyen.elib.ui.stories.StoryListActivity;
import com.pagenguyen.elib.ui.topics.TopicListActivity;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
	private static final String TAG = HomeActivity.class.getSimpleName();
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.homeMenu) ListView mHomeMenu;
	private ParseUser mCurrentUser;
    private HomeMenuItem[] mMenuItems;
    private boolean doubleBackToExitPressedOnce = false;
    private boolean isListening = false;
    private Menu mMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupToolbar();
        setHomeMenuAdapter();
        setupHomeMenuClick();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_for_home, mMenu);
        setVisibleMenuItem();

        // Define the listener
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                setVisibleMenuItem();
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                mMenu.getItem(0).setVisible(true);
                mMenu.getItem(1).setVisible(true);
                mMenu.getItem(2).setVisible(false);
                mMenu.getItem(3).setVisible(false);
                mMenu.getItem(4).setVisible(false);
                mMenu.getItem(5).setVisible(false);
                mMenu.getItem(0).setEnabled(true);
                mMenu.getItem(1).setEnabled(true);
                mMenu.getItem(2).setEnabled(false);
                mMenu.getItem(3).setEnabled(false);
                mMenu.getItem(4).setEnabled(false);
                mMenu.getItem(5).setEnabled(false);
                return true;  // Return true to expand action view
            }
        };

        // Get the MenuItem for the action item
        MenuItem searchItem = mMenu.findItem(R.id.action_search);
        // Assign the listener to that action item
        MenuItemCompat.setOnActionExpandListener(searchItem, expandListener);

        // Associate searchable configuration with the SearchView
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners...
        searchView.setQueryHint("Nhập từ cần tìm...");

        // set text color
        // traverse the view to the widget containing the hint text
        LinearLayout ll = (LinearLayout)searchView.getChildAt(0);
        LinearLayout ll2 = (LinearLayout)ll.getChildAt(2);
        LinearLayout ll3 = (LinearLayout)ll2.getChildAt(1);
        SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete)ll3.getChildAt(0);
        // set the text color
        autoComplete.setTextColor(getResources().getColor(R.color.TextColorWhite));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String vocab = "" + searchView.getQuery();

                //Go to vocabulary search result
                Intent intent = new Intent(HomeActivity.this, VocabContentActivity.class);
                intent.putExtra("vocab", vocab);
                startActivity(intent);

                mMenu.getItem(0).collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Do nothing
                return false;
            }
        });

        return super.onCreateOptionsMenu(mMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_voice):{
                if(!isListening) {
                    startListening();
                } else {
                    stopListening();
                }
                return true;
            }
            case (R.id.action_my_profile):{
                Toast.makeText(
                        HomeActivity.this,
                        "Chức năng chưa hỗ trợ!",
                        Toast.LENGTH_SHORT
                ).show();
                // TODO intent to my profile
                return true;
            }
            case (R.id.action_settings):{
                Toast.makeText(
                        HomeActivity.this,
                        "Chức năng chưa hỗ trợ!",
                        Toast.LENGTH_SHORT
                ).show();
                // TODO intent to settings
                return true;
            }
            case (R.id.action_login):{
                if (isNetworkAvailable()) {
                    // go to login activity
                    navigateToLogIn();
                } else {
                    Toast.makeText(
                            HomeActivity.this,
                            "Không có kết nối mạng!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                return true;
            }
            case (R.id.action_logout):{
                if (isNetworkAvailable()) {
                    // use ParseUser logout() function
                    ParseUser.logOut();
                    // go to login activity after logout
                    navigateToLogIn();
                } else {
                    Toast.makeText(
                            HomeActivity.this,
                            "Không có kết nối mạng!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn Back lần nữa để thoát.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SpeechRecognitionHelper.SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && null != data) {
                // results là mảng string các kết quả trả về
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if(results != null){
                    //get the first vocabulary in result
                    String vocab = results.get(0);

                    //Go to vocabulary search result
                    Intent intent = new Intent(HomeActivity.this, VocabContentActivity.class);
                    intent.putExtra("vocab", vocab);
                    startActivity(intent);
                } else {
                    //Code
                }
            }
        }
    }

    private void startListening() {
        isListening = true;

        //Start listening
        SpeechRecognitionHelper.onSpeech(HomeActivity.this);
        stopListening();
    }

    private void stopListening() {
        isListening = false;
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void setHomeMenuAdapter() {
        mMenuItems = new HomeMenuItem[7];
        mMenuItems[0] = new HomeMenuItem("grammar", "Học ngữ pháp", "GrammarActivity");
        mMenuItems[1] = new HomeMenuItem("topic", "Học từ theo chủ đề", "TopicActivity");
        mMenuItems[2] = new HomeMenuItem("book", "Học tiếng Anh qua truyện", "BookActivity");
        mMenuItems[3] = new HomeMenuItem("dictionary", "Từ điển Anh-Việt", "DictActivity");
        mMenuItems[4] = new HomeMenuItem("translator", "Dịch văn bản", "TranActivity");
        mMenuItems[5] = new HomeMenuItem("speak", "Luyện phát âm", "PronouncePracticeActivity");
        /*mMenuItems[5] = new HomeMenuItem("game", "Trò chơi", "GameActivity");*/
        mMenuItems[6] = new HomeMenuItem("favorite", "Sổ tay của tôi", "MyNotebookActivity");

        HomeMenuAdapter adapter = new HomeMenuAdapter(this, mMenuItems);
        mHomeMenu.setAdapter(adapter);
    }

    private void setVisibleMenuItem() {
        if (isNetworkAvailable()) {
            mCurrentUser = ParseUser.getCurrentUser();
            if (mCurrentUser == null) {
                // first time using app or logout
                // navigateToLogIn();
                Log.d(TAG, "have not logged in");
                mMenu.getItem(0).setVisible(true);
                mMenu.getItem(1).setVisible(false);
                mMenu.getItem(2).setVisible(false);
                mMenu.getItem(3).setVisible(true);
                mMenu.getItem(4).setVisible(true);
                mMenu.getItem(5).setVisible(false);
                mMenu.getItem(0).setEnabled(true);
                mMenu.getItem(1).setEnabled(false);
                mMenu.getItem(2).setEnabled(false);
                mMenu.getItem(3).setEnabled(true);
                mMenu.getItem(4).setEnabled(true);
                mMenu.getItem(5).setEnabled(false);
            } else {
                // we have current user
                Log.d(TAG, mCurrentUser.getUsername());
                mMenu.getItem(0).setVisible(true);
                mMenu.getItem(1).setVisible(false);
                mMenu.getItem(2).setVisible(true);
                mMenu.getItem(3).setVisible(true);
                mMenu.getItem(4).setVisible(false);
                mMenu.getItem(5).setVisible(true);
                mMenu.getItem(0).setEnabled(true);
                mMenu.getItem(1).setEnabled(false);
                mMenu.getItem(2).setEnabled(false);
                mMenu.getItem(3).setEnabled(true);
                mMenu.getItem(4).setEnabled(true);
                mMenu.getItem(5).setEnabled(false);
            }
        } else {
            // display message to user
            Log.d(TAG, "no internet");
            mMenu.getItem(0).setVisible(false);
            mMenu.getItem(1).setVisible(false);
            mMenu.getItem(2).setVisible(false);
            mMenu.getItem(3).setVisible(true);
            mMenu.getItem(4).setVisible(true);
            mMenu.getItem(5).setVisible(false);
            mMenu.getItem(0).setEnabled(true);
            mMenu.getItem(1).setEnabled(false);
            mMenu.getItem(2).setEnabled(false);
            mMenu.getItem(3).setEnabled(true);
            mMenu.getItem(4).setEnabled(true);
            mMenu.getItem(5).setEnabled(false);
        }
    }

    public void setupHomeMenuClick() {
        mHomeMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case (0): {
                        Intent intent = new Intent(HomeActivity.this, GrammarActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case (1): {
                        Intent intent = new Intent(HomeActivity.this, TopicListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case (2): {
                        Intent intent = new Intent(HomeActivity.this, StoryListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case (3): {
                        Intent intent = new Intent(HomeActivity.this, SearchVocabActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case (4): {
                        Intent intent = new Intent(HomeActivity.this, TranslatorActivity.class);
                        startActivity(intent);
                        break;
                    }
	                case (5): {
		                Intent intent = new Intent(HomeActivity.this, PronouncePracticeActivity.class);
		                startActivity(intent);
		                break;
	                }
	                case (6): {
		                Intent intent = new Intent(HomeActivity.this, MyNotebookActivity.class);
		                startActivity(intent);
		                break;
	                }
                    default: {
                        Toast.makeText(
                                HomeActivity.this,
                                ((HomeMenuAdapter) parent.getAdapter()).getItem(position).getLabel(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        });
    }

	private void navigateToLogIn() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return info != null && info.isConnected();
	}
}
