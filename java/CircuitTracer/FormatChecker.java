import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author candlewolf
 *
 */
public class FormatChecker {
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		boolean valid = false;

		for(String arg: args){
			valid = false;
			System.out.println(arg);
			valid = checkFormat(arg);
		
			if(valid == true){
			
				System.out.println("VALID");
			
			}else{
			
				System.out.println("INVALID");
			
			}
		
		}
	
	}
		
	
	/**
	 * @param arg
	 * @return
	 */
	public static boolean checkFormat(String arg){
	
		boolean valid = true;
		File file = new File(arg);
		Scanner scan = null;
		
		try{
			scan = new Scanner(file);
		}catch (FileNotFoundException e) {
			System.out.println("java.io.FileNotFoundException: For input " + arg);
			valid = false;
		}
		
		int rowInit = 0, colInit = 0, row = 0, col = 0, count = 0;
		String line = "", delims = "[      ]";
		
		while(scan.hasNextLine()){
		
			line = scan.nextLine();
	
			
			if(line.trim().isEmpty() != true){
				String[] tokens = line.split(delims);
				
				if(count == 0){
								
					if(tokens.length == 2){
			
						try{
							//string s = scan2.next();
							rowInit = Integer.parseInt(tokens[0].trim());
					
						}catch(NumberFormatException exception){
							System.out.println("java.lang.NumberFormatException: For array establishment of " + arg);
							return false;
						}
						
						try{
							colInit = Integer.parseInt(tokens[1].trim());
						}catch(NumberFormatException exception){
							System.out.println("java.lang.NumberFormatException: For array establishment of " + arg);
							return false;
						}
				
					}else{
						
						System.out.println("Improper amount of entries for array delineation in " + arg);
						return false;
					
					}
					
					count++;
			
				}else{
				
					for(String s : tokens){
					
					try{
						Double.parseDouble(s.trim());
					}catch (NumberFormatException exception){
						System.out.println("java.lang.NumberFormatException: For an array value of " + arg);
						return false;
					}
	
					col++;
				
				}
				
					if( col != (colInit)){
						System.out.println("Improper amount of column entries in " + arg );
						return false;
					}
	
					col = 0;
					row++;
				
				}
			}	
		}
	
	if( row != (rowInit )){
		System.out.println("Improper amount of row entries in" + arg );
		return false;
	}
		
	return valid;
	
	}	

}
	

