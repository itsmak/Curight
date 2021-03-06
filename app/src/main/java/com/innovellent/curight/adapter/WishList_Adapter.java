package com.innovellent.curight.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.fragment.ForyouFragment;
import com.innovellent.curight.fragment.WishListFragment;
import com.innovellent.curight.model.Article_FEED;
import com.innovellent.curight.model.WishList_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;

/**
 * Created by Mak on 3/2/2018.
 */

public class WishList_Adapter  extends RecyclerView.Adapter<WishList_Adapter.MyViewHolder> {

static final int NONE = 0;
static final int DRAG = 1;
static final int ZOOM = 2;
    public Double num;
    public boolean isWishlist=true;
    ArrayList<WishList_Model> arrayList;
    AdapterView.OnItemClickListener mItemClickListener;
    Matrix matrix = new Matrix();
    OnWishclicklistner listner;
    int position;
    Matrix savedMatrix = new Matrix();
    PointF startPoint = new PointF();
    PointF midPoint = new PointF(); float oldDist = 1f;
    int mode = NONE;
    WishListFragment fragment1;
    MyOfferingAdapter.MyViewHolder.ZoomImageListener zoomListener;
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private String wish, month,wishlistflag;
    public WishList_Adapter(Context context, ArrayList<WishList_Model> arrayList, String wishlist, WishListFragment fragment,int position,OnWishclicklistner listner) {
        mContext = context;
        this.wish = wishlist;
        this.arrayList = arrayList;
        this.fragment1=fragment;
        this.position=position;
        this.listner=listner;
    }

    @Override
    public WishList_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_articles, parent, false);
        return new WishList_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WishList_Adapter.MyViewHolder holder, final int position) {
        try {
            if(arrayList.get(position).getArticlewishlistid()>0)
            {
                holder.ivWishlist_article.setImageResource(R.drawable.ic_heart_dark);
            }else {
                holder.ivWishlist_article.setImageResource(R.drawable.heart);
            }
            holder.tvOfferTitle.setText(arrayList.get(position).getTitle());
            holder.expandableTextView.setText(arrayList.get(position).getDescription());
            Picasso.with(mContext)
                    .load(arrayList.get(position).getImageurl())
                    /*.placeholder(R.drawable.bitmap)*/ //this is optional the image to display while the url image is downloading
                    //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.ivBanner_article);

        }catch (Exception e){}

        holder.ivWishlist_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // holder.ivWishlist_article.setImageResource(R.drawable.ic_heart_dark);
                 listner.ontoggleClick(arrayList.get(position),position);
            }
        });
        holder.ivShare_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(mContext, "share clicked", Toast.LENGTH_SHORT).show();
                listner.onshareClick(arrayList.get(position),position);
            }
        });
        holder.ivLike_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onlikeClick(arrayList.get(position),position);
            }
        });

        holder.tv_readMore_article.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (holder.expandableTextView.isExpanded())
                {
                    holder.expandableTextView.collapse();
                    holder.tv_readMore_article.setText("Show More . .");
                }
                else
                {
                    holder.expandableTextView.expand();
                    holder.tv_readMore_article.setText("Show Less . .");
                }
            }
        });
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

    public interface OnWishclicklistner {

        void onlikeClick(WishList_Model item_m, int position);
        void ontoggleClick(WishList_Model item_m, int position);
        void onshareClick(WishList_Model item_m, int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvOfferTitle,tvdate_article,tv_readMore_article;
        public ImageView ivBanner_article,ivWishlist_article,ivLike_article,ivShare_article;
        ExpandableTextView expandableTextView;

        LinearLayout listitem11relativeLayout7,listitem11relativeLayout8;
        public MyViewHolder(View view) {
            super(view);

            tvOfferTitle = (TextView) view.findViewById(R.id.tvOfferTitle);
            tvdate_article = (TextView) view.findViewById(R.id.tvdate_article);
  //          tvOfferDescription = (TextView) view.findViewById(R.id.tvOfferDescription);
            tv_readMore_article = (TextView) view.findViewById(R.id.tv_readMore_article);
            ivBanner_article = (ImageView) view.findViewById(R.id.ivBanner_article);
            expandableTextView = (ExpandableTextView) view.findViewById(R.id.expandableTextView);
            ivWishlist_article = (ImageView) view.findViewById(R.id.ivWishlist_article);
            ivShare_article = (ImageView) view.findViewById(R.id.ivShare_article);
            ivLike_article= (ImageView) view.findViewById(R.id.ivLike_article);

            // set animation duration via code, but preferable in your layout files by using the animation_duration attribute
            expandableTextView.setAnimationDuration(750L);
            // set interpolators for both expanding and collapsing animations
            expandableTextView.setInterpolator(new OvershootInterpolator());
            // or set them separately
            expandableTextView.setExpandInterpolator(new OvershootInterpolator());
            expandableTextView.setCollapseInterpolator(new OvershootInterpolator());


        }
        public interface ZoomImageListener {
            void zoomImage(ImageView imageView);
        }
    }
}
