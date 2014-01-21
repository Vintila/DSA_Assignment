package core.models;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class WarehouseItem
{
    private String key;
    private String brand;
    private String model;
    private int weightInKilo;
    private BigDecimal price;

    /** Public constructor
     *
     * @param key
     * @param brand
     * @param model
     * @param weightInKilo
     * @param price
     */
    public WarehouseItem(String key, String brand, String model, int weightInKilo, BigDecimal price)
    {
        setKey(key);
        setBrand(brand);
        setModel(model);
        setWeightInKilo(weightInKilo);
        setPrice(price);
    }

    /** Constructor that takes a String in format of
     * key,brand,model,weight,price
     * and extracts the values from it to create a new Warehouse Item
     *
     * @param csvWarehouseItem The CSV string to convert
     */
    public WarehouseItem(String csvWarehouseItem)
    {
        String[] values = csvWarehouseItem.split(",");

        setKey(values[0]);
        setBrand(values[1]);
        setModel(values[2]);
        double weight = Double.parseDouble(values[3]);
        setWeightInKilo((int) Math.round(weight));
        BigDecimal p = new BigDecimal(values[4], MathContext.DECIMAL64);
        p = p.setScale(2, RoundingMode.HALF_UP);

        setPrice(p);

    }

    public String getKey()
    {
       return key;
    }

    private void setKey(String key)
    {
        //Checks that the key is in the format of 1+ letters, colon then 1+ numbers
        if (!key.matches("[a-zA-Z ]+:[0-9]+"))
        {
            throw new IllegalArgumentException(String.format("The key must match the format ...aBc...:...123... got : %s", key));
        }

        this.key = key;
    }

    public String getBrand()
    {
        return brand;
    }

    private void setBrand(String brand)
    {
        if (!brand.matches("[a-zA-Z -]+"))
            throw new IllegalArgumentException("Brand must not be null");
        this.brand = brand;
    }

    public String getModel()
    {
        return model;
    }

    private void setModel(String model)
    {
        if(!model.matches("[a-zA-Z-/0-9. ]+"))
            throw new IllegalArgumentException(String.format("Model name must be in the format ...a bc... got : %s", model));
        this.model = model;
    }

    public int getWeightInKilo()
    {
        return weightInKilo;
    }

    private void setWeightInKilo(int weightInKilo)
    {
        if((weightInKilo <= 0))
            throw new IllegalArgumentException(String.format("Weight must be greater than 0, got : %d", weightInKilo));
        this.weightInKilo = weightInKilo;
    }

   public BigDecimal getPrice()
    {
        return price;
    }

    private void setPrice(BigDecimal price)
    {
        if(!(price.doubleValue() > 0))
            throw new IllegalArgumentException("Price must be greater than 0 ");

        this.price = price;
    }

    @Override
    public String toString()
    {
        return String.format(
                "Key: %32s\n" +
                "Brand: %30s\n" +
                "Model: %30s\n" +
                "Weight: %26d kg\n" +
                "Price: %30s", getKey(), getBrand(), getModel(), getWeightInKilo(), NumberFormat.getCurrencyInstance().format(getPrice()).toString());

    }

    /** Formats the object as csv
     *
     * @return a string representing this object in csv form
     */
    public String printCSV()
    {

        return new StringBuilder()
                .append(getKey()).append(",")
                .append(getBrand()).append(",")
                .append(getModel()).append(",")
                .append(getWeightInKilo()).append(",")
                .append(getPrice().toString()).toString();
    }

}
