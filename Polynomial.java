import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.lang.Exception;

public class Polynomial {

	double [] coefficients;
	int [] exponents;

	public Polynomial(){
		coefficients = null;
		exponents = null;
	}

	public Polynomial(double [] given_coefficients, int [] given_exponents){
		coefficients = given_coefficients;
		exponents = given_exponents;
	}

	public Polynomial(File file) throws FileNotFoundException{
		Scanner scan = new Scanner(file);
		if(scan.hasNextLine() == false) {
			this.coefficients = null;
			this.exponents = null;
		}
		else {
			String line = scan.nextLine();
			line = line.replace("-", "+-");
			if (line.startsWith("+")) {
				line = line.substring(1, line.length());
			}
			String[] poly_arr = line.split("\\+");
			this.exponents = new int[poly_arr.length];
			this.coefficients = new double[poly_arr.length];
			for (int i = 0; i < poly_arr.length; i++) {
				String[] subArr = poly_arr[i].split("x");
				coefficients[i] = Double.parseDouble(subArr[0]);
				if (subArr.length > 1) {
					exponents[i] = Integer.parseInt(subArr[1]);
				}
				else {
					exponents[i] = 0;
				}
			}
		}
	}
	
	public int searchItem(int arr[], int item) {
		for (int i = 0; i < arr.length; i++) {
			if (item == arr[i]) {
				return i;
			}
		}
		return -1;
	}
	
	public void printPoly() {
		System.out.println("-----");
		System.out.println("coefficient:exponent");
		for (int i = 0; i < this.coefficients.length; i++) {
		System.out.println(this.coefficients[i] + ":" + this.exponents[i]);
		}
		System.out.println("-----");
	}
	
	public Polynomial add(Polynomial poly_2){
		Polynomial null_poly = new Polynomial();
		if (this.coefficients == null && poly_2.coefficients == null) {
			return null_poly;
		}
		else if (this.coefficients == null) {
			null_poly = new Polynomial(poly_2.coefficients, poly_2.exponents);
			return null_poly;
		}
		else if (poly_2.coefficients == null){
			null_poly = new Polynomial(this.coefficients, this.exponents);
			return null_poly;
		}
		int temp_length = this.exponents.length + poly_2.exponents.length;
		int [] temp_exponents = new int[temp_length];
		for (int l = 0; l < temp_exponents.length; l++) {
			temp_exponents[l] = -1;
		}
		double [] temp_coefficients = new double[temp_length];
		int i = 0;
		int j = 0;
		for (; i < this.exponents.length; i++) {
			int index = searchItem(poly_2.exponents, this.exponents[i]);
			if (index != -1) {
				double coefficient_sum = this.coefficients[i] + poly_2.coefficients[index];
				if (coefficient_sum != 0) {
					temp_coefficients[j] = coefficient_sum;
					temp_exponents[j] = this.exponents[i];
					j++;
				}
			}
			else {
				temp_coefficients[j] = this.coefficients[j];
				temp_exponents[j] = this.exponents[j];
				j++;
			}
		}
		i = 0;
		for (; i < poly_2.exponents.length; i++) {
			int index = searchItem(temp_exponents, poly_2.exponents[i]);
			if (index == -1) {
				index = searchItem(this.exponents, poly_2.exponents[i]);
				if (index == -1) {
					temp_coefficients[j] = poly_2.coefficients[i];
					temp_exponents[j] = poly_2.exponents[i];
					j++;
				}
			}
		}
		if (j == 0) {
			return null_poly;
		}
		double [] new_coefficients = new double[j];
		int [] new_exponents = new int[j];
		i = 0;
		for (; i < j; i++) {
			new_coefficients[i] = temp_coefficients[i];
			new_exponents[i] = temp_exponents[i];
		}
		Polynomial p = new Polynomial(new_coefficients, new_exponents);
		return p;
	}
	
	public Polynomial multiply(double num) {
		Polynomial null_poly = new Polynomial();
		if (num == 0) {
			return null_poly;
		}
		Polynomial p = new Polynomial(this.coefficients, this.exponents);
		for (int i = 0; i < p.coefficients.length; i++) {
			p.coefficients[i] = p.coefficients[i] * num;
		}
		return p;
	}

	public Polynomial multiply(Polynomial poly_2) {
		Polynomial [] multiplied = new Polynomial[this.exponents.length];
		double [] c = {0};
		int [] e = {0};
		for (int l = 0; l < this.exponents.length; l++) {
			multiplied[l] = new Polynomial();
		}
		Polynomial p = new Polynomial();
		for (int i = 0; i < this.exponents.length; i++) {
			multiplied[i].coefficients = new double[poly_2.coefficients.length];
			multiplied[i].exponents = new int[poly_2.exponents.length];
			for (int k = 0; k < poly_2.exponents.length; k++) {
				multiplied[i].coefficients[k] = poly_2.coefficients[k];
				multiplied[i].exponents[k] = poly_2.exponents[k];
			}
			for (int j = 0; j < poly_2.exponents.length; j++) {
				multiplied[i].coefficients[j] = this.coefficients[i] * multiplied[i].coefficients[j];
				multiplied[i].exponents[j] = this.exponents[i] + multiplied[i].exponents[j];
			}
			if (p.coefficients == null && p.coefficients == null) {
				p.coefficients = new double[multiplied[i].coefficients.length];
				p.exponents = new int[multiplied[i].coefficients.length];
				for(int n = 0; n < multiplied[i].coefficients.length; n++) {
					p.coefficients[n] = multiplied[i].coefficients[n];
					p.exponents[n] = multiplied[i].exponents[n];
				}
			}
			else {
				p = p.add(multiplied[i]);
			}
		}
		return p;
	}

	public double evaluate(double x){
		double sum = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			sum += this.coefficients[i]*(Math.pow(x, this.exponents[i]));
		}
		return sum;
	}

	public boolean hasRoot(double x){
		double sum = this.evaluate(x);
		if (sum == 0.0){
			return true;
		}
		return false;
	}
	
	public void saveToFile(String file) throws Exception{
		//remember to check if they are same length or both null
		if (this.coefficients == null && this.exponents == null) {
			System.out.println("Polynomial is null");
		}
		else if (this.coefficients.length != this.exponents.length){
			System.out.println("Coefficients and exponents are not the same length");
		}
		else {
			String writeString = "";
			for (int i = 0; i < this.coefficients.length; i++) {
				writeString += coefficients[i];
				if (exponents[i] != 0) {
					writeString +="x" + exponents[i]; 
				}
				if (this.coefficients.length - i > 1) {
					if (coefficients[i+1] > 0) {
					writeString += "+";
					}
				}
			}
			if (writeString.endsWith("+")){
				writeString = writeString.substring(0, writeString.length()-1);
			}
			
			FileWriter myWriter = new FileWriter(new File(file));
			myWriter.write(writeString);
			myWriter.close();
		}
	}
}
