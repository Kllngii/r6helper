package kllngii.r6h.model;

import java.io.File;
import java.net.URI;

import javax.swing.Timer;

public class Einstellungen {
    
    public static final int DEFAULT_REFRESH_INTERVAL_S = 5;
    
    //------ Variablen ------
    
    // Lesen aus URI
    private URI uriInput = newURI("https://www.dropbox.com/s/qg32536wsqswir5/r6helper.json?dl=1");
    
    // Lesen per FTP
    private boolean ftpInput = false;
    
    private int refreshIntervalS = DEFAULT_REFRESH_INTERVAL_S;
    private Timer refreshTimer = null;
    

    // Speichern in eine Datei
    private File dateiOutput = new File("r6helper.json");
    
    // Speichern per FTP
    private boolean ftpOutput = false;
    private String ftpHost = "";
    private String ftpUser = "";
    private String ftpPwd = "";
    
    
    //------ Getter und Setter ------

    public URI getUriInput() {
        return uriInput;
    }

    public void setUriInput(URI uriInput) {
        this.uriInput = uriInput;
    }


    public boolean isFtpInput() {
        return ftpInput;
    }

    public void setFtpInput(boolean ftpInput) {
        this.ftpInput = ftpInput;
    }


    public File getDateiOutput() {
        return dateiOutput;
    }

    public void setDateiOutput(File dateiOutput) {
        this.dateiOutput = dateiOutput;
    }

    
    public boolean isFtpOutput() {
        return ftpOutput;
    }

    public void setFtpOutput(boolean ftpOutput) {
        this.ftpOutput = ftpOutput;
    }


    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }


    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }


    public String getFtpPwd() {
        return ftpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }


    //------ Hilsmethoden ------
    
    private URI newURI(String url) {
        try {
            return new URI(url);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public int getRefreshIntervalS() {
        return refreshIntervalS;
    }

    /**
     * Merkt sich das gewünschte neue Refresh-Intervall und
     * ändert den Timer (falls vorhanden).
     */
    public void setRefreshIntervalS(int refreshIntervalS) {
        this.refreshIntervalS = refreshIntervalS;
        if (refreshTimer != null) {
            if (refreshIntervalS > 0) {
                refreshTimer.setDelay(refreshIntervalS*1000);
                refreshTimer.setRepeats(true);
                if (! refreshTimer.isRunning()) {
                    refreshTimer.start();
                }
            }
            else {
                if (refreshTimer.isRunning())
                    refreshTimer.stop();
            }
        }
    }

    public Timer getRefreshTimer() {
        return refreshTimer;
    }

    public void setRefreshTimer(Timer refreshTimer) {
        this.refreshTimer = refreshTimer;
    }
}
