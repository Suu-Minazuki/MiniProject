package com.example.miniproject.DAO;

import com.example.miniproject.Model.EventWithData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOEventWithData {

    private DatabaseReference databaseReference;

    public DAOEventWithData() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(EventWithData.class.getSimpleName());
    }

    public Task<Void> add(EventWithData eventWithData){

        return databaseReference.push().setValue(eventWithData);
    }

}
