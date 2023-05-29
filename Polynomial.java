public class Polynomial {

	double [] polynomial;

	public Polynomial(double [] coefficients){
		polynomial = coefficients;
	}

	public Polynomial(){
		polynomial = new double[1];
		polynomial[0] = 0;
	}

	public Polynomial add(Polynomial poly_2){
		int new_length;
		if (this.polynomial.length > poly_2.polynomial.length){
			new_length = this.polynomial.length;
		}
		else{
			new_length = poly_2.polynomial.length;
		}
		double [] new_polynomial = new double[new_length];
		for (int i = 0; i < this.polynomial.length; i++){
			new_polynomial[i] += this.polynomial[i];
		}
		for (int j = 0; j < poly_2.polynomial.length; j++){
			new_polynomial[j] += poly_2.polynomial[j];
		}
		Polynomial p = new Polynomial(new_polynomial);
		return p;
	}

	public double evaluate(double x){
		double sum = this.polynomial[0];
		double x_to_the_power = 1;
		for (int i = 1; i < this.polynomial.length; i++){
			for (int j = 0; j < i; j++){
				x_to_the_power *= x;
			}
			sum += this.polynomial[i]*x_to_the_power;
			x_to_the_power = 1;
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
}