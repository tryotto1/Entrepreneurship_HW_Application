package org.techtown.practice.recycler_Search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.practice.SubTab_Tab1.ChatActivity;
import org.techtown.practice.R;
import org.techtown.practice.recycler_MyWritings.MyWritingsData;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder> {
    Context context;

    private ArrayList<SearchData> arrayList; // 서버에서 받는 정보

    public SearchAdapter(ArrayList<SearchData> arrayList, Context context) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SearchAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_poem, parent, false);
        SearchAdapter.CustomViewHolder holder = new SearchAdapter.CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapter.CustomViewHolder holder, final int position) {
        if(arrayList.get(position)!=null) {
            holder.tv_title.setText(arrayList.get(position).getTitle());

            // 각 게시물을 클릭할 경우, 채팅을 시작한다
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 몇 번째 게시물인지 chatActivity에 전달한다
                    SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("idx_writing", arrayList.get(position).getIndex());
                    editor.commit();

                    // 채팅을 시작한다
                    Intent intent = new Intent(view.getContext(), ChatActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try {
            arrayList.remove(position);
            notifyItemRemoved(position); // 새로고침 해줌
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_title, tv_content;
        protected LinearLayout layout;
        protected ImageView device_img;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
