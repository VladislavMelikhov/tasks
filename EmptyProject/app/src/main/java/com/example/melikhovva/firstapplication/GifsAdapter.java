package com.example.melikhovva.firstapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public final class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.ViewHolder> {

    private final Context context;
    private final List<Gif> gifs;


    final class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public ViewHolder(final @NonNull RelativeLayout relativeLayout, final @NonNull ImageView imageView) {
            super(relativeLayout);

            ValidatorNotNull.validateArguments(imageView);
            this.imageView = imageView;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Toast.makeText(context,
                                   gifs.get(getAdapterPosition())
                                       .getName(),
                                   Toast.LENGTH_SHORT)
                         .show();
                }
            });
        }
    }

    public GifsAdapter(final @NonNull Context context, final @NonNull List<Gif> gifs) {
        ValidatorNotNull.validateArguments(context, gifs);
        ValidatorNotNull.validateArguments(gifs.toArray());
        this.context = context;
        this.gifs = gifs;
    }

    @Override
    public ViewHolder onCreateViewHolder(final @NonNull ViewGroup parent, final int viewType) {
        ValidatorNotNull.validateArguments(parent);
        final RelativeLayout relativeLayout = (SquareRelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,
                                                                                                                      parent,
                                                                                                                      false);
        relativeLayout.setBackgroundColor(Color.BLUE);

        return new ViewHolder(relativeLayout,
                              (ImageView) relativeLayout.findViewById(R.id.image_view));
    }

    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder, final int position) {
        ValidatorNotNull.validateArguments(holder);
        Glide.with(context)
            .load(gifs.get(position).getUrl())
            .asGif()
            .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }
}
