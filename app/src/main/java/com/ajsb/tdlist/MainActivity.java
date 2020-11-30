package com.ajsb.tdlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ajsb.tdlist.modelo.Tarea;
import com.ajsb.tdlist.modelo.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
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

                // vaciamos la lista original de tareas (la que pasamos al adaptador)
                // para que no se duplique la información
                tareas.clear() ;

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
                      Log.i("TAREA", "cargando tarea: " + idt) ;

                      int i ;

                      // buscamos en tareas si la tarea con idt existe
                      for (i=0; i < tareas.size(); i++)
                          if (tareas.get(i).getId()==idt) break ;

                      if (i >= tareas.size())
                      {
                          tareas.add(snapshot.getValue(Tarea.class)) ;
                          tareas.get(tareas.size()-1).setId(idt) ;
                          adapter.notifyDataSetChanged() ;
                      } else {
                          tareas.get(i).setTarea(snapshot.child("tarea").getValue().toString()) ;
                          tareas.get(i).setLista(Integer.parseInt(snapshot.child("lista").getValue().toString())) ;
                          tareas.get(i).setCompleta((Boolean) snapshot.child("completa").getValue());
                          adapter.notifyItemChanged(i) ;
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error)
                  {
                      Log.i("ERROR", error.getMessage()) ;
                  }
              }) ;
    }

    /**
     */
    @Override
    public void onBackPressed() { }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu) ;
        return super.onCreateOptionsMenu(menu) ;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.mnuPerfil:
                    Snackbar.make(rv, "Has pulsado en la opción PERFIL",Snackbar.LENGTH_LONG).show() ;
                    break ;
            case R.id.mnuSalir:
                    MaterialAlertDialogBuilder madb ;
                    madb = new MaterialAlertDialogBuilder(this) ;
                    madb.setTitle(R.string.app_name)
                        .setMessage(R.string.msg_salir)
                        .setPositiveButton("SI", (dialog, which) ->
                        {
                            fauth.signOut() ;   // Deslogueamos
                            finish() ;

                            return ;
                        })
                        .setNegativeButton("NO", (dialog, which) -> { })
                        .create()
                        .show();
                    break ;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true ;

    }
}