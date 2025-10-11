package pe.edu.upc.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pe.edu.upc.backend.Entitie.*;
import pe.edu.upc.backend.Repository.*;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
    @Bean
    public CommandLineRunner startConfiguration(UserRepository userRepository,
                                                AuthorityRepository authorityRepository,
                                                ClienteRepository clienteRepository,
                                                ParcelaRepository parcelaRepository,
                                                CultivoRepository cultivoRepository,
                                                ServicioRepository servicioRepository,
                                                SolicitudServicioRepository solicitudServicioRepository,
                                                FertilizanteRepository fertilizanteRepository,
                                                CultivoFertilizanteRepository cultivoFertilizanteRepository,
                                                NotificacionRepository notificacionRepository, SensorRepository sensorRepository,LecturaSensorRepository lecturaSensorRepository,
                                                NoticiasRepository noticiasRepository
    ) {
        return args -> {
            Authority adminRole = new Authority(null, "ADMIN", null);
            Authority userRole = new Authority(null, "USER", null);
            authorityRepository.save(adminRole);
            authorityRepository.save(userRole);

            // Crear usuarios
            User juanPerezUser = new User(null, "juanperez", "admin123", "juan.perez@example.com", true, List.of(adminRole), null, null, null);
            User mariaLopezUser = new User(null, "mariaLopez", "user123", "maria.lopez@example.com", true, List.of(userRole), null, null, null);
            User carlosGarciaUser = new User(null, "carlosgarcia", "carlo1234", "carlos.garcia@example.com", true, List.of(userRole), null, null, null);
            User luisMartinezUser = new User(null, "luismartinez", "luis1234", "luis.martinez@example.com", true, List.of(userRole), null, null, null);
            User pedroLopezUser = new User(null, "pedrolopez", "pedro1234", "pedro.lopez@example.com", true, List.of(adminRole), null, null, null);

            userRepository.save(juanPerezUser);
            userRepository.save(mariaLopezUser);
            userRepository.save(carlosGarciaUser);
            userRepository.save(luisMartinezUser);
            userRepository.save(pedroLopezUser);

            // Crear clientes y asociarlos a los usuarios
            Cliente cliente1 = new Cliente(null, "Juan Perez", "juan@example.com", "987654321", "Av. Lima 123", LocalDate.now(), "activo", juanPerezUser);
            Cliente cliente2 = new Cliente(null, "Maria Lopez", "maria@example.com", "998877665", "Av. Cusco 456", LocalDate.now(), "activo", mariaLopezUser);
            Cliente cliente3 = new Cliente(null, "Carlos Garcia", "carlos@example.com", "987654323", "Av. Piura 789", LocalDate.now(), "activo", carlosGarciaUser);
            Cliente cliente4 = new Cliente(null, "Luis Martinez", "luis@example.com", "987654324", "Av. Arequipa 101", LocalDate.now(), "activo", luisMartinezUser);
            Cliente cliente5 = new Cliente(null, "Pedro Lopez", "pedro@example.com", "987654325", "Av. San Isidro 202", LocalDate.now(), "activo", pedroLopezUser);

            clienteRepository.save(cliente1);
            clienteRepository.save(cliente2);
            clienteRepository.save(cliente3);
            clienteRepository.save(cliente4);
            clienteRepository.save(cliente5);

            // Crear parcelas y asociarlas a los clientes
            Parcela parcela1 = new Parcela(null, "Parcela 1", 12.345678, 54.321234, 100.5, cliente1);
            Parcela parcela2 = new Parcela(null, "Parcela 2", 15.678910, 50.123456, 120.75, cliente2);
            Parcela parcela3 = new Parcela(null, "Parcela 3", 14.567890, 53.123457, 80.25, cliente3);
            Parcela parcela4 = new Parcela(null, "Parcela 4", 16.543210, 52.234567, 95.60, cliente4);
            Parcela parcela5 = new Parcela(null, "Parcela 5", 17.234567, 51.234567, 85.00, cliente5);

            parcelaRepository.save(parcela1);
            parcelaRepository.save(parcela2);
            parcelaRepository.save(parcela3);
            parcelaRepository.save(parcela4);
            parcelaRepository.save(parcela5);

            // Crear cultivos y asociarlos a las parcelas (con algunos cultivos repetidos por parcela)
            Cultivo cultivo1 = new Cultivo(null, "Cultivo de Tomate", "Tomates orgánicos", "Verano", "Activo", parcela1);
            Cultivo cultivo2 = new Cultivo(null, "Cultivo de Maíz", "Maíz dulce", "Invierno", "Activo", parcela2);
            Cultivo cultivo3 = new Cultivo(null, "Cultivo de Papa", "Papas orgánicas", "Primavera", "Activo", parcela3);
            Cultivo cultivo4 = new Cultivo(null, "Cultivo de Arroz", "Arroz para consumo local", "Otoño", "Activo", parcela4);
            Cultivo cultivo5 = new Cultivo(null, "Cultivo de Pimientos", "Pimientos orgánicos", "Verano", "Activo", parcela5);
            Cultivo cultivo6 = new Cultivo(null, "Cultivo de Tomate", "Tomates orgánicos", "Verano", "Activo", parcela2);  // Repetido en otra parcela
            Cultivo cultivo7 = new Cultivo(null, "Cultivo de Maíz", "Maíz dulce", "Invierno", "Activo", parcela1);  // Repetido en otra parcela

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
            Sensor sensor3 = new Sensor(null, "Sensor de PH", "PH", "activo", "Modelo P350", null);

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

            // Crear noticias y asociarlas a los usuarios
            Noticia noticia1 = new Noticia(null, "Nuevo servicio disponible", "Ahora puedes solicitar servicios de asesoría técnica.", LocalDate.of(2025, 4, 15), juanPerezUser);
            Noticia noticia2 = new Noticia(null, "Mantenimiento de equipos", "Recuerda que el mantenimiento de equipos agrícolas será este fin de semana.", LocalDate.of(2025, 4, 10), mariaLopezUser);
            Noticia noticia3 = new Noticia(null, "Actualización de la plataforma", "La plataforma ha sido actualizada con nuevas funcionalidades.", LocalDate.of(2025, 4, 1), carlosGarciaUser);
            Noticia noticia4 = new Noticia(null, "Cultivo de tomates", "Guía completa sobre el cultivo de tomates en tu parcela.", LocalDate.of(2025, 4, 5), luisMartinezUser);

            noticiasRepository.save(noticia1);
            noticiasRepository.save(noticia2);
            noticiasRepository.save(noticia3);
            noticiasRepository.save(noticia4);

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
        };
    }



}
