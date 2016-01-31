package com.owner.janetgaedke.jgaedkelab12_1;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Owner on 11/29/2015.
 */
public class ContactListFragment extends ListFragment  {
    OnContactListSelectedListener mCallBack;
    private ArrayList<Contact> mContactsArray;
    private TextView mContactTextView;
    private TextView mArrayTextView;
    private ListView mArrayListView;
    private static final String listPosition = "listPosition";
    private static final String TAG = "jgaedke";
    public static final String ARRAY_LIST = "com.example.owner.janetgaedke.jgaedke_lab12_1.ARRAY";
    private static final String KEY_INDEX = "index";
    Resources res;
    ObjectArrayAdapter mAdapter;
    private int mCurPosition = 0;

    //item constructor
    public ContactListFragment() {
       // mContactsArray = new ArrayList<Contact>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.println(Log.DEBUG, TAG, "in list frag on create");

        //Current position should survive screen rotations.
        if (savedInstanceState != null) {
            Log.println(Log.DEBUG,TAG,"saved instanCe state was NOT null");
            mCurPosition = savedInstanceState.getInt("listPosition");
            mContactsArray = savedInstanceState.getParcelableArrayList("com.example.owner.janetgaedke.jgaedke_lab12_1.ARRAY");
        } else {
           mContactsArray = new ArrayList<Contact>();
        }
        Log.println(Log.DEBUG, TAG, "array now" + mContactsArray);
      //  setHasOptionsMenu(true);

        //display the content
        res = getResources();
        setListAdapter(new ObjectArrayAdapter(getContext(), R.layout.detail_line, mContactsArray));
        //  mArrayListView.setAdapter(mAdapter);
        // setListAdapter(mAdapter);

        //View view = inflater.inflate(R.layout.fragment_item, container, false);

        //set reference to resource links
        //   mArrayListView = (ListView) getListView().findViewById(R.id.list_contact);
        //   mArrayTextView = (TextView) getView().findViewById(R.id.empty_tv);

        // Set the adapter
        //  mAdapter = new ObjectArrayAdapter(getActivity().getApplicationContext(), R.layout.detail_line,mContactsArray);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.println(Log.DEBUG, TAG, "in list frag on create view");
        Log.println(Log.DEBUG,TAG,"mcontact: " + mContactsArray);

        setListAdapter(new ObjectArrayAdapter(getActivity(), R.layout.detail_line, mContactsArray));

        return inflater.inflate(R.layout.fragment_item, container, false);
    }
    public void updateListItems(ArrayList arrayList) {
        mContactsArray = arrayList;
        Log.println(Log.DEBUG,TAG,"updated list items, array is now: " + mContactsArray);

        getListView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.println(Log.DEBUG, TAG, "in list frag on view created");

        //if arraylist is empty, set text
     //   setEmptyText(getResources().getString(R.string.no_contact));
        if (!mContactsArray.isEmpty()){
            mArrayListView = getListView();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle contactInfo = getArguments();
        Log.println(Log.DEBUG, TAG, "in list frag on start, bundle is: " + contactInfo);

        if (contactInfo.containsKey("com.example.owner.janetgaedke.jgaedke_lab12_1.ARRAY")) {
             mContactsArray = contactInfo.getParcelableArrayList("com.example.owner.janetgaedke.jgaedke_lab12_1.ARRAY");
            Log.println(Log.DEBUG,TAG,"list assigned mcontactsarray not null bundle, array now: " + mContactsArray);

            TextView mContact_tv = (TextView) getActivity().findViewById(R.id.tv_contactsAddedValue);
            if (mContactsArray != null) {
                mContact_tv.setText("" + mContactsArray.size());

                setListAdapter(new ObjectArrayAdapter(getActivity().getApplicationContext(), R.layout.detail_line, mContactsArray));
            }
        } else {
            Log.println(Log.DEBUG,TAG,"initialized mcontact array");
            mContactsArray = new ArrayList<>();
        }
        Log.println(Log.DEBUG,TAG,"list assigned mcontactsarray " + mContactsArray);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mCallBack.onArticleSelected(position);

        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }


    public void passDataToFragment(ArrayList<Contact> mArrayList) {
        mContactsArray = mArrayList;
    }

    public interface OnContactListSelectedListener {
        //called by ContactList fragment when a list item is selected
        public void onArticleSelected(int position);

        //called to add a contact on toolbar
        public void addContact(Boolean addingContact);

        //called when contact has been added
        public void addContactCompleted();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Log.println(Log.DEBUG, TAG, "on options in contact list");
//        int id = item.getItemId();
//        switch (item.getItemId()) {
//            case R.id.help:
//                Toast.makeText(getContext(), "Help is coming soon.", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.action_add_contact:
//                Boolean addContact = true;
//                mCallBack.addContact(addContact);
//                Log.println(Log.DEBUG, TAG, "add icon clicked in list fragment");
//                return true;
//            default:
                return super.onOptionsItemSelected(item);
//        }
    }

        public void onResume() {
        super.onResume();
            Bundle bundle = new Bundle();
            if (bundle != null) {
                mContactsArray = getArguments().getParcelableArrayList("com.example.owner.janetgaedke.jgaedke_lab12_1.ARRAY");
                Log.println(Log.DEBUG, TAG, "RESUME received data from main containing " + mContactsArray);
            }
            if (mContactsArray != null) {
                Log.println(Log.DEBUG,TAG,"list fragment on resume in list" + mContactsArray);
            } else {
                //  refreshList();
                Log.println(Log.DEBUG,TAG,"list fragment list is empty on resume");
        }
       // mArrayListView.setAdapter(mAdapter);
        //mArrayListView = getListView();
    }
    public void refreshList(ArrayList<Contact> contactsArray) {
        Log.println(Log.DEBUG, TAG, "list on refresh list");
        mContactsArray = new ArrayList<Contact>();
        mContactsArray = contactsArray;
        Bundle bundle = new Bundle();
     //   Fragment fragmentTransaction = getFragmentManager().findFragmentById(R.id.fragment_contact1);
        //mContactsArray = getArguments().getParcelableArrayList(MainActivity.ARRAY);
        Log.println(Log.DEBUG, TAG, "mcontact in list refresh " + mContactsArray);
//        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallBack = (OnContactListSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnContactListSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(listPosition, mCurPosition);
        outState.putParcelableArrayList(ARRAY_LIST, mContactsArray);
    }

    public Contact getRecord(int index) {
        Contact record = mContactsArray.get(index);
        Log.println(Log.DEBUG,TAG,"record is" + record);
        return record;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.println(Log.DEBUG,TAG,"on view state restores list");
        //Current position should survive screen rotations.
        if (savedInstanceState != null) {
                mCurPosition = savedInstanceState.getInt("listPosition");
                mContactsArray = savedInstanceState.getParcelableArrayList("com.example.owner.janetgaedke.jgaedke_lab12_1.ARRAY");
                Log.println(Log.DEBUG, TAG, "on view state restore is: " + mContactsArray);
        }
    }
}
