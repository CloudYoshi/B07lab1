import java.io.File;

public class Driver {
	public static void main(String [] args) {
		double [] pc = {0};
		int [] pe = {0};
		Polynomial p = new Polynomial(pc, pe);
		System.out.println(p.evaluate(3));
		double [] c1 = {6, 5};
		int [] e1 = {0, 3};
		Polynomial p1 = new Polynomial(c1, e1);
		double [] c2 = {-2, -9};
		int [] e2 = {1, 4};
		Polynomial p2 = new Polynomial(c2, e2);
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1)) {
		System.out.println("1 is a root of s");
		}
		else {
		System.out.println("1 is not a root of s");
		}
		Polynomial t = p1.multiply(p2);
		t.printPoly();
		
		double [] negative_c = {-1,-2,-3};
		double [] positive_c = {1, 2, 3};
		int [] exponents = {0, 1, 2};
		
		Polynomial negative_p = new Polynomial(negative_c, exponents);
		Polynomial positive_p = new Polynomial(positive_c, exponents);
		
		Polynomial null_test = negative_p.add(positive_p);
		if (null_test.coefficients == null &&  null_test.exponents == null) {
			System.out.println("Add is null when sum = 0. Passed test.");
		}
		
		File file = new File("load.txt");
		try {
			Polynomial filePoly = new Polynomial(file);
			filePoly.printPoly();
			System.out.println("Coefficients:");
			for (double coeff: filePoly.coefficients) {
				System.out.println(coeff);
			}
			System.out.println("Exponents:");
			for (double exponent: filePoly.exponents) {
				System.out.println(exponent);
			}
			filePoly.saveToFile("save.txt");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}