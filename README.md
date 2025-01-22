# Image Editor API

## Descripción
Image Editor API es un proyecto desarrollado con Spring Boot que permite a los usuarios subir imágenes y añadir texto superpuesto con un tamaño configurable. La API devuelve la imagen editada en formato PNG.

---

## Características
- Subida de imágenes en formato PNG o JPEG.
- Superposición de texto personalizado en la imagen.
- Configuración del tamaño de fuente del texto.
- Respuesta con la imagen editada en formato PNG.

---

## Tecnologías utilizadas
- **Java 21**
- **Spring Boot 3.4.1**
    - Spring Web (para exponer el servicio REST)
- **Maven** (gestión de dependencias)

---

## Instalación y configuración

### Prerrequisitos
- **Java 21** o superior instalado.
- **Maven** instalado.

### Instrucciones
1. Clonar este repositorio:
   ```bash
   git clone https://github.com/SoyElGary1/Editor-image-API.git
   cd image-editor-api
   ```
2. Compilar el proyecto:
   ```bash
   mvn clean install
   ```
3. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

La API estará disponible en `http://localhost:8080`.

---

## Uso

### Endpoint: `POST /upload-image`
**Descripción:** Este endpoint permite subir una imagen y agregar texto superpuesto.

**URL:**  
`http://localhost:8080/upload-image`

**Parámetros del formulario:**
- `file` (MultipartFile): La imagen a editar.
- `text` (String): El texto que se superpondrá en la imagen.
- `size` (int): Tamaño de la fuente para el texto.

**Ejemplo de petición:**
```bash
curl -X POST http://localhost:8080/upload-image \
     -F "file=@path/to/image.png" \
     -F "text=Hola Mundo" \
     -F "size=36" \
     -o output.png
```

**Respuesta:**
- Código 200: La imagen editada en formato PNG.
- Código 500: Error interno del servidor.

---

## Ejemplo de código

### Petición con JavaScript (fetch)
```javascript
const formData = new FormData();
formData.append("file", imageFile); // Un archivo de imagen
formData.append("text", "Texto superpuesto");
formData.append("size", 36);

fetch("http://localhost:8080/upload-image", {
    method: "POST",
    body: formData,
})
    .then(response => {
        if (response.ok) {
            return response.blob();
        }
        throw new Error("Error al procesar la imagen");
    })
    .then(blob => {
        const url = URL.createObjectURL(blob);
        const img = document.createElement("img");
        img.src = url;
        document.body.appendChild(img);
    })
    .catch(error => console.error(error));
```

---

## Estructura del proyecto
```
src
├── main
│   ├── java
│   │   └── com.editor.imageeditorapi
│   │       ├── controller
│   │       │   └── ImageEditorController.java
│   │       └── service
│   │           └── ImageEditorService.java
│   └── resources
│       └── application.properties
├── test
└── pom.xml
```

---

## Licencia
Este proyecto está licenciado bajo los términos de la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

---

## Contribuciones
Las contribuciones son bienvenidas. Por favor, abre un issue o crea un pull request.

---

## Autor
**[Tu nombre o alias]**


