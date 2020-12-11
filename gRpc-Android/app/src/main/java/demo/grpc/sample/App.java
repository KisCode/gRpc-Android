package demo.grpc.sample;

import android.app.Application;
import android.support.multidex.MultiDex;


/****
 * Description: 
 * Author:  keno
 * CreateDate: 2020/11/16 22:01
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
