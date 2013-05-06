package com.github.bugra.MeasureRectangle;

import java.math.BigInteger;

public class Fraction {
	/*
	 * whole => integer
	 * numerator and denominator => not divided by the greatest common divisor
	 * num and den => divided by the greatest common divisor
	 */
	public int whole;
	public int numerator;
	public int denominator;
	public int num;
	public int den;
	
	public static int DEFAULT_DENOMINATOR = 65536;
	
	public Fraction(){
		this.whole = 0;
		this.numerator = 0;
		this.denominator = DEFAULT_DENOMINATOR;
		this.num = 0;
		this.den = 1;
	}
	
	public Fraction(int numerator, int denominator){
		this.numerator = numerator;
		this.denominator = denominator;
		this.num = numerator / getGcdNumbers(numerator, denominator);
		this.den = denominator / getGcdNumbers(numerator, denominator);
		this.whole = 0;
	}
	
	public Fraction(int whole, int numerator, int denominator){
		this.whole = whole;
		this.denominator = denominator;
		this.numerator = numerator;
		this.num = getNum();
		this.den = getDen(); 
	}
	
	// Getter Methods
	public int getWhole(){ return whole; }
	public int getNumerator(){ return numerator; }
	public int getDenominator(){ return denominator; }
	public int getNum(){ 
		if(this.denominator == DEFAULT_DENOMINATOR)
			return whole;
		else
			return  whole + (numerator / getGcdNumbers(numerator, denominator));
	}
	public int getDen(){
		if(this.denominator == DEFAULT_DENOMINATOR)
			return DEFAULT_DENOMINATOR;
		else
			return (denominator / getGcdNumbers(numerator, denominator));
	}		
	
	// Setter Methods
	public void setWhole(int w){ this.whole = w; }
	public void setNumerator(int n){ this.numerator = n; }
	public void setDenominator(int d){ this.denominator = d; }
	public void setNum(int n){  this.num = n; }
	public void setDen(int d){ this.den = d; }
	
	public double getDoubleValue(){
		return  (double) whole +  (numerator / (double) denominator);
	}
	
	public double getFractionPart(){
		if(this.denominator == DEFAULT_DENOMINATOR)
			return 0;
		else
			return numerator / (double) denominator;
	}
	
	// Greatest Common Divisor
	public static int getGcdNumbers(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }
	
	// Least Common Multiple
	public static int getLcmNumbers(int a, int b){
		return a * (b / getGcdNumbers(a, b));
	}
	
	public static void main(String[] args){
		Fraction a = new Fraction(0,2);
		System.out.println(getLcmNumbers(3, 2));
	}
}
