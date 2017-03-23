package wingmdia.com.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wingmedia.utils.views.CEditText;
import com.wingmedia.utils.views.CTextView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final CEditText cEditText = (CEditText) findViewById(R.id.edt);
    CTextView cTextView = (CTextView) findViewById(R.id.ctv);
    cEditText.displayDrawable(true, true, true, true);
    cEditText.setOnDrawableClickListener(new CEditText.OnClickDrawableListener() {
      @Override
      public void onClickLeftDrawable() {
        Toast.makeText(MainActivity.this, "LEFT", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onClickRightDrawable() {
        cEditText.setText("");
      }

    });
    cTextView.setOnDrawableClickListener(new CTextView.OnClickDrawableListener() {
      @Override
      public void onClickLeftDrawable() {
        Toast.makeText(MainActivity.this, "LEFT", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onClickRightDrawable() {
        Toast.makeText(MainActivity.this, "RIGHT", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
