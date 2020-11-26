package org.maktab.photogallery.event;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class RxBus {

    private static RxBus mInstance;
    private PublishSubject<Object> subject = PublishSubject.create();

    public static RxBus getInstance() {
        if (mInstance == null) {
            mInstance = new RxBus();
        }
        return mInstance;
    }

    private RxBus(){

    }

    public void setString(Object object) {
        subject.onNext(object);
    }

    public Observable<Object> getEvents() {
        return subject;
    }

    public void post(NotificationEvent event){
        subject.onNext(event);
    }
}
