package com.jiayang.githubnotesdemo.RxJava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.jiayang.githubnotesdemo.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 张 奎 on 2017-11-17 12:02.
 */

public class RxJavaActivity extends AppCompatActivity {
    private String TAG = "RxJavaActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
    }

    // 基础实现
    public void testFoundation(View view) {
        // 创建Observable(被观察者)
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("张三");
                subscriber.onNext("李四");
                subscriber.onCompleted();
            }
        });


        // 创建Observer(观察者)
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onStart() {
                Log.e(TAG, "onStart");
            }

            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext" + s);
            }
        };

        observable.subscribe(subscriber);

    }

    // 测试Interval
    public void testInterval(View view) {
        Observable.interval(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.e(TAG, "interval:" + aLong.intValue());
                    }
                });
    }

    // 测试Range
    public void testRange(View view) {
        Observable.range(0, 5)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "range:" + integer.intValue());
                    }
                });
    }

    // 测试Repeat
    public void testRepeat(View view) {
        Observable.range(0, 3)
                .repeat(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "repeat:" + integer.intValue());
                    }
                });
    }

    // 测试Map
    public void testMap(View view) {
        final String host = "https://github.com/";
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("JiaYang627");
            }
        }).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return host + s;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "map:" + s);
            }
        });
    }

    // 测试Filter
    public void testFilter(View view) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(4);

            }
        }).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "filter:" + integer);
            }
        });
    }


    // 测试doOnNext
    public void testDoOnNext(View view) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onNext(2);
            }
        }).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "doOnNext:" + integer);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "subscribe_onNext:" + integer);
            }
        });
    }

    // 测试 SubscribeOn和ObserveOn
    public void testSubscribeAndObserve(View view) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.e(TAG, "Observable:" + Thread.currentThread().getName());
                subscriber.onNext(1);
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Action1<Integer>() {
              @Override
              public void call(Integer integer) {
                  Log.e(TAG, "Observer:" + Thread.currentThread().getName());
              }
          });
    }

    public void testRetry(View view) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i =0 ; i < 5 ; i ++) {
                    if (i == 1) {
                        subscriber.onError(new Throwable("Throwable"));
                    } else {
                        subscriber.onNext(i);
                    }
                }
            }
        }).retry(2)
          .subscribe(new Subscriber<Integer>() {
              @Override
              public void onCompleted() {
                  Log.e(TAG, "onCompleted");
              }

              @Override
              public void onError(Throwable e) {
                  Log.e(TAG, "onError:" + e.getMessage());
              }

              @Override
              public void onNext(Integer integer) {
                  Log.e(TAG, "onNext:" + integer);
              }
          });
    }
}
