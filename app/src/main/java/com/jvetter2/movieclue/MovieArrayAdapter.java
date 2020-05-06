package com.jvetter2.movieclue;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MovieArrayAdapter extends RecyclerView.Adapter<MovieArrayAdapter.ViewHolder> {
    private List<Movie> mList;
    private Context mContext;
    public MovieArrayAdapter(List<Movie> list, Context context){
        super();
        mList = list;
        mContext = context;
    }

//     get the size of the list
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    // specify the row layout file and click for each row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
//        ImageButton item = holder.item;
//        item.setText(itemList.get(listPosition).getName());
//        item.setImageResource(R.drawable.kbcat);

        Movie movie = mList.get(listPosition);
        ((ViewHolder) holder).movieName.setText(movie.getName());
        ((ViewHolder) holder).movieDescription.setText(movie.getDescription());
        ((ViewHolder) holder).movieImage.setImageBitmap(movie.getImage());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //public ImageButton item;
        public TextView movieName;
        public TextView movieDescription;
        public ImageView movieImage;
        public ViewHolder(View itemView) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.tv_name);
            movieDescription = (TextView) itemView.findViewById(R.id.description);
            movieImage = (ImageView) itemView.findViewById(R.id.row_item);
            //itemView.setOnClickListener(this);
            //item = (ImageButton) itemView.findViewById(R.id.row_item);
        }
//        @Override
//        public void onClick(View view) {
//            Log.d("onclick", "onClick " + getLayoutPosition() + " " + item.getId());
//        }
    }
}