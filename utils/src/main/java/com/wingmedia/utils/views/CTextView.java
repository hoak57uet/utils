package com.wingmedia.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.wingmdia.viewutils.R;
import com.wingmedia.utils.DeviceUtils;
import com.wingmedia.utils.common.Typefaces;

import org.apache.commons.lang3.StringUtils;

/**
 * Custom Text view
 * Created by Alan's on 3/24/2016.
 */
public class CTextView extends TextView {
  private Drawable[] drawables;
  private static final int sizeDrawable = 20;
  private OnClickDrawableListener drawableClickListener;

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

  public void setCompoundDrawables(Drawable[] drawables) {
    try {
      setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1],
          drawables[2], drawables[3]);
    } catch (NullPointerException e) {
      setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_RIGHT = 2;

    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      if (event.getRawX() >= (getRight() - getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
        // your action here
        if (drawableClickListener != null)
          drawableClickListener.onClickRightDrawable();
        return true;
      }
    }
    if (event.getRawX() <= (getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
      if (drawableClickListener != null)
        drawableClickListener.onClickLeftDrawable();
      return true;
    }
    return false;
  }

  public interface OnClickDrawableListener {
    void onClickLeftDrawable();

    void onClickRightDrawable();
  }

  public void setOnDrawableClickListener(OnClickDrawableListener listener) {
    this.drawableClickListener = listener;
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

  public void displayDrawable(int position, boolean isShow) {
    Drawable[] currents = getCompoundDrawables();
    currents[position] = isShow ? currents[position] = drawables[position] : null;
    setCompoundDrawables(currents);
  }

  public void displayDrawable(boolean left, boolean top, boolean right, boolean bottom) {
    Drawable l = left ? drawables[0] : null;
    Drawable t = top ? drawables[1] : null;
    Drawable r = top ? drawables[2] : null;
    Drawable b = top ? drawables[3] : null;
    setCompoundDrawablesWithIntrinsicBounds(l, t, r, b);
  }
}