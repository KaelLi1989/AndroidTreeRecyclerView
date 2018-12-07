package com.kaelli.androidtreerecyclerview.tree;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaelli.androidtreerecyclerview.R;
import com.kaelli.androidtreerecyclerview.utils.ScreenUtils;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KaelLi on 2018/11/26.
 */
public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> implements TreeStateChangeListener {
    private final static int ITEM_STATE_CLOSE = 0;
    private final static int ITEM_STATE_OPEN = 1;
    private Context mContext;
    private List<TreeItem> mList;

    public TreeAdapter(Context context, List<TreeItem> list) {
        initList(list, 0);
        this.mList = new LinkedList<>();
        this.mContext = context;
        this.mList.addAll(list);
    }

    private void initList(List<TreeItem> list, int level) {
        if (list == null || list.size() <= 0) return;
        for (TreeItem item: list) {
            item.itemLevel = level;
            if (item.child != null && item.child.size() > 0) {
                initList(item.child, level + 1);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_tree, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final TreeItem treeItem = mList.get(i);
        viewHolder.mTextView.setText(treeItem.title);
        if (i == mList.size() - 1) {
            viewHolder.mDivider.setVisibility(View.VISIBLE);
        } else if (mList.get(i + 1).itemLevel == 0) {
            viewHolder.mDivider.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mDivider.setVisibility(View.INVISIBLE);
        }

        if (treeItem.child != null && treeItem.child.size() > 0) {
            viewHolder.tvState.setVisibility(View.VISIBLE);
            if (treeItem.itemState == ITEM_STATE_OPEN) {
                viewHolder.tvState.setText("-");
            } else {
                viewHolder.tvState.setText("+");
            }
        } else {
            viewHolder.tvState.setVisibility(View.INVISIBLE);
        }

        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams)viewHolder.mIndicator.getLayoutParams();
        lp.width = (int)(ScreenUtils.getScreenWidth() * (0.044444 - treeItem.itemLevel * 0.011111));
        lp.height = (int)(ScreenUtils.getScreenWidth() * (0.044444 - treeItem.itemLevel * 0.011111));
        viewHolder.mIndicator.setLayoutParams(lp);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (treeItem.itemState == ITEM_STATE_CLOSE) {
                    onOpen(treeItem, viewHolder.getAdapterPosition());
                } else {
                    onClose(treeItem, viewHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onOpen(TreeItem treeItem, int position) {
        if (treeItem.child != null && treeItem.child.size() > 0) {
            mList.addAll(position + 1, treeItem.child);
            treeItem.itemState = ITEM_STATE_OPEN;
            notifyItemRangeInserted(position + 1, treeItem.child.size());
            notifyItemChanged(position);
        }
    }

    @Override
    public void onClose(TreeItem treeItem, int position) {
        if (treeItem.child != null && treeItem.child.size() > 0) {
            int nextSameOrHigherLevelNodePosition = mList.size() - 1;
            if (mList.size() > position + 1) {
                for (int i = position + 1; i < mList.size(); i++) {
                    if (mList.get(i).itemLevel <= mList.get(position).itemLevel) {
                        nextSameOrHigherLevelNodePosition = i - 1;
                        break;
                    }
                }
                closeChild(mList.get(position));
                if (nextSameOrHigherLevelNodePosition > position) {
                    mList.subList(position + 1, nextSameOrHigherLevelNodePosition + 1).clear();
                    treeItem.itemState = ITEM_STATE_CLOSE;
                    notifyItemRangeRemoved(position + 1, nextSameOrHigherLevelNodePosition - position);
                    notifyItemChanged(position);
                }
            }
        }
    }

    private void closeChild(TreeItem treeItem) {
        if (treeItem.child != null) {
            for (TreeItem child: treeItem.child) {
                child.itemState = ITEM_STATE_CLOSE;
                closeChild(child);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vIndicator) View mIndicator;
        @BindView(R.id.tvState) TextView tvState;
        @BindView(R.id.tvTitle) TextView mTextView;
        @BindView(R.id.vDivider) View mDivider;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
