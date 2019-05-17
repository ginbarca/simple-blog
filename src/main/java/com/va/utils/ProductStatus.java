package com.va.utils;

public interface ProductStatus {
	public static final int IN_STORE = 0;// trong kho
	public static final int OUT_STORE = 1;// xuat kho
	public static final int SOLD = 2;// da ban

	public static final String HOT_PART = "N";// cuc nong
	public static final String COLD_PART = "L";// cuc lanh
}
