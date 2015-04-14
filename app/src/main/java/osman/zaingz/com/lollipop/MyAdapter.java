package osman.zaingz.com.lollipop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import osman.zaingz.com.lollipop.model.Player;

/**
 * Created by Zain on 10/27/2014.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Player> mDataset;
    Context con;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Player> myDataset)
    {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());



        View v = inflater.inflate(R.layout.my_text_view, parent, false);


        con = parent.getContext();
        ViewHolder vh = new ViewHolder( v );

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.mTextView.setText(mDataset.get(position).getName());
        holder.mTextViewAge.setText("Age :"+ mDataset.get(position).getAge());
        final int id =  mDataset.get(position).getId();
        holder.im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(con, Reading.class);
                myIntent.putExtra("id", id); //Optional parameters
                con.startActivity(myIntent);

            }
        });



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
        public Button im;
        public ViewHolder(View v) {
            super(v);


            mTextView = (TextView) v.findViewById(R.id.player_name);
            mTextViewAge = (TextView) v.findViewById(R.id.player_age);
            im = (Button) v.findViewById(R.id.button);
        }
    }
}
