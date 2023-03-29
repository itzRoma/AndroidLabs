package com.itzroma.kpi.semester6.lab3.fragment;

import static android.text.TextUtils.isEmpty;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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

public class InputFragment extends Fragment {

    private EditText siteInputET;
    private EditText usernameInputET;
    private EditText passwordInputET;

    private RadioGroup visibilityOptionsRG;

    private Button okButton;
    private Button afManagerButton;

    public static final String RETRIEVED_SITE_ARG = "retrieved_site";
    public static final String RETRIEVED_USERNAME_ARG = "retrieved_username";
    public static final String RETRIEVED_PASSWORD_ARG = "retrieved_password";

    private static final String EMPTY_SITE_ERROR = "Site not provided";
    private static final String EMPTY_USERNAME_ERROR = "Username not provided";
    private static final String EMPTY_PASSWORD_ERROR = "Password not provided";
    private static final String SITE_REGEX =
            "(https?://(www\\.)?)?[-a-zA-Z\\d@:%._+~#=]{1,256}\\.[a-zA-Z\\d()]{1,6}\\b([-a-zA-Z\\d()@:%_+.~#?&/=]*)";
    private static final String INVALID_SITE_ERROR = "Invalid site";
    private static final String ENTRY_EXISTS_ERROR = "Entry with provided site and username already exists";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        siteInputET = view.findViewById(R.id.site_input);
        usernameInputET = view.findViewById(R.id.username_input);
        passwordInputET = view.findViewById(R.id.password_input);
        visibilityOptionsRG = view.findViewById(R.id.visibility_options);
        okButton = view.findViewById(R.id.ok_button);
        afManagerButton = view.findViewById(R.id.af_manager_button);

        visibilityOptionsRG.setOnCheckedChangeListener((radioGroup, i) -> {
            int cursorStart = passwordInputET.getSelectionStart();
            int cursorEnd = passwordInputET.getSelectionEnd();

            if (i == R.id.hide_rb) {
                passwordInputET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (i == R.id.show_rb) {
                passwordInputET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            }

            passwordInputET.setSelection(cursorStart, cursorEnd);
        });

        okButton.setOnClickListener(v -> ok());

        afManagerButton.setOnClickListener(v -> gotoFragment(new AutofillManagerFragment()));
    }

    private void gotoFragment(Fragment fragment) {
        FragmentUtil.replaceFragmentFor(
                getActivity().getSupportFragmentManager(),
                fragment,
                ((FragmentHolder) getActivity()).getFragmentPlaceholderId()
        );
    }

    private void ok() {
        if (validateInput()) {
            Bundle args = new Bundle();
            args.putString(RETRIEVED_SITE_ARG, siteInputET.getText().toString());
            args.putString(RETRIEVED_USERNAME_ARG, usernameInputET.getText().toString());
            args.putString(RETRIEVED_PASSWORD_ARG, passwordInputET.getText().toString());
            RetrievedSiteAutofillEntryFragment retrievedSiteAutofillEntryFragment =
                    new RetrievedSiteAutofillEntryFragment();
            retrievedSiteAutofillEntryFragment.setArguments(args);

            gotoFragment(retrievedSiteAutofillEntryFragment);

            saveEntryToDB(new SiteAutofillEntry(
                    siteInputET.getText().toString(),
                    usernameInputET.getText().toString(),
                    passwordInputET.getText().toString()
            ));
        }
    }

    private boolean validateInput() {
        if (isEmpty(siteInputET.getText())) {
            Toast.makeText(getContext(), EMPTY_SITE_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isEmpty(usernameInputET.getText())) {
            Toast.makeText(getContext(), EMPTY_USERNAME_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isEmpty(passwordInputET.getText())) {
            Toast.makeText(getContext(), EMPTY_PASSWORD_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!siteInputET.getText().toString().matches(SITE_REGEX)) {
            Toast.makeText(getContext(), INVALID_SITE_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (DefaultSiteAutofillEntryRepository.getInstance(getContext()).existsBySiteAndUsername(
                siteInputET.getText().toString(), usernameInputET.getText().toString()
        )) {
            Toast.makeText(getContext(), ENTRY_EXISTS_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveEntryToDB(SiteAutofillEntry siteAutofillEntry) {
        SiteAutofillEntryRepository repository = DefaultSiteAutofillEntryRepository.getInstance(getContext());
        repository.createSiteAutofillEntry(siteAutofillEntry);
    }
}
