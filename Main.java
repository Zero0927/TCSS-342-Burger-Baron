//Siyuan Zhou

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Main {
	private ArrayList<String> pattyCount = null;
	private String input;
	private String output;
	private int orderNum;
	private final String processPrefix;
	private BufferedWriter bw = null;

	/**
	 * initialize fields
	 */
	public Main() {
		pattyCount = new ArrayList<String>();
		pattyCount.add("Single");
		pattyCount.add("Double");
		pattyCount.add("Triple");
		pattyCount.add("Quadruple");
		pattyCount.add("Quintuple");
		pattyCount.add("Sextuple");
		pattyCount.add("Septuple");
		pattyCount.add("Octuple");
		pattyCount.add("Nonuple");
		pattyCount.add("Decuple");
		pattyCount.add("Hendecuple");
		pattyCount.add("Duodecuple");
		processPrefix = "Processing Order ";
		orderNum = 0;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
		try {
			bw = new BufferedWriter(new FileWriter(new File(output)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * parses lines of input from the file and outputs the burger
	 * 
	 * @param line
	 */
	public void parseFile() {
		Scanner sc = null;
		if (input == null) {
			System.out.println("please input the input file!");
			return;
		}
		if (output == null) {
			System.out.println("please input the output file!");
			return;
		}
		try {
			sc = new Scanner(new File(input));
			// get all lines from input file, and parse it to order, then write
			// the order to output file
			while (sc.hasNext()) {
				String line = sc.nextLine();

				try {
					parseLine(line);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}

	/**
	 * parses a line of input from the file and outputs the burger
	 * 
	 * @param line
	 * @throws IOException
	 */
	public void parseLine(String line) throws IOException {
		List<String> lineList = Arrays.asList(line.split(" "));

		int burgerIndex = lineList.indexOf("Baron");
		int aburIndex = lineList.indexOf("Burger");
		int awithIndex = lineList.indexOf("with");
		int anoIndex = lineList.indexOf("no");
		if (burgerIndex >= 0 &&(aburIndex==(burgerIndex+1))&&(awithIndex==(aburIndex+1))&&(anoIndex==(awithIndex+1))) {
			// example : ... Baron Burger with no ...<but>
			// create a Baron Burger
			Burger burger = new Burger(true);
			// if the burgerIndex is bigger than -1, it means this
			// sentence is with no type
			int butIndex = lineList.indexOf("but");
			if (butIndex == -1)
				// example:... Baron Burger with no ...
				butIndex = lineList.size();// there is no exception
			// find all omissions, and remove those from Baron Burger
			for (int i = burgerIndex + 4; i < butIndex; i++) {
				String omission = lineList.get(i);
				switch (omission) {
				case "Cheese":
					burger.removeCategory("Cheese");
					break;
				case "Sauces":
					burger.removeCategory("Sauces");
					break;
				case "Veggies":
					burger.removeCategory("Veggies");
					break;
				default:
					omission = omission.replace("-", "");
					burger.removeIngredient(omission);
					break;
				}
			}// end for
				// find all exceptions, and add those to Baron
				// Burger
			for (int i = butIndex + 1, len = lineList.size(); i < len; i++) {
				String exceptions = lineList.get(i);
				burger.addIngredient(exceptions.replace("-", ""));
			}// end for
			if (burgerIndex > 1) {
				// the user pointed the type of patty and the number
				// of patty add patties
				// example:Double Beef Baron Burger with no .. <but>...
				int num = pattyCount.indexOf(lineList.get(0));
				while (num-- > 0) {
					burger.addPatty();
				}
				// correct type of patties
				String type = lineList.get(1);
				if (type.equals("Baron")) {
					burger.changePatties("Beef");
				} else {
					burger.changePatties(type);
				}
			} else if (burgerIndex == 1) {
				// the user pointed the type of patty or the number
				// of patty
				String numOrType = lineList.get(0);
				int num = pattyCount.indexOf(numOrType);
				if (num > -1) {
					// the user pointed the number of patty
					// example: Double Baron Burger with no .. <but>...
					while (num-- > 0)
						burger.addPatty();
					burger.changePatties("Beef");
				} else {
					// the user pointed the type of patty
					// example: Beef Baron Burger with no .. <but>...
					if (numOrType.equals("Baron")) {
						burger.changePatties("Beef");
					} else {
						burger.changePatties(numOrType);
					}
				}
			} else {
				// example: Baron Burger with no .. <but>...
				// the user didn't point the type of patty and the
				// number of patty
				burger.changePatties("Beef");
			}
			// write this burger to file
			bw.write(processPrefix + (orderNum++) + " : " + line + "\n");
			bw.write(burger.toString() + "\n");
			bw.newLine();
			bw.flush();
		} else {
			int burgerwithIndex = lineList.indexOf("Burger");
			int bwithIndex = lineList.indexOf("with");
			if (burgerwithIndex > 0&&(bwithIndex==(burgerwithIndex+1))) {
				// example: .... Burger with ... but no ..
				// create a simple burger
				Burger burger = new Burger(false);
				int butIndex = lineList.indexOf("but");
				if (butIndex == -1)
					butIndex = lineList.size();// there is no
													// exception
				// add all additions to burger
				for (int i = burgerwithIndex + 2; i < butIndex; i++) {
					String additions = lineList.get(i);
					switch (additions) {
					case "Cheese":
						burger.addCategory("Cheese");
						break;
					case "Sauces":
						burger.addCategory("Sauces");
						break;
					case "Veggies":
						burger.addCategory("Veggies");
						break;
					default:
						additions = additions.replace("-", "");
						burger.addIngredient(additions);
						break;
					}
				}// end for
					// remove exceptions from burger
				int gap = 1;
				int noIndex = lineList.indexOf("no");
				if(noIndex>-1) gap=2;
				for (int i = butIndex + gap, len = lineList.size(); i < len; i++) {
					burger.removeIngredient((lineList.get(i)).replace("-", ""));
				}// end for
				if (burgerwithIndex > 1) {
					// the user pointed the type of patty and the
					// number of patty add patties
					// example: Double Beef Burger with ... <but no> ..
					int num = pattyCount.indexOf(lineList.get(0));
					while (num-- > 0) {
						burger.addPatty();
					}
					// correct type of patties
					String type = lineList.get(1);
					if (type.equals("Baron")) {
						burger.changePatties("Beef");
					} else {
						burger.changePatties(type);
					}
				} else if (burgerwithIndex == 1) {
					// the user pointed the type of patty or the
					// number of patty
					String numOrType = lineList.get(0);
					int num = pattyCount.indexOf(numOrType);
					if (num > -1) {
						// the user pointed the number of patty
						// example: Double Burger with ... but no ..
						while (num-- > 0)
							burger.addPatty();
						burger.changePatties("Beef");
					} else {
						// the user pointed the type of patty
						// example: Beef Burger with ... but no ..
						if (numOrType.equals("Baron")) {
							burger.changePatties("Beef");
						} else {
							burger.changePatties(numOrType);
						}
					}
				} else {
					// the user didn't point the type of patty and
					// the number of patty
					// example: Burger with ... but no ..
					burger.changePatties("Beef");
				}
				// write this burger to file
				bw.write(processPrefix + (orderNum++) + " : " + line + "\n");
				bw.write(burger.toString() + "\n");
				bw.newLine();
				bw.flush();

			} else {
				int barBurIndex = lineList.indexOf("Baron");
				int cburIndex = lineList.indexOf("Burger");
				if (barBurIndex > -1&&(cburIndex==(barBurIndex+1))) {
					// example: ... Baron Burger
					Burger burger = new Burger(true);
					if (barBurIndex > 1) {
						// the user pointed the type of patty and
						// the number of patty add patties
						// example: Double Beef Baron Burger
						int num = pattyCount.indexOf(lineList.get(0));
						while (num-- > 0) {
							burger.addPatty();
						}
						// correct type of patties
						String type = lineList.get(1);
						if (type.equals("Baron")) {
							burger.changePatties("Beef");
						} else {
							burger.changePatties(type);
						}
					} else if (barBurIndex == 1) {
						// the user pointed the type of patty or the
						// number of patty
						String numOrType = lineList.get(0);
						int num = pattyCount.indexOf(numOrType);
						if (num > -1) {
							// the user pointed the number of patty
							// example: Double Baron Burger
							while (num-- > 0)
								burger.addPatty();
							burger.changePatties("Beef");
						} else {
							// the user pointed the type of patty
							// example: Beef Baron Burger
							if (numOrType.equals("Baron")) {
								burger.changePatties("Beef");
							} else {
								burger.changePatties(numOrType);
							}
						}
					} else {
						// the user didn't point the type of patty
						// and
						// the number of patty
						burger.changePatties("Beef");
					}
					// write this burger to file
					bw.write(processPrefix + (orderNum++) + " : " + line + "\n");
					bw.write(burger.toString() + "\n");
					bw.newLine();
					bw.flush();
				} else {
					int burIndex = lineList.indexOf("Burger");
					// example: ... Burger
					if (burIndex > -1) {
						Burger burger = new Burger(false);
						if (burIndex > 1) {
							// the user pointed the type of patty and
							// the number of patty add patties
							// example: Double Beef Baron Burger
							int num = pattyCount.indexOf(lineList.get(0));
							while (num-- > 0) {
								burger.addPatty();
							}
							// correct type of patties
							String type = lineList.get(1);
							if (type.equals("Baron")) {
								burger.changePatties("Beef");
							} else {
								burger.changePatties(type);
							}
						} else if (burIndex == 1) {
							// the user pointed the type of patty or the
							// number of patty
							String numOrType = lineList.get(0);
							int num = pattyCount.indexOf(numOrType);
							if (num > -1) {
								// the user pointed the number of patty
								// example: Double Baron Burger
								while (num-- > 0)
									burger.addPatty();
								burger.changePatties("Beef");
							} else {
								// the user pointed the type of patty
								// example: Beef Baron Burger
								if (numOrType.equals("Baron")) {
									burger.changePatties("Beef");
								} else {
									burger.changePatties(numOrType);
								}
							}
						} else {
							// the user didn't point the type of patty
							// and the number of patty
							burger.changePatties("Beef");
						}
						// write this burger to file
						bw.write(processPrefix + (orderNum++) + " : " + line
								+ "\n");
						bw.write(burger.toString() + "\n");
						bw.newLine();
						bw.flush();
					}// end burIndex>-1
				}
			}
		}

	}

	public static void main(String[] args) {
		Main test = new Main();
		test.setInput("customer.txt");
		test.setOutput("trace.txt");
		test.parseFile();
		test.testMyStack();
		test.testBurger();
	}
	/**
	 *  test method for MyStack
	 */
	public void testMyStack(){
		MyStack<String> mystack = new MyStack<String>();
		int count = 10;
		System.out.println(mystack.size());
		while(count-->0){
			mystack.push(count+" ");
		}
		System.out.println(mystack.size());
		while(count++<5){
			mystack.pop();
		}
		System.out.println(mystack.size());
		System.out.println(mystack.peek());
		System.out.println(mystack.toString());
	}
	/**
	 * test method for Burger
	 */
	public void testBurger(){
		Burger burgerTrue = new Burger(true);
		System.out.println(burgerTrue);
		burgerTrue.addPatty();
		System.out.println(burgerTrue);
		burgerTrue.addPatty();
		System.out.println(burgerTrue);
		burgerTrue.changePatties("Beef");
		System.out.println(burgerTrue);
		burgerTrue.removePatty();
		System.out.println(burgerTrue);
		burgerTrue.addCategory("Sauces");
		System.out.println(burgerTrue);
		burgerTrue.removeCategory("Sauces");
		System.out.println(burgerTrue);
		burgerTrue.addIngredient("Mushrooms");
		System.out.println(burgerTrue);
		burgerTrue.removeIngredient("Mushrooms");
		System.out.println(burgerTrue);
		
		Burger burgerFalse = new Burger(false);
		System.out.println(burgerFalse);
	}

}
