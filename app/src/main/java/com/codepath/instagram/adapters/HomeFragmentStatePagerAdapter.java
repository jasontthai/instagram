package com.codepath.instagram.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.codepath.instagram.R;
import com.codepath.instagram.fragments.PostsFragment;
import com.codepath.instagram.fragments.SearchFragment;
import com.codepath.instagram.helpers.SmartFragmentStatePagerAdapter;

/**
 * Created by trit on 10/31/15.
 */
public class HomeFragmentStatePagerAdapter extends SmartFragmentStatePagerAdapter {
    private static int NUM_ITEMS = 5;
    private int[] imageId = {
            R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_capture, R.drawable.ic_notifs, R.drawable.ic_profile
    };
    private Context context;

    public HomeFragmentStatePagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = PostsFragment.newInstance();
                break;
            default:
                fragment = SearchFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        // return tabTitles[position];

        // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
        // or ContextCompat.getDrawable(Context context, int id) if you want support for older versions.
        // Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
        // Drawable image = context.getResources().getDrawable(imageResId[position]);

        Drawable image = ContextCompat.getDrawable(context, imageId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
