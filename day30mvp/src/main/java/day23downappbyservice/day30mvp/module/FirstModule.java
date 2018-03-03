package day23downappbyservice.day30mvp.module;

import day23downappbyservice.day30mvp.http.LoaderJsonData;
import day23downappbyservice.day30mvp.utils.Constants;

/**
 * Created by hezijie on 2017/1/16.
 */
public class FirstModule implements IFirstModule {

    public IModuleCallback mMduleCall;

    public FirstModule(IModuleCallback mMduleCall) {
        this.mMduleCall = mMduleCall;
    }

    @Override
    public void getData() {
        LoaderJsonData load = new LoaderJsonData();
        load.getGift(Constants.URL, new LoaderJsonData.GetJsonData() {
            @Override
            public void getJsonData(String jsonData) {
                mMduleCall.setData(jsonData);
            }
        });
    }
}
