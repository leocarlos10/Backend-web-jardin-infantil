package com.jardininfantil.web_institucional.pattern;

import com.jardininfantil.web_institucional.pattern.observer.EventListener;
import com.jardininfantil.web_institucional.pattern.observer.EventManager;
import com.jardininfantil.web_institucional.pattern.observer.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para el Patrón Observer - EventManager
 * Demuestra cómo testear el sistema de eventos
 */
class EventManagerTest {

    private EventManager eventManager;
    private TestListener testListener;

    @BeforeEach
    void setUp() {
        eventManager = new EventManager();
        testListener = new TestListener();
    }

    @Test
    void testSubscribeAndNotify() {
        // Arrange
        String eventType = EventType.RESERVA_CREADA.getValue();
        eventManager.subscribe(eventType, testListener);

        // Act
        eventManager.notify(eventType, "Test Data");

        // Assert
        assertTrue(testListener.wasNotified());
        assertEquals(eventType, testListener.getLastEventType());
        assertEquals("Test Data", testListener.getLastData());
    }

    @Test
    void testMultipleListeners() {
        // Arrange
        TestListener listener1 = new TestListener();
        TestListener listener2 = new TestListener();
        String eventType = EventType.PAGO_VERIFICADO.getValue();

        eventManager.subscribe(eventType, listener1);
        eventManager.subscribe(eventType, listener2);

        // Act
        eventManager.notify(eventType, "Payment Data");

        // Assert
        assertTrue(listener1.wasNotified());
        assertTrue(listener2.wasNotified());
    }

    @Test
    void testUnsubscribe() {
        // Arrange
        String eventType = EventType.MATRICULA_CREADA.getValue();
        eventManager.subscribe(eventType, testListener);

        // Act
        eventManager.unsubscribe(eventType, testListener);
        eventManager.notify(eventType, "Test Data");

        // Assert
        assertFalse(testListener.wasNotified());
    }

    @Test
    void testNotifyWithoutSubscribers() {
        // Act & Assert - No debe lanzar excepción
        assertDoesNotThrow(() -> {
            eventManager.notify(EventType.ESTUDIANTE_CREADO.getValue(), "Data");
        });
    }

    /**
     * Listener de prueba para testing
     */
    private static class TestListener implements EventListener {
        private boolean notified = false;
        private String lastEventType;
        private Object lastData;

        @Override
        public void update(String eventType, Object data) {
            this.notified = true;
            this.lastEventType = eventType;
            this.lastData = data;
        }

        public boolean wasNotified() {
            return notified;
        }

        public String getLastEventType() {
            return lastEventType;
        }

        public Object getLastData() {
            return lastData;
        }
    }
}
