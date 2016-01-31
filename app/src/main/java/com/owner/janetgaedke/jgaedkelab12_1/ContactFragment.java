package com.owner.janetgaedke.jgaedkelab12_1;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Owner on 11/29/2015.
 */
public class ContactFragment extends Fragment {
    OnContactSelectedListener mCallback;
    final static String ARG_POSITION = "com.example.owner.janetgaedke.jgaedke_lab12_1.position";
    public static final String ARRAY = "com.example.owner.janetgaedke.jgaedke_lab12_1.MAIN";
    final static String UPDATE_ITEM = "update";  //variable to use for passing in item
    final static String UPDATE_ARRAY = "com.example.owner.janetgaedke.jgaedke_lab12_1.updateARRAY";
    final static String KEY_FIRST_NAME = "KEY_FIRST_NAME";
    final static String KEY_LAST_NAME = "KEY_LAST_NAME";
    final static String KEY_EMAIL = "KEY_EMAIL";
    final static String KEY_PHONE = "KEY_PHONE";


    public int mItemPosition = 0;  //position of the contact in the arraylist
    Button mAddButton;
    EditText mFirstName;
    EditText mLastName;
    EditText mEmail;
    EditText mPhone;
    Toolbar mAddToolBar;
    String firstName = "";
    String lastName = "";
    String email = "";
    String phone = "";
    private ArrayList<Contact> mTempArray;
    private static final String KEY_INDEX = "index";
    public static ArrayList<String> mContactString;
    private Contact mContact;
    boolean isValid;
    private static final String EXTRA_UPDATE_CONTACT_IS_TRUE = "com.example.owner.janetgaedke.jgaedke_lab7_1.update_contact";
    private boolean mUpdatingContact;
    Intent intent;
    private static final String TAG2 = "jgaedke";
    private View mContactView;
    MainActivity mainActivity;

    //item constructor
    public ContactFragment() {
        //mTempArray = new ArrayList<Contact>();
    }

   // @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         //super.onCreateView(inflater, container, savedInstanceState);

        //set up the toolbar
        setHasOptionsMenu(true);

        //set up the view container
        if (container == null) {
            return null;
        }

        mContactView = inflater.inflate(R.layout.contact_detail, container, false);

        //set references to items
        mFirstName = (EditText) mContactView.findViewById(R.id.et_firstName);
        mLastName = (EditText)mContactView.findViewById(R.id.et_lastName);
        mEmail = (EditText)mContactView.findViewById(R.id.et_email);
        mPhone = (EditText)mContactView.findViewById(R.id.et_phone);
        mAddButton = (Button)mContactView.findViewById(R.id.buttonAdd);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String returnString = "";
                CharSequence currentValue = mAddButton.getText();

                getInputData();
                if (isValid) {
                    Log.println(Log.DEBUG,TAG2,"it's valid");
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    addTheNewContact();
                    //return contact object
                    Bundle args = new Bundle();
                    args.putParcelableArrayList(ARRAY, mTempArray);
                    if (currentValue.equals("Add")) {
                        Log.println(Log.DEBUG, TAG2, "it's an add" + mContact);
                        mTempArray.add(mContact);
                        Log.println(Log.DEBUG,TAG2,"current size of temp" + mTempArray.size());
                    } else {
                        mTempArray.set(mItemPosition, mContact);
                        Log.println(Log.DEBUG,TAG2,"updating a records");
                    }
                }
                clearDataItems();  //clears the data fields display
                Log.println(Log.DEBUG, TAG2, "contact" + mTempArray);
                mCallback.onArticleSelected(mTempArray);
            }
        });

        return  mContactView;
    }
    public void passDataToFragment(ArrayList<Contact> mArrayList) {
        mTempArray = mArrayList;
        Log.println(Log.DEBUG,TAG2,"mtempsize 2nd received" + mTempArray.size());
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.println(Log.DEBUG, TAG2, "in on start contact frag, initialized mtemparray");
        //check if arguments passed to the fragment
        Bundle contactInfo = getArguments();

        if (contactInfo.containsKey("com.example.owner.janetgaedke.jgaedke_lab12_1.updateARRAY")) {
            Log.println(Log.DEBUG, TAG2, "on start, received bundle");
            mTempArray = contactInfo.getParcelableArrayList("com.example.owner.janetgaedke.jgaedke_lab12_1.updateARRAY"); //key used for contactFragment
        } else {
            mTempArray = new ArrayList<Contact>();

        }
        Log.println(Log.DEBUG,TAG2,"mtemp array on start: " + mTempArray);
        if (contactInfo.containsKey("com.example.owner.janetgaedke.jgaedke_lab12_1.position")) {
            //it's an update, position of contact in array
            mItemPosition = contactInfo.getInt("com.example.owner.janetgaedke.jgaedke_lab12_1.position");
            mAddButton.setText("Update");
            mUpdatingContact = true;

            //assign contact from bundle
            mContact = mTempArray.get(mItemPosition);
            Log.println(Log.DEBUG,TAG2,"contact is: " + mContact);

            if (!mTempArray.isEmpty()) {
                //Set the text of the fields
                loadValuesToFields(mContact);
            }
        } else {
            mAddButton.setText("Add");
            mUpdatingContact = false;
        }
    }

    private void addTheNewContact() {
        //create a new contact and set status
        mContact = new Contact(firstName, lastName, email, phone);
        Log.println(Log.DEBUG, TAG2, "mContact: " + lastName + firstName);
    }

    /**
     * Load values from contact object received into edit text fields.
     * @param data - A contact object.
     */
    private void loadValuesToFields(Contact data) {
        Log.println(Log.DEBUG, TAG2, "passed in: " + data);
        //get data fields from contact object and assign to editTextField
        mLastName.setText(data.getLastName());
        mFirstName.setText(data.getFirstName());
        mEmail.setText(data.getEmail());
        mPhone.setText(data.getPhone());
    }

    /**
     * Get input data method gets the items from the edit views, validates, and if errors,
     * displays a toast message for required first name and last name.  Sets isValid to true
     * if it passes all tests.
     */
    public void getInputData() {
        //get first name and validate
        firstName = mFirstName.getText().toString();

        if (firstName.equals("")) {
            Toast.makeText(getContext(), "First Name is required", Toast.LENGTH_SHORT).show();
            mFirstName.setError("First Name is required");
            isValid = false;
        }

        lastName = mLastName.getText().toString();
        if (lastName.equals("")) {
            Toast.makeText(getContext(), "Last Name is required!", Toast.LENGTH_SHORT).show();
            mLastName.setError("Last Name is required");
            isValid = false;
        }
        if ((!firstName.isEmpty()) && (!lastName.isEmpty())) {
            email = mEmail.getText().toString();
            phone = mPhone.getText().toString();
            isValid = true;
        }
    }

    //the container
    public interface OnContactSelectedListener {
        //called by contacts fragment when a list item is selected
        public void onArticleSelected(ArrayList<Contact> contactArrayList);

        //called by contact fragment when add or update completed
        public void onAddUpdate(ArrayList<Contact> contactArrayList);
    }

    /**
     * Clear data items method clears the editText fields.
     */
    public void clearDataItems() {
        mFirstName.setText(" ");
        mLastName.setText(" ");
        mEmail.setText(" ");
        mPhone.setText(" ");
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getSupportFragmentManager.getBackStackEntryCount > 1) {
            getSupportFragment.popBackStack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        firstName = mFirstName.getText().toString();
        Log.println(Log.DEBUG, TAG2, "saved: " + firstName);
        savedInstanceState.putString(KEY_FIRST_NAME, firstName);
        Log.println(Log.DEBUG, TAG2, "key first: " + "KEY_FIRST_NAME");
        lastName = mLastName.getText().toString();
        savedInstanceState.putString(KEY_LAST_NAME, lastName);
        email = mEmail.getText().toString();
        savedInstanceState.putString(KEY_EMAIL, email);
        phone = mPhone.getText().toString();
        savedInstanceState.putString(KEY_PHONE, phone);
        savedInstanceState.putInt(ARG_POSITION, mItemPosition);
        savedInstanceState.putParcelableArrayList(UPDATE_ARRAY, mTempArray);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            String firstSavedName = "";
            //  super.onRestoreInstanceState(savedInstanceState);
            firstSavedName = savedInstanceState.getString("KEY_FIRST_NAME");
            mFirstName.setText(firstSavedName);
            lastName = savedInstanceState.getString("KEY_LAST_NAME");
            mLastName.setText(lastName);
            email = savedInstanceState.getString("KEY_EMAIL");
            mEmail.setText(email);
            phone = savedInstanceState.getString("KEY_PHONE");
            mPhone.setText(phone);
            mItemPosition = savedInstanceState.getInt("com.example.owner.janetgaedke.jgaedke_lab12_1.position");
            mTempArray = savedInstanceState.getParcelableArrayList("com.example.owner.janetgaedke.jgaedke_lab12_1.updateARRAY");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
     //   Menu mMenu = menu;
     //   menu.findItem(R.id.action_add_contact).setVisible(false);

       // getActivity().getActionBar().setSubtitle(R.string.add_tb_title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(R.string.add_tb_title);
     //   ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        switch (item.getItemId()) {
            case 0:
                CharSequence currentValue = mAddButton.getText();
                if (currentValue.equals("Update")) {
                    //no update, just return the array
                    Bundle args = new Bundle();
                    //args.putBoolean(UPDATE_ITEM, mUpdatingContact);
                    args.putParcelableArrayList(ARRAY, mTempArray);
                    mAddButton.setText("Add");
                    Log.println(Log.DEBUG, TAG2, "onoptions in contact frag, added temparray");
                    getFragmentManager().popBackStack();
                    Log.println(Log.DEBUG, TAG2, "popped;");
                }
            default:
                return super.onOptionsItemSelected(item);
            //they clicked the back button, cancel transaction
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnContactSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnContactSelectedListener");
        }
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
  //      ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("");
       // ((AppCompatActivity)getActivity()).setSupportActionBar().setHomeButtonEnabled(false);
  //      ((AppCompatActivity)getActivity()).setSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }
}
