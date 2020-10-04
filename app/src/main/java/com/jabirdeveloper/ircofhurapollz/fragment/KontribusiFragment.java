package com.jabirdeveloper.ircofhurapollz.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.MainActivity;
import com.jabirdeveloper.ircofhurapollz.activity.PostActivity;
import com.jabirdeveloper.ircofhurapollz.activity.RegisterActivity;
import com.jabirdeveloper.ircofhurapollz.model.ArtikelPost;
import com.jabirdeveloper.ircofhurapollz.model.MediaModel;
import com.jabirdeveloper.ircofhurapollz.model.UserModel;
import com.jabirdeveloper.ircofhurapollz.model.authentication.UserAuth;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.network.WPApiUrl;
import com.jabirdeveloper.ircofhurapollz.network.WPConnection;
import com.vincent.filepicker.activity.ImagePickActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.vincent.filepicker.Constant.MAX_NUMBER;
import static com.vincent.filepicker.Constant.REQUEST_CODE_PICK_IMAGE;
import static com.vincent.filepicker.Constant.RESULT_PICK_IMAGE;

import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class KontribusiFragment extends Fragment {
    private Dialog dialog;

    Editable title;
    ArrayList<Integer> selectedCategoryPostView;
    String content;

    // media
    MultipartBody.Part filePart;

    // Global preview image
    ImageView imgUpload;

    public KontribusiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get JWT token in shared prefs
        SharedPreferences sh = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String token = sh.getString("token", "");

        View view;

        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage("Silahkan menunggu...");
        builder.setCancelable(false);
        dialog = builder.create();

        // Show login or post depends on token condition
        if (token.equalsIgnoreCase("")) {
            view = inflater.inflate(R.layout.fragment_kontribusi_sign_in, container, false);
            // Setup component view sign in
            signInView(view);
        } else {
            view = inflater.inflate(R.layout.fragment_kontribusi_post, container, false);
            // Setup component view sign in
            postView(view, token);
        }

        return view;
    }

    private void postView(View view, String token) {
        // get all elem
        TextInputEditText tiTitle = view.findViewById(R.id.tiTitle);
        Button btnCategory = view.findViewById(R.id.btnCategory);
        Button btnAddContent = view.findViewById(R.id.btnAddContent);
        Button btnImage = view.findViewById(R.id.btnImage);
        Button btnContribute = view.findViewById(R.id.btnContribute);

        // All post variable
        title = tiTitle.getText();

        // Define array category
        String[] categories = getResources().getStringArray(R.array.list_kategori);

        // Define array category
        String[] categoriesId = getResources().getStringArray(R.array.list_id_kategori);

        // Alert dialog for category
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        // Boolean array for initial selected items
        boolean[] checkedCategories = new boolean[categories.length];

        for (int i = 0; i < categories.length; i++) {
            checkedCategories[i] = false;
        }

        /*
          DialogInterface.OnMultiChoiceClickListener
          public abstract void onClick (DialogInterface dialog, int which, boolean isChecked)

          This method will be invoked when an item in the dialog is clicked.

          Parameters
          dialog The dialog where the selection was made.
          which The position of the item in the list that was clicked.
          isChecked True if the click checked the item, else false.
        */
        builder.setMultiChoiceItems(categories, checkedCategories, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                checkedCategories[which] = isChecked;

                // Get the current focused item
                String currentItem = categories[which];

                // Notify the current action
                Toast.makeText(getContext(),
                        currentItem + " dipilih", Toast.LENGTH_SHORT).show();
            }
        });

        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("Pilih kategori");

        // Set the positive/yes button click listener
        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                selectedCategoryPostView = new ArrayList<>();
                for (int i = 0; i < categoriesId.length; i++) {
                    boolean checked = checkedCategories[i];
                    if (checked) {
                        selectedCategoryPostView.add(Integer.parseInt(categoriesId[i]));
                    }
                }
                dialog.dismiss();
            }
        });

        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        // All button listener
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        btnAddContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra("html", content);
                startActivityForResult(intent, 1);
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(view);
            }
        });

        btnContribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        title != null
                                && !(title.toString().equals(""))
                                && selectedCategoryPostView != null
                                && !(selectedCategoryPostView.toString().equals(""))
                                && content != null
                                && filePart != null
                ) {
                    post(token);
                } else {
                    Toast.makeText(getContext(), "Harap mengisi field terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Content
        if (requestCode == 1) {
            content = data.getStringExtra("html");
            // Notify the current action
            Toast.makeText(getContext(),
                    "Sukses menambah konten", Toast.LENGTH_SHORT).show();
        }
        // Upload
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {

            // memunculkan btnUpload dan imgUpload.
            imgUpload.setVisibility(View.VISIBLE);

            // membuat variable yang menampung path dari picked image.
            ArrayList<ImageFile> parcel = data.getParcelableArrayListExtra(RESULT_PICK_IMAGE);
            if (parcel != null && parcel.size() > 0) {
                String pickedImg = parcel.get(0).getPath();

                // membuat request body yang berisi file dari picked image.
                RequestBody requestBodyFile = RequestBody.create(MediaType.parse("multipart"), new File(pickedImg));

                // mengoper value dari requestbody sekaligus membuat form data untuk upload. dan juga mengambil nama dari picked image
                filePart = MultipartBody.Part.createFormData("file", new File(pickedImg).getName(), requestBodyFile);

                // mempilkan image yang akan diupload dengan glide ke imgUpload.
                Glide.with(this).load(pickedImg).into(imgUpload);
            }

        }
    }

    private void signInView(View view) {
        // Get all button
        Button btnRegister = view.findViewById(R.id.btnRegister);
        Button btnLogin = view.findViewById(R.id.btnLogin);

        // Get all field
        TextInputEditText tiPassword = view.findViewById(R.id.tiPassword);
        TextInputEditText tiUsername = view.findViewById(R.id.tiUsername);

        // Build response button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable username = tiUsername.getText();
                Editable password = tiPassword.getText();
                if (username != null && !(username.toString().equals("")) && password != null && !(password.toString().equals(""))) {
                    login(username.toString(), password.toString());
                } else {
                    Toast.makeText(getContext(), "Username dan Password harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(String username, String password) {
        dialog.show();
        String url = WPApiUrl.BASE_URL_LOGIN;
        Call<UserModel> login = WPConnection.getWpService().login(url, new UserAuth(username, password));
        login.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                if (response.body() != null) {

                    // Save token
                    // Get JWT token in shared prefs
                    SharedPreferences sh = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("token", response.body().getToken());
                    editor.putString("display_name", response.body().getUser_display_name());
                    editor.putString("email", response.body().getUser_email());
                    editor.putString("nicename", response.body().getUser_nicename());
                    editor.apply();

                    startActivity(new Intent(getActivity(), MainActivity.class));
                    requireActivity().finish();
                } else if (response.errorBody() != null) {
                    String msg = "";
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        msg = jObjError.getString("message");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), Html.fromHtml(msg), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Gagal login", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();
                Toast.makeText(getContext(), "Gagal login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage(View view) {
        imgUpload = view.findViewById(R.id.imgUpload);
        // check permission untuk android M dan ke atas.
        // saat permission disetujui oleh user maka jalan script untuk intent ke pick image.
        if (EasyPermissions.hasPermissions(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent i = new Intent(requireActivity(), ImagePickActivity.class);
            i.putExtra(MAX_NUMBER, 1);
            startActivityForResult(i, REQUEST_CODE_PICK_IMAGE);
        } else {
            // tampilkan permission request saat belum mendapat permission dari user
            EasyPermissions.requestPermissions(requireActivity(),
                    "This application need your permission to access photo gallery.",
                    991, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void post(String token) {
        // Upload media first

        MultipartBody.Part titlePart = MultipartBody.Part.createFormData("title", title.toString());
        MultipartBody.Part captionPart = MultipartBody.Part.createFormData("caption", title.toString());
        MultipartBody.Part statusPart = MultipartBody.Part.createFormData("status", "publish");

        dialog.show();
        String url = WPApiUrl.BASE_URL + "media";
        Call<MediaModel> createMedia = WPConnection.getWpService().createMedia(
                url,
                "Bearer " + token,
                filePart,
                titlePart,
                captionPart,
                statusPart
        );
        createMedia.enqueue(new Callback<MediaModel>() {
            @Override
            public void onResponse(@NonNull Call<MediaModel> call, @NonNull Response<MediaModel> response) {
                if (response.code() == 401) {
                    // Remove token
                    // Get JWT token in shared prefs
                    SharedPreferences sh = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.remove("token");
                    editor.remove("display_name");
                    editor.remove("email");
                    editor.remove("nicename");
                    editor.apply();

                    startActivity(new Intent(getActivity(), MainActivity.class));
                    requireActivity().finish();
                    dialog.dismiss();
                } else {
                    if (response.body() != null) {
                        String url = WPApiUrl.BASE_URL + "posts";
                        Call<WpPostModel> wpPost = WPConnection.getWpService().contribute(url, "Bearer " + token, new ArtikelPost(
                                "pending",
                                title.toString(),
                                content,
                                selectedCategoryPostView,
                                response.body().getId()
                        ));
                        wpPost.enqueue(new Callback<WpPostModel>() {
                            @Override
                            public void onResponse(@NonNull Call<WpPostModel> call, @NonNull Response<WpPostModel> response) {
                                if (response.code() == 401) {
                                    // Remove token
                                    // Get JWT token in shared prefs
                                    SharedPreferences sh = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sh.edit();
                                    editor.remove("token");
                                    editor.remove("display_name");
                                    editor.remove("email");
                                    editor.remove("nicename");
                                    editor.apply();

                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                    requireActivity().finish();
                                    dialog.dismiss();
                                } else {
                                    if (response.body() != null) {
                                        Toast.makeText(getContext(), "Terima kasih untuk kontribusinya, artikel akan segera direview dan dipublish admin", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                        requireActivity().finish();
                                        dialog.dismiss();
                                    } else if (response.errorBody() != null) {
                                        String msg = "";
                                        try {
                                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                                            msg = jObjError.getString("message");
                                        } catch (JSONException | IOException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getContext(), Html.fromHtml(msg), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(getContext(), "Gagal posting artikel", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<WpPostModel> call, @NonNull Throwable t) {
                                Log.e(TAG, "onFailure: ", t);
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Gagal posting artikel", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.errorBody() != null) {
                        String msg = "";
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            msg = jObjError.getString("message");
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(), Html.fromHtml(msg), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Gagal mengupolad gambar", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MediaModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();
                Toast.makeText(getContext(), "Gagal mengupolad gambar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
