package day23downappbyservice.day32dependencyinjector;

import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by hezijie on 2017/2/4.
 */
public class Binder {

    public  static void bind(MainActivity activity){
        Class<? extends MainActivity> activityClass = activity.getClass();
        Field[] declaredFields = activityClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            BindView annotation = field.getAnnotation(BindView.class);
            BindString stringAnno = field.getAnnotation(BindString.class);
            if(annotation != null){
                int id = annotation.value();
                View view = activity.findViewById(id);
                try {
                    field.set(activity,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else if(stringAnno != null){
                int id = stringAnno.value();
                String str = activity.getResources().getString(id);
                try {
                    field.set(activity,str);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
