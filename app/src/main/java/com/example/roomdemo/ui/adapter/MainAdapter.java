package com.example.roomdemo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdemo.model.Note;
import com.example.roomdemo.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Note> mNoteList;
    private INoteItemClickListener mListener;

    public MainAdapter(Context context, List<Note> noteList) {
        mNoteList = noteList;
        mListener = (INoteItemClickListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindContent(position);
    }

    @Override
    public int getItemCount() {
        return mNoteList != null ? mNoteList.size() : 0;
    }

    public void updateNoteList(int pos) {
        mNoteList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, mNoteList.size());
        notifyDataSetChanged();
    }

    void updateItem(Integer pos) {
        notifyItemChanged(pos);
        notifyItemRangeChanged(pos,mNoteList.size());
        notifyDataSetChanged();
    }

    public interface INoteItemClickListener {
        void onNoteItemLongClick(View v, int adapterPosition, List<Note> mNoteList);
        default void onNoteItemClick(View v, int adapterPosition, List<Note> mNoteList){};
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvStatus;
        private final TextView mTvTitle;
        private final TextView mTvContent;
        private final TextView mTvAuthor;
        private final RelativeLayout mRlParent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvStatus = itemView.findViewById(R.id.tv_status);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mTvAuthor = itemView.findViewById(R.id.tv_author);
            mRlParent = itemView.findViewById(R.id.rl_parent);
        }

        void onBindContent(int position) {
            mTvStatus.setText(mNoteList.get(position).getStatus());
            mTvTitle.setText(mNoteList.get(position).getTitle());
            mTvContent.setText(mNoteList.get(position).getContent());
            mTvAuthor.setText(mNoteList.get(position).getAuthor());

            mRlParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null)
                        mListener.onNoteItemClick(v,getAdapterPosition(),mNoteList);
                }
            });

            mRlParent.setOnLongClickListener(v -> {
                if (mListener != null)
                    mListener.onNoteItemLongClick(v,getAdapterPosition(), mNoteList);
                return true;
            });
        }
    }
}
