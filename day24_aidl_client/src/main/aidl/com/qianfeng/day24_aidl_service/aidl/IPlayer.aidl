// IPlayer.aidl
package com.qianfeng.day24_aidl_service.aidl;

// Declare any non-default types here with import statements

interface IPlayer {
//    /**
//     * Demonstrates some basic types that you can use as parameters
//     * and return values in AIDL.
//     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void play(String path);
    void pause();
    void resume();
    void stop();
}
