package efs.task.reflection.json;


import com.fasterxml.jackson.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO: Użyj tu odpowiednich adnotacji z biblioteki Jackson
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"ProductID", "ProductName", "ProductPrice", "DateOfProduction", "DateOfExpiry"})
public class ProductDTO {

    private Long id;

    private String name;

    private BigDecimal price;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiryDate;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private Date productionDate;
    @JsonProperty("ProductID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonProperty("ProductName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("ProductPrice")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonProperty("DateOfProduction")
    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }
    @JsonProperty("DateOfExpiry")
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", productionDate=" + productionDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
