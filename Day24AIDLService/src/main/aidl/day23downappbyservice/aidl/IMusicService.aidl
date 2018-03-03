// IMusicService.aidl
package day23downappbyservice.aidl;

// Declare any non-default types here with import statements

interface IMusicService {
    void play(String str);
    void pause();
    void resume();
    void stop();
}
