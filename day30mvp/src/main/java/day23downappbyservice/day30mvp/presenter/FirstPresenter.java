package day23downappbyservice.day30mvp.presenter;

import day23downappbyservice.day30mvp.module.FirstModule;
import day23downappbyservice.day30mvp.module.IFirstModule;

/**
 * Created by hezijie on 2017/1/16.
 */
public class FirstPresenter implements IFirstPresenter,IFirstModule.IModuleCallback {

    private FirstModule mFirModule;
    private PresenterCall mPresenter;

    public FirstPresenter(PresenterCall mPresenter) {
        mFirModule = new FirstModule(this);
        this.mPresenter = mPresenter;
    }

    @Override
    public void queryList() {
        mFirModule.getData();
    }

    @Override
    public void setData(String str) {
        mPresenter.setData(str);
    }
}
