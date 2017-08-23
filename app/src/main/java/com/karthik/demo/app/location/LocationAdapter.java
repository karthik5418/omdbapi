package com.karthik.demo.app.location;

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
import com.karthik.demo.app.location.model.Result;
import com.karthik.demo.constants.Constants;
import com.karthik.demo.listener.ItemClickListener;
import com.karthik.demo.util.CommonFunction;

import java.util.List;

/**
 * Created by karthik on 20/6/16.
 */
public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "FeedAdapter";

    private List<Result> list;
    Result model;
    private Context context;
    private ItemClickListener itemClickListener;
    private float deviceHeight;

    private RelativeLayout.LayoutParams params;

    public LocationAdapter(Context context, List<Result> list, float height) {
        this.list = list;
        this.context = context;
        deviceHeight = height;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        model = list.get(position);
        LocationViewHolder viewHolder = (LocationViewHolder) holder;
        CommonFunction.getImage("https://maps.googleapis.com/maps/api/place/photo?maxwidth=" + model.getPhotos().get(0).getWidth() + "&photoreference=" + model.getPhotos().get(0).getPhotoReference() + "&key=" + Constants.GOOGLE_MAP_SERVER_KEY, viewHolder.ivMoviePoster, R.drawable.im_post_default);
        viewHolder.tvMovieTitle.setText(model.getName());
        viewHolder.tvRating.setText("Rating : " + model.getRating());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;
        private ImageView ivMoviePoster;
        private TextView tvMovieTitle;
        private TextView tvGenre;
        private TextView tvReleaseDate;
        private TextView tvPolt;
        private TextView tvRating;

        public LocationViewHolder(View itemView) {
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
