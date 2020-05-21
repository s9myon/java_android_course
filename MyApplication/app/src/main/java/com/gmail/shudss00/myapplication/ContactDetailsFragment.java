package com.gmail.shudss00.myapplication;

import android.os.Bundle;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ContactDetailsFragment extends Fragment {
    private ContactService mService;
    private View view;
    private ImageView contactImage;
    private TextView contactName;
    private TextView contactNumber;
    private TextView contactNumber2;
    private TextView contactEmail;
    private TextView contactEmail2;
    private TextView contactDescription;

    static ContactDetailsFragment newInstance(int contactId) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("contactId", contactId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ServiceInterface) {
            mService = ((ServiceInterface) context).getService();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setTitle("Список контактов");
        view = null;
        contactImage = null;
        contactName = null;
        contactNumber = null;
        contactNumber2 = null;
        contactEmail = null;
        contactEmail2 = null;
        contactDescription = null;
    }

    //  LayoutInflater используется для установки ресурса разметки для создания интерфейса
    //  ViewGroup container устанавливает контейнер интерфейса
    //  Bundle savedInstanceState передает ранее сохраненное состояние
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Контакт");
        view = inflater.inflate(R.layout.contact_detail, container, false);

        contactImage = (ImageView) view.findViewById(R.id.image_dt);
        contactName = (TextView) view.findViewById(R.id.name_dt);
        contactNumber = (TextView) view.findViewById(R.id.nmb_dt_1);
        contactNumber2 = (TextView) view.findViewById(R.id.nmb_dt_2);
        contactEmail = (TextView) view.findViewById(R.id.eml_dt_1);
        contactEmail2 = (TextView) view.findViewById(R.id.eml_dt_2);
        contactDescription = (TextView) view.findViewById(R.id.description);

        int contactId = this.getArguments().getInt("contactId");
        mService.getContactById(contactId, callback);

        return view;
    }

    public interface ResultListener {
        void onComplete(Contact result);
    }

    private ResultListener callback = new ResultListener() {
        @Override
        public void onComplete(Contact result) {
            final Contact currentContact = result;
            if (view != null) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        if (contactImage != null) {
                            contactImage.setImageResource(currentContact.getImage());
                        }
                        if (contactName != null) {
                            contactName.setText(currentContact.getName());
                        }
                        if (contactNumber != null) {
                            contactNumber.setText(currentContact.getNumber());
                        }
                        if (contactNumber2 != null) {
                            contactNumber2.setText(currentContact.getNumber2());
                        }
                        if (contactEmail != null) {
                            contactEmail.setText(currentContact.getEmail());
                        }
                        if (contactEmail2 != null) {
                            contactEmail2.setText(currentContact.getEmail2());
                        }
                        if (contactDescription != null) {
                            contactDescription.setText(currentContact.getDescription());
                        }
                    }
                });
            }
        }
    };

}

