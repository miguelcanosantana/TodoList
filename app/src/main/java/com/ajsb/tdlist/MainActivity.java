package com.ajsb.tdlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{

    private FirebaseAuth fauth ;
    private FirebaseDatabase fdbase ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtenemos la instancia de FirebaseAuth y FirebaseDatabase
        fauth  = FirebaseAuth.getInstance() ;
        fdbase = FirebaseDatabase.getInstance() ;

        // Obtenemos el UID de nuestro usuario
        String uid = fauth.getUid() ;

        //
        DatabaseReference ref = fdbase.getReference("usuarios/"+uid) ;
        Log.i("REF", ref.toString()) ;


    }
}