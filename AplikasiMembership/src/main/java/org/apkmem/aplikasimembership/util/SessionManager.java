package org.apkmem.aplikasimembership.util;

import java.io.*;
/**
 * @editor David.Seay-71220909
 */
public class SessionManager implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final String SESSION_FILE = "session.ser";
    private int id;
    private String username;
    private String email;
    private String no_telp;

    private static volatile SessionManager instance;
    private boolean isLoggedIn;

    // Private constructor to prevent instantiation from outside
    private SessionManager() {
        isLoggedIn = false;
    }


    // Static method to get the singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                    instance.createSessionFile();
                }
            }
        }
        return instance;
    }

    public void setUserInfo(int id, String username, String email, String no_telp) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.no_telp = no_telp;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    // Method to check if the session file doesn't exist
    public void createSessionFile() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            saveSession();
        } else {
            loadSession();
        }
    }

    private void loadSession() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE))) {
            SessionManager sessionManager = (SessionManager) ois.readObject();
            this.id = sessionManager.id;
            this.username = sessionManager.username;
            this.email = sessionManager.email;
            this.no_telp = sessionManager.no_telp;
            this.isLoggedIn = sessionManager.isLoggedIn;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading session: " + e.getMessage());
        }
    }

    private void saveSession() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if user is logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // Method to simulate login
    public void login() {
        isLoggedIn = true;
        saveSession();
    }

    // Method to simulate logout
    public void logout() {
        isLoggedIn = false;
        saveSession();
    }
}
