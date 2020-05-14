package com.gmail.shudss00.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.ListFragment;

public class ContactListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Список контактов");

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

                Contact currentContact = Contact.contacts[position];

                nameView.setText(currentContact.getName());
                numberView.setText(currentContact.getNumber());
                imageView.setImageResource(currentContact.getImage());

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