package com.gmail.shudss00.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ContactDetailsFragment extends Fragment {

    static ContactDetailsFragment newInstance(int contactId) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("contactId", contactId);
        fragment.setArguments(args);
        return fragment;
    }
    //  LayoutInflater используется для установки ресурса разметки для создания интерфейса
    //  ViewGroup container устанавливает контейнер интерфейса
    //  Bundle savedInstanceState передает ранее сохраненное состояние
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Контакт");

        View view = inflater.inflate(R.layout.contact_detail, container, false);

        int contactId = this.getArguments().getInt("contactId");
        ImageView contactImage = (ImageView) view.findViewById(R.id.image_dt);
        TextView contactName = (TextView) view.findViewById(R.id.name_dt);
        TextView contactNumber = (TextView) view.findViewById(R.id.nmb_dt_1);
        TextView contactNumber2 = (TextView) view.findViewById(R.id.nmb_dt_2);
        TextView contactEmail = (TextView) view.findViewById(R.id.eml_dt_1);
        TextView contactEmail2 = (TextView) view.findViewById(R.id.eml_dt_2);
        TextView contactDescr = (TextView) view.findViewById(R.id.description);

        Contact curContact = Contact.contacts[contactId];

        contactImage.setImageResource(curContact.getImage());
        contactName.setText(curContact.getName());
        contactNumber.setText(curContact.getNumber());
        contactNumber2.setText(curContact.getNumber2());
        contactEmail.setText(curContact.getEmail());
        contactEmail2.setText(curContact.getEmail2());
        contactDescr.setText(curContact.getDescription());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().setTitle("Список контактов");

    }
}

