package com.jabirdeveloper.ircofhurapollz.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.DetailActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class KomentarUserDialog extends BottomSheetDialogFragment {

    private static final String TAG = KomentarUserDialog.class.getSimpleName();
    private KomentarUserDialog kud;
    private KomentarUserListener komentarUserListener;

    public void tampilkan(FragmentManager fm, Bundle b){
        kud = new KomentarUserDialog();
        kud.setArguments(b);
        kud.show(fm, TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_komentar_user, container, false);
//        view.getViewTreeObserver().addOnGlobalLayoutListener(()->{
//            FrameLayout kudLayout = getDialog().findViewById(R.id.design_bottom_sheet);
//            kudLayout.setBackgroundResource(R.drawable.bg_bottom_sheet_dialog);
//        });

        EditText inputNama = view.findViewById(R.id.input_nama);
        EditText inputEmail = view.findViewById(R.id.input_email);
        EditText inputWebsite = view.findViewById(R.id.input_website);
        Button btnSubmit = view.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(v -> {
            String nama = inputNama.getText().toString();
            String email = inputEmail.getText().toString();
            String website = inputWebsite.getText().toString();
//            Toast.makeText(getContext(), nama + ", " + email + ", " + getArguments().getString("komentar"), Toast.LENGTH_SHORT).show();
            if (!TextUtils.isEmpty(nama)){
                if (!TextUtils.isEmpty(email)){
                    if (!TextUtils.isEmpty(website)){
                        komentarUserListener.onSubmitWithUrl(nama, email, website);
                    } else {
                        komentarUserListener.onSubmit(nama, email);
                    }
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public interface KomentarUserListener{
        void onSubmitWithUrl(String nama, String email, String url);
        void onSubmit(String nama, String email);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            komentarUserListener = (KomentarUserListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + "must implement BottomSheetListener");
        }
    }
}
