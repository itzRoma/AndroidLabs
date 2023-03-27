package com.itzroma.kpi.semester6.lab2.fragment;

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

import com.itzroma.kpi.semester6.lab2.FragmentHolder;
import com.itzroma.kpi.semester6.lab2.R;
import com.itzroma.kpi.semester6.lab2.util.FragmentUtil;

public class InputFragment extends Fragment {

    private EditText passwordInputET;

    private RadioGroup visibilityOptionsRG;

    private Button okButton;

    public static final String RETRIEVED_PASSWORD_ARG = "retrieved_password";
    private static final String EMPTY_PASSWORD_ERROR = "Password not provided";

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

        passwordInputET = view.findViewById(R.id.password_input);
        visibilityOptionsRG = view.findViewById(R.id.visibility_options);
        okButton = view.findViewById(R.id.ok_button);

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
    }

    private void ok() {
        if (validateInput()) {
            Bundle args = new Bundle();
            args.putString(RETRIEVED_PASSWORD_ARG, passwordInputET.getText().toString());
            RetrievedPasswordFragment retrievedPasswordFragment = new RetrievedPasswordFragment();
            retrievedPasswordFragment.setArguments(args);

            FragmentUtil.replaceFragmentFor(
                    getActivity().getSupportFragmentManager(),
                    retrievedPasswordFragment,
                    ((FragmentHolder) getActivity()).getFragmentPlaceholderId()
            );
        }
    }

    private boolean validateInput() {
        if (isEmpty(passwordInputET.getText())) {
            Toast.makeText(getContext(), EMPTY_PASSWORD_ERROR, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
