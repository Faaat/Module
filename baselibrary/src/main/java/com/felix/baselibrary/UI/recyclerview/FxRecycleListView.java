package com.felix.baselibrary.UI.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FxRecycleListView extends RecyclerView {
    public FxRecycleListView(@NonNull Context context) {
        this(context, null);
    }

    public FxRecycleListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FxRecycleListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);

    }

    private void initView(Context context, AttributeSet attrs) {
        addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        setLayoutManager(new LinearLayoutManager(context));
    }
}
