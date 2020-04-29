package com.felix.baselibrary.UI.recyclerview;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class FxRecycleModel {
    private static FxRecycleModel recycleModel;
    private String title;
    private String content;
    private Drawable image;
    private boolean isOpen = false;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Drawable getImageView() {
        return image;
    }

    public boolean isOpen() {
        return isOpen;
    }

    private FxRecycleModel() {
    }


    public static class Builder {
        private String title = "";
        private String content = "";
        private Drawable image = null;
        private boolean isOpen = false;
        private Context mContext;

        public Builder(Context context) {
            mContext = context;
        }

        public FxRecycleModel create() {
            recycleModel = new FxRecycleModel();
            recycleModel.title = title;
            recycleModel.content = content;
            recycleModel.image = image;
            recycleModel.isOpen = isOpen;
            clearAll();
            return recycleModel;
        }

        private void clearAll() {
            title = "";
            content = "";
            image = null;
            isOpen = false;
        }

        public Builder setTitle(String s) {
            this.title = s;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;

        }

        public Builder setImageView(Drawable image) {
            this.image = image;
            return this;

        }

        public Builder setImageView(int imageSrc) {
            this.image = mContext.getResources().getDrawable(imageSrc);
            return this;

        }

        public Builder setOpen(boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }
    }
}
