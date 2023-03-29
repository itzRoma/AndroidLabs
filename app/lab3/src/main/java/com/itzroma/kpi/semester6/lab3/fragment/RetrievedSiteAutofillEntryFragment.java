package com.itzroma.kpi.semester6.lab3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.itzroma.kpi.semester6.lab3.FragmentHolder;
import com.itzroma.kpi.semester6.lab3.R;
import com.itzroma.kpi.semester6.lab3.util.FragmentUtil;

public class RetrievedSiteAutofillEntryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retrieved_site_autofill_entry, container, false);

        Bundle args = getArguments();
        String retrievedSite = args.getString(InputFragment.RETRIEVED_SITE_ARG);
        String retrievedUsername = args.getString(InputFragment.RETRIEVED_USERNAME_ARG);
        String retrievedPassword = args.getString(InputFragment.RETRIEVED_PASSWORD_ARG);

        ((TextView) view.findViewById(R.id.retrieved_pass)).setText(String.format(
                "Autofill info for %s%nUsername: %s%nPassword: %s",
                retrievedSite, retrievedUsername, retrievedPassword
        ));

        view.findViewById(R.id.back_button).setOnClickListener(v -> FragmentUtil.replaceFragmentFor(
                getActivity().getSupportFragmentManager(),
                new InputFragment(),
                ((FragmentHolder) getActivity()).getFragmentPlaceholderId()
        ));

        return view;
    }
}
