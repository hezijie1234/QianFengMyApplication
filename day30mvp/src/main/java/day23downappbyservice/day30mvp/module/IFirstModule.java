package day23downappbyservice.day30mvp.module;

/**
 * Created by hezijie on 2017/1/16.
 */
public interface IFirstModule {

    void getData();

    interface IModuleCallback{
        void setData(String str);
    }

}
