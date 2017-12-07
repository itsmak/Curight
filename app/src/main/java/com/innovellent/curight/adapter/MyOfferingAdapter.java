package com.innovellent.curight.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.fragment.ForyouFragment;
import com.innovellent.curight.model.Offer;

import java.util.ArrayList;


public class MyOfferingAdapter extends RecyclerView.Adapter<MyOfferingAdapter.MyViewHolder> {

    private ArrayList<Offer> arrayList;
    private Context mContext;
    AdapterView.OnItemClickListener mItemClickListener;
    private SharedPreferences sharedPreferences;
    private String wish, month,wishlistflag;
    public Double num;
    public boolean isWishlist=true;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF(); float oldDist = 1f;
    static final int NONE = 0; static final int DRAG = 1;
    static final int ZOOM = 2; int mode = NONE;

    ForyouFragment fragment;
    MyViewHolder.ZoomImageListener zoomListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOfferTitle,tvPhoneno1,tvwishlistdelete;
        public TextView tvSellBuy;
        public TextView tvOffer,tvExpired;
        public TextView tvOfferDescription;
        public TextView tvToDate;
        public TextView tvFromDate;
        public ImageView ivItem;
        public ImageView ivWishlist,ivBanner;
        public TextView tvPhoneno,tvDistance;
        public TextView tvReadMore;
        public ImageView dailbutton,ivShare1,dailbutton1,ivShare;

        LinearLayout listitem11relativeLayout7,listitem11relativeLayout8;
        public interface ZoomImageListener {
            void zoomImage(ImageView imageView);
        }
        public MyViewHolder(View view) {
            super(view);

            tvOfferTitle = (TextView) view.findViewById(R.id.tvOfferTitle);
         //   tvOffer = (TextView) view.findViewById(R.id.tvOffer);
            tvOfferDescription = (TextView) view.findViewById(R.id.tvOfferDescription);
            tvToDate = (TextView) view.findViewById(R.id.tvToDate);
            tvFromDate = (TextView) view.findViewById(R.id.tvFromDate);
            ivItem = (ImageView) view.findViewById(R.id.ivItem);
            ivWishlist = (ImageView) view.findViewById(R.id.ivWishlist);
            ivBanner= (ImageView) view.findViewById(R.id.ivBanner);
            tvSellBuy = (TextView) view.findViewById(R.id.tvSellBuy);
            tvPhoneno = (TextView) view.findViewById(R.id.tvPhoneno);
            dailbutton = (ImageView) view.findViewById(R.id.dailbutton);
            tvReadMore = (TextView)view.findViewById(R.id.readMore);
            ivShare=(ImageView)view.findViewById(R.id.ivShare);
            tvDistance=(TextView)view.findViewById(R.id.tvDistance);
            tvExpired=(TextView)view.findViewById(R.id.tvExpired);
            listitem11relativeLayout7=(LinearLayout)view.findViewById(R.id.listitem11relativeLayout7);


        }
    }
    public MyOfferingAdapter(Context context, ArrayList<Offer> arrayList, String wishlist, ForyouFragment fragment) {
        mContext = context;
        this.wish = wishlist;
        this.arrayList = arrayList;
        this.fragment=fragment;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_articles, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            if (position % 4 == 0) {
                holder.ivBanner.setBackgroundResource(R.drawable.banner1);
                holder.ivItem.setBackgroundResource(R.drawable.syzng);
                holder.tvOfferTitle.setText("This Simple Japanese Weight Loss Technique Just Needs a Towel Roll!");
                holder.tvOfferDescription.setText("A Japanese doctor Toshiki Fukutsudzi introduced a very simple method to lose weight, which only needs a rolled towel as you lie down on it with a specific posture.");
            } else if (position % 4 == 1) {
                holder.tvOfferTitle.setText("Diet versus Exercise: Which One Wins the Battle Really?");
                holder.tvOfferDescription.setText("Maintaining a healthy lifestyle is all about working your diet and fitness routine in tandem with each other.");

                holder.ivItem.setBackgroundResource(R.drawable.bbclogo);
                holder.ivBanner.setBackgroundResource(R.drawable.banner2);
            } else if (position % 4 == 2) {
                holder.tvOfferTitle.setText("Vitamins for Good Eyesight: Foods That Can Help Nourish Your Eyes");
                holder.tvOfferDescription.setText("There are many vitamins that when made a part of the regular diet can help in keeping your eyes healthy and functioning well.");

                holder.ivItem.setBackgroundResource(R.drawable.lifeforce);
//                holder.ivBanner.setBackgroundResource(R.drawable.banner3);
            } else if (position % 4 == 3) {
                holder.tvOfferTitle.setText("8 Power Foods to Boost Your Stamina Instantly");
                holder.tvOfferDescription.setText("Here's our list of 8 power foods to keep you high on stamina, so that you don't compromise on fitness.");

                holder.ivItem.setBackgroundResource(R.drawable.metabolism);
                holder.ivBanner.setBackgroundResource(R.drawable.banner4);
            } else {
                holder.ivBanner.setBackgroundResource(R.drawable.banner1);
            }

        }catch (Exception e){}

    }
    public String dateFormate(String date) {
        String[] months = date.split("-");

        if (months[1].equals("01")) {
            month = "Jan";
        } else if (months[1].equals("02")) {
            month = "Feb";
        } else if (months[1].equals("03")) {
            month = "Mar";
        } else if (months[1].equals("04")) {
            month = "Apr";
        } else if (months[1].equals("05")) {
            month = "May";
        } else if (months[1].equals("06")) {
            month = "Jun";
        } else if (months[1].equals("07")) {
            month = "Jul";
        } else if (months[1].equals("08")) {
            month = "Aug";
        } else if (months[1].equals("09")) {
            month = "Sep";
        } else if (months[1].equals("10")) {
            month = "Oct";
        } else if (months[1].equals("11")) {
            month = "Nov";
        } else if (months[1].equals("12")) {
            month = "Dec";
        }
        return months[0]+"-"+month+"-"+months[2];
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
