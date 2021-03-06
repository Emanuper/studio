package es.iessaladillo.pedrojoya.pr017;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

// Estiende de ArrayAdapter porque éste hereda de ListAdapter y además implementa la intefaz
// Filterable, como requiere el AutoCompleteTextView.
class ConceptosAdapter extends ArrayAdapter<Concepto> {

    private final ArrayList<Concepto> mConceptos;
    private final ArrayList<Concepto> mSugerencias;
    private final LayoutInflater mInflador;

    public ConceptosAdapter(Context contexto, ArrayList<Concepto> conceptos) {
        // Se llama al constructor del padre. El segundo parámetro no es
        // usado. El tercer parámetro corresponde al resId del TextView que
        // contendrá el texto.
        super(contexto, R.layout.activity_main_item, R.id.lblEnglish,
                conceptos);
        mInflador = LayoutInflater.from(contexto);
        // Se crea una copia del ArrayList de datos, ya que se verá afectado
        // por el filtro.
        //noinspection unchecked
        mConceptos = (ArrayList<Concepto>) conceptos.clone();
        // El ArrayList de sugerencias está inicialmente vacío.
        mSugerencias = new ArrayList<>();
    }

    // Retorna el objeto Filter que va a filtrar el adaptador.
    @NonNull
    @Override
    public Filter getFilter() {
        // Se retorna un nuevo filtro subclase de Filter (inline).
        return new Filter() {
            // Se ejecuta cuando se debe filtrar. Recibe la cadena ya
            // introducida.
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // Se crea el objeto resultado del filtro, inicialmente
                // vacío.
                FilterResults filterResults = new FilterResults();
                // Si ya se ha introducido texto.
                if (constraint != null) {
                    // Se vacía el ArrayList de sugerencias.
                    mSugerencias.clear();
                    // Se comprueba concepto a concepto para incluirlo o no
                    // en las sugerencias.
                    // OJO se comprueba en el ArrayList con TODOS los
                    // conceptos.
                    for (Concepto concepto : mConceptos) {
                        // Si comienza igual se añade a las sugerencias.
                        if (concepto
                                .getEnglish()
                                .toLowerCase(Locale.getDefault())
                                .contains(
                                        constraint.toString().toLowerCase(
                                                Locale.getDefault()))) {
                            mSugerencias.add(concepto);
                        }
                    }
                    // Se establecen las sugerencias como resultado del
                    // filtro.
                    filterResults.values = mSugerencias;
                    filterResults.count = mSugerencias.size();
                }
                // Se retorna el resultado del filtrado.
                return filterResults;
            }

            // Se encarga de publicar el resultado seleccionado en el
            // widget.
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                // Si hay sugerencias se añaden los resultados filtrados
                // como únicos datos del adaptador.
                if (results != null && results.count > 0) {
                    clear();
                    //noinspection unchecked
                    for (Concepto concepto : (ArrayList<Concepto>) results.values) {
                        add(concepto);
                    }
                    // Se fuerza el repintado.
                    notifyDataSetChanged();
                }
            }

            // Retorna la cadena que debe escribirse en el widget. Recibe el
            // objeto seleccionado
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Se obtiene el concepto y se retorna la cadena.
                Concepto concepto = (Concepto) resultValue;
                return concepto.getEnglish();
            }

        };
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        // Si no se puede reciclar.
        if (convertView == null) {
            convertView = mInflador.inflate(R.layout.activity_main_item, parent, false);
            // Se crea el contenedor de vistas para la vista-fila.
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }

    // Cuando se deben escribir los datos en la vista del elemento.
    private void onBindViewHolder(ViewHolder holder, int position) {
        // Se escriben los datos en las vistas.
        // OJO! usar getItem del Adaptador para obtener el album
        // correspondiente ya que puede que del array de datos original
        // algunos hayan sido filtrados, por lo que sólo debemos tener en
        // cuenta los incluidos en el adaptador.
        holder.bind(getItem(position));
    }

    public static class ViewHolder {

        private final ImageView imgFoto;
        private final TextView lblEnglish;

        public ViewHolder(View itemView) {
            imgFoto = (ImageView) itemView
                    .findViewById(R.id.imgFoto);
            lblEnglish = (TextView) itemView
                    .findViewById(R.id.lblEnglish);
        }

        void bind(Concepto concepto) {
            imgFoto.setImageResource(concepto.getFotoResId());
            lblEnglish.setText(concepto.getEnglish());
        }

    }

}