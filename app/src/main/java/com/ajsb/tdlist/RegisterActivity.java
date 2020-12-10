package com.ajsb.tdlist;

/**
 * Programación Multimedia y de Dispositivos Móviles
 * Antonio J.Sánchez
 * ToDoList
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ajsb.tdlist.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity
{
    private Spinner ciu ;
    private Button btnReg, btnCan ;
    private EditText ema, pass, nom, ape ;
    private ArrayAdapter<String> adaptador ;

    private FirebaseAuth fbauth ;
    private FirebaseDatabase fbdbase ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //
        ema  = findViewById(R.id.email) ;
        pass = findViewById(R.id.password) ;
        nom  = findViewById(R.id.nombre) ;
        ape = findViewById(R.id.apellidos) ;

        List<String> ciudades =  new ArrayList<String>()
        {{
            add("Málaga") ;
            add("Estepona") ;
            add("Marbella") ;
            add("Fuengirola") ;
            add("Córdoba") ;
            add("Cádiz") ;
            add("Jaen") ;
            add("Huelva") ;
            add("Barcelona") ;
            add("Almería") ;
            add("Granada") ;
            add("Sevilla") ;
        }} ;

        //
        adaptador = new ArrayAdapter(this,
                                        android.R.layout.simple_list_item_1, ciudades) ;
        ciu     = findViewById(R.id.spinner) ;
        ciu.setAdapter(adaptador) ;

        // Registro
        btnReg = findViewById(R.id.btnRegistro) ;
        btnReg.setOnClickListener(v ->
        {

            String email     = getString(ema) ;
            String password  = getString(pass) ;

            // Comprobar si todos los campos importantes están rellenos.
            if (email.isEmpty() || password.isEmpty())
            {
                Snackbar.make(v, R.string.err_registro, Snackbar.LENGTH_LONG).show() ;
                return ;
            }

            // REGISTRAR
            fbauth = FirebaseAuth.getInstance() ;
            fbauth.createUserWithEmailAndPassword(email, password)
                  .addOnCompleteListener(task ->
                  {
                      if (task.isSuccessful())
                      {
                          DatabaseReference ref ;

                          //
                          Usuario usr = new Usuario() ;
                          usr.setNombre(getString(nom)) ;
                          usr.setApellidos(getString(ape));
                          usr.setCiudad("Málaga");
                          usr.setTareas(new ArrayList<>());

                          // Cogemos el UID del usuario
                          String uid = fbauth.getUid() ;

                          // Guardar en la base de datos, la información
                          // del usuario.
                          fbdbase = FirebaseDatabase.getInstance() ;
                          ref = fbdbase.getReference("usuarios") ;
                          ref.child(uid)
                             .setValue(usr) ;
                          //
                          finish() ;
                          return ;
                      }
                  }) ;



        });


        // Cancelar
        btnCan = findViewById(R.id.btnCancelar) ;
        btnCan.setOnClickListener(v ->
        {
            finish() ;
            return ;
        });

    }

    /**
     * @param view
     * @return
     */
    private String getString(TextView view)
    {
        return view.getText().toString().trim() ;
    }
}