package trivial.speckmussweg.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import trivial.speckmussweg.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
        implements  View.OnTouchListener{

    private List<String> mName;
    private List<String> mKcal;
    private List<String> mFett;
    int mId;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public RecyclerViewAdapter(Context context, List<String> names, List<String> calories, int id) {
        this.mInflater = LayoutInflater.from(context);
        this.mName = names;
        this.mKcal = calories;
        this.mId = id;
        //this.mFett = fett;
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
        /*for (Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }*/


        String name = mName.get(position);
        String kcal = mKcal.get(position);
        //String fett = mFett.get(position);
        holder.nameTextView.setText(name);
        holder.kcalTextView.setText(kcal);
        //holder.kcalTextView.setText(kcal);
        //holder.fettTextView.setText(fett);

        //1= brot, 2 = cheese, 3 = Meat, 4 = salad, 5 = extra, 6 = sauce
        if (mId == 1){
            holder.iconImageView.setImageResource(R.drawable.ic_configurator_bread);
        }
        if (mId == 2){
            holder.iconImageView.setImageResource(R.drawable.ic_configurator_cheese);
        }
        if (mId == 3){
            holder.iconImageView.setImageResource(R.drawable.ic_configurator_meat);
        }
        if (mId == 4){
            holder.iconImageView.setImageResource(R.drawable.ic_configurator_salad);
        }
        if (mId == 5){
            holder.iconImageView.setImageResource(R.drawable.ic_configurator_extra);
        }
        if (mId == 6){
            holder.iconImageView.setImageResource(R.drawable.ic_configurator_sauce);
        }



    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(data, shadowBuilder, view, 0);
                } else {
                    view.startDrag(data, shadowBuilder, view, 0);
                }
                return true;
        }
        return false;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        TextView kcalTextView;
        ImageView iconImageView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.header_configurator_name);
            kcalTextView = itemView.findViewById(R.id.header_configurator_calories);
            iconImageView = itemView.findViewById(R.id.header_configurator_icon);

            //fettTextView = itemView.findViewById(R.id.mobile);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mName.get(id);
    }

    public String getCalories(int id){
        return mKcal.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }
}
