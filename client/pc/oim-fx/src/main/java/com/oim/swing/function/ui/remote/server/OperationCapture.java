package com.oim.swing.function.ui.remote.server;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.oim.core.common.component.remote.EventData;
import com.oim.core.common.component.remote.EventDataHandler;

public class OperationCapture implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	EventDataHandler eventDataHandler;

	public OperationCapture(EventDataHandler eventDataHandler) {
		this.eventDataHandler = eventDataHandler;
	}

	public void keyPressed(KeyEvent e) {
		//System.out.println("keyPressed:" + e.getID() + ":" + e.getKeyCode());
		EventData eventData = new EventData();
		eventData.setId(e.getID());
		eventData.setKeyCode(e.getKeyCode());
		sendEvent(eventData);
	}

	public void keyReleased(KeyEvent e) {
		//System.out.println("keyReleased:" + e.getID() + ":" + e.getKeyCode());
		EventData eventData = new EventData();
		eventData.setId(e.getID());
		eventData.setKeyCode(e.getKeyCode());
		sendEvent(eventData);
	}

	public void keyTyped(KeyEvent e) {
		// System.out.println("keyTyped:" + e.getID() + ":" + e.getID());
	}

	public void mouseClicked(MouseEvent e) {
		// System.out.println("mouseClicked:" + e.getID());
	}

	public void mouseEntered(MouseEvent e) {
		// System.out.println("mouseEntered:" + e.getID());
	}

	public void mouseExited(MouseEvent e) {
		// System.out.println("mouseExited:" + e.getID());
	}

	public void mousePressed(MouseEvent e) {
		//System.out.println("mousePressed:" + e.getID() + ":" + e.getButton());
		EventData eventData = new EventData();
		eventData.setId(e.getID());
		eventData.setMouseButton(e.getButton());
		eventData.setMouseX(e.getX());
		eventData.setMouseY(e.getY());
		sendEvent(eventData);
	}

	public void mouseReleased(MouseEvent e) {
		//System.out.println("mouseReleased:" + e.getID() + ":" + e.getButton());
		EventData eventData = new EventData();
		eventData.setId(e.getID());
		eventData.setMouseButton(e.getButton());
		eventData.setMouseX(e.getX());
		eventData.setMouseY(e.getY());
		sendEvent(eventData);
	}

	public void mouseDragged(MouseEvent e) {
		//System.out.println("mouseDragged:" + e.getID() + ":" + e.getX());
		EventData eventData = new EventData();
		eventData.setId(e.getID());
		eventData.setMouseButton(e.getButton());
		eventData.setMouseX(e.getX());
		eventData.setMouseY(e.getY());
		sendEvent(eventData);
	}

	public void mouseMoved(MouseEvent e) {
		//System.out.println("mouseMoved:" + e.getID() + ":" + e.getX());
		EventData eventData = new EventData();
		eventData.setId(e.getID());
		eventData.setMouseButton(e.getButton());
		eventData.setMouseX(e.getX());
		eventData.setMouseY(e.getY());
		sendEvent(eventData);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		//System.out.println("mouseWheelMoved:" + e.getID() + ":" + e.getWheelRotation());
		EventData eventData = new EventData();
		eventData.setId(e.getID());
		eventData.setMouseRotation(e.getWheelRotation());
		eventData.setMouseButton(e.getButton());
		eventData.setMouseX(e.getX());
		eventData.setMouseY(e.getY());
		sendEvent(eventData);
	}

	private void sendEvent(EventData eventData) {
		if (null != eventDataHandler) {
			eventDataHandler.handle(eventData);
		}
	}
}
