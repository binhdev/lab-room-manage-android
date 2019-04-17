package huedev.org.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import huedev.org.R;
import huedev.org.data.model.Computer;
import huedev.org.ui.device.DeviceActivity;
import huedev.org.utils.AppConstants;
import huedev.org.utils.navigator.Navigator;


public class ComputerAdapter extends RecyclerView.Adapter<ComputerAdapter.ComputerViewholder> {
    private Context mContext;
    private List<Computer> mListComputer;
    Navigator navigator;

    public ComputerAdapter(Context Context, List<Computer> ListComputer) {
        this.mContext = Context;
        this.mListComputer = ListComputer;
        navigator = new Navigator((Activity) mContext);

    }

    public class ComputerViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvDetail;
        ImageButton ibInsivilityDetailComputer;
        LinearLayout linearRoomDetail,linearRoomMain, linearChildDetailComputer;

        Computer cptItem;
        public ComputerViewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nameComputer);
            tvDetail = itemView.findViewById(R.id.tv_describeItemComputer);
            ibInsivilityDetailComputer = itemView.findViewById(R.id.ib_insivilityDetailComputer);
            linearRoomDetail = itemView.findViewById(R.id.linear_detailcomputer);
            linearRoomMain = itemView.findViewById(R.id.linear_maincomputer);
            linearChildDetailComputer = itemView.findViewById(R.id.linear_childDetailComputer);
            linearChildDetailComputer.setOnClickListener(this);
            tvDetail.setOnClickListener(this);
            ibInsivilityDetailComputer.setOnClickListener(this);

        }

        public void setData (Computer computer){
            this.cptItem = computer;
            tvName.setText(computer.getName());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.linear_childDetailComputer:
                    hideView(linearRoomDetail, mContext, R.anim.slide_top_in, View.VISIBLE);
                    break;
                case R.id.tv_describeItemComputer:
                    hideView(linearRoomDetail, mContext, R.anim.slide_top_in, View.VISIBLE);
                    break;
                case R.id.ib_insivilityDetailComputer:
                    hideView(linearRoomDetail, mContext, R.anim.slide_bottom_out, View.INVISIBLE);
                    break;
            }
        }

        private void hideView(LinearLayout linearRoomDetail, Context context, int anim, int visibility) {
            Animation animation = AnimationUtils.loadAnimation(context, anim);
            linearRoomDetail.setVisibility(visibility);
            linearRoomDetail.startAnimation(animation);
        }

    }

    @NonNull
    @Override
    public ComputerAdapter.ComputerViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_computer, viewGroup, false);
        return new ComputerViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComputerAdapter.ComputerViewholder myViewHolder, int i) {
        myViewHolder.setData(mListComputer.get(i));
        myViewHolder.itemView.setOnClickListener(v -> {
            Log.d("th2509", mListComputer.get(i).getId());
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.ID_COMPUTER, mListComputer.get(i).getId());
            bundle.putString(AppConstants.ID_COMPUTER, mListComputer.get(i).getName());
            navigator.startActivity(DeviceActivity.class, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return mListComputer.size();
    }

}
