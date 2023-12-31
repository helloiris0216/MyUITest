package com.helliris.taipei.myuitest;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.helliris.taipei.myuitest.view.DetailActivity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<User> _list;


    public ListAdapter() {

    }

    public ListAdapter(List<User> list) {
        this._list = list;
    }

    public void setData(List<User> dataList) {
        this._list = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position < 0 || position > _list.size()) return;

        User item = _list.get(position);

        holder.userId.setText(item.id);
        holder.userName.setText(item.name);
        holder.userLevel.setText(String.valueOf(item.level));

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra(Constant.TAG_LEVEL, item.level);
            intent.putExtra(Constant.TAG_NAME, item.name);

            holder.itemView.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return _list == null ? 0 : _list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView userId, userName, userLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.userId);
            userName = itemView.findViewById(R.id.userName);
            userLevel = itemView.findViewById(R.id.userLevel);

        }
    }

}
