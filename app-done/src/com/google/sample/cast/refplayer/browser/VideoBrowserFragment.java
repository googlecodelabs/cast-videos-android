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

package com.google.sample.cast.refplayer.browser;

import com.google.sample.cast.refplayer.R;
import com.google.sample.cast.refplayer.mediaplayer.LocalPlayerActivity;
import com.google.sample.cast.refplayer.utils.MediaItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A fragment to host a list view of the video catalog.
 */
public class VideoBrowserFragment extends Fragment implements VideoListAdapter.ItemClickListener,
        LoaderManager.LoaderCallbacks<List<MediaItem>> {

    private static final String TAG = "VideoBrowserFragment";
    private static final String CATALOG_URL =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/f.json";
    private RecyclerView mRecyclerView;
    private VideoListAdapter mAdapter;
    private View mEmptyView;
    private View mLoadingView;

    public VideoBrowserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_browser_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.list);
        mEmptyView = getView().findViewById(R.id.empty_view);
        mLoadingView = getView().findViewById(R.id.progress_indicator);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new VideoListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void itemClicked(View view, MediaItem item, int position) {
        String transitionName = getString(R.string.transition_image);
        VideoListAdapter.ViewHolder viewHolder =
                (VideoListAdapter.ViewHolder) mRecyclerView.findViewHolderForPosition(position);
        Pair<View, String> imagePair = Pair
                .create((View) viewHolder.getImageView(), transitionName);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), imagePair);

        Intent intent = new Intent(getActivity(), LocalPlayerActivity.class);
        intent.putExtra("media", item.toBundle());
        intent.putExtra("shouldStart", false);
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @Override
    public Loader<List<MediaItem>> onCreateLoader(int id, Bundle args) {
        return new VideoItemLoader(getActivity(), CATALOG_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<MediaItem>> loader, List<MediaItem> data) {
        mAdapter.setData(data);
        mLoadingView.setVisibility(View.GONE);
        mEmptyView.setVisibility(null == data || data.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<MediaItem>> loader) {
        mAdapter.setData(null);
    }

}
