/*
 * Copyright (C) 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sample.cast.refplayer.utils;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * Class representing local media metadata.
 */
public class MediaItem {

    private String mTitle;
    private String mSubTitle;
    private String mStudio;
    private String mUrl;
    private String mContentType;
    private int mDuration;
    private ArrayList<String> mImageList = new ArrayList<String>();

    public static final String KEY_TITLE = "title";
    public static final String KEY_SUBTITLE = "subtitle";
    public static final String KEY_STUDIO= "studio";
    public static final String KEY_URL= "movie-urls";
    public static final String KEY_IMAGES = "images";
    public static final String KEY_CONTENT_TYPE = "content-type";

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {

        return mTitle;
    }
    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String contentType) {
        mContentType = contentType;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public String getStudio() {
        return mStudio;
    }

    public void setStudio(String studio) {
        mStudio = studio;
    }

    public void addImage(String url) {
        mImageList.add(url);
    }

    public void addImage(String url, int index) {
        if (index < mImageList.size()) {
            mImageList.set(index, url);
        }
    }

    public String getImage(int index) {
        if (index < mImageList.size()) {
            return mImageList.get(index);
        }
        return null;
    }

    public boolean hasImage() {
        return !mImageList.isEmpty();
    }

    public ArrayList<String> getImages() {
        return mImageList;
    }

    public Bundle toBundle() {
        Bundle wrapper = new Bundle();
        wrapper.putString(KEY_TITLE, mTitle);
        wrapper.putString(KEY_SUBTITLE, mSubTitle);
        wrapper.putString(KEY_URL, mUrl);
        wrapper.putString(KEY_STUDIO, mStudio);
        wrapper.putStringArrayList(KEY_IMAGES, mImageList);
        wrapper.putString(KEY_CONTENT_TYPE, "video/mp4");
        return wrapper;
    }

    public static final MediaItem fromBundle(Bundle wrapper) {
        if (null == wrapper) {
            return null;
        }
        MediaItem media = new MediaItem();
        media.setUrl(wrapper.getString(KEY_URL));
        media.setTitle(wrapper.getString(KEY_TITLE));
        media.setSubTitle(wrapper.getString(KEY_SUBTITLE));
        media.setStudio(wrapper.getString(KEY_STUDIO));
        media.mImageList.addAll(wrapper.getStringArrayList(KEY_IMAGES));
        media.setContentType(wrapper.getString(KEY_CONTENT_TYPE));
        return media;
    }


}
