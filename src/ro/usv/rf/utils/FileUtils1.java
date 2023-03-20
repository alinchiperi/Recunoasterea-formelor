package ro.usv.rf.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils1 
{
	protected static String[][] readStringMatrixFromFileStream(String fileName) {
	     try (Stream<String> stream = Files.lines(Paths.get(fileName)))
	    {
	            return stream.map(Line -> Line.split("\\s+"))    
	                                  .toArray(String[][]::new);
	    } catch (IOException e) {
	             e.printStackTrace();
	             return null;
	    }
	}
	
	private static final String inputFileValuesSeparator = "[ \t]"; // pentru a permite utilizarea a 2 separatori
	private static final String outputFileValuesSeparator = ",";
	
    public static double[][] readMatrixFromFileStream(String fileName)
	{
		// read file into stream, try-with-resources
                double[][] matrice=null;
		try (Stream<String> streamLiniiFisier = Files.lines(Paths.get(fileName))) {
                    matrice = streamLiniiFisier
                    		         .map(linie -> 
                                                  Arrays.stream(linie.split(inputFileValuesSeparator))
                                                        .filter(x -> !x.isEmpty())
                                                        .mapToDouble(Double::parseDouble)  
                                                        .toArray()
                                         )
                                     .filter(linMatrice -> linMatrice.length!=0)
                                     .toArray(double[][]::new);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return matrice;
	}
	
	public static double[][] convertToBiDimensionalArray(List<ArrayList<Double>> learningSetList ) {
            return learningSetList.stream()
                        .map((ArrayList<Double> lin) -> ((ArrayList<Double>)lin).stream()
                                .mapToDouble(Double::doubleValue)
                                .toArray()
                             )
                        .toArray(double[][]::new);
	}
	
	
	public static void writeLearningSetToFile(String fileName, double[][] patternMatrix, String separatorCampuri)
	{
            // first create the output to be written
            StringBuilder stringBuilder = new StringBuilder();
		for(double [] lineSet: patternMatrix) //for each row
		{
			//for each column
			 for(double val: lineSet) 
			 {
				//append to the output string
				 stringBuilder.append(val).append(separatorCampuri);
			 }
            //replace separatorCampuri at the end of the row by \n
            stringBuilder.replace(stringBuilder.lastIndexOf(separatorCampuri),
                                              stringBuilder.length(), "\n");
		}		
        try {
                  Files.write(Paths.get(fileName), stringBuilder.toString().getBytes());
        } catch (IOException e) {
			e.printStackTrace();
        }

	}
        


}

