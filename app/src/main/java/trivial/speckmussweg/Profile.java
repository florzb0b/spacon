package trivial.speckmussweg;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
    TextView textViewAge, textViewDateOfBirth, textViewGender;
    LinearLayout linearLayoutAge;
    ImageView imgViewBtnDeleteContent, imgViewProfilePic;
    Uri uriProfileImage;
    String stringPhotoRes, stringFirstName, stringLastName, stringWeight, stringHeight, stringDateOfBirth, stringGender;
    Boolean booleanPhotoSelected = false, booleanBackPressedCheck = false;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Enable Optionsmenu
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        viewMain = inflater.inflate(R.layout.fragment_profile, container, false);

        editTextFirstName = viewMain.findViewById(R.id.user_profile_content_firstname);
        editTextLastName = viewMain.findViewById(R.id.user_profile_content_lastname);
        editTextWeight = viewMain.findViewById(R.id.user_profile_content_weight);
        editTextHeight = viewMain.findViewById(R.id.user_profile_content_height);
        textViewAge = viewMain.findViewById(R.id.user_profile_content_age);
        textViewDateOfBirth = viewMain.findViewById(R.id.user_profile_content_dateofbirth);
        textViewGender = viewMain.findViewById(R.id.user_profile_content_gender);
        linearLayoutAge = viewMain.findViewById(R.id.user_profile_linearlayout_age);
        imgViewBtnDeleteContent = viewMain.findViewById(R.id.user_profile_button_deleteContent);
        imgViewProfilePic = viewMain.findViewById(R.id.user_profile_content_photo);

        database = new MyDatabase(getActivity());
        Cursor cursor = database.selectProfile(1);
        ;
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
            textViewAge.setText(getAge(Integer.parseInt(splittedDate[2]), Integer.parseInt(splittedDate[1]), Integer.parseInt(splittedDate[0])));
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
                textViewAge.setText("");
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
                textViewAge.setText(getAge(calenderProfile.get(Calendar.YEAR),
                        calenderProfile.get(Calendar.MONTH),
                        calenderProfile.get(Calendar.DAY_OF_MONTH)));
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

                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                builder.setTitle("I indentify myself as a");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.optionsmenu_profile_save:
                // All Textviews must have Content
                if (!(TextUtils.isEmpty(editTextFirstName.getText().toString())
                        || TextUtils.isEmpty(editTextLastName.getText().toString())
                        || TextUtils.isEmpty(editTextWeight.getText().toString())
                        || TextUtils.isEmpty(editTextHeight.getText().toString())
                        || TextUtils.isEmpty(textViewDateOfBirth.getText().toString())
                        || TextUtils.isEmpty(textViewGender.getText().toString()))) {
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
                    database.close();
                    Toast.makeText(getActivity(), "Profile saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Please fill in all Fields with '*'",
                            Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getAge(int year, int month, int day) {
        Calendar dateOfBirth = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dateOfBirth.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return String.valueOf((Integer) age);
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
    //TODO: fürs backPressed in Home, ist noch nicht gemacht.
    //Theoretisch dazu da, falls man ausversehen den zurückbutton drückt, dass es abgefangen wird mit nem Dialog
    public static Boolean checkContent() {
        //abfrage in datenbank ob die jetzigen zeichen der datenbank übereinstimmen
        return true;
    }

}

/*
boolean keypressCheck;

     //Backtaste gedrückt UND es wurde mind. ein Name eingeben, dann trotzdem speichern?
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!keypressCheck) {
                keypressCheck = !TextUtils.isEmpty(editTextNameChanging.getText().toString());
                //keypressCheck(backtaste gedrückt) wird nach 1ms wieder zurückgesetzt
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        keypressCheck = false;
                    }
                }, 1);
                db.getWritableDatabase();
                Cursor cursor = db.selectEintrag(id);
                Cursor cursorAlbum = db.selectAlbum(id);
                //Überprüfung ob User irgendeine Änderung vorgenommen hat
                //Keine Änderung
                if (TextUtils.equals(editTextNameChanging.getText().toString(), cursor.getString(1))
                        && TextUtils.equals(editTextOrtChanging.getText().toString(), cursor.getString(2))
                        && TextUtils.equals(editTextAdresseChanging.getText().toString(), cursor.getString(3))
                        && TextUtils.equals(editTextPLZChanging.getText().toString(), cursor.getString(4))
                        && TextUtils.equals(editTextLandChanging.getText().toString(), cursor.getString(5))
                        && TextUtils.equals(editTextNotizChanging.getText().toString(), cursor.getString(6))
                        && TextUtils.equals(textViewDatumStartChanging.getText().toString(), cursor.getString(7))
                        && TextUtils.equals(textViewDatumEndChanging.getText().toString(), cursor.getString(8))
                        && ratingBarChanging.getRating() == cursor.getFloat(9)
                        && imageUri.equals(Uri.parse(cursor.getString(10)))
                        && Integer.parseInt(textViewAlbumCountChanging.getText().toString()) == cursorAlbum.getCount()) {
                    db.close();
                    openNoChangesDialog();
                    //Änderung
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("Speichern?");
                    dialog.setMessage("Änderungen speichern??");

                    dialog.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveDataChanging();
                        }
                    });
                    dialog.setNegativeButton("Verwerfen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Changing.this, "Eintrag nicht geändert!",
                                    Toast.LENGTH_SHORT).show();
                            hideKeyboard(Changing.this);
                            threadAdding.start();
                        }
                    });
                    dialog.create();
                    dialog.show();
                }
                return true;
            }
        } else if (keypressCheck) {
            keypressCheck = false;
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }
 */


/*
        db.getWritableDatabase();
                Cursor cursor = db.selectEintrag(id);
                Cursor cursorAlbum = db.selectAlbum(id);
                //Überprüfung ob User irgendeine Änderung vorgenommen hat
                //Keine Änderung
                if (TextUtils.equals(editTextNameChanging.getText().toString(), cursor.getString(1))
                        && TextUtils.equals(editTextOrtChanging.getText().toString(), cursor.getString(2))
                        && TextUtils.equals(editTextAdresseChanging.getText().toString(), cursor.getString(3))
                        && TextUtils.equals(editTextPLZChanging.getText().toString(), cursor.getString(4))
                        && TextUtils.equals(editTextLandChanging.getText().toString(), cursor.getString(5))
                        && TextUtils.equals(editTextNotizChanging.getText().toString(), cursor.getString(6))
                        && TextUtils.equals(textViewDatumStartChanging.getText().toString(), cursor.getString(7))
                        && TextUtils.equals(textViewDatumEndChanging.getText().toString(), cursor.getString(8))
                        && ratingBarChanging.getRating() == cursor.getFloat(9)
                        && imageUri.equals(Uri.parse(cursor.getString(10)))
                        && Integer.parseInt(textViewAlbumCountChanging.getText().toString()) == cursorAlbum.getCount()) {
                    db.close();
                    openNoChangesDialog();
                    //Änderung vorgenommen
 */