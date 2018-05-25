package trivial.speckmussweg.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import trivial.speckmussweg.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> mName;
    private List<String> mKcal;
    private List<String> mFett;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public RecyclerViewAdapter(Context context, List<String> name, List<String> kcal, List<String> fett) {
        this.mInflater = LayoutInflater.from(context);
        this.mName = name;
        this.mKcal = kcal;
        this.mFett = fett;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listview_configurator_sub, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = mName.get(position);
        //String kcal = mKcal.get(position);
        //String fett = mFett.get(position);
        holder.nameTextView.setText(name);
        //holder.kcalTextView.setText(kcal);
        //holder.fettTextView.setText(fett);

    }

    @Override
    public int getItemCount() {
        return mName.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView nameTextView;
        TextView kcalTextView;
        TextView fettTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.header_configurator_name);
            //kcalTextView = itemView.findViewById(R.id.header_configurator_name);
            //fettTextView = itemView.findViewById(R.id.mobile);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) mClickListener.onLongClick(view, getAdapterPosition());
            return true;
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mName.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
