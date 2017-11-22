package livroslembrete.com.br.livroslembrete;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.j256.ormlite.android.apptools.OpenHelperManager;


import livroslembrete.com.br.livroslembrete.models.Usuario;
import livroslembrete.com.br.livroslembrete.dao.DataBaseHelper;

public class Application extends android.app.Application {

    private static String URL = "http://10.100.5.146:8080/livroslembrete/api/";
    //private static String URL = "https://eventostest.herokuapp.com/rest/";
    private static Application instance = null;
    private Usuario usuario = null;
    private DataBaseHelper dataBaseHelper = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(getCallback());
    }

    @Override
    public void onTerminate() {
        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
        super.onTerminate();
    }

    public static Application getInstance() {
        return instance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DataBaseHelper getDataBaseHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(this, DataBaseHelper.class);
        }
        return dataBaseHelper;
    }

    public static String getURL() {
        return URL;
    }

    @NonNull
    private ActivityLifecycleCallbacks getCallback() {
        return new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                // new activity created; force its orientation to portrait
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        };
    }
}
