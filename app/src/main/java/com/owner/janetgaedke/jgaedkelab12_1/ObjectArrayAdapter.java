package com.owner.janetgaedke.jgaedkelab12_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ListAdapter;
import java.util.ArrayList;

/**
 * Created by Owner on 11/29/2015.
 */
public class ObjectArrayAdapter extends ArrayAdapter<Contact>
    implements ListAdapter {

    //declare ArrayList of contacts
    private ArrayList<Contact> objects;

    /**
     *  Override the constructor for ArrayAdapter
     *  The only variable we care about now ArrayList<PlatformVersion> objects
     *  it is the list of the objects we want to display
     *
     * @param context
     * @param resource
     * @param objects
     */
    public ObjectArrayAdapter(Context context, int resource, ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // assign the view we are converting to a local variable
        View view = convertView;

            /*
              Check to see if view null.  If so, we have to inflate the view
              "inflate" basically mean to render or show the view
             */

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.detail_line, null);
        }

            /*

              Recall that the variable position is sent in as an argument to this method
              The variable simply refers to the position of the current object on the list.
              The ArrayAdapter iterate through the list we sent it

              versionObject refers to the current Contact Object

             */
        Contact versionObject = objects.get(position);

        if (versionObject != null) {
            // obtain a reference to the widgets in the defined layout "wire up the widgets from detail_line"
            TextView mName = (TextView) view.findViewById(R.id.txtName);
            TextView mEmail = (TextView) view.findViewById(R.id.txtEmail);
            TextView mPhone = (TextView)view.findViewById(R.id.txtPhone);

            if (mName != null) {
                String mFullName = versionObject.getLastName()
                        + ", "
                        + versionObject.getFirstName();
                mName.setText(mFullName);
            }
            if (mEmail != null) {
                mEmail.setText(versionObject.getEmail());
            }
            if (mPhone != null) {
                mPhone.setText(versionObject.getPhone());
            }
        }

        // the view must be returned to our Activity
        return view;
    }
}
