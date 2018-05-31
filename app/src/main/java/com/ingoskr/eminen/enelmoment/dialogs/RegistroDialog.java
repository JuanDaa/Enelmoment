package com.ingoskr.eminen.enelmoment.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ingoskr.eminen.enelmoment.R;
import com.ingoskr.eminen.enelmoment.ui.activitys.Login;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

/**
 * Fragmento con un diálogo personalizado
 */
public class RegistroDialog extends DialogFragment {
    private static final String TAG = RegistroDialog.class.getSimpleName();


    EditText use, corr, con, repCon;
    Button Register;
    boolean mostrar=false;
    RequestQueue requestQueue;
    private static final String URL = "http://108.59.1.32/~oscarito/enelmomento/Insertar.php";
    StringRequest request;
    AlertDialog.Builder builder;
    LinearLayout custoToast;
    ImageView imagenToast;
    TextView mensajeToast;

    public RegistroDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createRegDialogo();
    }

    /**
     * Crea un diálogo con personalizado para comportarse
     * como formulario de login
     *
     * @return Diálogo
     */
    public AlertDialog createRegDialogo() {
         builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.registrarse, null);

        builder.setView(v);
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        use = (EditText) v.findViewById(R.id.nomUs);
        corr = (EditText) v.findViewById(R.id.correoUs);
        con = (EditText) v.findViewById(R.id.passUs);
        repCon = (EditText) v.findViewById(R.id.passUsRep);
        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        Register = (Button) v.findViewById(R.id.btnRegistrarse);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (use.getText().toString().isEmpty() && corr.getText().toString().isEmpty() && con.getText().toString().isEmpty() && repCon.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Todos los campos son requeridos", Snackbar.LENGTH_SHORT).setAction("Listo", null).show();
                } else if (use.getText().toString().isEmpty() || corr.getText().toString().isEmpty() || con.getText().toString().isEmpty() || repCon.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Todos los campos son requeridos", Snackbar.LENGTH_SHORT).setAction("Listo", null).show();

                } else {
                    if (validarNombre(use.getText().toString()) == false) {
                        use.setError("Nombre invalido");
                        use.requestFocus();
                    }


                    if (esCorreo(corr.getText().toString()) && esPassValida(con.getText().toString())) {

                        if (con.getText().toString().equals(repCon.getText().toString()) == false) {
                            Snackbar.make(view, "Las contraseñas no coinciden", Snackbar.LENGTH_LONG).setAction("Listo", null).show();
                            repCon.requestFocus();
                            repCon.setError("Las contraseñas no coinciden");

                        } else {
                            registrarse();
                        }

                    } else if (esCorreo(corr.getText().toString()) == true && esPassValida(con.getText().toString()) == false) {
                        con.setError("La contraseña debe ser mayor a 4 caracteres");
                        con.requestFocus();
                    } else if (esCorreo(corr.getText().toString()) == false && esPassValida(con.getText().toString()) == true) {
                        corr.setError("Debes agregar un correo valido (@)");
                        corr.requestFocus();
                    }

                    if (esPassValida(con.getText().toString())==false) {
                        con.setError("La contraseña debe ser mayor a 4 caracteres");
                        repCon.setError("Nombre invalido");
                        con.requestFocus();

                    }

                }


            }
        });
        return builder.create();
    }
    private void registrarse() {
        final Toast toast = new Toast(getContext());
        final View toast_layout = getActivity().getLayoutInflater().inflate(R.layout.toast_informacion, (ViewGroup) getActivity().findViewById(R.id.toastCustom));
        custoToast = (LinearLayout) toast_layout.findViewById(R.id.toastCustom);
        imagenToast=(ImageView) toast_layout.findViewById(R.id.imagenToast);
        mensajeToast=(TextView)  toast_layout.findViewById(R.id.mensajeToast);
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.completo));
                custoToast.setBackgroundColor(getResources().getColor(R.color.toast_correcto));
                mensajeToast.setText("Tus datos han sido guardados correctamente");
                toast.setGravity(Gravity.CENTER,0,-150);
                toast.setView(toast_layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

                createRegDialogo().dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.atencion));
                custoToast.setBackgroundColor(getResources().getColor(R.color.toast_alerta));
                mensajeToast.setText("A ocurrido un error inesperado por favor intentelo mas tarde");
                toast.setGravity(Gravity.CENTER,0,-150);
                toast.setView(toast_layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                createRegDialogo().dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("usuario", use.getText().toString());
                hashMap.put("contra", con.getText().toString());
                hashMap.put("email", corr.getText().toString());

                return hashMap;
            }

        };

        requestQueue.add(request);
    }

    private boolean esCorreo(String correo) {
        return correo.contains("@");
    }

    private boolean esPassValida(String s) {
        return s.length() > 4;
    }

    public void login(View view) {
        startActivity(new Intent(getContext().getApplicationContext(), Login.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    private boolean validarNombre(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        return patron.matcher(nombre).matches() || nombre.length() > 30;
    }

    public boolean accionMostrar() {
        return mostrar;
    }

}

