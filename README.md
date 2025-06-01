
# 🧪 Apuntes de Testing Unitario en Java con Mockito

Este repositorio es un apunte personal y práctico sobre cómo utilizar el framework Mockito para pruebas unitarias en Java. Además de describir los fundamentos del framework, incluye ejemplos explicativos de código real, especialmente pruebas de una clase de servicio con dependencias.

## 📂 Estructura del Repositorio

- `MockitoFramework.txt`: Notas teóricas sobre el uso de Mockito.
- `src/test/java/.../PlayerServiceImplTest.java`: Pruebas de unidad aplicando buenas prácticas con Mockito y JUnit 5.
- `src/main/java/...`: Código fuente de clases de dominio (`Player`), repositorios y servicios.

## 🧰 Fundamentos del uso de Mockito

### 🔧 Mockito y JUnit: Roles

- **JUnit** se encarga de ejecutar la clase que estamos testeando (en este caso `PlayerServiceImpl`).
- **Mockito** simula las dependencias de esa clase (como `PlayerRepositoryImpl`), permitiendo control y verificación del comportamiento.

### 🔩 Inicialización de Mocks

Existen varias formas de inicializar mocks, siendo la más recomendable:

```java
@ExtendWith(MockitoExtension.class)
@Mock private PlayerRepositoryImpl playerRepository;
@InjectMocks private PlayerServiceImpl playerService;
```

Alternativamente, se puede usar `MockitoAnnotations.openMocks(this)` en un `@BeforeEach` o instanciarlos manualmente.

### 🧪 Ejemplos de pruebas reales

#### `testFindAll()`
- Se simula el comportamiento del repositorio con `when(...).thenReturn(...)`.
- Se invoca el método real `playerService.findAll()`.
- Se verifica el resultado con `assertEquals()` y que el método del mock fue invocado con `verify(...)`.

#### `testFindById()`
- Se simula la respuesta del método `findById(...)` y se verifica que devuelve los datos correctos del mock.

#### `testSave()`
- Se testea un método `void` que no retorna nada.
- Se usa `ArgumentCaptor` para capturar el argumento con el que se llamó al método `save(...)` del repositorio.
- Se verifican los valores capturados.

#### `testDeleteById()`
- Se testea la invocación al método `deleteById(...)`.
- Se verifica que se llamó con el ID correcto utilizando un capturador de argumentos.

## ✅ Buenas Prácticas en Mockito

- Separar claramente la **clase bajo prueba** de sus **dependencias mockeadas**.
- Usar `@InjectMocks` y `@Mock` junto con `@ExtendWith(MockitoExtension.class)` para configuración automática.
- Utilizar `ArgumentCaptor` para verificar argumentos en métodos `void`.
- Verificar interacciones con `verify()`.

## 🚀 Ejecución de pruebas

```bash
git clone https://github.com/hectorgm26/study-apunte-java-testing-mockito.git
cd study-apunte-java-testing-mockito
mvn clean test
```

## 📚 Recursos útiles

- [Mockito Docs](https://site.mockito.org/)
- [JUnit 5 Docs](https://junit.org/junit5/)
- [Mockito ArgumentCaptor](https://www.baeldung.com/mockito-argumentcaptor)

---

Este repositorio sirve como guía de estudio práctico para comprender en profundidad el uso de Mockito en proyectos reales de Java con JUnit.
