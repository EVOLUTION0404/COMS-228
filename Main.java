package edu.iastate.cs228.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Asks user for file name, pulls file, finds the pattern and encodes message.
 * Calling MsgTree
 * 
 * @author Christian Lapnow
 *
 */
public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Please enter filename to decode:");
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();
		sc.close();

		// Read the content of the file specified by fileName
		String content = new String(Files.readAllBytes(Paths.get(fileName))).trim();
		// Find the index of the last newline character
		int pos = content.lastIndexOf('\n');
		//Extract the substring from the beginning to the last newline character 
		//(exclusive), representing the pattern
		String pattern = content.substring(0, pos); 
		// Extract the substring from the last newline character to the end, 
		//and trim any leading/trailing whitespaces, representing the binary code
		String binaryCode = content.substring(pos).trim(); 
		
		/*
		 * Create a HashSet to store unique characters from the 'pattern'.
		 * Then  Iterate through each character in the 'pattern' string.
		 * If the character is not '^', add it to the HashSet 'chars'.
		 */
		Set<Character> chars = new HashSet<>();
		for (char c : pattern.toCharArray()) 
		{
			if (c != '^') 
			{
				chars.add(c);
			}
		}
		String chardict = chars.stream().map(String::valueOf).collect(Collectors.joining());

		// call to the edu.iastate.cs228.hw4.MsgTree method
		MsgTree root = new MsgTree(pattern);
		MsgTree.printCodes(root, chardict);
		root.decode(root, binaryCode);
	}
}
