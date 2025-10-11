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
                                                NotificacionRepository notificacionRepository
    ) {
        return args -> {
            // 1. Crear autoridades (Roles) Hola
            Authority adminRole = new Authority(null, "ADMIN", null);
            Authority userRole = new Authority(null, "USER", null);
            authorityRepository.save(adminRole);
            authorityRepository.save(userRole);

            // 2. Crear usuarios
            // Renombramos las variables para hacer más claro el rol y cliente
            User juanPerezUser = new User(null, "juanperez", "admin123", "juan.perez@example.com", true, List.of(adminRole),null,null,null);  // Juan Perez (admin)
            User mariaLopezUser = new User(null, "mariaLopez", "user123", "maria.lopez@example.com", true, List.of(userRole),null,null,null);  // Maria Lopez (normal)
            User carlosGarciaUser = new User(null, "carlosgarcia", "carlo1234", "carlos.garcia@example.com", true, List.of(userRole),null,null,null);  // Carlos Garcia (cliente)
            User luisMartinezUser = new User(null, "luismartinez", "luis1234", "luis.martinez@example.com", true, List.of(userRole),null,null,null);  // Luis Martinez (cliente)

            userRepository.save(juanPerezUser);
            userRepository.save(mariaLopezUser);
            userRepository.save(carlosGarciaUser);
            userRepository.save(luisMartinezUser);

            // 3. Crear clientes y asociarlos a los usuarios
            Cliente cliente1 = new Cliente(null, "Juan Perez", "juan@example.com", "987654321", "Av. Lima 123", LocalDate.now(), "activo", juanPerezUser);
            Cliente cliente2 = new Cliente(null, "Maria Lopez", "maria@example.com", "998877665", "Av. Cusco 456", LocalDate.now(), "activo", mariaLopezUser);
            Cliente cliente3 = new Cliente(null, "Carlos Garcia", "carlos@example.com", "987654323", "Av. Piura 789", LocalDate.now(), "activo", carlosGarciaUser);  // Asociado a carlosGarciaUser
            Cliente cliente4 = new Cliente(null, "Luis Martinez", "luis@example.com", "987654324", "Av. Arequipa 101", LocalDate.now(), "activo", luisMartinezUser);  // Asociado a luisMartinezUser

            clienteRepository.save(cliente1);
            clienteRepository.save(cliente2);
            clienteRepository.save(cliente3);
            clienteRepository.save(cliente4);

            // 4. Crear parcelas y asociarlas a los clientes
            Parcela parcela1 = new Parcela(null, "Parcela 1", 12.345678, 54.321234, 100.5, cliente1);
            Parcela parcela2 = new Parcela(null, "Parcela 2", 15.678910, 50.123456, 120.75, cliente2);
            Parcela parcela3 = new Parcela(null, "Parcela 3", 14.567890, 53.123457, 80.25, cliente3);
            Parcela parcela4 = new Parcela(null, "Parcela 4", 16.543210, 52.234567, 95.60, cliente4);

            parcelaRepository.save(parcela1);
            parcelaRepository.save(parcela2);
            parcelaRepository.save(parcela3);
            parcelaRepository.save(parcela4);

            // 5. Crear cultivos y asociarlos a las parcelas
            Cultivo cultivo1 = new Cultivo(null, "Cultivo de Tomate", "Tomates orgánicos", "Verano", "Activo", parcela1);
            Cultivo cultivo2 = new Cultivo(null, "Cultivo de Maíz", "Maíz dulce", "Invierno", "Activo", parcela2);
            Cultivo cultivo3 = new Cultivo(null, "Cultivo de Papa", "Papas orgánicas", "Primavera", "Activo", parcela3);
            Cultivo cultivo4 = new Cultivo(null, "Cultivo de Arroz", "Arroz para consumo local", "Otoño", "Activo", parcela4);

            cultivoRepository.save(cultivo1);
            cultivoRepository.save(cultivo2);
            cultivoRepository.save(cultivo3);
            cultivoRepository.save(cultivo4);

            Servicio servicio1 = new Servicio(null, "Guía de cultivo", "Asesoramiento sobre cómo cultivar tomates.", "Asesoría", 50.0, "Activo", "Sigue estos pasos para cultivar tomates de forma óptima.",null);
            Servicio servicio2 = new Servicio(null, "Guía de recursos", "Provisión de recursos como fertilizantes y pesticidas.", "Recursos", 100.0, "Activo", "Te proporcionamos fertilizantes y equipos.",null    );
            Servicio servicio3 = new Servicio(null, "Mantenimiento preventivo", "Mantenimiento de equipos agrícolas y tierras de cultivo.", "Mantenimiento", 150.0, "Activo", "Revisión periódica de los equipos y tierras de cultivo.",null);
            Servicio servicio4 = new Servicio(null, "Asesoría sobre pestes", "Evaluación y asesoría sobre el manejo de plagas.", "Asesoría", 75.0, "Activo", "Inspección de cultivos para detectar plagas y medidas a tomar.",null);

            servicioRepository.save(servicio1);
            servicioRepository.save(servicio2);
            servicioRepository.save(servicio3);
            servicioRepository.save(servicio4);

            // Crear solicitudes de servicio para el cultivo de Tomate (Cultivo1)
            SolicitudServicio solicitud1 = new SolicitudServicio(null, LocalDate.now(), "Pendiente", servicio1, cultivo1);
            SolicitudServicio solicitud2 = new SolicitudServicio(null, LocalDate.now(), "Pendiente", servicio2, cultivo2);
            SolicitudServicio solicitud3 = new SolicitudServicio(null, LocalDate.now(), "Pendiente", servicio3, cultivo3);
            SolicitudServicio solicitud4 = new SolicitudServicio(null, LocalDate.now(), "Pendiente", servicio4, cultivo4);

            solicitudServicioRepository.save(solicitud1);
            solicitudServicioRepository.save(solicitud2);
            solicitudServicioRepository.save(solicitud3);
            solicitudServicioRepository.save(solicitud4);


// 6. Crear fertilizantes
            Fertilizante fert1 = new Fertilizante(null, "Nitrofoska", "NPK", 50.00, null);
            Fertilizante fert2 = new Fertilizante(null, "Urea", "Nitrogenado", 30.00, null);
            Fertilizante fert3 = new Fertilizante(null, "Fosfato Diamónico", "Fosforado", 40.00, null);
            fertilizanteRepository.save(fert1);
            fertilizanteRepository.save(fert2);
            fertilizanteRepository.save(fert3);

// 7. Asignar fertilizantes a cultivos con fechas fijas
            CultivoFertilizante cf1 = new CultivoFertilizante(null, cultivo1, fert1, LocalDate.of(2025, 5, 15), 25.00);
            CultivoFertilizante cf2 = new CultivoFertilizante(null, cultivo2, fert2, LocalDate.of(2025, 6, 10), 15.00);
            CultivoFertilizante cf3 = new CultivoFertilizante(null, cultivo3, fert3, LocalDate.of(2025, 7, 5), 20.00);
            cultivoFertilizanteRepository.save(cf1);
            cultivoFertilizanteRepository.save(cf2);
            cultivoFertilizanteRepository.save(cf3);


// 8. Llamada de notificaciones a sus respectivos usuarios
            Notificacion not1 = new Notificacion(null,"Riego completado","Tu cultivo fue regado correctamente.",LocalDateTime.of(2025, 5, 15, 10, 30),true,"INFO",juanPerezUser);
            Notificacion not2 = new Notificacion(null,"Solicitud aprobada","Tu solicitud de servicio fue aprobada.",LocalDateTime.now(),false,"ALERT",mariaLopezUser);
            Notificacion not3 = new Notificacion(null,"Mensaje nuevo","Nuevo mensaje del soporte técnico.",LocalDateTime.now(),false,"INFO",carlosGarciaUser);


        };
    }

}
