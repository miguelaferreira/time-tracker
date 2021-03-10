package com.functorful;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import java.time.LocalTime;

@Slf4j
public class GlobalActionListener implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener {

    private LocalTime lastActivity = LocalTime.now();

    @SneakyThrows
    public static void startListening(GlobalActionListener listener) {
        log.debug("Star listening for system input");
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(listener);
        GlobalScreen.addNativeMouseListener(listener);
        GlobalScreen.addNativeMouseMotionListener(listener);
        GlobalScreen.addNativeMouseWheelListener(listener);
    }

    @SneakyThrows
    public static void stopListening() {
        log.debug("Stop listening for system input");
        GlobalScreen.unregisterNativeHook();
    }

    public void resetLastActivity() {
        log.trace("Resetting last activity");
        this.lastActivity = LocalTime.now();
    }

    public LocalTime getLastActivity() {
        return lastActivity;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        log.trace("Key Pressed: {}", NativeKeyEvent.getKeyText(e.getKeyCode()));
        resetLastActivity();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        log.trace("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        resetLastActivity();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        log.trace("Key Typed: {}", NativeKeyEvent.getKeyText(e.getKeyCode()));
        resetLastActivity();
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        log.trace("Mouse Clicked: {}", e.getClickCount());
        resetLastActivity();
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        log.trace("Mouse Pressed: {}", e.getButton());
        resetLastActivity();
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        log.trace("Mouse Released: {}", e.getButton());
        resetLastActivity();
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        log.trace("Mouse Moved: {}, {}", e.getX(), e.getY());
        resetLastActivity();
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        log.trace("Mouse Dragged: {}, {}", e.getX(), e.getY());
        resetLastActivity();
    }

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        log.trace("Mouse Wheel Moved: {}, {}", e.getX(), e.getY());
        resetLastActivity();
    }
}
