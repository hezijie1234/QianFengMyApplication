package day23downappbyservice.day32dependencyinjector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_text)
    TextView mTextView;
    @BindView(R.id.activity_main_btn)
    Button mButton;
    @BindString(R.string.app_name)
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Binder.bind(this);
        mButton.setText("按钮"+name);
        mTextView.setText("text");
    }
}
