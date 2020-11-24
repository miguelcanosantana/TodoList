package com.ajsb.tdlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ajsb.tdlist.modelo.Tarea;
import com.ajsb.tdlist.modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView rv ;
    private TareasAdapter adapter ;

    private Usuario usuario ;
    private List<Tarea> tareas = new ArrayList<>() ;

    private FirebaseAuth fauth ;
    private FirebaseDatabase fdbase ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos el objeto RecyclerView
        rv = findViewById(R.id.recyclerView) ;

        adapter = new TareasAdapter(this,tareas) ;
        rv.setLayoutManager(new LinearLayoutManager(this)) ;
        rv.setAdapter(adapter) ;

        // Obtenemos la instancia de FirebaseAuth y FirebaseDatabase
        fauth  = FirebaseAuth.getInstance() ;
        fdbase = FirebaseDatabase.getInstance() ;

        // Obtenemos el UID de nuestro usuario
        String uid = fauth.getUid() ;

        //Usuario.loadInfoUsuario(fdbase, uid);
        loadInfoUsuario(uid) ;

    }

    /**
     * @param uid
     */
    private void loadInfoUsuario(String uid)
    {
        fdbase.getReference("usuarios/"+uid)
              .addValueEventListener(new ValueEventListener()
        {
            /**
             * @param snapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // obtenemos el nodo usuario
                usuario = snapshot.getValue(Usuario.class) ;

                // obtenemos la lista de tareas del usuario
                List<Integer> tar = usuario.getTareas() ;
                              tar.clear() ;

                // lanzamos una petitición a la base de datos
                // por cada tarea.
                for (int i=0; i < tar.size(); i++)
                    loadInfoTarea(tar.get(i)) ;
            }

            /**
             * @param error
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.i("ERROR", error.getMessage()) ;
            }
        }) ;
    }

    /**
     */
    private void loadInfoTarea(Integer idt)
    {
        fdbase.getReference("tareas/" + idt)
              .addValueEventListener(new ValueEventListener()
              {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot)
                  {
                        tareas.add(snapshot.getValue(Tarea.class)) ;
                        adapter.notifyDataSetChanged() ;
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error)
                  {
                      Log.i("ERROR", error.getMessage()) ;
                  }
              }) ;
    }
}