package kllngii.r6h.model;

import java.net.URI;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class Einstellungen {
    
    public static final int DEFAULT_REFRESH_INTERVAL_S = 5;
    
    //------ Variablen ------
    
    private final Logger log = Logger.getLogger(getClass());
    
    // Lesen aus URI
    private URI uriInput = newURI("http://192.168.2.10:8080/r6/service/data/all");
    
    // Lesen per FTP
    private boolean ftpInput = false;
    
    private final ScheduledThreadPoolExecutor refreshThreadPool = new ScheduledThreadPoolExecutor(1);
    private int refreshIntervalS = DEFAULT_REFRESH_INTERVAL_S;
    private Runnable refreshRunnable = null;
    private String refreshAufgabe = null;
    private ScheduledFuture<?> refreshFuture = null;
    

    // Speichern in eine Datei
    private String urlOutput = "r6helper.json";
    
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


    public String getUrlOutput() {
        return urlOutput;
    }

    public void setUrlOutput(String urlOutput) {
        this.urlOutput = urlOutput;
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
    
    public ScheduledThreadPoolExecutor getRefreshThreadPool() {
        return refreshThreadPool;
    }

    public int getRefreshIntervalS() {
        return refreshIntervalS;
    }

    /**
     * Merkt sich das gewünschte neue Refresh-Intervall und
     * startet den Job (falls vorhanden) im refreshThreadPool mit dem aktuellen Intervall neu.
     */
    public void setRefreshIntervalS(int refreshIntervalS) {
        this.refreshIntervalS = refreshIntervalS;
        if (refreshFuture != null) {
            if (refreshIntervalS > 0) {
                refreshFuture.cancel(false);
                setRefreshRunnable(refreshRunnable, refreshAufgabe);
            }
            else {
                refreshFuture.cancel(false);
            }
        }
    }

    public void setRefreshRunnable(Runnable r, String aufgabe) {
        refreshRunnable = r;
        refreshAufgabe = aufgabe;
        
        long delay = Math.max(refreshIntervalS*1000L, 100L);
        refreshFuture = refreshThreadPool.scheduleAtFixedRate(r, 0, delay, TimeUnit.MILLISECONDS);
        log.info("Runnable wurde geschedulet. Aufgabe: "+ aufgabe + ", Intervall: " + delay + " ms.");
    }
    
    
    /**
     * Beendet den Threadpool für die Hintergrund-Aktualisierungen und wartet auf das Ende eventuell
     * noch laufender Threads.
     */
    public void shutdown() {
        log.info("Shutdown start");
        refreshThreadPool.shutdown();
        try {
            refreshThreadPool.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e1) {
            log.info("", e1);
        }
        log.info("Shutdown end");

    }
}
