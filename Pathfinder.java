package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;
import java.util.List;



/**
 *
 * @author Andrew Hardie 40162946
 */
public class Pathfinder {
    
    static int noOfCaves = 0;
    
    
    public static void main(String[] args) throws IOException
    {
        menu();
    }
    
    public static void menu()
    {
        int option;
        
        //Sets options for menu to be displayed to user
        Scanner scanOption = new Scanner(System.in);
        System.out.println("Option 1 - Step Through Program");
        System.out.println("Option 2 - Just find shortest path");
        
        System.out.println("Please Choose an option: ");
        
        option = scanOption.nextInt();
        
        //Decides what to do depedning what option is choosen
        if(option == 1)
        {
            readFile();
        }
        else if(option == 2)
        {
            justFindPath();
        }
    }
    
    public static void readFile()
    {
        //Set file name
        String fileName = "";
        BufferedReader br;
        
        Scanner scOne = new Scanner(System.in);
        
        //Allows user to specify filename and opens .cav file
        System.out.println("Please specify file name");
        fileName = scOne.next();
        try
        {
            br = new BufferedReader(new FileReader(fileName));
            //Read the line of comma separated text from the file
            String buffer = br.readLine();
            System.out.println();
            System.out.println("Raw data : " + buffer);
            
            br.close();
            
            Scanner keyIn = new Scanner(System.in);
            //Waits for user to press return key to continue running program
            System.out.print("Press the enter key to continue");
            keyIn.nextLine();
            
            //Convert the data to an array
            String[] data = buffer.split(",");
            
            //Now extract data from the array - note that we need to convert from String to int as we go
            noOfCaves = Integer.parseInt(data[0]);
            System.out.println();
            System.out.println ("There are " + noOfCaves + " caves.");
            
            //Waits for user to press enter key to continue running program
            System.out.print("Press the return key to continue");
            keyIn.nextLine();
            
            System.out.println("");
            
            //Get Coordinates
            getCords(data);

        }
        catch(IOException ex)
        {
            System.out.println("File Does Not Exist, the program will now close");
            Scanner keyIn = new Scanner(System.in);
            //Waits for user to press return key to continue running program
            System.out.println("Press the return key to EXIT");
            keyIn.nextLine();
        }
    }
    
    public static void getCords(String[] data)
    {    
        //Takes X Y coordinates from file and puts them into 2D array
        int x = 0;
        int y = 0;
        int[][] coords = new int[noOfCaves][2]; 
        
        for (int i = 1; i < (noOfCaves * 2) + 1; i++)
        {
            coords[x][y] = Integer.parseInt(data[i]);

            if (y == 0) { y = 1; } 
            else { y = 0; x++; }
        }
        
        System.out.println("Coordinates of caves saved into matrix as: ");
        System.out.println(Arrays.deepToString(coords));
        
        //Call method
        buildMatrix(data, coords);
    }
    
    
    public static void buildMatrix(String[] data, int [][]coords)
    {
        //Declare the array
        int[][] connected = new int[noOfCaves][];
        
        for (int row= 0; row < noOfCaves; row++){
            connected[row] = new int[noOfCaves];
        }
        //Reads in the data which is the starting point in the array is after the coordinates
        int col = 0;
        int row = 0;
        
        for (int point = (noOfCaves*2)+1 ; point < data.length; point++){
            //Work through the array
            
            if (data[point].equals("1"))
                connected[row][col] = 1;
            else
                connected[row][col] = 0;
            
            row++;
            if (row == noOfCaves){
                row=0;
                col++;
            }
            
        }
        
        Scanner keyIn = new Scanner(System.in);
        //Waits for user to press return key to continue running program
        System.out.print("Press the enter key to continue");
        keyIn.nextLine();
        
        //Connected now has the adjacency matrix within it
        System.out.println();
        System.out.println("Adjacency Matrix saved as: ");
        System.out.println(Arrays.deepToString(connected));
        System.out.println();
        
        //Calls method
        findConnectedCaves(connected, data, coords);
    }
    
    public static void findConnectedCaves(int[][] connected, String[] data, int [][]coords)
    {
        //Set scanner
        Scanner keyIn = new Scanner(System.in);
         //Waits for user to press return key to continue running program
        System.out.print("Press the enter key to continue");
        keyIn.nextLine();
        
        //Finds connected caves and prints on screen which caves are connected
        System.out.println("The following caves are connected: ");
        for (int i = 0; i < connected.length; i++) {
            for (int j = 0; j < connected[i].length; j++) {
                if (connected[i][j] == 1) {
                    System.out.println("Cave" + i + " " + "&" + " " + "cave" + j + " " + "are connected");
                }
            }
        }
        

        //Waits for user to press return key to continue running program
        System.out.print("Press the enter key to continue");
        keyIn.nextLine();
            
        //Call method
        runAlgorithm(connected, data, coords);
        
    }
    
    public static void runAlgorithm(int[][] connected, String[] data, int [][]coords)
    {
        //Starts Dijkstras Algorithm
        int noOfNodes = noOfCaves -1;
        int[][] matrix = connected;
        int[] distance = new int [noOfCaves];
        int[] visited = new int [noOfCaves];
        int[] preD = new int [noOfCaves];
        int min;
        int nextNode = 0;
        int[][]distanceXY = new int[noOfCaves][2];
            
           for(int i = 0; i < distance.length; i++)
            {
                
                visited[i] = 0; //initialize visited array to zeros
                
                preD[i] = 0;
                
                for(int j = 0; j < distance.length; j++)
                {
                   //Calculation for working out distances
                    if (matrix[i][j] == 0) 
                    {
                        matrix[i][j] = 999; 
                    }
                    else
                    { 
                        int X;
                        int Y;

                        X = coords[i][0] - coords[j][0];
                        X = (int)(Math.sqrt(X * X));

                        Y = distanceXY[i][1] - distanceXY[j][1];
                        Y = (int)Math.sqrt(Y * Y);

                        connected[i][j] = (int)(Math.sqrt(X * X + Y * Y));
                    }
                }
            }
            
            distance = matrix[0]; //initializes the distance array
            visited[0] = 1; //sets the source node as visited
            distance[0] = 0; //sets the distance from source to zero which is the starting point
            
            for(int counter = 0; counter < noOfCaves; counter++)
            {
                min = 999;
                
                for(int i = 0; i < noOfCaves; i++)
                { 
                    if(min > distance[i] && visited[i]!=1)
                    {
                        min = distance[i];
                        nextNode = i;  
                    }
                    
                }
                
                visited[nextNode] = 1
                        ;
                for(int i = 0; i < noOfCaves; i++)
                {   
                    if(visited[i]!=1)
                    {
                        if(min+matrix[nextNode][i] < distance[i])
                        {
                            distance[i] = min+matrix[nextNode][i];
                            preD[i] = nextNode;
                        }
                        
                    }
                    
                }
            }
            //Gets shortes path
            for(int i = 0; i < noOfCaves; i++)
            {
            }
            noOfNodes = noOfNodes +1;
            System.out.println("");
            System.out.println("The shortest path from node " + " 1 to node: " + noOfNodes + " is" );
            
            int j;
            for(int i = noOfCaves -1; i > 0; i--){
                
                if(i!=0)
                {
                    
                    System.out.print("Node" + (i+1));
                    j = i;  
                    do{  
                        j=preD[j];                      
                        System.out.print(" <- " + "Node " + (j+1));   
                    
                    //gets node jumps totals and displays visited routes
                    }while(j!=0);
                    System.out.println("");
                    System.out.println("");
                    System.out.println("This Path distance is " + distance[i]);
                    System.out.println("");
                    System.out.println("Other route explored: ");
                }
            }
            
            Scanner keyIn = new Scanner(System.in);
            //Waits for user to press return key to continue running program
            System.out.println("Program finished");
            System.out.println("Press the enter key to EXIT");
            keyIn.nextLine();
    }
 
    public static void justFindPath()
    {
        //Set file name
        String fileName = "";
        BufferedReader br;
        
        Scanner scOne = new Scanner(System.in);
        
        //Allows user to specify filename and opens .cav file
        System.out.println("Please specify file name");
        fileName = scOne.next();
        try
        {
            br = new BufferedReader(new FileReader(fileName));
            //Read the line of comma separated text from the file
            String buffer = br.readLine();
            
            br.close();
            
            //Convert the data to an array
            String[] data = buffer.split(",");
            
            //Now extract data from the array - note that we need to convert from String to int as we go
            noOfCaves = Integer.parseInt(data[0]);
            
            //Gets coordinates & stores them into a list
            //Gets coordinates & stores them into a list
            int[][] coords = new int[noOfCaves][2]; 

            for (int count = 1; count < ((noOfCaves*2)+1); count=count+2)
            {
                
            }
            
            //Takes X Y coordinates from file and puts them into 2D array
            int x = 0;
            int y = 0;
            
            for (int i = 1; i < (noOfCaves * 2) + 1; i++)
            {
                coords[x][y] = Integer.parseInt(data[i]);

                if (y == 0) { y = 1; } 
                else { y = 0; x++; }
            }
            

                
            //Declare the array
            int[][] connected = new int[noOfCaves][];
            
            for (int row= 0; row < noOfCaves; row++)
            {
                connected[row] = new int[noOfCaves];
            }
            
            //Now read in the data - the starting point in the array is after the coordinates
            int col = 0;
            int row = 0;
            int[][]distanceXY = new int[noOfCaves][2];
            
            for (int point = (noOfCaves*2)+1; point < data.length; point++)
            {
                //Work through the array
                
                if (data[point].equals("1"))
                {
                    connected[row][col] = 1; 
               
                }
                else
                {
                    connected[row][col] = 0;   
                }

                row++;
                if (row == noOfCaves){
                    row=0;
                    col++;
                }

            }
            

            int noOfNodes = noOfCaves -1;
            int[][] matrix = connected;
            int[] distance = new int [noOfCaves];
            int[] visited = new int [noOfCaves];
            int[] preD = new int [noOfCaves];
            int min;
            int nextNode = 0;
            
            for(int i = 0; i < distance.length; i++)
            {
                
                visited[i] = 0; //initialize visited array to zeros
                
                preD[i] = 0;
                
                for(int j = 0; j < distance.length; j++)
                {
                   //Calculation for working out distances
                    if (matrix[i][j] == 0) 
                    {
                        matrix[i][j] = 999; 
                    }
                    else
                    {    
                        int X;
                        int Y;
                        
                        X = (coords[i][0] - coords[j][0]) * (coords[i][0] - coords[j][0]);
                        //X = (int)(Math.sqrt(X * X));

                        Y = (distanceXY[i][1] - distanceXY[j][1]) * (distanceXY[i][1] - distanceXY[j][1]);
                        //Y = (int)Math.sqrt(Y * Y);
                        
                        matrix[i][j] = (int)Math.sqrt(X + Y);
                        
                    }
                }
            }
            
            distance = matrix[0]; //initialize the distance array
            visited[0] = 1; //set the source node as visited
            distance[0] = 0; //set the distance from source to zero which is the starting point
            
            for(int counter = 0; counter < noOfCaves; counter++)
            {
                min = 999;
                
                for(int i = 0; i < noOfCaves; i++)
                { 
                    if(min > distance[i] && visited[i]!=1)
                    {
                        min = distance[i];
                        nextNode = i;  
                    }
                    
                }
                
                visited[nextNode] = 1
                        ;
                for(int i = 0; i < noOfCaves; i++)
                {   
                    if(visited[i]!=1)
                    {
                        if(min+matrix[nextNode][i] < distance[i])
                        {
                            distance[i] = min+matrix[nextNode][i];
                            preD[i] = nextNode;
                        }
                        
                    }
                    
                }
            }
            for(int i = 0; i < noOfCaves; i++)
            {
            }
            noOfNodes = noOfNodes +1;
            System.out.println("");
            System.out.println("The shortest path from node " + "1 to node " + noOfNodes + " is" );
            
            int j;
            for(int i = noOfCaves-1; i < noOfCaves; i++){
                
                if(i!=0)        
                {
                    
                    System.out.print("Node" + (i+1));
                    j = i;  
                    do{  
                        j=preD[j];                      
                        System.out.print(" <- " + "Node " + (j+1));   
                        
                    }while(j!=0);
                    System.out.println("");
                    System.out.println("");
                    System.out.println("Path distance is " + distance[i]);
                }
            }
            
            Scanner keyIn = new Scanner(System.in);
            //Waits for user to press return key to continue running program
            System.out.println("Program finished");
            System.out.println("Press the return key to EXIT");
            keyIn.nextLine();
            
        }

        catch(IOException ex)
        {
            System.out.println("File Does Not Exist, the program will now close");
            Scanner keyIn = new Scanner(System.in);
            //Waits for user to press return key to continue running program
            System.out.println("Press the return key to EXIT");
            keyIn.nextLine();
        }
        

    }
}
        
        
    


      

    
    

