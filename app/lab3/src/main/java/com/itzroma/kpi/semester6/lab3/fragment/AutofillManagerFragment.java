package com.itzroma.kpi.semester6.lab3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.itzroma.kpi.semester6.lab3.FragmentHolder;
import com.itzroma.kpi.semester6.lab3.R;
import com.itzroma.kpi.semester6.lab3.db.SiteAutofillEntryRepository;
import com.itzroma.kpi.semester6.lab3.db.impl.DefaultSiteAutofillEntryRepository;
import com.itzroma.kpi.semester6.lab3.model.SiteAutofillEntry;
import com.itzroma.kpi.semester6.lab3.util.FragmentUtil;

import java.util.List;

public class AutofillManagerFragment extends Fragment {

    private TableLayout tableLayout;

    private Button clearDBButton;
    private Button backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_autofill_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tableLayout = view.findViewById(R.id.tableLayout);
        clearDBButton = view.findViewById(R.id.clear_db_button);
        backButton = view.findViewById(R.id.back_button);

        clearDBButton.setOnClickListener(v -> clearDB());
        backButton.setOnClickListener(v -> FragmentUtil.replaceFragmentFor(
                getActivity().getSupportFragmentManager(),
                new InputFragment(),
                ((FragmentHolder) getActivity()).getFragmentPlaceholderId()
        ));

        SiteAutofillEntryRepository repository = DefaultSiteAutofillEntryRepository.getInstance(getContext());
        List<SiteAutofillEntry> allEntries = repository.getAllSiteAutofillEntries();

        if (allEntries.isEmpty()) {
            showEmptyMessage();
        } else {
            insertTableHeader();
            allEntries.forEach(this::addEntryToTable);
        }
    }

    private void clearDB() {
        DefaultSiteAutofillEntryRepository.getInstance(getContext()).deleteAllSiteAutofillEntries();
        Toast.makeText(getContext(), "All the entries was deleted from database!", Toast.LENGTH_LONG).show();
        showEmptyMessage();
    }

    private void showEmptyMessage() {
        TextView textView = new TextView(getContext());
        textView.setText("No autofill entries found!");
        tableLayout.removeAllViews();
        tableLayout.addView(textView);
    }

    private void insertTableHeader() {
        TableRow row = new TableRow(getContext());

        TextView siteLabel = new TextView(getContext());
        siteLabel.setText("site");
        row.addView(siteLabel);

        TextView usernameLabel = new TextView(getContext());
        usernameLabel.setText("username");
        row.addView(usernameLabel);

        TextView passwordLabel = new TextView(getContext());
        passwordLabel.setText("password");
        row.addView(passwordLabel);

        tableLayout.addView(row);

        TextView textView = new TextView(getContext());
        textView.setText("   ");
        tableLayout.addView(textView);
    }

    private void addEntryToTable(SiteAutofillEntry siteAutofillEntry) {
        TableRow row = new TableRow(getContext());

        TextView siteView = new TextView(getContext());
        siteView.setText(siteAutofillEntry.getSite());
        row.addView(siteView);

        TextView usernameView = new TextView(getContext());
        usernameView.setText(siteAutofillEntry.getUsername());
        row.addView(usernameView);

        TextView passwordView = new TextView(getContext());
        passwordView.setText(siteAutofillEntry.getPassword());
        row.addView(passwordView);

        tableLayout.addView(row);
    }
}