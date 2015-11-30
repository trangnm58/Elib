package com.pagenguyen.elib;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by Page on 30/11/2015.
 */
public class ElibApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "J6qI1wPiQcWbr8MYYJKqtwDoEGFDIES97i0yRT0T", "LMCeRvr44kcTJOlOQdYJXPm5rLqysyt7bAx71PKq");
	}
}
