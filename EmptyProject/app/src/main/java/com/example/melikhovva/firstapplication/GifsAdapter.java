package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public final class GifsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Gif> gifs = new ArrayList<>();

    public GifsAdapter(final @NonNull List<Gif> gifs) {
        ValidatorNotNull.validateArguments(gifs);
        ValidatorNotNull.validateArguments(gifs.toArray());
        this.gifs.addAll(gifs);
    }

    public void addAll(final @NonNull List<Gif> gifs) {
        ValidatorNotNull.validateArguments(gifs);
        ValidatorNotNull.validateArguments(gifs.toArray());

        this.gifs.addAll(gifs);
        notifyItemRangeInserted(getItemCount(), gifs.size());
    }

    public void clear() {
        final int gifsSize = gifs.size();
        gifs.clear();
        notifyItemRangeRemoved(0, gifsSize);
    }

    @Override
    public ViewHolder onCreateViewHolder(final @NonNull ViewGroup parent, final int viewType) {
        ValidatorNotNull.validateArguments(parent);
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.square_image,
                                                                               parent,
                                                                               false));
    }

    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder, final int position) {
        ValidatorNotNull.validateArguments(holder);
        holder.setGif(gifs.get(position));
    }

    @Override
    public void onViewRecycled(final ViewHolder holder) {
        holder.clearView();
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }
}

final class ViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    public ViewHolder(final @NonNull View view) {
        super(view);

        imageView = (ImageView) view.findViewById(R.id.image_view);
        view.setBackgroundColor(Color.BLUE);
    }

    public void setGif(final @NonNull Gif gif) {
        ValidatorNotNull.validateArguments(gif);

        final Context context = imageView.getContext();

        GifLoader.getInstance().loadAndDisplay(gif, imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //TODO: go to new activity
                final Intent intent = new Intent(context, DetailGifActivity.class);
                intent.putExtra(DetailGifActivity.DETAIL_GIF, gif);
                context.startActivity(intent);
            }
        });
    }

    public void clearView() {
        GifLoader.getInstance().stopLoading(imageView);
    }
}