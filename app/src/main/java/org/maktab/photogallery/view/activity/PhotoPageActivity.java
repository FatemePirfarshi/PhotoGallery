package org.maktab.photogallery.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.maktab.photogallery.R;
import org.maktab.photogallery.databinding.FragmentPhotoPageBinding;
import org.maktab.photogallery.view.fragment.PhotoPageFragment;

public class PhotoPageActivity extends SingleFragmentActivity{

    private static final String EXTRA_PHOTO_PAGE_URI = "org.maktab.photogallery.photoPageUri";
    private FragmentPhotoPageBinding mBinding;

    public static Intent newIntent(Context context, Uri uri) {
        Intent intent = new Intent(context, PhotoPageActivity.class);
        intent.putExtra(EXTRA_PHOTO_PAGE_URI, uri);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        Uri photoPageUri = getIntent().getParcelableExtra(EXTRA_PHOTO_PAGE_URI);
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_photo_page);
        return PhotoPageFragment.newInstance(photoPageUri);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mBinding.webViewPhotoPage.canGoBack()) {
                        mBinding.webViewPhotoPage.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
