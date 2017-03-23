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
 * Created by Alan's on 5/9/2016.
 */
public class CEditText extends EditText implements View.OnFocusChangeListener {
  private Drawable[] drawables;
  private static int sizeDrawableDefault = 20;
  private static int sizeDrawableLeft;
  private static int sizeDrawableRight;
  OnClickDrawableListener drawableClickListener;

  public CEditText(Context context) {
    super(context);
    initFont(null);
    setOnFocusChangeListener(this);
  }

  public CEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    initFont(attrs);
    initAttrs(context, attrs);
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    setOnFocusChangeListener(this);
  }

  public CEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initFont(attrs);
    initAttrs(context, attrs);
    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    setOnFocusChangeListener(this);
  }

  private void initAttrs(Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableView);
    float sizeLeftDp = a.getDimension(R.styleable.DrawableView_drawableLeftSize, sizeDrawableDefault);
    sizeDrawableLeft = DeviceUtils.dpToPx(context, sizeLeftDp);
    float sizeRightDp = a.getDimensionPixelOffset(R.styleable.DrawableView_drawableRightSize, sizeDrawableDefault);
    sizeDrawableRight = DeviceUtils.dpToPx(context, sizeRightDp);
    drawables = getCompoundDrawables();
    adjustDrawableSize();
  }

  public void adjustDrawableSize() {
    drawables[0] = new BitmapDrawable(getResources(),
        Bitmap.createScaledBitmap(((BitmapDrawable) drawables[0]).getBitmap(),
            sizeDrawableLeft, sizeDrawableLeft, true));
    drawables[2] = new BitmapDrawable(getResources(),
        Bitmap.createScaledBitmap(((BitmapDrawable) drawables[2]).getBitmap(),
            sizeDrawableRight, sizeDrawableRight, true));
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

    void onClickRightDrawable();
  }

  public void setOnDrawableClickListener(OnClickDrawableListener listener) {
    this.drawableClickListener = listener;
  }
}