package com.simplecity.amp_library.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.simplecity.amp_library.R;

public class TextViewDrawableSize extends AestheticDrawableTextView {

    private int drawableWidth;
    private int drawableHeight;

    public TextViewDrawableSize(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TextViewDrawableSize(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TextViewDrawableSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewDrawableSize, defStyleAttr, 0);

        try {
            drawableWidth = array.getDimensionPixelSize(R.styleable.TextViewDrawableSize_compoundDrawableWidth, -1);
            drawableHeight = array.getDimensionPixelSize(R.styleable.TextViewDrawableSize_compoundDrawableHeight, -1);
        } finally {
            array.recycle();
        }

        if (drawableWidth > 0 || drawableHeight > 0) {
            initCompoundDrawableSize();
        }
    }

    private void initCompoundDrawableSize() {
        Drawable[] drawables = getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable == null) {
                continue;
            }

            Rect realBounds = drawable.getBounds();
            float scaleFactor = realBounds.height() / (float) realBounds.width();

            float drawWidth = realBounds.width();
            float drawHeight = realBounds.height();

            if (this.drawableWidth > 0) {
                if (drawWidth > this.drawableWidth) {
                    drawWidth = this.drawableWidth;
                    drawHeight = drawWidth * scaleFactor;
                }
            }
            if (this.drawableHeight > 0) {
                if (drawHeight > this.drawableHeight) {
                    drawHeight = this.drawableHeight;
                    drawWidth = drawHeight / scaleFactor;
                }
            }

            realBounds.right = realBounds.left + Math.round(drawWidth);
            realBounds.bottom = realBounds.top + Math.round(drawHeight);

            drawable.setBounds(realBounds);
        }
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }
}