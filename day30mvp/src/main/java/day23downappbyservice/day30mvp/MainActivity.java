package day23downappbyservice.day30mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import day23downappbyservice.day30mvp.presenter.FirstPresenter;
import day23downappbyservice.day30mvp.presenter.IFirstPresenter;

public class MainActivity extends AppCompatActivity implements IFirstPresenter.PresenterCall{

    private TextView mTextView;
    private IFirstPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text_view);
       mPresenter = new FirstPresenter(this);
        mPresenter.queryList();
    }

    @Override
    public void setData(String str) {
        mTextView.setText(str);
    }
}
