package com.jetbrains.kmpapp

import android.app.Application
import com.jetbrains.kmpapp.di.initKoin

/**
 * PASO 11: CLASE APPLICATION ANDROID
 *
 * RickAndMortyApp es la clase Application personalizada para Android
 * Se crea ANTES que cualquier Activity cuando la app inicia
 * Es el lugar perfecto para inicializar librerías globales como Koin
 *
 * IMPORTANTE: Esta clase debe estar registrada en AndroidManifest.xml
 * con android:name=".RickAndMortyApp"
 *
 * Conceptos clave:
 * - Application: Clase base que mantiene estado global de la aplicación
 * - Singleton: Solo existe una instancia durante toda la vida de la app
 * - Inicialización: Aquí inicializamos Koin para inyección de dependencias
 */
class RickAndMortyApp : Application() {
    /**
     * Método llamado cuando la aplicación es creada
     * Se ejecuta antes que cualquier Activity o componente
     */
    override fun onCreate() {
        super.onCreate()

        // Inicializar Koin para inyección de dependencias
        // Esto configura todos nuestros módulos (dataModule, viewModelModule)
        initKoin()
    }
}
