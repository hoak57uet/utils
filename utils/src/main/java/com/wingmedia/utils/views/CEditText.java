package com.wingmedia.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.wingmdia.viewutils.R;
import com.wingmedia.utils.DeviceUtils;
import com.wingmedia.utils.common.Typefaces;

/**
 * Custom Text view
 * Created by p on 5/9/2016.
 */
public class CEditText extends EditText implements View.OnFocusChangeListener {
  private Drawable[] drawables;
  int actionX, actionY;
  private static final int sizeDrawable = 20;
  OnClickDrawableListener drawableClickListener;
  int extraTapArea = (int) (13 * getResources().getDisplayMetrics().density + 0.5);

  public CEditText(Context context) {
    super(context);
    initFont(null);
    setOnFocusChangeListener(this);
  }

  public CEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    initFont(attrs);
    initAttrs(attrs);
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    setOnFocusChangeListener(this);
  }

  public CEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initFont(attrs);
    initAttrs(attrs);
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    setOnFocusChangeListener(this);
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
  }

  public void setCompoundDrawables(Drawable[] drawables) {
    try {
      setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1],
          drawables[2], drawables[3]);
    } catch (NullPointerException e) {
      setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

  }

  /**
   * init font text view
   */
  private void initFont(AttributeSet attrs) {
    if (attrs == null) {
      return;
    }
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CTextView);
    String font = a.getString(R.styleable.CTextView_typeFaceFont);
    if (font != null) {
      this.setTypeface(Typefaces.get(getContext(), "tff"));
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

  @Override
  public void onFocusChange(View view, boolean b) {
    if (!b) {
      setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    } else {
      if (getText().length() > 0) {
        setCompoundDrawables(drawables);
        setCompoundDrawablePadding(-10);
      }
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    Rect bounds;
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      actionX = (int) event.getX();
      actionY = (int) event.getY();
      if (drawables[3] != null
          && drawables[3].getBounds().contains(actionX, actionY)) {
        if (drawableClickListener != null)
          drawableClickListener.onClickBottomDrawable();
        return super.onTouchEvent(event);
      }

      if (drawables[1] != null
          && drawables[1].getBounds().contains(actionX, actionY)) {
        if (drawableClickListener != null)
          drawableClickListener.onClickTopDrawable();
        return super.onTouchEvent(event);
      }

      // this works for left since container shares 0,0 origin with bounds
      if (drawables[0] != null) {
        bounds = null;
        bounds = drawables[0].getBounds();

        int x, y;

        x = actionX;
        y = actionY;

        if (!bounds.contains(actionX, actionY)) {
          /** Gives the +20 area for tapping. */
          x = (int) (actionX - extraTapArea);
          y = (int) (actionY - extraTapArea);

          if (x <= 0)
            x = actionX;
          if (y <= 0)
            y = actionY;

          /** Creates square from the smallest value */
          if (x < y) {
            y = x;
          }
        }

        if (bounds.contains(x, y) && drawableClickListener != null) {
          drawableClickListener.onClickLeftDrawable();
          event.setAction(MotionEvent.ACTION_CANCEL);
          return false;

        }
      }

      if (drawables[2] != null) {

        bounds = null;
        bounds = drawables[2].getBounds();

        int x, y;
        int extraTapArea = 13;

        /**
         * IF USER CLICKS JUST OUT SIDE THE RECTANGLE OF THE DRAWABLE
         * THAN ADD X AND SUBTRACT THE Y WITH SOME VALUE SO THAT AFTER
         * CALCULATING X AND Y CO-ORDINATE LIES INTO THE DRAWBABLE
         * BOUND. - this process help to increase the tappable area of
         * the rectangle.
         */
        x = (int) (actionX + extraTapArea);
        y = (int) (actionY - extraTapArea);

        /**Since this is right drawable subtract the value of x from the width
         * of view. so that width - tappedarea will result in x co-ordinate in drawable bound.
         */
        x = getWidth() - x;

                 /*x can be negative if user taps at x co-ordinate just near the width.
                 * e.g views width = 300 and user taps 290. Then as per previous calculation
                 * 290 + 13 = 303. So subtract X from getWidth() will result in negative value.
                 * So to avoid this add the value previous added when x goes negative.
                 */

        if (x <= 0) {
          x += extraTapArea;
        }

                 /* If result after calculating for extra tappable area is negative.
                 * assign the original value so that after subtracting
                 * extratapping area value doesn't go into negative value.
                 */

        if (y <= 0)
          y = actionY;

        /**If drawble bounds contains the x and y points then move ahead.*/
        if (bounds.contains(x, y) && drawableClickListener != null) {
          if (drawableClickListener != null)
            drawableClickListener.onClickRightDrawable();
          event.setAction(MotionEvent.ACTION_CANCEL);
          return false;
        }
        return super.onTouchEvent(event);
      }

    }
    return super.onTouchEvent(event);
  }

  public void setupSimpleText() {
    setOnFocusChangeListener(this);
    addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
          setCompoundDrawables(drawables);
        } else {
          setCompoundDrawablesWithIntrinsicBounds(drawables[0], null, null, null);
        }
      }
    });
  }

  public void setupSearchBar() {
    setOnFocusChangeListener(this);
    setCompoundDrawablesWithIntrinsicBounds(drawables[0], null,
        null, null);
    setOnFocusChangeListener(this);
    addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
          setCompoundDrawables(drawables);
        } else {
          setCompoundDrawablesWithIntrinsicBounds(drawables[0], null, null, null);
        }
      }
    });
  }

  public interface OnClickDrawableListener {
    void onClickLeftDrawable();

    void onClickTopDrawable();

    void onClickRightDrawable();

    void onClickBottomDrawable();
  }

  public void setOnDrawableClickListener(OnClickDrawableListener listener) {
    this.drawableClickListener = listener;
  }
}