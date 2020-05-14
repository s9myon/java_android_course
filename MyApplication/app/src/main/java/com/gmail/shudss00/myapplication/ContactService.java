package com.gmail.shudss00.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ContactService extends Service{
    private IBinder mBinder;

    public class ContactBinder extends Binder {
        ContactService getService() {
            // возвращает this экземпляра ContactService
            // чтобы клиенты могли вызывать публичные методы
            return ContactService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new ContactBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinder = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    public interface ResultListener {
        public Contact[] getContactsCallback(Contact[] contacts);
        public Contact getContactByIdCallback(int id, Contact[] contacts);
    }

    // методы для клиента
    // метод получения списка контактов с короткой информацией(асинхронный)
    public Contact[] getContacts(ResultListener callback) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        final WeakReference<ResultListener> ref = new WeakReference<>(callback);
        Callable<Contact[]> callable = new MyCallable(ref);
        Future<Contact[]> future = executor.submit(callable);
        executor.shutdown();
        return future.get();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Contact[] result = Contact.contacts;
//                ResultListener local = ref.get();
//                if (local != null) {
//                    local.getContactsCallback(result);
//                }
//            }
//        }).start();
    }
    static class MyCallable implements Callable<Contact[]> {
        final WeakReference<ResultListener> ref;
        MyCallable(WeakReference<ResultListener> callback) {
            this.ref = callback;
        }
        @Override
        public Contact[] call() throws Exception {
            Contact[] contacts = Contact.contacts;
            Contact[] result = null;
            ResultListener local = ref.get();
            if (local != null) {
                result = local.getContactsCallback(contacts);
            }
            return result;
        }
    }
    public void getContactById(final int id, ResultListener callback) {
        final WeakReference<ResultListener> ref = new WeakReference<>(callback);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Contact[] result = Contact.contacts;
                ResultListener local = ref.get();
                if (local != null) {
                    local.getContactByIdCallback(id ,result);
                }
            }
        }).start();
    }


}
