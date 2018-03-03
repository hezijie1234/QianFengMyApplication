package day23downappbyservice.day30mvp.presenter;

/**
 * Created by hezijie on 2017/1/16.
 */
public interface IFirstPresenter {



    void queryList();

    interface PresenterCall{
        void setData(String str);
    }
}
