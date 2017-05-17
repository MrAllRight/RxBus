package com.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by liuyong
 * Data: 2017/5/17
 * Github:https://github.com/MrAllRight
 */

public class RxBus {
    private static  RxBus Instance;

    private final Subject<Object> bus;
    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        bus=PublishSubject.create().toSerialized();
    }
    // 单例RxBus
    public static RxBus getDefault() {
        if (Instance == null) {
            synchronized (RxBus.class) {
                if (Instance == null) {
                    Instance = new RxBus();
                }
            }
        }
        return Instance ;
    }

    public void post (Object o) {
        bus.onNext(o);
    }

    public <T> Observable<T> toObservable (Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
