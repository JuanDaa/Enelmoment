package com.ingoskr.eminen.enelmoment.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.ingoskr.eminen.enelmoment.ui.activitys.MenuPrincipal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Fragmento con un diálogo personalizado
 */
public class LoginDialog extends DialogFragment {
    private static final String TAG = LoginDialog.class.getSimpleName();
    EditText usuario,contra;
    Button ingresar;
    boolean mostrar = false;
    AlertDialog.Builder builder;
    private static final String URL="http://108.59.1.32/~oscarito/enelmomento/consultar.php";
    RequestQueue requestQueue;
    StringRequest request;
    LinearLayout custoToast;
    ImageView imagenToast;
    TextView mensajeToast;
    public LoginDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createLoginDialogo();
    }

    /**
     * Crea un diálogo con personalizado para comportarse
     * como formulario de login
     *
     * @return Diálogo
     */
    public AlertDialog createLoginDialogo() {
        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.iniciar_sesion, null);

        builder.setView(v);
        requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
        usuario=(EditText)v.findViewById(R.id.usuarioIng);
        contra=(EditText)v.findViewById(R.id.passIng);
        ingresar=(Button)v.findViewById(R.id.iniciar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuario.getText().toString().equals("")&& contra.getText().toString().equals("")){
                    Snackbar.make(view,"Todos los campos son requeridos", Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).setAction("Listo",null).show();
                    ingreso();
                } else if (usuario.getText().toString().isEmpty() && contra.getText().toString() != "") {
                    usuario.setError("Debes agregar tu correo electronico", getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
                    usuario.requestFocus();
                }  else if (contra.getText().toString().isEmpty() && usuario.getText().toString() != "") {
                    contra.setError("Debes ingresa tu contraseña", getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
                    contra.requestFocus();
                } else {
                    if (esCorreo(usuario.getText().toString()) == false && esPassValida(contra.getText().toString()) == false) {
                        Snackbar.make(view,"Debes agregar correctamente tu nombre de usuario y contraseña", Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).setAction("Listo",null).show();
                    } else if (esCorreo(usuario.getText().toString()) == false) {
                        usuario.setError("Debes agregar un correo valido (@)", getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
                        usuario.requestFocus();
                    } else if (esPassValida(contra.getText().toString()) == false) {
                        contra.setError("La contraseña debe ser mayor a 4 caracteres", getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
                        contra.requestFocus();
                    } else {
                        ingreso();
                    }

                }


            }
        });
        return builder.create();
    }

    private boolean esPassValida(String s) {
        return s.length() > 4;
    }

    private boolean esCorreo(String correo) {
        return correo.contains("@");
    }

    public boolean ingreso() {
        final Toast toast = new Toast(getContext());
        final View toast_layout = getActivity().getLayoutInflater().inflate(R.layout.toast_informacion, (ViewGroup) getActivity().findViewById(R.id.toastCustom));
        custoToast = (LinearLayout) toast_layout.findViewById(R.id.toastCustom);
        imagenToast=(ImageView) toast_layout.findViewById(R.id.imagenToast);
        mensajeToast=(TextView)  toast_layout.findViewById(R.id.mensajeToast);
        //final ProgressDialog dialog = new ProgressDialog(getContext().getApplicationContext());
        //dialog.setMessage("Espere un mommento por favor");
        //dialog.setTitle("Verificando");
        //dialog.show();
        request= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.names().get(0).equals("correcto")){
                       // dialog.dismiss();
                        Toast.makeText(getContext().getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getContext().getApplicationContext(),MenuPrincipal.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        mostrar = true;
                    }
                    else{
                       // dialog.dismiss();
                        imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.prohibido));
                        custoToast.setBackgroundColor(getResources().getColor(R.color.toast_incorrecto));
                        mensajeToast.setText("El usuario y/o la contraseña no existen");
                        toast.setGravity(Gravity.CENTER,0,-150);
                        toast.setView(toast_layout);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                        ingreso();
                    }

                } catch (JSONException e) {
                    //dialog.dismiss();
                    imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.atencion));
                    custoToast.setBackgroundColor(getResources().getColor(R.color.toast_alerta));
                    mensajeToast.setText("A ocurrido un error, por favor intente de nuevo mas tarde");
                    toast.setGravity(Gravity.CENTER,0,-150);
                    toast.setView(toast_layout);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                    ingreso();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //dialog.dismiss();
                imagenToast.setImageDrawable(getResources().getDrawable(R.drawable.atencion));
                custoToast.setBackgroundColor(getResources().getColor(R.color.toast_alerta));
                mensajeToast.setText("El usuario y/o la contraseña no existen");
                toast.setGravity(Gravity.CENTER,0,-150);
                toast.setView(toast_layout);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                ingreso();

            }
        })
        {
            protected Map<String,String> getParams()throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<String,String>();
                hashMap.put("alias",usuario.getText().toString());
                hashMap.put("contra",contra.getText().toString());


                return hashMap;
            }
        };
        requestQueue.add(request);
        return mostrar;
    }


}

