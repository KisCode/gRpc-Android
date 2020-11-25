package kiscode.grpcgo;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;


/**
 * Description:
 * Author: KENO
 * Date : 2020/11/19 15:50
 **/
public class RxCallAdapterFactory extends CallAdapter.Factory {
    public static RxCallAdapterFactory create() {
        return new RxCallAdapterFactory();
    }

    /**
     * Extract the upper bound of the generic parameter at {@code index} from {@code type}. For
     * example, index 1 of {@code Map<String, ? extends Runnable>} returns {@code Runnable}.
     */
    protected static Type getParameterUpperBound(int index, ParameterizedType type) {
        return Utils.getParameterUpperBound(index, type);
    }

    @Override
    public CallAdapter<?> get(Type returnType) {
        Class<?> rawType = getRawType(returnType);
        String canonicalName = rawType.getCanonicalName();
        boolean isSingle = "rx.Single".equals(canonicalName);
        boolean isCompletable = "rx.Completable".equals(canonicalName);
        Log.i("RxCallAdapterFactory", returnType + "---" + canonicalName + "---" + rawType);

//        if (rawType != Observable.class && !isSingle && !isCompletable) {
//            return null;
//        }


        CallAdapter<Observable<?>> callAdapter = getCallAdapter(returnType);

        return callAdapter;
    }

    private CallAdapter<Observable<?>> getCallAdapter(Type returnType) {
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        Log.i("RxCallAdapterFactoryGet", observableType + "---" + rawObservableType);
        return new RxCallAdapter(observableType);
    }
}