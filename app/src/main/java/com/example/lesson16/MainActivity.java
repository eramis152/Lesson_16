package com.example.lesson16;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private EditText editTextTitulo, editTextTexto;
    private Spinner spinnerComentarios;
    private TextView textViewComentario;
    private Button btnCrear, btnVer, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextTexto = findViewById(R.id.editTextTexto);
        spinnerComentarios = findViewById(R.id.spinnerComentarios);
        textViewComentario = findViewById(R.id.textViewComentario);
        btnCrear = findViewById(R.id.btnCrear);
        btnVer = findViewById(R.id.btnVer);
        btnEliminar = findViewById(R.id.btnEliminar);

        cargarComentarios();

        btnCrear.setOnClickListener(v -> {
            String titulo = editTextTitulo.getText().toString();
            String texto = editTextTexto.getText().toString();
            dbHelper.insertComentario(new Comentario(0, titulo, texto));
            cargarComentarios();
        });

        btnVer.setOnClickListener(v -> {
            Comentario comentarioSeleccionado = (Comentario) spinnerComentarios.getSelectedItem();
            if (comentarioSeleccionado != null) {
                textViewComentario.setText(comentarioSeleccionado.getTexto());
            }
        });

        btnEliminar.setOnClickListener(v -> {
            Comentario comentarioSeleccionado = (Comentario) spinnerComentarios.getSelectedItem();
            if (comentarioSeleccionado != null) {
                dbHelper.deleteComentario(comentarioSeleccionado.getId());
                cargarComentarios();
                textViewComentario.setText("");
            }
        });
    }

    private void cargarComentarios() {
        List<Comentario> comentarios = dbHelper.getAllComentarios();
        ArrayAdapter<Comentario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, comentarios);
        spinnerComentarios.setAdapter(adapter);
    }
}
