package pe.edu.upc.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pe.edu.upc.backend.dtos.UserDTO;
import pe.edu.upc.backend.entities.*;
import pe.edu.upc.backend.repositories.*;
import pe.edu.upc.backend.services.AuthorityService;
import pe.edu.upc.backend.services.UserService;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
    @Bean
    public CommandLineRunner startConfiguration(
            UserRepository userRepository,
            AuthorityRepository authorityRepository,
            ClienteRepository clienteRepository,
            ParcelaRepository parcelaRepository,
            CultivoRepository cultivoRepository,
            ServicioRepository servicioRepository,
            SolicitudServicioRepository solicitudServicioRepository,
            FertilizanteRepository fertilizanteRepository,
            CultivoFertilizanteRepository cultivoFertilizanteRepository,
            NotificacionRepository notificacionRepository,
            SensorRepository sensorRepository,
            LecturaSensorRepository lecturaSensorRepository,
            NoticiasRepository noticiasRepository,

            UserService userService,
            AuthorityService authorityService
    ) {
        return args -> {

            // ==== Creacion de roles ====
            Authority adminRole  = authorityService.add(new Authority(null, "ROLE_ADMIN", null));
            Authority userRole   = authorityService.add(new Authority(null, "ROLE_USER", null));

            // ==== Creacion de usuarios ====
            UserDTO userJuan  = new UserDTO(null, "juanperez", "admin123", "juan.perez@example.com", "ROLE_ADMIN", null );
            UserDTO userMaria = new UserDTO(null, "marialopez", "user123", "maria.lopez@example.com", "ROLE_ASSIST", null);
            UserDTO userCarlos= new UserDTO(null, "carlosgarcia", "carlo1234", "carlos.garcia@example.com", "ROLE_USER", null);
            UserDTO userLuis  = new UserDTO(null, "luismartinez", "luis1234", "luis.martinez@example.com", "ROLE_USER", null);
            UserDTO userPedro = new UserDTO(null, "pedrolopez", "pedro1234", "pedro.lopez@example.com", "ROLE_ADMIN", null);

            userService.add(userJuan);
            userService.add(userMaria);
            userService.add(userCarlos);
            userService.add(userLuis);
            userService.add(userPedro);

            User juan   = userService.findByUsername("juanperez");
            User maria  = userService.findByUsername("marialopez");
            User carlos = userService.findByUsername("carlosgarcia");
            User luis   = userService.findByUsername("luismartinez");
            User pedro  = userService.findByUsername("pedrolopez");

            // Crear clientes asociados a los usuarios
            Cliente c1 = new Cliente(null, "Juan Perez",  "juan@example.com",  "987654321", "Av. Lima 123", LocalDate.now(), "activo", juan);
            Cliente c2 = new Cliente(null, "Maria Lopez", "maria@example.com", "998877665", "Av. Cusco 456", LocalDate.now(), "activo", maria);
            Cliente c3 = new Cliente(null, "Carlos Garcia", "carlos@example.com", "987654323", "Av. Piura 789", LocalDate.now(), "activo", carlos);
            Cliente c4 = new Cliente(null, "Luis Martinez", "luis@example.com", "987654324", "Av. Arequipa 101", LocalDate.now(), "activo", luis);
            Cliente c5 = new Cliente(null, "Pedro Lopez", "pedro@example.com", "987654325", "Av. San Isidro 202", LocalDate.now(), "activo", pedro);

            clienteRepository.save(c1);
            clienteRepository.save(c2);
            clienteRepository.save(c3);
            clienteRepository.save(c4);
            clienteRepository.save(c5);

            System.out.println("=== Datos cargados exitosamente ===");

            // Verificar que los usuarios y autoridades se han creado correctamente
            System.out.println("Usuarios creados: " + userRepository.findAll());

            // Crear parcelas y asociarlas a los clientes
            Parcela parcela1 = new Parcela(null, "Parcela 1", 12.345678, 54.321234, 100.5, c1);
            Parcela parcela2 = new Parcela(null, "Parcela 2", 15.678910, 50.123456, 120.75, c1);
            Parcela parcela3 = new Parcela(null, "Parcela 3", 14.567890, 53.123457, 80.25, c1);
            Parcela parcela4 = new Parcela(null, "Parcela 1", 16.543210, 52.234567, 95.60, c2);
            Parcela parcela5 = new Parcela(null, "Parcela 2", 17.234567, 51.234567, 85.00, c2);
            Parcela parcela6 = new Parcela(null, "Parcela 3", 15.572567, 50.341567, 90.00, c2);


            parcelaRepository.save(parcela1);
            parcelaRepository.save(parcela2);
            parcelaRepository.save(parcela3);
            parcelaRepository.save(parcela4);
            parcelaRepository.save(parcela5);
            parcelaRepository.save(parcela6);

            // Crear cultivos y asociarlos a las parcelas (con algunos cultivos repetidos por parcela)
            Cultivo cultivo1 = new Cultivo(null, "Cultivo de Tomate", "Tomates orgánicos", "Verano", LocalDate.of(2025, 10, 1), LocalDate.of(2026, 1, 15), "Activo", parcela1);
            Cultivo cultivo2 = new Cultivo(null, "Cultivo de Maíz", "Maíz dulce", "Invierno", LocalDate.of(2025, 6, 10), LocalDate.of(2025, 9, 30), "Activo", parcela2);
            Cultivo cultivo3 = new Cultivo(null, "Cultivo de Papa", "Papas orgánicas", "Primavera", LocalDate.of(2025, 8, 20), LocalDate.of(2025, 12, 5), "Activo", parcela3);
            Cultivo cultivo4 = new Cultivo(null, "Cultivo de Arroz", "Arroz para consumo local", "Otoño", LocalDate.of(2025, 3, 5), LocalDate.of(2025, 7, 25), "Activo", parcela4);
            Cultivo cultivo5 = new Cultivo(null, "Cultivo de Pimientos", "Pimientos orgánicos", "Verano", LocalDate.of(2025, 11, 1), LocalDate.of(2026, 2, 20), "Activo", parcela5);
            Cultivo cultivo6 = new Cultivo(null, "Cultivo de Tomate", "Tomates orgánicos", "Verano", LocalDate.of(2025, 10, 10), LocalDate.of(2026, 1, 20), "Activo", parcela2);
            Cultivo cultivo7 = new Cultivo(null, "Cultivo de Maíz", "Maíz dulce", "Invierno", LocalDate.of(2025, 6, 15), LocalDate.of(2025, 10, 5), "Activo", parcela1);

            cultivoRepository.save(cultivo1);
            cultivoRepository.save(cultivo2);
            cultivoRepository.save(cultivo3);
            cultivoRepository.save(cultivo4);
            cultivoRepository.save(cultivo5);
            cultivoRepository.save(cultivo6);
            cultivoRepository.save(cultivo7);

            // Crear servicios
            Servicio servicio1 = new Servicio(null, "Guía de cultivo", "Asesoramiento sobre cómo cultivar tomates.", "Asesoría", 50.0, "Activo", "Sigue estos pasos para cultivar tomates de forma óptima.", null);
            Servicio servicio2 = new Servicio(null, "Guía de recursos", "Provisión de recursos como fertilizantes y pesticidas.", "Recursos", 100.0, "Activo", "Te proporcionamos fertilizantes y equipos.", null);
            Servicio servicio3 = new Servicio(null, "Mantenimiento preventivo", "Mantenimiento de equipos agrícolas y tierras de cultivo.", "Mantenimiento", 150.0, "Activo", "Revisión periódica de los equipos y tierras de cultivo.", null);
            Servicio servicio4 = new Servicio(null, "Asesoría sobre pestes", "Evaluación y asesoría sobre el manejo de plagas.", "Asesoría", 75.0, "Activo", "Inspección de cultivos para detectar plagas y medidas a tomar.", null);
            Servicio servicio5 = new Servicio(null, "Planificación de riego", "Planificación del sistema de riego para cultivos.", "Asesoría", 60.0, "Activo", "Te ayudamos a organizar un plan de riego eficiente.", null);

            servicioRepository.save(servicio1);
            servicioRepository.save(servicio2);
            servicioRepository.save(servicio3);
            servicioRepository.save(servicio4);
            servicioRepository.save(servicio5);

            // Crear solicitudes de servicio para los cultivos
            SolicitudServicio solicitud1 = new SolicitudServicio(null, LocalDate.now(), "Pendiente", servicio1, cultivo1);
            SolicitudServicio solicitud2 = new SolicitudServicio(null, LocalDate.now(), "Pendiente", servicio2, cultivo2);
            SolicitudServicio solicitud3 = new SolicitudServicio(null, LocalDate.now(), "Pendiente", servicio3, cultivo3);
            SolicitudServicio solicitud4 = new SolicitudServicio(null, LocalDate.now(), "Pendiente", servicio4, cultivo4);
            SolicitudServicio solicitud5 = new SolicitudServicio(null, LocalDate.now(), "Pendiente", servicio5, cultivo5);

            solicitudServicioRepository.save(solicitud1);
            solicitudServicioRepository.save(solicitud2);
            solicitudServicioRepository.save(solicitud3);
            solicitudServicioRepository.save(solicitud4);
            solicitudServicioRepository.save(solicitud5);

            // Crear fertilizantes
            Fertilizante fert1 = new Fertilizante(null, "Nitrofoska", "NPK", 50.00, null);
            Fertilizante fert2 = new Fertilizante(null, "Urea", "Nitrogenado", 30.00, null);
            Fertilizante fert3 = new Fertilizante(null, "Fosfato Diamónico", "Fosforado", 40.00, null);
            Fertilizante fert4 = new Fertilizante(null, "Potasa", "Fertilizante de Potasio", 60.00, null);

            fertilizanteRepository.save(fert1);
            fertilizanteRepository.save(fert2);
            fertilizanteRepository.save(fert3);
            fertilizanteRepository.save(fert4);

            // Asignar fertilizantes a cultivos con fechas fijas
            CultivoFertilizante cf1 = new CultivoFertilizante(null, cultivo1, fert1, LocalDate.of(2025, 5, 15), 25.00);
            CultivoFertilizante cf2 = new CultivoFertilizante(null, cultivo2, fert2, LocalDate.of(2025, 6, 10), 15.00);
            CultivoFertilizante cf3 = new CultivoFertilizante(null, cultivo3, fert3, LocalDate.of(2025, 7, 5), 20.00);
            CultivoFertilizante cf4 = new CultivoFertilizante(null, cultivo4, fert4, LocalDate.of(2025, 8, 10), 30.00);

            cultivoFertilizanteRepository.save(cf1);
            cultivoFertilizanteRepository.save(cf2);
            cultivoFertilizanteRepository.save(cf3);
            cultivoFertilizanteRepository.save(cf4);

            // Crear sensores
            Sensor sensor1 = new Sensor(null, "Sensor de Temperatura", "Temperatura", "activo", "Modelo T150", null);
            Sensor sensor2 = new Sensor(null, "Sensor de Humedad", "Humedad", "activo", "Modelo H250", null);
            Sensor sensor3 = new Sensor(null, "Sensor de PH", "PH", "inactivo", "Modelo P350", null);

            sensorRepository.save(sensor1);
            sensorRepository.save(sensor2);
            sensorRepository.save(sensor3);

            // Crear lecturas de sensor y asociarlas a las parcelas y sensores
            LecturaSensor lectura10 = new LecturaSensor(null, 21.5, LocalDate.now(), sensor1, parcela1);
            LecturaSensor lectura11 = new LecturaSensor(null, 24.0, LocalDate.now(), sensor1, parcela2);
            LecturaSensor lectura12 = new LecturaSensor(null, 23.3, LocalDate.now(), sensor2, parcela1);
            LecturaSensor lectura13 = new LecturaSensor(null, 48.7, LocalDate.now(), sensor2, parcela4);
            LecturaSensor lectura14 = new LecturaSensor(null, 7.0, LocalDate.now(), sensor3, parcela4);

            lecturaSensorRepository.save(lectura10);
            lecturaSensorRepository.save(lectura11);
            lecturaSensorRepository.save(lectura12);
            lecturaSensorRepository.save(lectura13);
            lecturaSensorRepository.save(lectura14);

            //Crear noticias y asociarlas a los usuarios
            Noticia noticia1 = new Noticia(null, "Verificación de la construcción de canales en sectores agrícolas", "En la reunión se abordó la solicitud de verificación de la construcción de canales en estos sectores. Junto con la Oficina de Planificación Agraria de la GRA y en colaboración con la Junta de Usuarios de Chancay Lambayeque, se determinaron las necesidades prioritarias. Además, se alcanzaron acuerdos donde la Gerencia de Agricultura brindará el apoyo necesario a los pobladores para avanzar con los proyectos mencionados.", LocalDate.of(2025, 3, 25), null, juan);

            Noticia noticia2 = new Noticia(null, "Acciones conjuntas para la preservación ambiental en Lambayeque", "En un esfuerzo conjunto, el Gobierno Regional de Lambayeque a través de la Gerencia Regional de Agricultura y la comunidad de Cañaris, articulan acciones orientadas a la preservación y el desarrollo sostenible ambiental, con la forestación y gestión del recurso hídrico. Se enfatizó que en el aspecto ambiental, la actividad contribuirá a evitar el deterioro de los suelos, proteger el deslizamiento de tierra, mejorar la captura de CO2 y servirá de cortina rompeviento y captura de partículas de polvo.", LocalDate.of(2025, 3, 18), null, pedro);

            Noticia noticia3 = new Noticia(null, "Impacto de los incendios forestales en 22 regiones del país", "Los incendios forestales han generado grave afectación en 22 regiones del país, no solo en zonas de bosques sino también de cultivos. Además ya se registran 16 personas muertas debido a estos siniestros. Así lo indicó el presidente de la Asociación de Gremios Productores Agrarios del Perú (AGAP), Gabriel Amaro Alzamora, quien dijo que estos incendios, que se producen todos los años, se originan principalmente por la realización de malas prácticas como la quema de residuos agrícolas o restos vegetales para originar lluvias.", LocalDate.of(2025, 3, 10), null, juan);

            noticiasRepository.save(noticia1);
            noticiasRepository.save(noticia2);
            noticiasRepository.save(noticia3);

/*
            // 13. Crear notificaciones y asociarlas a los usuarios
            Notificacion notificacion1 = new Notificacion(null, "Nuevo mantenimiento programado", "El mantenimiento de equipos será el 20 de octubre.", LocalDateTime.now(), false, "Mantenimiento", juanPerezUser);
            Notificacion notificacion2 = new Notificacion(null, "Nueva actualización en la plataforma", "La plataforma se actualizará el 25 de octubre con nuevas funciones.", LocalDateTime.now(), false, "Actualización", mariaLopezUser);
            Notificacion notificacion3 = new Notificacion(null, "Fertilización programada", "Recuerda que la fertilización de tu parcela es el 15 de octubre.", LocalDateTime.now(), false, "Fertilización", carlosGarciaUser);
            Notificacion notificacion4 = new Notificacion(null, "Nueva plaga detectada", "Se detectó una plaga en tu cultivo de tomate. Contáctanos para asesoría.", LocalDateTime.now(), false, "Plaga", luisMartinezUser);
            Notificacion notificacion5 = new Notificacion(null, "Aviso importante", "Las lluvias de esta semana pueden afectar tu cultivo. Revisa tus sistemas de drenaje.", LocalDateTime.now(), false, "Clima", pedroLopezUser);

// Guardar notificaciones
            notificacionRepository.save(notificacion1);
            notificacionRepository.save(notificacion2);
            notificacionRepository.save(notificacion3);
            notificacionRepository.save(notificacion4);
            notificacionRepository.save(notificacion5);

            // 🔔 Notificaciones adicionales (variadas por usuario)
// Usuario: Juan Perez
            Notificacion n6 = new Notificacion(null, "Aviso de fertilización", "Recuerda aplicar fertilizante Nitrofoska mañana.", LocalDateTime.now().minusDays(2), false, "Fertilizante", juanPerezUser);
            Notificacion n7 = new Notificacion(null, "Lectura de sensor", "La humedad del suelo ha disminuido un 20%.", LocalDateTime.now().minusDays(1), false, "Sensor", juanPerezUser);
            Notificacion n8 = new Notificacion(null, "Revisión de plagas", "Se detectó una posible plaga en el cultivo de tomate.", LocalDateTime.now(), false, "Plaga", juanPerezUser);
            Notificacion n9 = new Notificacion(null, "Riego programado", "Tu riego automático se ejecutará en 2 horas.", LocalDateTime.now(), false, "Riego", juanPerezUser);

// Usuario: María Lopez
            Notificacion n10 = new Notificacion(null, "Nuevo artículo", "Consulta el nuevo artículo sobre control de humedad.", LocalDateTime.now().minusDays(3), false, "Noticia", mariaLopezUser);
            Notificacion n11 = new Notificacion(null, "Mantenimiento completado", "El mantenimiento de tu sensor ha finalizado exitosamente.", LocalDateTime.now(), false, "Mantenimiento", mariaLopezUser);

// Usuario: Carlos Garcia
            Notificacion n12 = new Notificacion(null, "Alerta climática", "Se esperan lluvias intensas esta noche.", LocalDateTime.now().minusDays(2), false, "Clima", carlosGarciaUser);
            Notificacion n13 = new Notificacion(null, "Nivel de pH bajo", "El pH del suelo está por debajo del rango óptimo.", LocalDateTime.now().minusDays(1), false, "Sensor", carlosGarciaUser);
            Notificacion n14 = new Notificacion(null, "Fertilizante agotado", "Tu reserva de Urea está por agotarse.", LocalDateTime.now(), false, "Fertilizante", carlosGarciaUser);
            Notificacion n15 = new Notificacion(null, "Plaga controlada", "La plaga detectada en tu cultivo fue eliminada.", LocalDateTime.now(), false, "Plaga", carlosGarciaUser);
            Notificacion n16 = new Notificacion(null, "Lectura de temperatura", "Temperatura promedio de 22°C en tu parcela.", LocalDateTime.now(), false, "Sensor", carlosGarciaUser);

// Usuario: Luis Martinez
            Notificacion n17 = new Notificacion(null, "Recordatorio de riego", "Activa el riego en la parcela 4 antes del medio día.", LocalDateTime.now(), false, "Riego", luisMartinezUser);

// Usuario: Pedro Lopez
            Notificacion n18 = new Notificacion(null, "Actualización del sistema", "Se ha actualizado el módulo de cultivos.", LocalDateTime.now().minusDays(1), false, "Sistema", pedroLopezUser);
            Notificacion n19 = new Notificacion(null, "Alerta de humedad", "El sensor reporta 18% de humedad, por debajo del óptimo.", LocalDateTime.now(), false, "Sensor", pedroLopezUser);
            Notificacion n20 = new Notificacion(null, "Recomendación de cultivo", "El cultivo de pimientos presenta buenas condiciones.", LocalDateTime.now(), false, "Asesoría", pedroLopezUser);

// Guardar las nuevas notificaciones
            notificacionRepository.saveAll(List.of(
                    n6, n7, n8, n9,
                    n10, n11,
                    n12, n13, n14, n15, n16,
                    n17,
                    n18, n19, n20
            ));
            */


        };
    }



}
