package com.example.melikhovva.firstapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

public final class GifsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Gif> gifs;

    public GifsAdapter(final @NonNull List<Gif> gifs) {
        ValidatorNotNull.validateArguments(gifs);
        ValidatorNotNull.validateArguments(gifs.toArray());
        this.gifs = gifs;
    }

    @Override
    public ViewHolder onCreateViewHolder(final @NonNull ViewGroup parent, final int viewType) {
        ValidatorNotNull.validateArguments(parent);
        //TODO: CHANGE TO VIEW
        final RelativeLayout relativeLayout = (SquareRelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,
                                                                                                                      parent,
                                                                                                                      false);
        //TODO: DO IN CONSTRUCTOR
        relativeLayout.setBackgroundColor(Color.BLUE);

        //TODO: REMOVE IMAGEVIEW
        return new ViewHolder(relativeLayout,
                              (ImageView) relativeLayout.findViewById(R.id.image_view));
    }

    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder, final int position) {
        ValidatorNotNull.validateArguments(holder);
        holder.setGif(gifs.get(position));
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }
}

final class ViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;


    public ViewHolder(final @NonNull RelativeLayout relativeLayout, final @NonNull ImageView imageView) {
        super(relativeLayout);

        ValidatorNotNull.validateArguments(imageView);
        this.imageView = imageView;
    }

    public void setGif(final @NonNull Gif gif) {
        ValidatorNotNull.validateArguments(gif);

        Glide.with(imageView.getContext())
                .load(gif.getUrl())
                .asGif()
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(view.getContext(), DetailGifActivity.class);
                intent.putExtra(DetailGifActivity.DETAIL_GIF, gif);
                view.getContext().startActivity(intent);
            }
        });
    }
}
