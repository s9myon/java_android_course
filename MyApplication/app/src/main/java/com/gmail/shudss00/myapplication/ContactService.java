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

public class ContactService extends Service {
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

    // методы для клиента
    // метод получения списка контактов с короткой информацией(асинхронный)
    public void getContacts(ContactListFragment.ResultListener callback) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        final WeakReference<ContactListFragment.ResultListener> ref =
                new WeakReference<>(callback);
        executor.execute(new Runnable() {
            public void run() {
                Contact[] contacts = Contact.contacts;

                ContactListFragment.ResultListener local = ref.get();
                if(local != null) {
                    local.onComplete(contacts);
                }
            }
        });
        executor.shutdown();
    }

    // метод получения контактa с подробной информацией(асинхронный)
    public void getContactById(final int id, ContactDetailsFragment.ResultListener callback) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        final WeakReference<ContactDetailsFragment.ResultListener> ref =
                new WeakReference<>(callback);
        final int contactId = id;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Contact contact = Contact.contacts[contactId];

                ContactDetailsFragment.ResultListener local = ref.get();
                if(local != null) {
                    local.onComplete(contact);
                }
            }
        });

    }

}
