package core.utils;

import core.models.WarehouseItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
/** Class for reading a CSV file in the correct format to create WarehouseItems
 *
 */
public class AssetFileIOReader
{
    private BufferedReader inputStream;
    public AssetFileIOReader(String fileName) throws FileNotFoundException
    {
        inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
    }

    /** Reads the entire file that was opened in the constructor
     *
     * @return an array containing each valid element from the file or null if none were read
     * @throws IllegalStateException if the file has not been opened correctly
     */
    public WarehouseItem[] readFile()
    {

        String line;
		//A dynamicly sized List to store the read elements
        List<WarehouseItem> lines = new ArrayList<WarehouseItem>();

        WarehouseItem item;

		//Check if the file has been opened
        if(inputStream == null)
           throw new IllegalStateException("The file was not opened");


        try
        {
            while((line = inputStream.readLine()) != null)
            {
                //Checks that the line is the right format.

                if(line.matches(
                        //for 1..M letters, colon, then 1..M digits
                        //Key
                        "[a-zA-Z ]+:[0-9]+," +
                        //Checks for 1..M letters
                        //Brand
                        "[a-zA-Z -]+," +
                        //Checks for 1..M letters
                        //Model
                        "[a-zA-Z-/0-9. ]+," +
                        //Checks for 1..M 0..1 . 0..M digits
                        //Weight
                        "[0-9]+[.]?[0-9]*," +
                        //Checks for 1..M digits 0..1 . 0..M digits
                        //Price
                        "[0-9]+[.]?[0-9]*"
                ))
                {

                    item = new WarehouseItem(line);
                    lines.add(item);
                }
                else
                {
                    System.out.println(String.format("Found non valid Line: %s", line));
                }

            }
        } catch (IOException e)
        {
            System.out.println("There was an error Reading the file");
        }

        WarehouseItem[] returnedItems = new WarehouseItem[lines.size()];

        //Returns the array of items or null if none were read
        return lines.size() != 0 ? lines.toArray(returnedItems) : null;
    }

    /** Closes the file
     *
     */
    public void close()
    {
        try
        {
            inputStream.close();

        } catch (IOException e)
        {

            System.out.println("There was an error closing the file");
        }
    }

}
