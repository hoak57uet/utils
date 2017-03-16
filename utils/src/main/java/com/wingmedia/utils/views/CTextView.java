package com.wingmedia.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wingmdia.viewutils.R;
import com.wingmedia.utils.DeviceUtils;
import com.wingmedia.utils.common.Typefaces;

import org.apache.commons.lang3.StringUtils;

/**
 * Custom Text view
 * Created by neo on 3/24/2016.
 */
public class CTextView extends TextView {
  private Drawable[] drawables;
  private static final int sizeDrawable = 20;

  public CTextView(Context context) {
    super(context);
    initFont(null);
  }

  public CTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initFont(attrs);
    initAttrs(attrs);
  }

  public CTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initFont(attrs);
    initAttrs(attrs);
  }

  private void initAttrs(AttributeSet attrs) {
    drawables = getCompoundDrawables();
    adjustDrawableSize();
  }

  public void adjustDrawableSize() {
    int drawableSize = DeviceUtils
        .dpToPx(getContext(), sizeDrawable);
    for (int i = 0; i < drawables.length; i++) {
      if (drawables[i] != null) {
        drawables[i] = new BitmapDrawable(getResources(),
            Bitmap.createScaledBitmap(((BitmapDrawable) drawables[i]).getBitmap(),
                drawableSize, drawableSize, true));
      }
    }
    setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1],
        drawables[2], drawables[3]);
  }

  /**
   * init font text view
   */
  public void initFont(AttributeSet attrs) {
    if (attrs == null) {
      return;
    }

    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CTextView);
    String font = a.getString(R.styleable.CTextView_typeFaceFont);
    String style = a.getString(R.styleable.CTextView_typeFaceStyle);

    if (font != null) {
      if (!StringUtils.isEmpty(style)) {
        font = font + "-" + style;
      }
      this.setTypeface(Typefaces.get(getContext(), font));
    }
    a.recycle();
  }

}