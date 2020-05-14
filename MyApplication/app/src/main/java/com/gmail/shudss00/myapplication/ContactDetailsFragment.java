package com.gmail.shudss00.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutionException;

public class ContactDetailsFragment extends Fragment {
    private ContactService mService;
    private ContactService.ResultListener contactService;

    static ContactDetailsFragment newInstance(int contactId, IBinder binder) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("contactId", contactId);
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

    //  LayoutInflater используется для установки ресурса разметки для создания интерфейса
    //  ViewGroup container устанавливает контейнер интерфейса
    //  Bundle savedInstanceState передает ранее сохраненное состояние
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Контакт");
        ContactService.ContactBinder contactBinder =
                (ContactService.ContactBinder) getArguments().getBinder("binder");
        mService = contactBinder.getService();

        View view = inflater.inflate(R.layout.contact_detail, container, false);

        ImageView contactImage = (ImageView) view.findViewById(R.id.image_dt);
        TextView contactName = (TextView) view.findViewById(R.id.name_dt);
        TextView contactNumber = (TextView) view.findViewById(R.id.nmb_dt_1);
        TextView contactNumber2 = (TextView) view.findViewById(R.id.nmb_dt_2);
        TextView contactEmail = (TextView) view.findViewById(R.id.eml_dt_1);
        TextView contactEmail2 = (TextView) view.findViewById(R.id.eml_dt_2);
        TextView contactDescr = (TextView) view.findViewById(R.id.description);

        int contactId = this.getArguments().getInt("contactId");
        Contact currentContact = null;
        try {
            currentContact = mService.getContactById(contactId, contactService);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        contactImage.setImageResource(currentContact.getImage());
        contactName.setText(currentContact.getName());
        contactNumber.setText(currentContact.getNumber());
        contactNumber2.setText(currentContact.getNumber2());
        contactEmail.setText(currentContact.getEmail());
        contactEmail2.setText(currentContact.getEmail2());
        contactDescr.setText(currentContact.getDescription());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().setTitle("Список контактов");

    }
}

