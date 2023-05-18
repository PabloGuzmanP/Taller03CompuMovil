package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private TextView textViewNombre;
    private TextView textViewApellido;
    private Button buttonActualizar;
    private Button buttonCerrarSesion;
    private Button buttonCambiarDisponibilidad;

    private DatabaseReference usuariosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Obtiene una referencia a la ubicación "usuarios" en la base de datos
        usuariosRef = FirebaseDatabase.getInstance().getReference("usuarios");

        textViewNombre = findViewById(R.id.textViewNombre);
        textViewApellido = findViewById(R.id.textViewApellido);
        buttonActualizar = findViewById(R.id.buttonActualizar);
        buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        buttonCambiarDisponibilidad = findViewById(R.id.buttonCambiarDisponibilidad);

        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateActivity();
            }
        });

        buttonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        buttonCambiarDisponibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarEstadoUsuariosDisponibles();
            }
        });

        // Obtener información del usuario actual
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference currentUserRef = usuariosRef.child(uid);
            currentUserRef.get().addOnSuccessListener(snapshot -> {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("nombre").getValue(String.class);
                    String apellido = snapshot.child("apellido").getValue(String.class);
                    textViewNombre.setText(nombre);
                    textViewApellido.setText(apellido);
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(MenuActivity.this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void openUpdateActivity() {
        Intent intent = new Intent(this, ModifyUserInfoActivity.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void cambiarEstadoUsuariosDisponibles() {
        usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DatabaseReference userRef = userSnapshot.getRef();
                    userRef.child("disponible").setValue(true);
                }
                Toast.makeText(MenuActivity.this, "Usuarios disponibles actualizados", Toast.LENGTH_SHORT).show();
                mostrarEstadoUsuariosDisponibles();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MenuActivity.this, "Error al actualizar usuarios disponibles", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mostrarEstadoUsuariosDisponibles() {
        usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Usuario> usuariosDisponibles = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = userSnapshot.getValue(Usuario.class);
                    if (usuario != null && usuario.isDisponible()) {
                        usuariosDisponibles.add(usuario);
                    }
                }

                // Configurar el RecyclerView
                RecyclerView recyclerView = findViewById(R.id.recyclerViewUsuarios);
                recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));

                // Crear y adjuntar el adaptador
                UsuariosAdapter adapter = new UsuariosAdapter(usuariosDisponibles);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MenuActivity.this, "Error al actualizar usuarios disponibles", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

