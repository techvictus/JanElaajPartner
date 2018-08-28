package com.janelaaj.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.janelaaj.R;


/**
 * <h4>Created</h4> 7/18/2017
 *
 * @author Altametrics Inc.
 */
public class TileView extends LinearLayout {

    protected TextView titleView, countView, explanationView;
    protected ImageView imageView;
    protected View view;
    protected CardView cardViewLayout;
    protected LinearLayout imageContainer;
    TextView titleHorizontal;
    private int imgHeight, imgWidth;

    public TileView(Context context) {
        this(context, null);
    }

    public TileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TileView, defStyle, 0);
        if (array.hasValue(R.styleable.TileView_imgHeight)) {
            this.imgHeight = (int) array.getDimension(R.styleable.TileView_imgHeight, getResources().getDimension(R.dimen._30dp));
        }
        if (array.hasValue(R.styleable.TileView_imgWidth)) {
            this.imgWidth = (int) array.getDimension(R.styleable.TileView_imgWidth, getResources().getDimension(R.dimen._30dp));
        }
        array.recycle();
        this.init();
    }

    private void init() {
        if (this.isInEditMode()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(layoutId(), this, true);
        this.titleView = view.findViewById(R.id.title);
        this.countView = view.findViewById(R.id.count);
        this.imageView = view.findViewById(R.id.image);
        this.imageContainer = view.findViewById(R.id.imageContainer);
        this.explanationView = view.findViewById(R.id.explanation);
        this.cardViewLayout = view.findViewById(R.id.cardViewLayout);
        titleHorizontal = findViewById(R.id.titleHorizontral);
        setTileImageSize();
    }

    public void setTileImageSize() {
        if (this.imgHeight > 0 && imgWidth > 0) {
            LayoutParams parms = new LayoutParams(imgWidth, imgHeight);
            this.imageView.setLayoutParams(parms);
        }
    }

    protected int layoutId() {
        return R.layout.tile_view;
    }

    public void setTitle(String title) {
        TextView titleView = getTileView();
        if (titleView == null) {
            return;
        }
        titleView.setText(title);
    }


    public void setTitle(@StringRes int title) {
        if (this.titleView == null) {
            return;
        }
        this.titleView.setText(title);
    }

    public void setTitleColor(@ColorRes int colorId) {
        TextView titleView = getTileView();
        if (titleView == null) {
            return;
        }
        int resId = colorId;
        if (resId != 0) {
            // titleView.setTextColor(resId);
        }
    }

    int orientation = 1;

    private boolean isVertical() {
        return orientation == LinearLayout.VERTICAL;
    }


    public void setExplanation(String explanation) {
        if (this.explanationView == null) {
            return;
        }
        this.explanationView.setVisibility(VISIBLE);
        this.explanationView.setText(explanation);
    }

    public void setExplanation(@StringRes int explanation) {
        setExplanation(explanation);
    }

    public void hideImage() {
        if (imageView != null) {
            this.imageView.setVisibility(View.GONE);
        }
    }

    private boolean isImageViewInit() {
        if (this.imageView == null) {
            return false;
        }
        this.imageView.setVisibility(View.VISIBLE);
        return true;
    }

    public void setImageUrl(String imgUrl) {
        if (isImageViewInit()) {
            //  imageView.setURL(imgUrl, R.drawable.noimage);
        }
    }

    public void setImageDrawable(Drawable icon) {
        if (isImageViewInit()) {
            this.imageView.setImageDrawable(icon);
        }
    }

    public void setImageResource(@DrawableRes int icon) {
        if (isImageViewInit()) {
            this.imageView.setImageResource(icon);
        }
    }

    public void hideCountField() {
        if (countView == null) {
            return;
        }
        this.countView.setVisibility(View.GONE);
    }

    public void hideTitleView() {
        if (titleView != null) {
            titleView.setVisibility(GONE);
        }
    }


    public void changeDirection(int orientation) {
        this.orientation = orientation;
        switch (orientation) {
            case LinearLayout.HORIZONTAL:
                titleHorizontal.setVisibility(View.VISIBLE);
                titleView.setVisibility(View.GONE);
                break;
            case LinearLayout.VERTICAL:
                titleHorizontal.setVisibility(View.GONE);
                titleView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private TextView getTileView() {
        switch (orientation) {
            case LinearLayout.HORIZONTAL:
                return titleHorizontal;
            case LinearLayout.VERTICAL:
                return titleView;
        }
        return null;
    }

    public void setCardViewBg(int colorId) {
        if (this.cardViewLayout != null) {
            cardViewLayout.setCardBackgroundColor(colorId);
        }
    }
}
