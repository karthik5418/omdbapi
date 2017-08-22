package com.karthik.demo.app.omdb;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karthik.demo.R;
import com.karthik.demo.app.omdb.model.FeedModel;
import com.karthik.demo.listener.ItemClickListener;
import com.karthik.demo.util.CommonFunction;

import java.util.List;

/**
 * Created by karthik on 20/6/16.
 */
public class OMDBAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "FeedAdapter";

    private List<FeedModel.Response> list;
    FeedModel.Response model;
    private Context context;
    private ItemClickListener itemClickListener;
    private float deviceHeight;

    private RelativeLayout.LayoutParams params;

    public OMDBAdapter(Context context, List<FeedModel.Response> list, float height) {
        this.list = list;
        this.context = context;
        deviceHeight = height;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.item_movie, parent, false);
        return new OmdbViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        model = list.get(position);
        OmdbViewHolder viewHolder = (OmdbViewHolder) holder;
        CommonFunction.getImage(model.getPhotoUrl(), viewHolder.ivMoviePoster, R.drawable.im_post_default);
        viewHolder.tvMovieTitle.setText(model.getFirstName() + " " + model.getLastName());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class OmdbViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private ImageView ivMoviePoster;
        private TextView tvMovieTitle;
        private TextView tvGenre;
        private TextView tvReleaseDate;
        private TextView tvPolt;
        private TextView tvRating;

        public OmdbViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            ivMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            tvMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            tvGenre = (TextView) itemView.findViewById(R.id.tv_genre);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tv_release_date);
            tvPolt = (TextView) itemView.findViewById(R.id.tv_plot);
            tvRating = (TextView) itemView.findViewById(R.id.tv_rating);
            cardView.setOnClickListener(this);

            params = (RelativeLayout.LayoutParams) ivMoviePoster.getLayoutParams();
            params.height = (int) deviceHeight / 3;
            ivMoviePoster.setLayoutParams(params);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_view:
                    break;
            }
        }
    }

}
