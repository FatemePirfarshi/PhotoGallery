package org.maktab.photogallery.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.maktab.photogallery.R;
import org.maktab.photogallery.data.model.GalleryItem;
import org.maktab.photogallery.databinding.ItemListPhotoGalleryBinding;
import org.maktab.photogallery.viewmodel.PhotoGalleryViewModel;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    private final PhotoGalleryViewModel mViewModel;

    public PhotoAdapter(PhotoGalleryViewModel photoGalleryViewModel) {
        mViewModel = photoGalleryViewModel;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListPhotoGalleryBinding binding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(mViewModel.getApplication()),
                        R.layout.item_list_photo_gallery,
                        parent,
                        false);
        return new PhotoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        GalleryItem item = mViewModel.getCurrentItems().get(position);
        holder.bindGalleryItem(item, position);
    }

    @Override
    public int getItemCount() {
        return mViewModel.getCurrentItems().size();
    }

    class PhotoHolder extends RecyclerView.ViewHolder {

        private ItemListPhotoGalleryBinding mBinding;

        public PhotoHolder(ItemListPhotoGalleryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.setViewModel(mViewModel);
        }

        public void bindGalleryItem(GalleryItem item, int position) {
            mBinding.setPosition(position);

            Picasso.get()
                    .load(item.getUrl())
                    .placeholder(R.mipmap.ic_android_placeholder)
                    .into(mBinding.itemImageView);
        }
    }
}
