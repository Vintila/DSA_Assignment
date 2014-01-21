package core.utils;
import core.models.WarehouseItem;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Menu
{
    //Array to store items read from the file
	WarehouseItem[] items;
    //A Binary Search Tree
	WarehouseItemBinaryTree tree = null;

    /** Displays the menu options and prompts the user for a selection
     *
     * @return Returns the users selection
     */
	private static int showMenuAndReceiveSelection()
	{

		int selection;
		do
		{
			System.out.println("\n(1) Load records into array\n" +
				"(2) Build binary search tree from array\n" +
				"(3) Search array\n" +
				"(4) Search tree\n" +
				"(5) Save data to file\n" +
				"(6) Quit\n" +
				"Enter your Choice:");
            //Keep looping until the selection is greater than 0 and less than 7
		}while((selection = ConsoleInput.readInt()) < 0 && selection > 7) ;

		return selection;

	}

    /** Prints out the status of the array and binary tree
     *
     */
    private void printStatus()
    {

        System.out.println("\n==============================");
        if(tree != null)
            System.out.println("Tree is built");
        else
            System.out.println("Tree is not built");

        if(items != null)
            System.out.println("Data is loaded");
        else
            System.out.println("No Data is loaded");


        System.out.println("==============================");
    }

    /** Receives input and dispatches to relevant methods
     *
     */
    private void run()
	{	
		int selection;
		boolean running = true;
		while(running)
		{
		    printStatus();
			selection = showMenuAndReceiveSelection();
			switch(selection)
			{
				case 1:
					doLoadArray();		
					break;
				case 2:
					doBuildBinarySearchTree();
					break;
				case 3:
					doSearchArray();
					break;
				case 4:
					doSearchBinaryTree();
					break;
				case 5:
					doSaveDataToFile();
					break;
				case 6:
					running = false;
					break;	
				default:
                    System.out.println("The entry was not valid");
					break;	
			}
		}
	}

    /** Loads the CSV file of WarehouseItems specified by the user into the array
     *
     */
	private void doLoadArray()
	{
		System.out.println("\nPlease enter the name of the file to read from: ");
		String fileName = ConsoleInput.readLine();
		double startTime, endTime;
		try
		{
			AssetFileIOReader fileReader = new AssetFileIOReader(fileName);
			startTime = System.nanoTime();	
			items = fileReader.readFile();
			endTime = System.nanoTime() - startTime;
            //Destroy the tree because new data has been loaded
            tree = null;
			fileReader.close();

            if(items != null)
                System.out.println(String.format("Read %d records in %.2f milliseconds", items.length, endTime / 1000000));
            else
                System.out.println("\nThe file was opened and read but no valid records were found");
		}
		catch(FileNotFoundException fnfException)
		{
			System.out.println("The file could not be opened because it does not exist");
		}
		
	}

    /** Takes the data loaded from the file and creates a binary search tree
     *  The data must be loaded into the array for this to work
     */
	private void doBuildBinarySearchTree()
	{
        //No data has been loaded into the array
		if(items != null)
		{
            tree = new WarehouseItemBinaryTree();
			for(int ii = 0; ii < items.length; ii++)
            {
                //Checks if insert returns -1 indicating that something has failed
				if(tree.insert(items[ii]) == -1)
                {
                    //Create new tempory array
                    WarehouseItem[] tempArr = new WarehouseItem[items.length -1 ];
                    //Copy elements from the start to the duplicate
                    System.arraycopy(items, 0, tempArr, 0, ii);
                    //Copy the elements after the duplicate to the end
                    System.arraycopy(items, ii + 1, tempArr, ii, tempArr.length - ii);
                    //Decrement ii because we have shifted everything down
                    ii--;

                    items = tempArr;
                }
            }

            System.out.println("The tree was built successfully");
		}	
	
		else
			System.out.println("You haven't loaded any data yet");
	}

    /** Performs a linear search through the array for a key specified by the user
     *
     */
	private void doSearchArray()
	{
        //No data has been loaded into the array
		if( items != null )
        {

            double startTime, endTime;
            System.out.println("\nEnter your search key");
            String searchKey = ConsoleInput.readLine();

            WarehouseItem foundItem = null;
            startTime = System.nanoTime();
            //Loops through each element or until it finds the one it is looking for
            for(int ii = 0 ; foundItem == null  && ii < items.length; ii++)
            {
                if(items[ii].getKey().compareTo(searchKey) == 0)
                    foundItem = items[ii];
            }
            endTime = System.nanoTime() - startTime;

            if(foundItem != null)
            {
                System.out.println(String.format("Took %.2f milliseconds to find your item:\n", endTime / 1000000));
                System.out.println(foundItem.toString());
            }

            else
                System.out.println(String.format("Your item could not be found but the search took %.2f milliseconds", endTime / 1000000));
        }
        else
        {
           System.out.println("You have not loaded any data into the array to search.");
        }
	}

    /** Performs a binary search to find a key specified by the user
     *
     */
	private void doSearchBinaryTree()
	{
        //No data has been loaded into the array
        if(tree != null)
        {
            double startTime, endTime;

            System.out.println("\nEnter your search key");
            String searchKey = ConsoleInput.readLine();

            WarehouseItem foundItem;
            startTime = System.nanoTime();
            foundItem = tree.find(searchKey);
            endTime = System.nanoTime() - startTime;

            if(foundItem != null)
            {
                System.out.println(String.format("Took %.2f milliseconds to find your item:\n", endTime / 1000000));
                System.out.println(foundItem.toString());
            }
            else
                System.out.println(String.format("Your item could not be found but the search took %.2f", endTime / 1000000));
        }
        else
            System.out.println("The tree has not been built");

	}

    /** Saves contents of the binary tree to a file specified by the user
     *
     */
	private void doSaveDataToFile()
	{
        //Checks that there is data to save and the tree has been constructed
		if(items != null && tree != null)
		{
			System.out.println("\nEnter the name of the file to save the data to...:");
			String fileName = ConsoleInput.readLine();

			try
			{	
				
				AssetFileIOWriter fileWriter = new AssetFileIOWriter(fileName);
				fileWriter.writeFile(tree.sort());
				fileWriter.close();
                System.out.println("The data was saved successfully");

			
			}
			catch(IOException ioException)
			{
                System.out.println("There was a problem creating the file, please try again...");
			}
		}
		else
		{
			System.out.println("The array must be loaded and tree constructed to save to file");
		}
	}
	
	public static void main(String[] argV)
	{
		Menu menu = new Menu();

		menu.run();	
	}
} 
