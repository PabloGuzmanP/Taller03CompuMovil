package com.example.firebase;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference usuariosRef;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextIdentificacion;
    private EditText editTextLatitud;
    private EditText editTextLongitud;
    private Button buttonRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Obtiene una instancia de la base de datos
        database = FirebaseDatabase.getInstance();
// Obtiene una referencia a la ubicación "usuarios" en la base de datos
        usuariosRef = database.getReference("usuarios");

// Inicializa los campos de entrada y el botón
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextIdentificacion = findViewById(R.id.editTextIdentificacion);
        editTextLatitud = findViewById(R.id.editTextLatitud);
        editTextLongitud = findViewById(R.id.editTextLongitud);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);

// Establece el evento de clic para el botón de registro
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }
    private void registrarUsuario() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        // Obtiene los valores ingresados por el usuario
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String identificacion = editTextIdentificacion.getText().toString();
        String latitud = editTextLatitud.getText().toString();
        String longitud = editTextLongitud.getText().toString();

        // Crea un objeto para almacenar los datos del usuario
        Usuario usuario = new Usuario(nombre, apellido, email, password, identificacion, latitud, longitud);

        // Guarda el objeto de usuario en la base de datos
        usuariosRef.push().setValue(usuario);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        // Puedes guardar información adicional sobre el usuario en Firebase Firestore o Realtime Database si lo deseas.
                    } else {
                        // Hubo un error en el registro.
                        Toast.makeText(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        // Limpia los campos de entrada después de guardar los datos
        limpiarCampos();
    }

    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextApellido.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextIdentificacion.setText("");
        editTextLatitud.setText("");
        editTextLongitud.setText("");
    }
}
