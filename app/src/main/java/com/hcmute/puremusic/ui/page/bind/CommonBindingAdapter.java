package com.hcmute.puremusic.ui.page.bind;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.hcmute.architecture.utils.ClickUtils;

public class CommonBindingAdapter {

  @BindingAdapter(value = {"imageUrl", "placeHolder"}, requireAll = false)
  public static void imageUrl(ImageView view, String url, Drawable placeHolder) {
    Glide.with(view.getContext()).load(url).placeholder(placeHolder).into(view);
  }

  @BindingAdapter(value = {"visible"}, requireAll = false)
  public static void visible(View view, boolean visible) {
    view.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  @BindingAdapter(value = {"textColor"}, requireAll = false)
  public static void setTextColor(TextView textView, int textColorRes) {
    textView.setTextColor(textView.getContext().getColor(textColorRes));
  }

  @BindingAdapter(value = {"selected"}, requireAll = false)
  public static void selected(View view, boolean select) {
    view.setSelected(select);
  }


  @BindingAdapter(value = {"onClickWithDebouncing"}, requireAll = false)
  public static void onClickWithDebouncing(View view, View.OnClickListener clickListener) {
    ClickUtils.applySingleDebouncing(view, clickListener);
  }
}
