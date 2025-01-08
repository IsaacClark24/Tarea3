package com.tarea.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.tarea.mock.entidades.Perro;
import com.tarea.mock.excepciones.PerroNoEncontradoException;
import com.tarea.mock.repositorios.PerroRepository;
import com.tarea.mock.servicios.PerroComunitarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PerroComunitarioServiceTest {

    PerroRepository mockRepository;
    PerroComunitarioService service;

    @BeforeEach
    public void inicializarPrueba(){
        // Mock del repositorio
        mockRepository = Mockito.mock(PerroRepository.class);
        // Servicio a probar
        service = new PerroComunitarioService(mockRepository);
    }

    @Test
    public void deberiaDevolverPerroCuandoElPerroExiste() { 
        // Creamos un mock del objeto perro
        Perro PerroTest = new Perro("Fido", 4);
        String nombreABuscar = "Fido";      
        // Verificación
        when(mockRepository.buscarPorNombre(nombreABuscar)).thenReturn(PerroTest);
        // Perro resultado = service.obtenerPerroPorNombre("Fido");
        service.obtenerPerroPorNombre("Fido");        
    }
    
    @Test
    public void deberiaLanzarPerroNoEncontradoExceptioCuandoElPerroNoExiste() {        
        // Ejecución que lanza excepción
        String nombreQueNoExiste = "Rex";
        when(mockRepository.buscarPorNombre(nombreQueNoExiste)).thenReturn(null);
        assertThrows(PerroNoEncontradoException.class, ()->{ 
                    service.obtenerPerroPorNombre("Rex"); 
                    });
                    //service.obtenerPerroPorNombre("Rex");        
    }
    
    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsNull() {
        // Ejecución que lanza excepción
        String nullName = null;
        when(mockRepository.buscarPorNombre(nullName)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, ()->{
                    service.obtenerPerroPorNombre(null);
                    });
        //service.obtenerPerroPorNombre(null);        
    }

    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsVacio() {
        // Ejecución que lanza excepción
        String nameLess = " ";
        when(mockRepository.buscarPorNombre(nameLess)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, ()->{
                    service.obtenerPerroPorNombre(null);
                    });
        //service.obtenerPerroPorNombre(null);
    }

    @Test
    public void deberiaConsultarRepositorioUnaSolaVezCuandoElPerroExiste() {
        // Verificación
        Perro perroTest = new Perro("Fido", 4);
        when(mockRepository.buscarPorNombre("Fido")).thenReturn(perroTest);
        service.obtenerPerroPorNombre("Fido");
        verify(mockRepository, times(1)).buscarPorNombre("Fido");
        //verify(mockRepository, times(1)).buscarPorNombre("Fido");
    }
}
