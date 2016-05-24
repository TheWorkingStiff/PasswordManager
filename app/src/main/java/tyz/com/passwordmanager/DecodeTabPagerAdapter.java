package tyz.com.passwordmanager;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * FragmentPagerAdapter for Activity and Help Screens.
 */





public class DecodeTabPagerAdapter extends FragmentStatePagerAdapter {

    public enum Tab {
        DECODE(R.string.decode);

        private final int mStringResource;

        Tab(@StringRes int stringResource) {
            mStringResource = stringResource;
        }

        public int getStringResource() {
            return mStringResource;
        }
    }

    private final Tab[] mTabs = Tab.values();
    private final CharSequence[] mTitles = new CharSequence[mTabs.length];

    public DecodeTabPagerAdapter(FragmentManager fm, Resources res) {
        super(fm);
        for (int i = 0; i < mTabs.length; i++) {
            mTitles[i] = res.getString(mTabs[i].getStringResource());
        }
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ScreenPasswordDecodeFragment.newInstance();

            default:
                throw new IllegalArgumentException("Unhandled position: " + position);
        }
    }

    @Override
    public int getCount() {
        return mTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }


}