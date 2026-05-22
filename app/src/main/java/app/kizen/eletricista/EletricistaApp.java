package app.kizen.eletricista;

import android.app.Application;
import com.google.android.material.color.DynamicColors;
import com.google.firebase.FirebaseApp;

/**
 * Application base para aplicar Dynamic Colors (Material You) quando disponível (Android 12+).
 */
public class EletricistaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
        FirebaseApp.initializeApp(this);
    }
}
