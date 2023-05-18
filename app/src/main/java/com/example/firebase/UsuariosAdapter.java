package com.example.firebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarios;

    public UsuariosAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.textViewNombreUsuario.setText(usuario.getNombre());
        holder.textViewCorreoUsuario.setText(usuario.getCorreo());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNombreUsuario;
        TextView textViewCorreoUsuario;

        UsuarioViewHolder(View itemView) {
            super(itemView);
            textViewNombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            textViewCorreoUsuario = itemView.findViewById(R.id.textViewCorreoUsuario);
        }
    }
}

