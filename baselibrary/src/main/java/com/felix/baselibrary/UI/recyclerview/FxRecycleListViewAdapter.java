package com.felix.baselibrary.UI.recyclerview;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.felix.baselibrary.R;

import java.util.ArrayList;

public class FxRecycleListViewAdapter extends RecyclerView.Adapter<FxRecycleListViewAdapter.FxRecyclerViewHolder> {
    private static final String TAG = FxRecycleListViewAdapter.class.getSimpleName();
    private int width;
    private ArrayList<FxRecycleModel> models;
    private Context mContext;

    public FxRecycleListViewAdapter(Context context, ArrayList<FxRecycleModel> models) {
        width = ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getWidth();
        this.models = models;
        this.mContext = context;
    }

    @NonNull
    @Override
    public FxRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = View.inflate(parent.getContext(), R.layout.item_recycle_list, null);
        return new FxRecyclerViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final FxRecyclerViewHolder holder, final int position) {
        FxRecycleModel model = models.get(position);
        if (model.isOpen()) {
            holder.itemView.scrollTo(holder.tvDelWidth, 0);
        } else {
            holder.itemView.scrollTo(0, 0);
        }
        holder.tvLine1.setVisibility(TextUtils.isEmpty(model.getTitle()) ? View.GONE : View.VISIBLE);
        holder.tvLine1.setText(model.getTitle());
        holder.tvLine2.setVisibility(TextUtils.isEmpty(model.getContent()) ? View.GONE : View.VISIBLE);
        holder.tvLine2.setText(model.getContent());
        if (model.getImageView() == null) {
            holder.iv.setVisibility(View.GONE);
        } else {
            holder.iv.setVisibility(View.VISIBLE);
            holder.iv.setBackground(model.getImageView());
        }
        ViewGroup.LayoutParams params = holder.rlItem.getLayoutParams();
        params.width = width;
        final int[] eventX = new int[2];
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        eventX[0] = (int) event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        eventX[1] = (int) event.getX();
                        if (Math.abs(eventX[1] - eventX[0]) < 5) {
                            v.performClick();
                        }
                        int scrollX = holder.hsv.getScrollX();
                        Log.d(TAG, "onTouch: scrollX-> " + scrollX);
                        if (scrollX >= holder.tvDelWidth / 2) {
                            holder.hsv.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.hsv.smoothScrollTo(holder.tvDelWidth, 0);
                                }
                            });
                            Log.d(TAG, "onTouch: scrollX2-> " + holder.hsv.getScrollX());
//                            mItemOpenedList.add(position + "");
                        } else {
                            holder.hsv.post(new Runnable() {
                                @Override
                                public void run() {
                                    holder.hsv.smoothScrollTo(0, 0);
                                }
                            });
//                            mItemOpenedList.remove(position + "");
                            Log.d(TAG, "onTouch: scrollX3-> " + holder.hsv.getScrollX());
                        }
                        return true;
                }
                return false;
            }
        });
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.scrollTo(0, 0);
//                mTitles.remove(position);
                models.remove(position);
//                mItemOpenedList.remove(position + "");
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    static class FxRecyclerViewHolder extends RecyclerView.ViewHolder {
        final TextView tvLine1;
        final TextView tvLine2;
        final RelativeLayout rlItem;
        final TextView tvDel;
        final HorizontalScrollView hsv;
        final int tvDelWidth;
        final ImageView iv;


        FxRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLine1 = itemView.findViewById(R.id.tv_item_list);
            tvLine2 = itemView.findViewById(R.id.tv_item2_list);
            tvDel = itemView.findViewById(R.id.tv_del);
            rlItem = itemView.findViewById(R.id.rl_item);
            hsv = itemView.findViewById(R.id.hsv);
            tvDelWidth = tvDel.getLayoutParams().width;
            iv = itemView.findViewById(R.id.iv_item_list);
        }
    }

    public interface onDeleteButtonClickedListener {
        void onDeleted();
    }
}
