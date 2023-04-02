package id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration;


import org.springframework.web.multipart.MultipartFile;

public class AppData {
    private String name;
    private MultipartFile imageFile;
    private String description;
    private MultipartFile installerFile;
    private String version;
    private Double price;
    public AppData(String name, String description, MultipartFile imageFile,
                   MultipartFile installerFile, String version, double price) {
        this.name = name;
        this.description = description;
        this.imageFile = imageFile;
        this.installerFile = installerFile;
        this.version = version;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getInstallerFile() {
        return installerFile;
    }

    public void setInstallerFile(MultipartFile installerFile) {
        this.installerFile = installerFile;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
}
