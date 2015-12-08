package com.pagenguyen.elib.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pagenguyen.elib.R;
import com.pagenguyen.elib.adapter.HomeMenuAdapter;
import com.pagenguyen.elib.model.HomeMenuItem;
import com.pagenguyen.elib.ui.dictionary.SearchVocabActivity;
import com.pagenguyen.elib.ui.stories.StoryListActivity;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
	private static final String TAG = HomeActivity.class.getSimpleName();
    @Bind(R.id.my_toolbar) Toolbar mToolbar;
    @Bind(R.id.homeMenu) ListView mHomeMenu;
	private ParseUser mCurrentUser;
    private HomeMenuItem[] mMenuItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupToolbar();
        checkOnFirstOpenApp();
        setHomeMenuAdapter();
        setupHomeMenuClick();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_for_home, menu);
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
                // go to login activity
                navigateToLogIn();
                return true;
            }
            case (R.id.action_logout):{
                // use ParseUser logout() function
                ParseUser.logOut();
                // go to login activity after logout
                navigateToLogIn();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void checkOnFirstOpenApp() {
        // TODO check on first
        if (isNetworkAvailable()) {
            mCurrentUser = ParseUser.getCurrentUser();
            if (mCurrentUser == null) {
                // first time using app or logout
                //navigateToLogIn();
                Log.d(TAG, "have not logged in");
            } else {
                // we have current user
                Log.d(TAG, mCurrentUser.getUsername());
            }
        } else {
            // display message to user
            Log.d(TAG, "no internet");
        }
    }

    private void setHomeMenuAdapter() {
        mMenuItems = new HomeMenuItem[6];
        mMenuItems[0] = new HomeMenuItem("topic", "Học từ theo chủ đề", "TopicActivity");
        mMenuItems[1] = new HomeMenuItem("book", "Học tiếng Anh qua truyện", "BookActivity");
        mMenuItems[2] = new HomeMenuItem("dictionary", "Từ điển tích hợp", "DictActivity");
        mMenuItems[3] = new HomeMenuItem("speak", "Luyện phát âm", "SpeakActivity");
        mMenuItems[4] = new HomeMenuItem("game", "Trò chơi", "GameActivity");
        mMenuItems[5] = new HomeMenuItem("favorite", "Danh mục từ yêu thích", "FavActivity");

        HomeMenuAdapter adapter = new HomeMenuAdapter(this, mMenuItems);
        mHomeMenu.setAdapter(adapter);
    }

    public void setupHomeMenuClick() {
        mHomeMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case (1): {
                        Intent intent = new Intent(HomeActivity.this, StoryListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case (2): {
                        Intent intent = new Intent(HomeActivity.this, SearchVocabActivity.class);
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
		// clear activity stack -> user can't back when in login activity
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return info != null && info.isConnected();
	}
}