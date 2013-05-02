package com.github.bugra.MeasureRectangle;

import java.math.BigInteger;

public class Fraction {
	/*
	 * numerator and denominator => not divided by the greatest common divisor
	 * num and den => divided by the greatest common divisor
	 */
	public int numerator;
	public int denominator;
	public int num;
	public int den;
	
	public Fraction(){
		this.numerator = 0;
		this.denominator = 0;
		this.num = 0;
		this.den = 1;
	}
	
	public Fraction(int numerator, int denominator){
		this.numerator = numerator;
		this.denominator = denominator;
		this.num = numerator / getGcdNumbers(numerator, denominator);
		this.den = denominator / getGcdNumbers(numerator, denominator);
	}
	
	public void setSumWithInteger(int number){
		this.numerator += ( denominator * number );
		this.num = this.numerator / getGcdNumbers(this.numerator, this.denominator);
		this.den = this.denominator / getGcdNumbers(this.numerator, this.denominator);
	}
	
	// Getter Methods
	public int getNumerator(){ return numerator; }
	public int getDenominator(){ return denominator; }
	public int getNum(){ return num; }
	public int getDen(){ return den; }
	
	// Setter Methods
	public void setNumerator(int n){ this.numerator = n; }
	public void setDenominator(int d){ this.denominator = d; }
	public void setNum(int n){  this.num = n; }
	public void setDen(int d){ this.den = d; }
	
	// Greatest Common Divisor
	public static int getGcdNumbers(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }
	
	// Least Common Multiple
	public static int getLcmNumbers(int a, int b){
		return a * (b / getGcdNumbers(a, b));
	}
}
