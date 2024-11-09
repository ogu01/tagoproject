package com.Tago.Board;

public class ddddd {

	public static void main(String[] args) {
		int[] number = {1,2,3,4,5,6,7,8,9,10};
		int[] num = {2,4};
		
		for(int i : number) {
			boolean check = false;
			for(int j : num) {
				if( i == j) {
					check = true;
				}
			}
			
			if(check) {
				System.out.println("["+i+"]");
			} else {
				System.out.println(i);
			}
		}
	}

}
