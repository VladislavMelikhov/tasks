package com.example.melikhovva.firstapplication;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

final class StringsAdapter extends RecyclerView.Adapter<StringsAdapter.ViewHolder> {

    private final String[] data;

    final class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;

        public ViewHolder(final TextView textView){
            super(textView);
            this.textView = textView;
        }
    }

    public StringsAdapter(final String[] data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType){
        final TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view,
                                                                                              parent,
                                                                                              false);
        textView.setBackgroundColor(Color.BLUE);
        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position){
        viewHolder.textView.setText(data[position]);
    }

    @Override
    public int getItemCount(){
        return data.length;
    }
}
