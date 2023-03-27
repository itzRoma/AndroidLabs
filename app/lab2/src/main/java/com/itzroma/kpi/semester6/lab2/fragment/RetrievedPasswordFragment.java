package com.itzroma.kpi.semester6.lab2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.itzroma.kpi.semester6.lab2.FragmentHolder;
import com.itzroma.kpi.semester6.lab2.R;
import com.itzroma.kpi.semester6.lab2.util.FragmentUtil;

public class RetrievedPasswordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retrieved_password, container, false);

        Bundle args = getArguments();
        String retrievedPassword = args.getString(InputFragment.RETRIEVED_PASSWORD_ARG);

        ((TextView) view.findViewById(R.id.retrieved_pass)).setText(
                getResources().getString(R.string.retrieved_pass, retrievedPassword)
        );

        view.findViewById(R.id.back_button).setOnClickListener(v -> FragmentUtil.replaceFragmentFor(
                getActivity().getSupportFragmentManager(),
                new InputFragment(),
                ((FragmentHolder) getActivity()).getFragmentPlaceholderId()
        ));

        return view;
    }
}
