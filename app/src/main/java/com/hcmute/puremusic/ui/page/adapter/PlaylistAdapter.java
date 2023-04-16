package com.hcmute.puremusic.ui.page.adapter;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.puremusic.R;
import com.hcmute.puremusic.data.bean.TestAlbum;
import com.hcmute.puremusic.databinding.AdapterPlayItemBinding;
import com.hcmute.puremusic.player.PlayerManager;
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;

public class PlaylistAdapter extends SimpleDataBindingAdapter<TestAlbum.TestMusic, AdapterPlayItemBinding> {

  public PlaylistAdapter(Context context) {
    super(context, R.layout.adapter_play_item, DiffUtils.getInstance().getTestMusicItemCallback());
  }

  protected void onBindItem(AdapterPlayItemBinding binding, TestAlbum item, RecyclerView.ViewHolder holder) {
    binding.setAlbum(item);
    int currentIndex = PlayerManager.getInstance().getAlbumIndex();
    binding.ivPlayStatus.setColor(currentIndex == holder.getAbsoluteAdapterPosition()
            ? binding.getRoot().getContext().getColor(R.color.gray) : Color.TRANSPARENT);
  }

  @Override
  protected void onBindItem(AdapterPlayItemBinding binding, TestAlbum.TestMusic item, RecyclerView.ViewHolder holder) {

  }
}
