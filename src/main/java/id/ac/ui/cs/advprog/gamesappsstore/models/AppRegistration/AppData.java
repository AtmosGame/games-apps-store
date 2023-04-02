package id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration;


public class AppData {
    private String name;
    private String imageFileName;
    private String description;
    private String installerFileName;
    private String version;
    private Double price;
    public AppData(String name, String description, String imageFileName,
                   String installerFileName, String version, double price) {
        this.name = name;
        this.description = description;
        this.imageFileName = imageFileName;
        this.installerFileName = installerFileName;
        this.version = version;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstallerFileName() {
        return installerFileName;
    }

    public void setInstallerFileName(String installerFileName) {
        this.installerFileName = installerFileName;
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
}
