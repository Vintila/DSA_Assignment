package core.utils;

import core.models.WarehouseItem;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class AssetFileIOWriter
{
    private PrintWriter outputStream;
   	/** Constructor to open a new file
 	 * 
	 *
 	 */
    public AssetFileIOWriter(String fileName) throws FileNotFoundException
    {
        outputStream = new PrintWriter(new FileOutputStream(fileName));
    }

    /** Writes a file out in CSV format from an array
     *
     * @param items the array of items that will be writen to a file
     */
    public void writeFile(WarehouseItem[] items)
    {
       for(WarehouseItem item : items)
       {
          outputStream.println(item.printCSV());
       }

    }

    /** Closes the file
     *
     */
    public void close()
    {
        outputStream.close();
    }
}
