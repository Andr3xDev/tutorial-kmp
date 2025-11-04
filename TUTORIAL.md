# Tutorial: App Rick and Morty con Kotlin Multiplatform

Este tutorial te guiará paso a paso para completar una aplicación móvil multiplataforma que consume la API pública de Rick and Morty.

## 📋 Prerequisitos

- Android Studio instalado (última versión estable)
- Conocimientos básicos de Kotlin
- Conocimientos básicos de Jetpack Compose

## 🎯 Objetivo

Completar una aplicación que:
1. Muestra una lista de personajes de Rick and Morty
2. Permite ver el detalle de cada personaje
3. Funciona en Android (y potencialmente iOS)

## 📂 Estructura del Proyecto

El proyecto usa **Kotlin Multiplatform** con las siguientes capas:

```
composeApp/src/commonMain/kotlin/
├── data/
│   ├── RickAndMortyApi.kt          # Llamadas HTTP (PARTE 2)
│   ├── RickAndMortyRepository.kt   # Lógica de negocio (PARTE 2)
│   └── Models.kt                   # Modelos de datos
├── screens/
│   ├── ListScreen.kt               # Pantalla de lista (PARTE 1)
│   └── DetailScreen.kt             # Pantalla de detalle (PARTE 1)
└── App.kt                          # Navegación principal
```

## 🚀 Parte 1: Implementar la UI (Interfaz de Usuario)

### 1.1 Pantalla de Lista (ListScreen.kt)

**Objetivo:** Mostrar los personajes en una cuadrícula de 2 columnas usando LazyVerticalGrid.

**Pasos:**

1. Abre el archivo `composeApp/src/commonMain/kotlin/com/jetbrains/kmpapp/screens/ListScreen.kt`

2. Localiza el comentario `// TODO PARTE 1: Implementa el LazyVerticalGrid`

3. Reemplaza el `Text("Lista vacía")` con:

```kotlin
LazyVerticalGrid(
    columns = GridCells.Fixed(2),
    contentPadding = PaddingValues(16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(characters) { character ->
        CharacterCard(
            character = character,
            onClick = { onCharacterClick(character.id) }
        )
    }
}
```

4. Localiza el composable `CharacterCard` (comentario `// TODO PARTE 1: Completa el Card del personaje`)

5. Dentro del `Column` del Card, reemplaza el `Text("Nombre aquí")` con:

```kotlin
Text(
    text = character.name,
    style = MaterialTheme.typography.titleMedium,
    fontWeight = FontWeight.Bold,
    maxLines = 2,
    overflow = TextOverflow.Ellipsis
)

Spacer(modifier = Modifier.height(4.dp))

Text(
    text = character.species,
    style = MaterialTheme.typography.bodySmall,
    color = MaterialTheme.colorScheme.onSurfaceVariant
)
```

**Resultado esperado:** Verás una cuadrícula vacía (sin personajes todavía).

---

### 1.2 Pantalla de Detalle (DetailScreen.kt)

**Objetivo:** Mostrar toda la información del personaje seleccionado.

**Pasos:**

1. Abre el archivo `composeApp/src/commonMain/kotlin/com/jetbrains/kmpapp/screens/DetailScreen.kt`

2. Localiza el comentario `// TODO PARTE 1: Completa el detalle del personaje`

3. Reemplaza el `Text("Detalle del personaje")` con el siguiente código dentro del `Column`:

```kotlin
// Imagen del personaje
AsyncImage(
    model = character.image,
    contentDescription = character.name,
    modifier = Modifier
        .fillMaxWidth()
        .height(300.dp),
    contentScale = ContentScale.Crop
)

Spacer(modifier = Modifier.height(16.dp))

// Nombre
Text(
    text = character.name,
    style = MaterialTheme.typography.headlineMedium,
    fontWeight = FontWeight.Bold
)

Spacer(modifier = Modifier.height(8.dp))

// Status
DetailRow(label = "Status", value = character.status)
DetailRow(label = "Species", value = character.species)
DetailRow(label = "Gender", value = character.gender)

character.type.takeIf { it.isNotEmpty() }?.let {
    DetailRow(label = "Type", value = it)
}

DetailRow(label = "Origin", value = character.origin.name)
DetailRow(label = "Location", value = character.location.name)
```

4. Verifica que el composable `DetailRow` ya esté implementado (debería estar al final del archivo).

**Resultado esperado:** Verás la pantalla de detalle vacía (sin datos todavía).

---

## 🌐 Parte 2: Implementar las Llamadas a la API

### 2.1 Implementar RickAndMortyApi.kt

**Objetivo:** Realizar las peticiones HTTP a la API de Rick and Morty.

**Pasos:**

1. Abre el archivo `composeApp/src/commonMain/kotlin/com/jetbrains/kmpapp/data/RickAndMortyApi.kt`

2. Localiza la función `getCharacters()` y reemplaza el `return null` con:

```kotlin
return client.get("$BASE_URL/character/?page=$page").body()
```

3. Localiza la función `getCharacterById()` y reemplaza el `return null` con:

```kotlin
return client.get("$BASE_URL/character/$id").body()
```

**Explicación:**
- `client.get()` realiza una petición HTTP GET
- `.body()` deserializa automáticamente el JSON a nuestros objetos Kotlin
- Ktor se encarga de la serialización gracias a las anotaciones `@Serializable`

---

### 2.2 Implementar RickAndMortyRepository.kt

**Objetivo:** Agregar manejo de errores y lógica de negocio.

**Pasos:**

1. Abre el archivo `composeApp/src/commonMain/kotlin/com/jetbrains/kmpapp/data/RickAndMortyRepository.kt`

2. Localiza la función `getCharacters()` y reemplaza el `return emptyList()` con:

```kotlin
return try {
    val response = api.getCharacters(page = 1)
    response?.results?.take(15) ?: emptyList()
} catch (e: Exception) {
    e.printStackTrace()
    emptyList()
}
```

3. Localiza la función `getCharacterById()` y reemplaza el `return null` con:

```kotlin
return try {
    api.getCharacterById(id)
} catch (e: Exception) {
    e.printStackTrace()
    null
}
```

**Explicación:**
- `try/catch` maneja errores de red o deserialización
- `.take(15)` limita la lista a 15 personajes
- Si hay error, retornamos datos seguros (`emptyList()` o `null`)

---

## ✅ Verificación Final

### Compilar y Ejecutar

1. Compila el proyecto:
   ```bash
   ./gradlew build
   ```

2. Ejecuta la app en Android:
   - Abre Android Studio
   - Selecciona un emulador o dispositivo físico
   - Presiona el botón "Run" ▶️

### Funcionalidad Esperada

✅ **Lista de Personajes:**
- Se muestra una cuadrícula de 2 columnas
- Cada card muestra: imagen, nombre y especie
- Total de 15 personajes

✅ **Detalle del Personaje:**
- Al hacer clic en un personaje, navega a la pantalla de detalle
- Muestra: imagen grande, nombre, status, species, gender, origin, location

✅ **Navegación:**
- Botón "Back" en la pantalla de detalle regresa a la lista

---

## 🐛 Solución de Problemas

### La lista está vacía
- Verifica que implementaste **PARTE 1** y **PARTE 2**
- Revisa los logs en Logcat para errores de red
- Confirma que tienes conexión a internet

### Error de compilación
- Ejecuta: `./gradlew clean build`
- Verifica que copiaste el código correctamente
- Revisa que los imports estén completos

### Imágenes no cargan
- Verifica permisos de internet en `AndroidManifest.xml`
- Confirma que la URL de la API está correcta: `https://rickandmortyapi.com/api`

---

## 📚 Conceptos Aprendidos

1. **Kotlin Multiplatform (KMP):** Código compartido entre plataformas
2. **Jetpack Compose:** UI declarativa moderna
3. **Ktor Client:** Cliente HTTP multiplataforma
4. **Kotlinx Serialization:** Serialización/deserialización JSON
5. **Coil:** Carga de imágenes asíncrona
6. **Coroutines:** Programación asíncrona en Kotlin
7. **Repository Pattern:** Separación de capas (UI, Repository, API)

---

## 🎓 Ejercicios Adicionales (Opcionales)

1. **Paginación:** Implementa scroll infinito para cargar más personajes
2. **Búsqueda:** Agrega un campo de búsqueda por nombre
3. **Favoritos:** Permite marcar personajes favoritos (persistencia local)
4. **Filtros:** Filtra por status (Alive, Dead, Unknown)
5. **Modo Offline:** Implementa caché local con SQLDelight

---

## 🔗 Referencias

- [Rick and Morty API](https://rickandmortyapi.com/documentation)
- [Kotlin Multiplatform Docs](https://kotlinlang.org/docs/multiplatform.html)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Ktor Documentation](https://ktor.io/docs/client.html)

---

## 📝 Licencia

Este proyecto es un tutorial educativo basado en la API pública de Rick and Morty.

---

**¡Felicidades! 🎉 Has completado tu primera app Kotlin Multiplatform con consumo de API REST.**

