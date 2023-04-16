package com.hcmute.puremusic.ui.page.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.hcmute.puremusic.data.bean.LibraryInfo;
import com.hcmute.puremusic.data.bean.TestAlbum;


public class DiffUtils {

  private DiffUtil.ItemCallback<LibraryInfo> mLibraryInfoItemCallback;

  private DiffUtil.ItemCallback<TestAlbum.TestMusic> mTestMusicItemCallback;

  private DiffUtils() {
  }

  private static final DiffUtils S_DIFF_UTILS = new DiffUtils();

  public static DiffUtils getInstance() {
    return S_DIFF_UTILS;
  }

  public DiffUtil.ItemCallback<LibraryInfo> getLibraryInfoItemCallback() {
    if (mLibraryInfoItemCallback == null) {
      mLibraryInfoItemCallback = new DiffUtil.ItemCallback<LibraryInfo>() {
        @Override
        public boolean areItemsTheSame(@NonNull LibraryInfo oldItem, @NonNull LibraryInfo newItem) {
          return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull LibraryInfo oldItem, @NonNull LibraryInfo newItem) {
          return oldItem.getTitle().equals(newItem.getTitle());
        }
      };
    }
    return mLibraryInfoItemCallback;
  }

  public DiffUtil.ItemCallback<TestAlbum.TestMusic> getTestMusicItemCallback() {
    if (mTestMusicItemCallback == null) {
      mTestMusicItemCallback = new DiffUtil.ItemCallback<TestAlbum.TestMusic>() {
        @Override
        public boolean areItemsTheSame(@NonNull TestAlbum.TestMusic oldItem, @NonNull TestAlbum.TestMusic newItem) {
          return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull TestAlbum.TestMusic oldItem, @NonNull TestAlbum.TestMusic newItem) {
          return oldItem.getMusicId().equals(newItem.getMusicId());
        }
      };
    }
    return mTestMusicItemCallback;
  }
}
