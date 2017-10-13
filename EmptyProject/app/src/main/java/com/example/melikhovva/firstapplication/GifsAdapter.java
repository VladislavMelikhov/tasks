package com.example.melikhovva.firstapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,
                                                                               parent,
                                                                               false));
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


    public ViewHolder(final @NonNull View view) {
        super(view);

        imageView = (ImageView) view.findViewById(R.id.image_view);
        view.setBackgroundColor(Color.BLUE);
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
