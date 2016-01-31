package com.owner.janetgaedke.jgaedkelab12_1;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.GpsStatus;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements ContactFragment.OnContactSelectedListener,
        ContactListFragment.OnContactListSelectedListener {
    Toolbar mToolBar;
    private static final String TAG = "jgaedke";
    public static final String ITEM = "item";
    private static final String BUNDLE_MAIN = "com.example.owner.janetgaedke.jgaedke_lab12_1.MAIN_BUNDLE";
    public static final String ARRAY = "com.example.owner.janetgaedke.jgaedke_lab12_1.MAIN";
    final static String ARG_POSITION = "com.example.owner.janetgaedke.jgaedke_lab12_1.position";

    final static String UPDATE_ARRAY = "com.example.owner.janetgaedke.jgaedke_lab12_1.updateARRAY";

    public static final String ARRAY_LIST = "com.example.owner.janetgaedke.jgaedke_lab12_1.ARRAY";
    private boolean isPhone;
    private TextView mCallbackText;
    private Contact mContact;
    protected ArrayList<Contact> mContactArrayList;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar) findViewById(R.id.myToolBar);
        setUpToolbar();

       // mContactArrayList = new ArrayList<Contact>();
       // Log.println(Log.DEBUG,TAG,"created main arraylist");

        Log.println(Log.DEBUG, TAG, "in main on create");

        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }
        if (findViewById(R.id.fragment_container   ) != null) {
            isPhone = true;
            Log.println(Log.DEBUG,TAG,"creating list fragment view");
            createContactListFragment();
        } else {
            isPhone = false;

        }
    }

    private void createContactListFragment() {
        ContactListFragment firstFragment = new ContactListFragment();
        Log.println(Log.DEBUG,TAG,"mcntact crlf: " + mContactArrayList);
        if (mContactArrayList == null) {
            mContactArrayList = new ArrayList<>();
            Log.println(Log.DEBUG,TAG,"it was null, create new list");
        }
        Bundle args = new Bundle();
        Log.println(Log.DEBUG, TAG, "creating list fragment, mcontactarray: " + mContactArrayList);
        args.putParcelableArrayList(ARRAY_LIST, mContactArrayList);
        firstFragment.setArguments(args);

        ///add the fragment to the fragment container framelayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstFragment)
                .commit();
    }

    protected void setUpToolbar() {

        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
           // mToolBar.setSubtitle("Contact List");
            mToolBar.setLogo(R.mipmap.ic_launcher);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.println(Log.DEBUG, TAG, "on item clicked in main");
        int id = item.getItemId();
Log.println(Log.DEBUG,TAG,"id: " + id);
        switch (item.getItemId()) {
            case R.id.help:
                Toast.makeText(getApplicationContext(), "Help is coming soon.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add_contact:
                Boolean addingContact = true;
                displayContactInfo(id, addingContact);  //create contact fragment
                return true;
            default:
                Log.println(Log.DEBUG, TAG, "home?? ");
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ARRAY_LIST, mContactArrayList);
                ContactListFragment firstFragment = new ContactListFragment();
                firstFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, firstFragment)
                        .addToBackStack(null);

                fragmentTransaction.commit();
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void addContact(Boolean addContact) {
        Boolean addingContact = false;
        if (addContact) {
            addingContact = true;
        }
      displayContactInfo(0, addingContact);
    }

    @Override
    public void addContactCompleted() {
        //get the updated array back from add/update fragment
        Bundle bundle = new Bundle();
        mContactArrayList = bundle.getParcelableArrayList("com.example.owner.janetgaedke.jgaedke_lab12_1.MAIN");
        Log.println(Log.DEBUG,TAG, "addContactCompleted, array is now: " + mContactArrayList);
        //getFragmentManager().popBackStack();
        ContactListFragment firstFragment = new ContactListFragment();
        firstFragment.setArguments(bundle);
        // firstFragment.setArguments(getIntent().getExtras());

        ///add the fragment to the fragment container framelayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstFragment)
                .commit();
        Log.println(Log.DEBUG, TAG, "addContactCompleted marray: " + mContactArrayList);
       // createContactListFragment();
       // firstFragment.refreshList();
    }

    /**
     * Display contact info creates intent and starts second fragment
     */
    //call second fragment
    public void displayContactInfo(int position, Boolean addingContact) {

        Log.println(Log.DEBUG, TAG, "displayContact");
        //get record from contact list fragment
        Bundle args = new Bundle();
        if (mContactArrayList != null) {
            if (!addingContact) {
               // mContact = mContactArrayList.get(position);
                args.putInt(ARG_POSITION, position);
             //   Log.println(Log.DEBUG, TAG, "received from list in main: " + mContact);
            }
        } else {
            mContactArrayList = new ArrayList<>();
        }
        //create new contact fragment
        ContactFragment secondFragment = new ContactFragment();
        args.putParcelableArrayList(UPDATE_ARRAY, mContactArrayList);
        Log.println(Log.DEBUG, TAG, "current record: " + position);
       //check to see which fragment is up

        //ContactFragment contactFragment = (ContactFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_contact1);
        if (secondFragment != null) {
            //show this fragment
            Log.println(Log.DEBUG,TAG,"contact fragment is not null, mcontactarray is: " + mContactArrayList);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
 //           args.putParcelableArrayList(ARRAY_LIST, mContactArrayList);
//            secondFragment.passDataToFragment(mContactArrayList);
            secondFragment.setArguments(args);
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back

            fragmentTransaction.replace(R.id.fragment_container, secondFragment)
            .addToBackStack(null);
            fragmentTransaction.commit();
        }

       Log.println(Log.DEBUG, MainActivity.TAG, "calling second fragment");
    }

    @Override
    public void onArticleSelected(int position) {
        //from list get the position number

        Log.println(Log.DEBUG,TAG, "list on articles selected");
        //the user selected a contact to be updated
        // Capture the contact fragment from the activity layout
        // Create fragment and give it an argument for the selected contact

        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putParcelableArrayList(UPDATE_ARRAY, mContactArrayList);
        //mContact = mContactArrayList.get(position);
        Log.println(Log.DEBUG, TAG, "record in " + mContact.toString());
        Log.println(Log.DEBUG, TAG, "current record" + position);

        ContactFragment newFragment = new ContactFragment();
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        Log.println(Log.DEBUG,TAG,"is it returning here");
    }

    @Override
    public void onArticleSelected(ArrayList<Contact> contactArrayList) {
        Log.println(Log.DEBUG,TAG,"back in main on article selected for contact");
        //called from contact fragment

        ContactListFragment firstFragment = (ContactListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_item);
        if (firstFragment != null) {
            // If list fragment is available, we're in two-pane layout...
            Log.println(Log.DEBUG,TAG,"we're in tablet");
            firstFragment.updateListItems(mContactArrayList);
        } else {
            //get the updated array back from add/update fragment
            mContactArrayList = contactArrayList;
            Log.println(Log.DEBUG, TAG, "addContactCompleted marray: " + mContactArrayList);

            //reset items in the action bar
            mToolBar.setSubtitle("");

            //go back to start in first fragment
            // firstFragment.refreshList(mContactArrayList);
            //  mToolBar.setHomeButtonEnabled(false);
            //  mToolBar.setDisplayHomeAsUpEnabled(false);

            ///add the fragment to the fragment container framelayout
            Bundle bundle = new Bundle();
            Log.println(Log.DEBUG,TAG,"assigned ARRAYLIST");
            ContactListFragment newFragment = new ContactListFragment();
            bundle.putParcelableArrayList(ARRAY_LIST, mContactArrayList);
            getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            newFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, newFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onAddUpdate(ArrayList<Contact> contactArrayList) {
        //when finished add or update in contact fragment this method is called.
        //assign updated arraylist to main variable
        Bundle args = new Bundle();
        mContactArrayList = args.getParcelableArrayList("com.example.owner.janetgaedke.jgaedke_lab12_1.updateARRAY");
        Log.println(Log.DEBUG,TAG,"back in main array now" + mContactArrayList.size());
    }

    public void passDataToActivity(ArrayList<Contact> tempArray) {
        mContactArrayList = tempArray;
        Log.println(Log.DEBUG,TAG,"updated main list mcontains: " + mContactArrayList.size());
    }

    public ArrayList<Contact> getDataFromActivity() {
        return mContactArrayList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.println(Log.DEBUG,TAG,"main on resume");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mContactArrayList = savedInstanceState.getParcelableArrayList("com.example.owner.janetgaedke.jgaedke_lab12_1.ARRAY");
        }
        Log.println(Log.DEBUG, TAG, "onRestore ");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARRAY, mContactArrayList);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.println(Log.DEBUG,TAG,"main on post resume");

        //mView = inflater.inflate(R.layout.contact_detail, container,false);
    }

}
