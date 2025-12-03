package com.example.act09_listview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactoActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etTelefono, etEmail, etDireccion, etFechaNacimiento, etNotas;
    private Button btnGuardar, btnCancelar, btnEliminar;
    private Titular contactoActual;

    private ImageView imgEmail, imgTelefono;

    private int posicion = -1;
    private boolean esNuevoContacto = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Referencias a los elementos de la interface
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);
        etDireccion = findViewById(R.id.etDireccion);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etNotas = findViewById(R.id.etNotas);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEliminar = findViewById(R.id.btnEliminar);
        imgEmail = findViewById(R.id.imgEmail);
        imgTelefono = findViewById(R.id.imgTelefono);

        // Verificar si estamos editando un contacto existente o es uno nuevo
        Intent intent = getIntent();
        if (intent.hasExtra("contacto")) {
            contactoActual = (Titular) intent.getSerializableExtra("contacto");
            posicion = intent.getIntExtra("posicion", -1);
            esNuevoContacto = false;
            cargarDatosContacto();
        } else {
            // Si es un nuevo contacto, ocultar el botón eliminar
            btnEliminar.setVisibility(View.INVISIBLE);
        }

        // Configurar listeners de los botones
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {guardarContacto();}
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {eliminarContacto();}
        });

        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                if (!email.isEmpty()) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(android.net.Uri.parse("mailto:" + email));
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(ContactoActivity.this, R.string.msg_email_vacio, Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefono = etTelefono.getText().toString().trim();
                if (!telefono.isEmpty()) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(android.net.Uri.parse("tel:" + telefono));
                    startActivity(dialIntent);
                } else {
                    Toast.makeText(ContactoActivity.this, R.string.msg_telefono_vacio, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void cargarDatosContacto() {
        if (contactoActual != null) {
            etNombre.setText(contactoActual.getNombre());
            etApellido.setText(contactoActual.getApellido());
            etTelefono.setText(contactoActual.getTelefono());
            etEmail.setText(contactoActual.getEmail());
            etDireccion.setText(contactoActual.getDireccion());
            etFechaNacimiento.setText(contactoActual.getFechaNacimiento());
            etNotas.setText(contactoActual.getNotas());
        }
    }

    private void guardarContacto() {
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String fechaNacimiento = etFechaNacimiento.getText().toString().trim();
        String notas = etNotas.getText().toString().trim();

        // Validar que al menos el nombre no esté vacío
        if (nombre.isEmpty()) {
            Toast.makeText(this, R.string.msg_nombre_obligatorio, Toast.LENGTH_SHORT).show();
            etNombre.requestFocus();
            return;
        }

        // Crear el contacto con los datos
        Titular nuevoContacto = new Titular(nombre, apellido, telefono, email, direccion, fechaNacimiento, notas);

        // Devolver el resultado a MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("contacto", nuevoContacto);
        resultIntent.putExtra("posicion", posicion);
        resultIntent.putExtra("accion", "guardar");
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void eliminarContacto() {
        // Devolver el resultado a MainActivity indicando que se debe eliminar
        Intent resultIntent = new Intent();
        resultIntent.putExtra("posicion", posicion);
        resultIntent.putExtra("accion", "eliminar");
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}

