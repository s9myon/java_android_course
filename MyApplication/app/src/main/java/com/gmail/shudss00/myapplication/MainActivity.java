package com.gmail.shudss00.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements ContactService.ResultListener {
    ContactService mService;
    ServiceConnection mConnection;
    boolean mBound = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder binder) {
                // связались с ContactService, получили IBinder и экземпляр ContactService
                ContactService.ContactBinder contactBinder = (ContactService.ContactBinder) binder;
                mService = contactBinder.getService();
                mBound = true;
                if(savedInstanceState == null) {
                    new ContactListFragment();
                    getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_list, ContactListFragment.newInstance(binder))
                        .commit();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName className) {
                mBound = false;
                mService = null;
            }
        };
        // привязываемся к ContactService
        Intent intent = new Intent(this, ContactService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // отвязываемся от ContactService
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
            mService = null;
        }
    }

    @Override
    public Contact[] getContactsCallback(Contact[] contacts) {
        return contacts;
    }

    @Override
    public Contact getContactByIdCallback(int id, Contact[] contacts) {
        return contacts[id];
    }

}
