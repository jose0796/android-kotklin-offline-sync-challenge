# FieldForms – Kotlin Coding Challenge

Bienvenido al reto técnico de The Empire. El objetivo es evaluar tus habilidades desarrollando una aplicación Android funcional en Kotlin, con almacenamiento local y sincronización automática.

---

## 🧠 Concepto

Desarrolla una app que simule el registro de visitas en campo, permitiendo guardar formularios offline y enviarlos automáticamente a una API cuando haya red disponible.

---

## 📌 Requisitos funcionales

### 1. Formulario
- Nombre del sitio visitado
- Nombre del agente
- Comentario
- Botón "Guardar visita"

### 2. Almacenamiento
- Guardar formularios localmente con Room
- Cada formulario debe tener un estado: Pendiente, Enviado, Error

### 3. Lista
- Mostrar todos los registros y su estado
- Íconos o texto para diferenciar estados

### 4. Sincronización
- Usar WorkManager para enviar formularios pendientes a un endpoint
- Enviar solo cuando haya conexión
- Cambiar estado al éxito o error según respuesta

### 5. API simulada
- Puedes usar: `https://httpbin.org/post` o una API de mock (Mocky, Beeceptor)

---

## ✅ Requisitos técnicos

- Kotlin
- Room
- WorkManager
- Retrofit
- MVVM (mínimo)
- Jetpack Libraries

---

## 🧪 ¿Cómo entregar?

1. Haz un **fork** de este repositorio.
2. Crea una nueva rama con tu nombre:  
   `git checkout -b nombre-apellido`
3. Trabaja sobre esa rama.
4. Cuando termines, crea un **Pull Request a `main`** en este repositorio original.
5. Agrega en tu PR:
   - Descripción breve de tu solución
   - Capturas de pantalla (opcional)
   - ¿Qué harías diferente con más tiempo?

⏱ Tiempo estimado: 4–5 horas  
📅 Entrega máxima: 72 horas después de recibir el reto

---

## 🙌 ¡Buena suerte!
