package com.ajsb.tdlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajsb.tdlist.modelo.Tarea;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareasViewHolder>
{
    private Context context ;
    private List<Tarea> lista ;

    /**
     * @param context
     * @param lista
     */
    public TareasAdapter(Context context, List<Tarea> lista)
    {
        this.context = context ;
        this.lista   = lista ;
    }

    /**
     * Por cada elemento de la lista, se lanza este método para crear el contenedor, que es
     * el layout donde se mostrará (más adelante) la información de dicho elemento de la lista.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public TareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).
                                   inflate(R.layout.item_tarea_layout, parent, false) ;
        //
        return new TareasViewHolder(view) ;
    }

    /**
     * Por cada elemento creado con el método anterior, el adaptador vincula el valor
     * del elemento de la lista con el contenedor creado.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull TareasViewHolder holder, int position)
    {
        holder.bindHolder(lista.get(position)) ;
    }

    /**
     * Devuelve el número de elementos que contiene la lista
     * @return
     */
    @Override
    public int getItemCount()
    {
        return lista.size() ;
    }

    /**
     * Definimos la clase que utilizará el patrón ViewHolder
     */
    class TareasViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView completo ;
        private TextView tarea ;

        /**
         * @param itemView
         */
        public TareasViewHolder(@NonNull View itemView)
        {
            super(itemView) ;

            completo = itemView.findViewById(R.id.completo) ;
            tarea    = itemView.findViewById(R.id.tarea) ;
        }

        /**
         * @param item
         */
        public void bindHolder(Tarea item)
        {
            // mostramos el texto de la tarea
            tarea.setText(item.getTarea()) ;

            // indicamos si está completa o no
            if (item.getCompleta()) completo.setImageResource(R.drawable.ic_completa) ;
            else                    completo.setImageResource(R.drawable.ic_incompleta) ;
        }
    }
}
