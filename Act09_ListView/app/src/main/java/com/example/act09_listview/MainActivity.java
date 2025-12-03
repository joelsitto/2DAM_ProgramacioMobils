package com.example.act09_listview;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private static final int REQUEST_CODE_CONTACTO = 1;

	private ArrayList<Titular> listaContactos;
	private AdaptadorTitulares adaptador;
	private ListView lstOpciones;
	private Button btnAgregar;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



		// Aquí guardo los contactos
		listaContactos = new ArrayList<>();

		// Referencias a los elementos de la interface
		btnAgregar = findViewById(R.id.btnAgregar);
		lstOpciones = findViewById(R.id.lstOpcions);

		// Configurar el adaptador personalizado
		adaptador = new AdaptadorTitulares(this);
		lstOpciones.setAdapter(adaptador);

		// Configurar el listener del botón Añadir
		btnAgregar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Abrir ContactoActivity para crear un nuevo contacto
				Intent intent = new Intent(MainActivity.this, ContactoActivity.class);
				startActivityForResult(intent, REQUEST_CODE_CONTACTO);
			}
		});

		// Configurar el listener para hacer clic en un item de la lista
		lstOpciones.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Abrir ContactoActivity para editar el contacto seleccionado
				Intent intent = new Intent(MainActivity.this, ContactoActivity.class);
				intent.putExtra("contacto", listaContactos.get(position));
				intent.putExtra("posicion", position);
				startActivityForResult(intent, REQUEST_CODE_CONTACTO);
			}
		});
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_CONTACTO && resultCode == RESULT_OK && data != null) {
			String accion = data.getStringExtra("accion");
			int posicion = data.getIntExtra("posicion", -1);

			if ("eliminar".equals(accion) && posicion != -1) {
				// Eliminar contacto
				listaContactos.remove(posicion);
				adaptador.notifyDataSetChanged();
				Toast.makeText(this, R.string.msg_contacto_borrado, Toast.LENGTH_SHORT).show();
			} else {
				Titular contacto = (Titular) data.getSerializableExtra("contacto");
				if (contacto != null) {
					if (posicion == -1) {
						// Añadir nuevo contacto
						listaContactos.add(contacto);
						Toast.makeText(this, R.string.msg_contacto_agregado, Toast.LENGTH_SHORT).show();
					} else {
						// Actualizar contacto existente
						listaContactos.set(posicion, contacto);
						Toast.makeText(this, R.string.msg_contacto_actualizado, Toast.LENGTH_SHORT).show();
					}
					// Actualizar el ListView
					adaptador.notifyDataSetChanged();
				}
			}
		}
	}


    // Para borrar un conctacto
	private void borrarContacto(int position) {
		listaContactos.remove(position);
		adaptador.notifyDataSetChanged();
		Toast.makeText(this, R.string.msg_contacto_borrado, Toast.LENGTH_SHORT).show();
	}

    // Adapter personalizado

    class AdaptadorTitulares extends ArrayAdapter<Titular>  {

		Activity context;

		public AdaptadorTitulares(Activity context) {
			super(context, R.layout.listitem_titular, listaContactos);
			this.context = context;
		}

		// GetView se ejecuta por cada elemento del array de datos y lo que hace
		// es "inflar" el layout correspondiente y poner los datos en cada elemento

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		LayoutInflater inflater = context.getLayoutInflater();
    		View item = inflater.inflate(R.layout.listitem_titular, null);

    		// Referencias a los elementos del layout
    		TextView lblTitulo = item.findViewById(R.id.lblTitulo);
    		TextView lblSubTitulo = item.findViewById(R.id.lblSubtitulo);
			Button btnBorrar = item.findViewById(R.id.btnBorrar);

			// Obtener el contacto actual
			Titular contacto = listaContactos.get(position);

			// Establecer los datos
    		lblTitulo.setText(contacto.getNombreCompleto());
			lblSubTitulo.setText(contacto.getInfoResumida());

			// Configurar el botón de borrar
			btnBorrar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					borrarContacto(position);
				}
			});

    		return item;
    	}
    }
}

