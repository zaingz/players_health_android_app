package osman.zaingz.com.lollipop;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;

import osman.zaingz.com.lollipop.model.ReadingModel;

/**
 * Created by Zain on 10/27/2014.
 */
public class MyAdapterReading extends RecyclerView.Adapter<MyAdapterReading.ViewHolder> {
    private ArrayList<ReadingModel> mDataset;
    Context con;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterReading(ArrayList<ReadingModel> myDataset)
    {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterReading.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());



        View v = inflater.inflate(R.layout.my_text_view_reading, parent, false);


        con = parent.getContext();
        ViewHolder vh = new ViewHolder( v );

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.mTextView.setText(mDataset.get(position).getKey());
        holder.mTextViewAge.setText( mDataset.get(position).getValue());





    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mTextViewAge;

        public ViewHolder(View v) {
            super(v);


            mTextView = (TextView) v.findViewById(R.id.player_name);
            mTextViewAge = (TextView) v.findViewById(R.id.player_age);

        }
    }
}
