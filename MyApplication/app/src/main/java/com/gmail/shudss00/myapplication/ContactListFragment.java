package com.gmail.shudss00.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.ListFragment;

import java.lang.reflect.Field;

public class ContactListFragment extends ListFragment {
    private ContactService.ResultListener contactService;
    private ContactService mService;

    static ContactListFragment newInstance(IBinder binder) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putBinder("binder", binder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ContactService.ResultListener) {
            contactService = (ContactService.ResultListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        contactService = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Список контактов");
        ContactService.ContactBinder contactBinder =
                (ContactService.ContactBinder) getArguments().getBinder("binder");
        mService = contactBinder.getService();

        ArrayAdapter<Contact> contactAdapter = new ArrayAdapter<Contact>(getActivity(), 0, Contact.contacts) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.contact_list_row, null,
                            false);
                }

                TextView nameView = convertView.findViewById(R.id.contact_name);
                TextView numberView = convertView.findViewById(R.id.contact_number);
                ImageView imageView = convertView.findViewById(R.id.contact_image);

                mService.getContacts(contactService);

//                Contact curContact = Contact.contacts[contactId];
//                Contact currentContact = Contact.contacts[position];
//
//                nameView.setText(currentContact.getName());
//                numberView.setText(currentContact.getNumber());
//                imageView.setImageResource(currentContact.getImage());

                return convertView;
            }

        };
        setListAdapter(contactAdapter);
    }


    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_list, ContactDetailsFragment.newInstance(position))
                .addToBackStack(null)
                .commit();
    }
}