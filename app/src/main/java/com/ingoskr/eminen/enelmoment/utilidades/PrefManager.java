package com.ingoskr.eminen.enelmoment.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Bienvenida";

    private static final String MOSTRAR_INTRO = " mostrarIntro";
    private static final String MOSTRAR_SESION = " mostrarSesion";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void mostrarIntroduccion(boolean mostrarIntro) {
        editor.putBoolean(MOSTRAR_INTRO, mostrarIntro);
        editor.commit();
    }

    public boolean seMostraraIntro() {
        return pref.getBoolean(MOSTRAR_INTRO, true);
    }

    public void mostrarSesion(boolean mostrarSes) {
        editor.putBoolean(MOSTRAR_SESION, mostrarSes);
        editor.commit();
    }

    public boolean seMostraraSesion() {
        return pref.getBoolean(MOSTRAR_SESION, true);
    }
}
