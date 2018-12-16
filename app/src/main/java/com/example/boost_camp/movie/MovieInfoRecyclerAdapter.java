package com.example.boost_camp.movie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.boost_camp.R;
import com.example.boost_camp.model.movie_material.movie_material.Items;

import java.util.ArrayList;

/**
 * Created by park on 2018-12-07.
 */

public class MovieInfoRecyclerAdapter extends RecyclerView.Adapter<MovieInfoRecyclerAdapter.ViewHolder> {

    private ArrayList<Items> mItemList;

    //생성자
    public MovieInfoRecyclerAdapter(ArrayList<Items> itemList) {
        mItemList = itemList;
    }

    //뷰 홀더 생성, 레이아웃 만드는
    @Override
    public MovieInfoRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_card, parent, false);
        return new ViewHolder(view);
}

    //뷰 홀더에 데이터 설정
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //html 처리
        holder.mTitle.setText(Html.fromHtml(mItemList.get(position).getTitle()));
        holder.mPubDate.setText(mItemList.get(position).getPubDate());
        holder.mDirector.setText(Html.fromHtml(mItemList.get(position).getDirector()));
        holder.mActor.setText(Html.fromHtml(mItemList.get(position).getActor()));

        //Glide
        Glide.with(holder.mImage.getContext())
                .load(mItemList.get(position).getImage())
                .skipMemoryCache(true)
                .priority(Priority.HIGH)
                .into(holder.mImage);

        //ratingBar
        double d=mItemList.get(position).getUserRating();
        float ff= (float) (d/2);
        holder.mRatingBar.setRating(ff);
        holder.mRatingBar.setNumStars(5);
        holder.mRatingBar.setStepSize(0.5f);
        holder.mRatingBar.setIsIndicator(true);

        final int pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webPage = Uri.parse(mItemList.get(pos).getLink());

                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                if(intent.resolveActivity(v.getContext().getPackageManager())!=null){
                    v.getContext().startActivity(intent);

                }
                //
            }
        });

    }
    //아이템의 수
    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    //각각의 아이템을 저장할 부 홀더 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mPubDate;
        TextView mDirector;
        TextView mActor;

        //이미지 뷰
        ImageView mImage;
        //레이팅 바
        RatingBar mRatingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.movie_title);
            mPubDate = itemView.findViewById(R.id.movie_pub_date);
            mDirector = itemView.findViewById(R.id.movie_director);
            mActor = itemView.findViewById(R.id.movie_actor);

            mImage = itemView.findViewById(R.id.movie_image);
            //ratingBar
            mRatingBar=itemView.findViewById(R.id.rating_bar);

        }
    }

    public void addItems(ArrayList<Items>items){
        for(Items items123 : items){
            mItemList.add(items123);
        }
    }
}