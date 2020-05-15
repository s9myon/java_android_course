package com.gmail.shudss00.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.ListFragment;

public class ContactListFragment extends ListFragment {
    private ContactService mService;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ServiceInterface) {
            mService = ((ServiceInterface) context).getService();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("Список контактов");
        view = getView();
        mService.getContacts(callback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_list, ContactDetailsFragment.newInstance(position))
                .addToBackStack(null)
                .commit();
    }

    public interface ResultListener {
        void onComplete(Contact[] contacts);
    }

    private ResultListener callback = new ResultListener() {
        @Override
        public void onComplete(Contact[] result) {
            final Contact[] contacts = result;
            if (view != null) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<Contact> contactAdapter =
                                new ArrayAdapter<Contact>(getActivity(), 0, Contact.contacts) {

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                if (convertView == null) {
                                    convertView = getLayoutInflater()
                                            .inflate(R.layout.contact_list_row, null, false);
                                }

                                TextView nameView = convertView.findViewById(R.id.contact_name);
                                TextView numberView = convertView.findViewById(R.id.contact_number);
                                ImageView imageView = convertView.findViewById(R.id.contact_image);

                                Contact currentContact = contacts[position];

                                nameView.setText(currentContact.getName());
                                numberView.setText(currentContact.getNumber());
                                imageView.setImageResource(currentContact.getImage());

                                return convertView;
                            }
                        };
                        setListAdapter(contactAdapter);
                    }
                });
            }
        }
    };
}