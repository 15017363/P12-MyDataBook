package com.example.a15017363.p12_mydatabook;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class BioFragment extends Fragment {

    Button btnEdit;
    TextView tvBio;
    FirebaseDatabase database;
    DatabaseReference myRef;

    public BioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.biofragment, container, false);

        btnEdit = (Button)view.findViewById(R.id.btnFragBio);
        tvBio = (TextView)view.findViewById(R.id.textViewBio);

        myRef = FirebaseDatabase.getInstance().getReference().child("Bio");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                tvBio.setText(val);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View mView = inflater.inflate(R.layout.edit_bio, null);

                builder.setTitle("Edit Bio")
                        // Set text for the positive button and the corresponding
                        //  OnClickListener when it is clicked
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EditText etBio = (EditText)mView.findViewById(R.id.editTextBio);
                                String a = etBio.getText().toString();
                                myRef.setValue(a);
                            }
                        })
                        // Set text for the negative button and the corresponding
                        //  OnClickListener when it is clicked
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getContext(), "You clicked no", Toast.LENGTH_LONG).show();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.setView(mView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        return view;
    }

}
