package com.example.invetariozhetta;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class agregarProductoActivity extends AppCompatActivity {

    private EditText nombreProducto, precioProducto, cantidadProducto, precioProductoUnidad, precioProductoMayor;
    private Spinner spinnertipo;
    private int contador, cantidad;
    private String nombre, tipo, seleccion;
    private double  precio, precioUnidad, precioMayor;
    private ArrayList<Producto> productos= new ArrayList<Producto>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cargarProducto();
        nombreProducto= (EditText) findViewById(R.id.e_nombreProductos);
        cantidadProducto= (EditText) findViewById(R.id.e_cantidadProducto);
        precioProducto= (EditText) findViewById(R.id.e_precio);
        precioProductoUnidad= (EditText) findViewById(R.id.e_productoUnidad);
        precioProductoMayor= (EditText) findViewById(R.id.e_precioMayor);
        spinnertipo=(Spinner)findViewById(R.id.spinner);
        String[] opciones= {"limpieza", "comestible", "higiene personal", "bebidas"};
        ArrayAdapter<String> arreglo1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinnertipo.setAdapter(arreglo1);
    }
    public void agregar(View view){
        nombre=nombreProducto.getText().toString();
        precio= Double.parseDouble(precioProducto.getText().toString());
        cantidad= Integer.parseInt(cantidadProducto.getText().toString());
        precioUnidad= Double.parseDouble(precioProductoUnidad.getText().toString());
        precioMayor= Double.parseDouble(precioProductoMayor.getText().toString());
        seleccion = spinnertipo.getSelectedItem().toString();

        productos.add(contador,new Producto(nombre, seleccion, cantidad, precioUnidad, precioMayor));
        contador+=1;
        guardarProducto();
        Toast.makeText(this,"productoAgregado",Toast.LENGTH_SHORT).show();
        for (int i=0; i<=productos.size()-1;i++){
            Log.i("info", "nombre del producto es: " + productos.get(i).getNombre()+"unidades:"+productos.get(i).getCantidad());
        }
    }
    public void guardarProducto() {
        try {
            FileOutputStream fos = openFileOutput("productos.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(productos);
            oos.close();
        } catch (Exception e) {
        }
    }

    public void cargarProducto(){
        try {
            FileInputStream fin = openFileInput("productos.dat");
            ObjectInputStream oin = new ObjectInputStream(fin);
            productos =(ArrayList<Producto>) oin.readObject();
            oin.close();
            contador=productos.size()-1;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}