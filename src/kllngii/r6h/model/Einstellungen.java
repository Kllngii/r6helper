package kllngii.r6h.model;

import java.io.File;
import java.net.URI;

public class Einstellungen {
    
    //------ Variablen ------
    
    // Lesen aus URI
    private URI uriInput = newURI("https://www.dropbox.com/s/qg32536wsqswir5/r6helper.json?dl=1");
    
    // Lesen per FTP
    private boolean ftpInput = false;
    

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

}
