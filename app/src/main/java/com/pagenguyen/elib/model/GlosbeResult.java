package com.pagenguyen.elib.model;

/**
 * Created by Page on 02/12/2015.
 */
public class GlosbeResult {
	private String[] mTranslations; // array of meanings
	private String[] mExamples; // array of english - vietnamese examples

	public String[] getTranslations() {
		return mTranslations;
	}

	public void setTranslations(String[] translations) {
		mTranslations = translations;
	}

	public String[] getExamples() {
		return mExamples;
	}

	public void setExamples(String[] examples) {
		mExamples = examples;
	}
}
