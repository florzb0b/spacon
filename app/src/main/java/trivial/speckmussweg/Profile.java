package trivial.speckmussweg;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import pub.devrel.easypermissions.EasyPermissions;
import trivial.speckmussweg.database.*;

public class Profile extends Fragment {
    // TODO: Methode schreiben um daten abzufragen, ob was eingegeben wurde. Diese dann in Home abfragen, um onbackPressed() zum laufen zu kriegen.
    private static final int REQUEST_CODE = 20;
    Calendar calenderProfile = Calendar.getInstance();
    MyDatabase database;
    View viewMain;
    EditText editTextFirstName, editTextLastName, editTextWeight, editTextHeight;
    TextView textViewDateOfBirth, textViewGender;
    LinearLayout linearLayoutAge;
    ImageView imgViewBtnDeleteContent, imgViewProfilePic;
    Uri uriProfileImage;
    String stringPhotoRes, stringFirstName, stringLastName, stringWeight, stringHeight, stringDateOfBirth, stringGender;
    Boolean booleanPhotoSelected = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Enable Optionsmenu
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        viewMain = inflater.inflate(R.layout.fragment_profile_new, container, false);

        editTextFirstName = viewMain.findViewById(R.id.user_profile_content_firstname);
        editTextLastName = viewMain.findViewById(R.id.user_profile_content_lastname);
        editTextWeight = viewMain.findViewById(R.id.user_profile_content_weight);
        editTextHeight = viewMain.findViewById(R.id.user_profile_content_height);
        textViewDateOfBirth = viewMain.findViewById(R.id.user_profile_content_dateofbirth);
        textViewGender = viewMain.findViewById(R.id.user_profile_content_gender);
        linearLayoutAge = viewMain.findViewById(R.id.user_profile_linearlayout_age);
        imgViewBtnDeleteContent = viewMain.findViewById(R.id.user_profile_button_deleteContent);
        imgViewProfilePic = viewMain.findViewById(R.id.user_profile_content_photo);

        database = new MyDatabase(getActivity());
        Cursor cursor = database.selectProfile(1);
        if (cursor.getCount() >= 1) {
            /*
    "firstname";
    "lastname";
    "gender";
    "birthday";
    "height";
    "weight";
    "picture";
 */
            editTextFirstName.setText(cursor.getString(1));
            editTextLastName.setText(cursor.getString(2));
            if (cursor.getInt(3) == 0) {
                textViewGender.setText("Female");
            } else if (cursor.getInt(3) == 1) {
                textViewGender.setText("Male");
            }
            textViewDateOfBirth.setText(cursor.getString(4));
            String dateToSplit = cursor.getString(4);
            String[] splittedDate = dateToSplit.split("/");
            editTextHeight.setText(cursor.getString(5));
            editTextWeight.setText(cursor.getString(6));
            uriProfileImage = Uri.parse(cursor.getString(7));
            Glide.with(this).load(uriProfileImage).crossFade().
                    diskCacheStrategy(DiskCacheStrategy.ALL).into(imgViewProfilePic);
        }
        database.close();
        imgViewBtnDeleteContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFirstName.setText("");
                editTextLastName.setText("");
                editTextWeight.setText("");
                editTextHeight.setText("");
                textViewDateOfBirth.setText("");
                textViewGender.setText("");
            }
        });

        imgViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booleanPhotoSelected = true;
                openAlbum(view);
            }
        });
        imgViewProfilePic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                uriProfileImage = Uri.parse("");
                Glide.with(Profile.this).load(uriProfileImage).crossFade().
                        diskCacheStrategy(DiskCacheStrategy.ALL).into(imgViewProfilePic);
                Toast.makeText(getActivity(), "Image has been reset",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        });

        final DatePickerDialog.OnDateSetListener datePickerSetting = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calenderProfile.set(Calendar.YEAR, year);
                calenderProfile.set(Calendar.MONTH, monthOfYear);
                calenderProfile.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setDate();
            }
        };
        linearLayoutAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Objects.requireNonNull(getActivity()), datePickerSetting,
                        calenderProfile.get(Calendar.YEAR),
                        calenderProfile.get(Calendar.MONTH),
                        calenderProfile.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        textViewGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence genders[] = new CharSequence[]{"Female", "Male"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.AlertDialogStyle);
                builder.setTitle("I identify myself as a...");
                builder.setItems(genders, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index) {
                        if (index == 0) {
                            textViewGender.setText("Female");
                        }
                        if (index == 1) {
                            textViewGender.setText("Male");
                        }
                    }
                });
               builder.show();

            }
        });
        return viewMain;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.optionsmenu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public Boolean savePossible() {
        return TextUtils.isEmpty(editTextFirstName.getText().toString())
                || TextUtils.isEmpty(editTextLastName.getText().toString())
                || TextUtils.isEmpty(editTextWeight.getText().toString())
                || TextUtils.isEmpty(editTextHeight.getText().toString())
                || TextUtils.isEmpty(textViewDateOfBirth.getText().toString())
                || TextUtils.isEmpty(textViewGender.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.optionsmenu_profile_save:
                // All Textviews must have Content
                if (!(savePossible())) {
                    // Profilepic is selected
                    if (booleanPhotoSelected) {
                        stringPhotoRes = uriProfileImage.toString();
                    } else {
                        stringPhotoRes = "";
                    }
                    stringFirstName = editTextFirstName.getText().toString();
                    stringLastName = editTextLastName.getText().toString();
                    stringWeight = editTextWeight.getText().toString();
                    stringHeight = editTextHeight.getText().toString();
                    stringDateOfBirth = textViewDateOfBirth.getText().toString();
                    stringGender = textViewGender.getText().toString();
                    //1 = Male, 0 = Female
                    byte genderId = 1;
                    if (stringGender.equals("Male")) {
                        genderId = 1;
                    } else if (stringGender.equals("Female")) genderId = 0;
                    database = new MyDatabase(getActivity());
                    Cursor cursor = database.selectProfile(1);
                    if (cursor.getCount() == 0) {
                        database.insertNewProfile(stringFirstName, stringLastName, genderId, stringDateOfBirth, stringHeight, stringWeight, stringPhotoRes);
                    } else if (cursor.getCount() == 1) {
                        database.updateProfile(1, stringFirstName, stringLastName, genderId, stringDateOfBirth, stringHeight, stringWeight, stringPhotoRes);
                    }
                    cursor.close();
                    database.close();
                    Toast.makeText(getActivity(), "Profile saved", Toast.LENGTH_LONG).show();
                    Home.setBooleanSwitchPossible();
                } else {
                    Toast.makeText(getActivity(), "Please fill in all Fields with '*'",
                            Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void setDate() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        try {
            textViewDateOfBirth.setText(sdf.format(calenderProfile.getTime()));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void openAlbum(View v) {
        // Intent to open Album on Device
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        // Ask Permission
        if (EasyPermissions.hasPermissions(Objects.requireNonNull(getActivity()), galleryPermissions)) {
            Uri data = Uri.parse(pictureDirectoryPath);
            // * = png || gif || jpg || jpeg || bmp
            photoPickerIntent.setDataAndType(data, "image/*");
            startActivityForResult(photoPickerIntent, REQUEST_CODE);
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    REQUEST_CODE, galleryPermissions);
            if (EasyPermissions.hasPermissions(getActivity(), galleryPermissions)) {
                Uri data = Uri.parse(pictureDirectoryPath);
                //* = png, gif, jpg, jpeg, bmp
                photoPickerIntent.setDataAndType(data, "image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                uriProfileImage = data.getData();
                Glide.with(this).load(uriProfileImage).crossFade().
                        diskCacheStrategy(DiskCacheStrategy.ALL).into(imgViewProfilePic);
            }
        }
    }

}
