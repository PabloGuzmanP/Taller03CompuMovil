package com.example.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModifyUserInfoActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextApellido;
    private Button buttonGuardar;

    private DatabaseReference usuariosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        // Obtiene una referencia a la ubicación "usuarios" en la base de datos
        usuariosRef = FirebaseDatabase.getInstance().getReference("usuarios");

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarUsuario();
            }
        });
    }

    private void guardarUsuario() {
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference currentUserRef = usuariosRef.child(uid);
            currentUserRef.child("nombre").setValue(nombre);
            currentUserRef.child("apellido").setValue(apellido)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ModifyUserInfoActivity.this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                            finish(); // Finaliza la actividad actual y vuelve a la actividad anterior
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ModifyUserInfoActivity.this, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}