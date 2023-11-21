package com.example.myhelp.ui.home;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myhelp.R;
import com.example.myhelp.databinding.FragmentHomeBinding;
import com.example.myhelp.ui.Adminsql;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private EditText codetxt;
    private EditText desctxt;
    private EditText pricetxt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        codetxt=(EditText)root.findViewById(R.id.code_in);
        desctxt=(EditText)root.findViewById(R.id.des_in);
        pricetxt=(EditText)root.findViewById(R.id.price_in);
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar_producto(v);
            }
        });
        binding.serachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar_producto(v);
            }
        });
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar_producto(v);
            }
        });
        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificar_producto(v);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void registrar_producto(View v){// codigo para registrar un producto
        Adminsql admin = new Adminsql(getContext(),"administracion",null,1);
        SQLiteDatabase bd =admin.getWritableDatabase();
        String codigo_producto=codetxt.getText().toString();
        String des_producto=desctxt.getText().toString();
        String precio_producto=pricetxt.getText().toString();
        if (!codigo_producto.isEmpty() && !des_producto.isEmpty() && !precio_producto.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo_producto);
            registro.put("des",des_producto);
            registro.put("precio",precio_producto);
            bd.insert("productos",null,registro);
            bd.close();
            codetxt.setText("");
            desctxt.setText("");
            pricetxt.setText("");
            Toast.makeText(getContext(),"exito",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),"fallo",Toast.LENGTH_LONG).show();
        }

    }
    public void buscar_producto(View view){// codigo para buscar un producto
        Adminsql admin = new Adminsql(getContext(),"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String codigo_producto=codetxt.getText().toString();
        if (!codigo_producto.isEmpty()){
            Cursor datasraw=db.rawQuery("Select des,precio from productos where codigo= "+codigo_producto,null);
            if (datasraw.moveToFirst()){
                desctxt.setText(datasraw.getString(0));
                pricetxt.setText(datasraw.getString(1));
            }
        }else{
            Toast.makeText(getContext(),"error",Toast.LENGTH_LONG);
        }
        db.close();
    }
    public void eliminar_producto(View view){// codigo para eliminar un producto
        Adminsql adminsql=new Adminsql(getContext(),"administracion",null,1);//creamos una instancia de la clase Adminsql
        SQLiteDatabase db=adminsql.getWritableDatabase();
        String codigo_producto=codetxt.getText().toString();
        if (!codigo_producto.isEmpty()){
            int cant=db.delete("productos","codigo="+codigo_producto, null);//eliminamos el producto
            db.close();//cerramos la base de datos
            codetxt.setText("");
            desctxt.setText("");
            pricetxt.setText("");
            if (cant==1){
                Toast.makeText(getContext(),"eliminado",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(),"error 1",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getContext(),"error 2",Toast.LENGTH_LONG).show();
        }
        db.close();
    }
    public void modificar_producto(View view){// codigo para modificar un producto
        Adminsql adminsql=new Adminsql(getContext(),"administracion",null,1);//creamos una instancia de la clase Adminsql
        SQLiteDatabase db=adminsql.getWritableDatabase();//creamos una base de datos para escribir
        String codigo_producto=codetxt.getText().toString();
        String des_producto=desctxt.getText().toString();
        String precio_producto=pricetxt.getText().toString();
        if (!codigo_producto.isEmpty() && !des_producto.isEmpty() && !precio_producto.isEmpty()){//verificamos que los campos no esten vacios
            ContentValues registro = new ContentValues();//creamos un objeto de tipo contentvalues
            registro.put("codigo",codigo_producto);
            registro.put("des",des_producto);
            registro.put("precio",precio_producto);
            int cant=db.update("productos",registro,"codigo="+codigo_producto,null);
            db.close();
            codetxt.setText("");
            desctxt.setText("");
            pricetxt.setText("");
            if (cant==1){
                Toast.makeText(getContext(),"modificado",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(),"error 1",Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getContext(),"error 2",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

}