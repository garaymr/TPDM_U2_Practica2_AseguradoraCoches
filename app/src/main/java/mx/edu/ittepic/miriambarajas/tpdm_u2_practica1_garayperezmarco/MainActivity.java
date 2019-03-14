package mx.edu.ittepic.miriambarajas.tpdm_u2_practica1_garayperezmarco;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nombre, telefono, fecha, descripcion;
    Spinner seguro;
    Button insertar;
    AseguradoraBD basedatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basedatos = new AseguradoraBD(this, "Aseguradora", null, 1);

        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.tel);
        fecha = findViewById(R.id.fecha);
        descripcion = findViewById(R.id.desc);
        seguro = findViewById(R.id.tipo);
        insertar = findViewById(R.id.guardar);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingrese nombre", Toast.LENGTH_SHORT).show();;
                    return;
                }if (telefono.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingrese teléfono", Toast.LENGTH_LONG).show();
                    return;
                }if (descripcion.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Ingrese descripción", Toast.LENGTH_LONG).show();
                    return;
                }if (seguro.getSelectedItemPosition() == 0){
                    Toast.makeText(MainActivity. this, "Seleccione tipo de seguro", Toast.LENGTH_LONG).show();
                    return;
                }else{
                    insertarP();
                }
            }
        });

    }

    private void insertarP() {
        int s = seguro.getSelectedItemPosition();
        SQLiteDatabase insert = basedatos.getWritableDatabase();
        int tipo =0;
        String idseguro ="";
        switch (s){
            case 1:
                tipo = 1;
                idseguro = "segurocasa";
                break;
            case 2:
                tipo = 2;
                idseguro = "seguroauto";
                break;
            case 3:
                tipo = 3;
                idseguro = "seguromedico";
                break;
        }

        String consulta1 = "INSERT INTO PROPIETARIO VALUES ('"+telefono.getText().toString()+"','"+nombre.getText().toString()+"','"+
                fecha.getText().toString()+"')";
        String consulta2 = "INSERT INTO PERSONA VALUES ('"+idseguro+"','"+descripcion.getText().toString()+"','"+fecha.getText().toString()+"',"+tipo+",'"+telefono.getText().toString()+"')";

        insert.execSQL(consulta1);
        insert.execSQL(consulta2);
        insert.close();

        nombre.setText("");
        fecha.setText("");
        descripcion.setText("");
        telefono.setText("");

        Toast.makeText(this, "Se pudo insertar con éxito", Toast.LENGTH_LONG).show();

    }
}
