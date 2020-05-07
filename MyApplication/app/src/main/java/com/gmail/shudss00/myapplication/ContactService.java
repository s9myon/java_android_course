package com.gmail.shudss00.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

public class ContactService extends Service {
    // создаём интерфейс
    private final IBinder mBinder = new ContactBinder();

    public class ContactBinder extends Binder {
        ContactService getService() {
            // возвращает this экземпляра ContactService
            // чтобы клиенты могли вызывать публичные методы
            return ContactService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    // методы для клиента
    // метод получения списка контактов с короткой информацией(асинхронный)
    private class DownloadContactTask extends AsyncTask<Void, Void, Contact[]> {
        @Override
        protected Contact[] doInBackground(Void... voids){
            return Contact.contacts;
        }
        @Override
        protected Contact[] onPostExecute(Contact[] result) {
            return result;
        }
    }
    // метод получения детальной информации по ID контакта(асинхронный)
}
