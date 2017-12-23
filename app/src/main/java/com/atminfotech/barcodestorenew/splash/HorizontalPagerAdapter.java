package com.atminfotech.barcodestorenew.splash;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atminfotech.barcodestorenew.R;

import static com.atminfotech.barcodestorenew.splash.Utils.setupItem;

public class HorizontalPagerAdapter extends PagerAdapter {

    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.mipmap.ic_launcher,
                    "ATM Group Of Companies"
            ),
            new Utils.LibraryObject(
                    R.mipmap.atm_demo,
                    "Accounts and Tax Miners"
            ),
            new Utils.LibraryObject(
                    R.mipmap.atm_demo,
                    "ATM Infotech - Surat"
            ),
            new Utils.LibraryObject(
                    R.mipmap.atm_icon_new_demo,
                    "Barcode Store India"
            ),
            new Utils.LibraryObject(
                    R.mipmap.shribalaji,
                    "SriBalaji Barcode Pvt.Ltd."
            ),
            new Utils.LibraryObject(
                    R.mipmap.atm_demo,
                    "ATM Infotech - Delhi"
            ),
            new Utils.LibraryObject(
                    R.mipmap.sri_balaji_logo,
                    "Sri Balaji Automation"
            )
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private boolean mIsTwoWay;

    public HorizontalPagerAdapter(final Context context, final boolean isTwoWay) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIsTwoWay = isTwoWay;
    }

    @Override
    public int getCount() {
        return mIsTwoWay ? 6 : LIBRARIES.length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view=null;
        if (mIsTwoWay) {
            /*view = mLayoutInflater.inflate(R.layout.two_way_item, container, false);

            final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                    (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
            verticalInfiniteCycleViewPager.setAdapter(
                    new VerticalPagerAdapter(mContext)
            );
            verticalInfiniteCycleViewPager.setCurrentItem(position);*/
        } else {
            view = mLayoutInflater.inflate(R.layout.item, container, false);
            setupItem(view, LIBRARIES[position]);
        }

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
