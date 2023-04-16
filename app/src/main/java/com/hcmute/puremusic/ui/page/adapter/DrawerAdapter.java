package com.hcmute.puremusic.ui.page.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import com.hcmute.puremusic.R;
import com.hcmute.puremusic.data.bean.LibraryInfo;
import com.hcmute.puremusic.databinding.AdapterLibraryBinding;
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;


public class DrawerAdapter extends SimpleDataBindingAdapter<LibraryInfo, AdapterLibraryBinding> {

  public DrawerAdapter(Context context) {
    super(context, R.layout.adapter_library, DiffUtils.getInstance().getLibraryInfoItemCallback());


    setOnItemClickListener((viewId, item, position) -> {
      Uri uri = Uri.parse(item.getUrl());
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      mContext.startActivity(intent);
    });
  }

  @Override
  protected void onBindItem(AdapterLibraryBinding binding, LibraryInfo item, RecyclerView.ViewHolder holder) {
    binding.setInfo(item);
  }
}
